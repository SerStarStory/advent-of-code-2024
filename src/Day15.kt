fun List<String>.parse() =
    partition { it.startsWith("#") }.let { (first, second) -> first.map(String::toCharArray) to second.joinToString("") }

val List<CharArray>.start: Pair<Int, Int>
    get() {
        for (i in indices) {
            for (j in first().indices) {
                if (this[i][j] == '@') {
                    return i to j
                }
            }
        }
        throw RuntimeException("Should not reach here")
    }

val List<CharArray>.hash: Int
    get() {
        var result = 0
        for (i in indices) {
            for (j in first().indices) {
                if (this[i][j] == 'O' || this[i][j] == '[') {
                    result += i * 100 + j
                }
            }
        }
        return result
    }

fun part1(grid: List<CharArray>, commands: String): Int {
    fun List<CharArray>.shift(startI: Int, startJ: Int, di: Int, dj: Int): Pair<Int, Int> {
        var i = startI + di
        var j = startJ + dj
        while (this[i][j] == 'O') {
            i += di
            j += dj
        }
        if (this[i][j] == '.') {
            this[i][j] = 'O'
            this[startI + di][startJ + dj] = '@'
            this[startI][startJ] = '.'
            return startI + di to startJ + dj
        }
        return startI to startJ
    }

    var (i, j) = grid.start
    for (command in commands) {
        val (nextI, nextJ) = when (command) {
            '<' -> grid.shift(i, j, 0, -1)
            '>' -> grid.shift(i, j, 0, 1)
            '^' -> grid.shift(i, j, -1, 0)
            'v' -> grid.shift(i, j, 1, 0)
            else -> throw RuntimeException("Should not reach here")
        }
        i = nextI; j = nextJ
    }

    return grid.hash
}

val Char.reversed: Char
    get() = when (this) {
        '[' -> ']'
        ']' -> '['
        else -> throw RuntimeException("Should not reach here")
    }

fun part2(grid: List<CharArray>, commands: String): Int {
    fun List<CharArray>.shiftHorizontal(startI: Int, startJ: Int, dj: Int): Pair<Int, Int> {
        var j = startJ + dj
        while (this[startI][j] == '[' || this[startI][j] == ']') j += dj
        if (this[startI][j] == '.') {
            this[startI][j] = this[startI][startJ + dj]
            this[startI][startJ + dj] = '@'
            this[startI][startJ] = '.'
            for (k in if (dj > 0) (startJ + 2 * dj)..j else (startJ + 2 * dj) downTo j) {
                this[startI][k] = this[startI][k].reversed
            }
            return startI to startJ + dj
        }
        return startI to startJ
    }

    val cache = HashMap<Pair<Int, Int>, Boolean>()

    fun List<CharArray>.isMovable(i: Int, j: Int, di: Int): Boolean {
        return cache.getOrPut(i to j) {
            when (this[i][j]) {
                '@' -> when (this[i + di][j]) {
                    '.' -> true
                    '[' -> isMovable(i + di, j, di)
                    ']' -> isMovable(i + di, j - 1, di)
                    else -> false
                }
                '[' -> {
                    val left = when (this[i + di][j]) {
                        ']' -> isMovable(i + di, j - 1, di)
                        '[' -> isMovable(i + di, j, di)
                        '.' -> true
                        else -> false
                    }
                    val right = when (this[i + di][j + 1]) {
                        '[' -> isMovable(i + di, j + 1, di)
                        ']', '.' -> true
                        else -> false
                    }
                    left && right
                }
                else -> throw RuntimeException("Should not reach here")
            }
        }
    }

    fun List<CharArray>.moveOne(i: Int, j: Int, di: Int) {
        when (this[i][j]) {
            '@' -> {
                this[i + di][j] = '@'
                this[i][j] = '.'
            }
            '[' -> {
                this[i + di][j] = '['
                this[i + di][j + 1] = ']'
                this[i][j] = '.'
                this[i][j + 1] = '.'
            }
            else -> throw RuntimeException("Should not reach here")
        }
    }

    fun List<CharArray>.shiftVertical(startI: Int, startJ: Int, di: Int): Pair<Int, Int> {
        cache.clear()
        if (isMovable(startI, startJ, di)) {
            val sortedPoints = cache.keys.sortedBy { it.first }.toTypedArray()
            if (di > 0) sortedPoints.reverse()
            for ((i, j) in sortedPoints) {
                moveOne(i, j, di)
            }
            return startI + di to startJ
        }
        return startI to startJ
    }

    var (i, j) = grid.start
    for (command in commands) {
        val (nextI, nextJ) = when (command) {
            '<' -> grid.shiftHorizontal(i, j, -1)
            '>' -> grid.shiftHorizontal(i, j, 1)
            '^' -> grid.shiftVertical(i, j, -1)
            'v' -> grid.shiftVertical(i, j, 1)
            else -> throw RuntimeException("Should not reach here")
        }
        i = nextI; j = nextJ
    }

    return grid.hash
}

fun List<CharArray>.toPart2() =
    this.map {
        it.flatMap { c ->
            when (c) {
                '#' -> "##"
                'O' -> "[]"
                '.' -> ".."
                '@' -> "@."
                else -> throw RuntimeException("Should not reach here")
            }.toList()
        }.toCharArray()
    }

fun main() {
    val (grid, commands) = readInput("Day").parse()
    val part2Grid = grid.toPart2()
    part1(grid, commands).println()
    part2(part2Grid, commands).println()
}