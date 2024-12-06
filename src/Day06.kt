fun main() {
    fun startPos(input: List<CharArray>): Pair<Int, Int> {
        val cols = input.size
        val rows = input[0].size
        for (i in 0 until cols) {
            for (j in 0 until rows) {
                if (input[i][j] == '^') {
                    return j to i
                }
            }
        }
        return 0 to 0
    }

    fun walk(input: List<CharArray>, p2: Boolean): Int {
        val cols = input.size
        val rows = input[0].size
        var (x, y) = startPos(input)
        var dir = 0 // 0 - up, 1 - right, 2 - down, 3 - left
        val visited = mutableSetOf(Triple(x, y, 0))
        while (true) {
            val nx = x + if (dir == 1) 1 else if (dir == 3) -1 else 0
            val ny = y + if (dir == 0) -1 else if (dir == 2) 1 else 0
            if (nx < 0 || nx >= cols) break
            if (ny < 0 || ny >= rows) break
            if (input[ny][nx] == '#') {
                dir = (dir + 1) % 4
                continue
            }
            val cur = Triple(nx, ny, if (p2) dir else 0)
            if (p2 && visited.contains(cur)) {
                return -1
            }
            visited += cur
            x = nx
            y = ny
        }
        return visited.size
    }

    fun part1(input: List<CharArray>): Int {
        return walk(input, false)
    }

    fun part2(inputList: List<CharArray>): Int {
        val input = inputList.toMutableList()
        val cols = input.size
        val rows = input[0].size
        val (sx, sy) = startPos(input)
        var count = 0
        for (x in 0 until rows) {
            for (y in 0 until cols) {
                if (input[y][x] == '#') continue
                if (x == sx && y == sy) continue
                input[y][x] = '#'
                if (walk(input, true) == -1) {
                    count++
                }
                input[y][x] = '.'
            }
        }
        return count
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day").map { it.toCharArray() }
    part1(input).println()
    part2(input).println()
}
