import kotlin.math.abs

fun main() {
    fun part1(input: List<String>) = input.flatMap { it.split("\\s+".toRegex()) }
            .map { it.toInt() }
            .withIndex()
            .partition { it.index % 2 == 0 }.let {
                it.first.map { it.value }.sorted().zip(it.second.map { it.value }.sorted())
            }.sumOf { abs(it.first - it.second) }

    fun part2(input: List<String>) = input.flatMap { it.split("\\s+".toRegex()) }
            .map { it.toInt() }
            .withIndex()
            .partition { it.index % 2 == 0 }.let {
                val f = it.first.map { it.value }
                val s = it.second.map { it.value }.groupingBy { it }.eachCount()
                f.sumOf { it * s.getOrDefault(it, 0) }
            }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
