import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.split("\\s+".toRegex()).map { it.toInt() } }
            .filter { it.zipWithNext().all { (a, b) -> abs(a - b) in 1..3 } }
            .count { it.zipWithNext().all { (a, b) -> a < b } || it.zipWithNext().all { (a, b) -> a > b } }
    }

    fun isSafe(list: List<Int>): Boolean {
        return list.zipWithNext().all { (a, b) -> abs(a - b) in 1..3 } &&
                (list.zipWithNext().all { (a, b) -> a < b } || list.zipWithNext().all { (a, b) -> a > b })
    }

    fun isSafeWithOneRemove(list: List<Int>): Boolean = isSafe(list) || list.indices.any {
        isSafe(list.filterIndexed { index, _ -> it != index })
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split("\\s+".toRegex()).map { it.toInt() } }
            .count { isSafeWithOneRemove(it) }
    }

    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
