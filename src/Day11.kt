fun main() {
    fun execute(input: List<String>, repeats: Int): Long {
        var list = input[0].split(" ").map { it.toLong() }.groupingBy { it }
            .foldTo(mutableMapOf(), 0L) { acc, _ -> acc + 1 }
        repeat(repeats) {
            val next = mutableMapOf<Long, Long>()
            list.forEach { (k, v) ->
                if (k == 0L) {
                    next.compute(1L) { _, n -> (n ?: 0) + v }
                    return@forEach
                }
                val s = k.toString()
                if (s.length % 2 == 0) {
                    next.compute(s.substring(0, s.length / 2).toLong()) { _, n -> (n ?: 0) + v }
                    next.compute(s.substring(s.length / 2).toLong()) { _, n -> (n ?: 0) + v }
                } else {
                    next.compute(k * 2024) { _, n -> (n ?: 0) + v }
                }
            }
            list = next
        }
        return list.values.sum()
    }

    fun part1(input: List<String>): Long {
        return execute(input, 25)
    }

    fun part2(input: List<String>): Long {
        return execute(input, 75)
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
