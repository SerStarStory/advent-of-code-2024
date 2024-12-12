fun main() {
    fun part1(input: List<String>): Int {
        val rows = input.size
        val cols = input[0].length
        val dirs = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
        val visited = Array(rows) { BooleanArray(cols) }
        fun isInBound(x: Int, y: Int) = x in 0 until cols && y in 0 until rows

        fun visitArea(sx: Int, sy: Int, letter: Char): Pair<Int, Int> {
            val stack = mutableListOf(sx to sy)
            visited[sy][sx] = true
            var perimeter = 0
            var area = 0

            while (stack.isNotEmpty()) {
                val (x, y) = stack.removeLast()
                area++
                for ((dx, dy) in dirs) {
                    val nx = x + dx
                    val ny = y + dy
                    if (!isInBound(nx, ny) || input.getOrNull(ny)?.getOrNull(nx) != letter) {
                        perimeter++
                    } else if (!visited[ny][nx]) {
                        visited[ny][nx] = true
                        stack.add(nx to ny)
                    }
                }
            }
            return perimeter to area
        }

        return (0 until rows).sumOf { y ->
            (0 until cols).sumOf { x ->
                if (!visited[y][x]) {
                    val (p, a) = visitArea(x, y, input[y][x])
                    p * a
                } else 0
            }
        }
    }

    fun part2(input: List<String>): Int {
        val rows = input.size
        val cols = input[0].length
        val visited = Array(rows) { BooleanArray(cols) }
        var price = 0
        val directions = listOf(Triple(-1, 0, 0), Triple(0, 1, 1), Triple(1, 0, 2), Triple(0, -1, 3))

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (!visited[i][j]) {
                    val plantType = input[i][j]
                    val stack = mutableListOf(Pair(i, j))
                    visited[i][j] = true
                    var area = 0
                    val perimeterEdges = mutableListOf<Triple<Int, Int, Int>>()

                    while (stack.isNotEmpty()) {
                        val (x, y) = stack.removeAt(stack.size - 1)
                        area++
                        directions.forEach { (dx, dy, dir) ->
                            val nx = x + dx
                            val ny = y + dy
                            if (nx in 0 until rows && ny in 0 until cols) {
                                if (input[nx][ny] == plantType && !visited[nx][ny]) {
                                    visited[nx][ny] = true
                                    stack.add(Pair(nx, ny))
                                } else if (input[nx][ny] != plantType || !visited[nx][ny]) {
                                    perimeterEdges.add(Triple(x, y, dir))
                                }
                            } else perimeterEdges.add(Triple(x, y, dir))
                        }
                    }

                    val edgesByDirection = MutableList(4) { mutableMapOf<Int, MutableSet<Int>>() }
                    perimeterEdges.forEach { (x, y, dir) ->
                        edgesByDirection[dir].getOrPut(if (dir in listOf(0, 2)) x else y) { mutableSetOf() }
                            .add(if (dir in listOf(0, 2)) y else x)
                    }

                    price += area * edgesByDirection.sumOf { dir ->
                        dir.values.sumOf { positions ->
                            positions.sorted().fold(0 to -2) { (count, prev), pos ->
                                val newCount = count + if (pos != prev + 1) 1 else 0
                                newCount to pos
                            }.first
                        }
                    }
                }
            }
        }
        return price
    }
    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
