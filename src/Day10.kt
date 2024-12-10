fun main() {
    fun walkMap(input: List<String>, factory: () -> MutableCollection<Pair<Int, Int>>): Int {
        val map = input.map { it.toCharArray().map { it - '0' } }
        val rows = map.size
        val cols = map[0].size

        fun isInBound(x: Int, y: Int) = x in 0..<cols && y in 0..<rows

        val directions = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)

        fun walk(x: Int, y: Int, search: Int, visited: MutableCollection<Pair<Int, Int>>, end: Int = 9) {
            directions.forEach { (dx, dy) ->
                val xx = x + dx
                val yy = y + dy
                if (isInBound(xx, yy) && map[yy][xx] == search) {
                    if (search == end) {
                        visited.add(xx to yy)
                    } else {
                        walk(xx, yy, search + 1, visited, end)
                    }
                }
            }
        }

        return (0 until rows).sumOf { y ->
            (0 until cols).sumOf { x ->
                if (map[y][x] == 0) factory().also { walk(x, y, 1, it) }.size else 0
            }
        }
    }

    fun part1(input: List<String>): Int {
        return walkMap(input) { mutableSetOf() }
    }

    fun part2(input: List<String>): Int {
        return walkMap(input) { mutableListOf() }
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
