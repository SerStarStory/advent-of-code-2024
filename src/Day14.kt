import java.io.FileWriter

fun main() {
    fun parseInput(input: List<String>, rows: Int, cols: Int): Array<Array<MutableList<Pair<Int, Int>>>> {
        val array = Array(rows) { Array(cols) { mutableListOf<Pair<Int, Int>>() } }
        val regex = """^p=(\d+),(\d+) v=(-?\d+),(-?\d+)$""".toRegex()
        input.mapNotNull { regex.find(it)?.destructured }
            .forEach {
                val (x, y, vx, vy) = it
                array[y.toInt()][x.toInt()].add(vx.toInt() to vy.toInt())
            }
        return array
    }

    fun part1(input: List<String>, rows: Int, cols: Int): Int {
        val array = parseInput(input, rows, cols)
        val newArray = Array(rows) { IntArray(cols) {0} }
        repeat(rows) { y ->
            repeat(cols) { x ->
                array[y][x].forEach {
                    val (vx, vy) = it
                    val nx = (x + 100 * vx).mod(cols)
                    val ny = (y + 100 * vy).mod(rows)
                    newArray[ny][nx]++
                }
            }
        }
        val q1 = newArray.take(rows / 2).flatMap { it.take(cols / 2) }.sum()
        val q2 = newArray.take(rows / 2).flatMap { it.takeLast(cols / 2) }.sum()
        val q3 = newArray.takeLast(rows / 2).flatMap { it.take(cols / 2) }.sum()
        val q4 = newArray.takeLast(rows / 2).flatMap { it.takeLast(cols / 2) }.sum()
        return q1 * q2 * q3 * q4
    }

    fun part2(input: List<String>, rows: Int, cols: Int): Int {
        val array = parseInput(input, rows, cols)
        val newArray = Array(rows) { Array(cols) { mutableListOf<Pair<Int, Int>>() } }
        val writer = FileWriter("out.txt")
        repeat(100000) { s ->
            repeat(rows) { y ->
                repeat(cols) { x ->
                    array[y][x].forEach {
                        val (vx, vy) = it
                        val nx = (x + s * vx).mod(cols)
                        val ny = (y + s * vy).mod(rows)
                        newArray[ny][nx].add(it)
                    }
                }
            }
            writer.write("For second = $s:\n")
            repeat(rows) { y ->
                repeat(cols) { x ->
                    val size = newArray[y][x].size
                    writer.append(if (size == 0) ' ' else '@')
                    newArray[y][x].clear()
                }
                writer.write("\n")
            }
        }
        writer.flush()
        return 0
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input, 103, 101).println()
    part2(input, 103, 101).println()
}
