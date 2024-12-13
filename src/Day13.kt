import kotlin.math.floor

fun main() {
    fun parseInput(input: List<String>): MutableList<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>> {
        val btn = """X\+(\d+), Y\+(\d+)""".toRegex()
        val pos = """X=(\d+), Y=(\d+)""".toRegex()
        return MutableList((input.size + 1) / 4) {
            val (f1, f2) = btn.find(input[it * 4])!!.destructured
            val (s1, s2) = btn.find(input[it * 4 + 1])!!.destructured
            val (t1, t2) = pos.find(input[it * 4 + 2])!!.destructured
            Triple(f1.toLong() to f2.toLong(), s1.toLong() to s2.toLong(), t1.toLong() to t2.toLong())
        }
    }

    fun solve(list: List<Triple<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>>, isValid: (Double, Double) -> Boolean): Long {
        return list.sumOf { (f, s, t) ->
            val y = (f.first * t.second - f.second * t.first) / (f.first * s.second - f.second * s.first).toDouble()
            val x = (t.first - s.first * y) / f.first
            if (y % 1 == 0.0 && x % 1 == 0.0 && isValid(x, y)) 3 * x.toLong() + y.toLong() else 0L
        }
    }

    fun part1(input: List<String>): Long {
        return solve(parseInput(input)) { x, y -> x <= 100 && y <= 100 }
    }

    fun part2(input: List<String>): Long {
        return solve(parseInput(input).map {
            Triple(it.first, it.second, (it.third.first + 10000000000000) to (it.third.second + 10000000000000))
        }) { _, _ -> true }
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
