fun main() {
    fun part1(input: List<String>): Int {
        val word = "XMAS"
        val rows = input.size
        val cols = input[0].length
        val directions = listOf(
            0 to 1, 0 to -1,
            1 to 0, -1 to 0,
            1 to 1, -1 to -1,
            1 to -1, -1 to 1
        )

        fun searchFrom(x: Int, y: Int, dx: Int, dy: Int): Boolean {
            for (i in word.indices) {
                val nx = x + i * dx
                val ny = y + i * dy
                if (nx !in 0 until rows || ny !in 0 until cols) return false
                if (input[nx][ny] != word[i]) return false
            }
            return true
        }

        return (0 until rows).sumOf { x ->
            (0 until cols).sumOf { y ->
                directions.count { (dx, dy) -> searchFrom(x, y, dx, dy) }
            }
        }
    }

    fun part2(input: List<String>): Int {
        fun matches(str: String) = str == "MAS" || str == "SAM"
        fun matches(x: Int, y: Int): Boolean {
            if (input[x][y] != 'A') return false
            val a = "" + input[x - 1][y - 1] + input[x][y] + input[x + 1][y + 1]
            val b = "" + input[x + 1][y - 1] + input[x][y] + input[x - 1][y + 1]
            return matches(a) && matches(b)
        }
        return (1 until input.size - 1).sumOf { x ->
            (1 until input[0].length - 1).count { y ->
                matches(x, y)
            }
        }
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
