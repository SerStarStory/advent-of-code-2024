fun main() {
    val regex = """mul\((\d+),(\d+)\)""".toRegex()
    fun part1(input: List<String>): Int {
        return input.flatMap { regex.findAll(it) }
            .sumOf { it.destructured.toList().map { it.toInt() }.reduce(Int::times) }
    }

    fun part2(input: List<String>): Int {
        return input.joinToString("").replace("""don't\(\).*?do\(\)""".toRegex(), "")
            .let { s -> regex.findAll(s) }
            .sumOf { it.destructured.toList().map { it.toInt() }.reduce(Int::times) }
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
