fun main() {
    fun decodeInput(input: List<String>): Pair<Map<Int, List<Int>>, List<List<Int>>> {
        val rules = input.takeWhile { it != "" }
            .map { it.split("|") }
            .map { it[0].toInt() to it[1].toInt() }
            .groupBy { it.first }
            .mapValues { it.value.map { i -> i.second } }
        val lines = input.takeLastWhile { it != "" }
            .map { it.split(",").map { i -> i.toInt() } }
        return rules to lines
    }

    fun part1(input: List<String>): Int {
        val (rules, lines) = decodeInput(input)
        var sum = 0
        outer@for (line in lines) {
            for (i in 1 until line.size) {
                val rule = rules[line[i]] ?: continue
                for (j in 0 until i) {
                    if (rule.contains(line[j])) continue@outer
                }
            }
            sum += line[line.size / 2]
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val (rules, lines) = decodeInput(input)
        val correctLines = mutableListOf<List<Int>>()
        outer@for (line in lines) {
            for (i in 1 until line.size) {
                val rule = rules[line[i]] ?: continue
                for (j in 0 until i) {
                    if (rule.contains(line[j])) continue@outer
                }
            }
            correctLines += line
        }
        return (lines - correctLines.toSet()).map {
            it.sortedWith() { x, y ->
                if (x == y) return@sortedWith 0
                return@sortedWith if (rules[x]?.contains(y) == true) 1 else -1
            }
        }.sumOf { it[it.size / 2] }
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
