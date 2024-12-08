fun main() {
    fun parseInput(input: List<String>): Map<Char, MutableList<Pair<Int, Int>>> {
        val map = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        val cols = input.size
        val rows = input[0].length
        repeat(cols) { y ->
            repeat(rows) inner@{ x ->
                val c = input[y][x]
                if (c == '.') return@inner
                map.getOrPut(c) { mutableListOf() }.add(x to y)
            }
        }
        return map
    }

    fun part1(input: List<String>): Int {
        val cols = input.size
        val rows = input[0].length
        val map = parseInput(input)
        val antinodes = mutableSetOf<Pair<Int, Int>>()
        map.forEach { (_, pos) ->
            for (i in 0..<pos.size) {
                val f1 = pos[i]
                for (j in (i + 1)..<pos.size) {
                    val f2 = pos[j]
                    val x1 = 2 * f2.first - f1.first
                    val y1 = 2 * f2.second - f1.second
                    val x2 = 2 * f1.first - f2.first
                    val y2 = 2 * f1.second - f2.second

                    if (x1 in 0..<rows && y1 in 0..<cols) {
                        antinodes.add(x1 to y1)
                    }
                    if (x2 in 0..<rows && y2 in 0..<cols) {
                        antinodes.add(x2 to y2)
                    }
                }
            }
        }
        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val cols = input.size
        val rows = input[0].length
        val map = parseInput(input)
        val antinodes = mutableSetOf<Pair<Int, Int>>()
        map.forEach { (_, pos) ->
            for (i in 0..<pos.size) {
                val f1 = pos[i]
                for (j in (i + 1)..<pos.size) {
                    val f2 = pos[j]
                    for (k in 1..rows) {
                        val x1 = k * f2.first - (k - 1) * f1.first
                        val y1 = k * f2.second - (k - 1) * f1.second
                        val x2 = k * f1.first - (k - 1) * f2.first
                        val y2 = k * f1.second - (k - 1) * f2.second

                        if (x1 in 0..<rows && y1 in 0..<cols) {
                            antinodes.add(x1 to y1)
                        }
                        if (x2 in 0..<rows && y2 in 0..<cols) {
                            antinodes.add(x2 to y2)
                        }
                    }
                }
            }
        }
        return antinodes.size
    }

    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
