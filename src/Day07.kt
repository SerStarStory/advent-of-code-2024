fun main() {
    val gens = mutableListOf(listOf(""))

    fun parseInput(input: List<String>): List<Pair<Long, List<Long>>> {
        val list = mutableListOf<Pair<Long, List<Long>>>()
        input.forEach {
            val i = it.split(": ")
            val f = i[0].toLong()
            val s = mutableListOf<Long>()
            i[1].split(" ").forEach { j -> s.add(j.toLong()) }
            list += f to s
        }
        return list
    }

    fun generateSymbols(n: Int, symbols: List<String>): List<String> {
        if (n <= 0) return gens[0]
        if (gens.size > n) return gens[n]
        return generateSymbols(n - 1, symbols).flatMap { s ->
            symbols.map { s + it }
        }.apply { gens += this }
    }

    fun eval(num: List<Long>, operations: String): Long {
        var n = num[0]
        repeat(operations.length) {
            when (operations[it]) {
                '+' -> n += num[it + 1]
                '*' -> n *= num[it + 1]
                '|' -> n = (n.toString() + num[it + 1]).toLong()
            }
        }
        return n
    }

    fun isValid(p: Pair<Long, List<Long>>, symbols: List<String>): Boolean {
        val ops = generateSymbols(p.second.size - 1, symbols)
        val f = p.first
        val s = p.second
        return ops.any { eval(s, it) == f }
    }

    fun part1(input: List<String>): Long {
        return parseInput(input).sumOf {
            if (isValid(it, listOf("*", "+"))) {
                it.first
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Long {
        gens.clear()
        gens += listOf("")
        return parseInput(input).sumOf {
            if (isValid(it, listOf("*", "+", "|"))) {
                it.first
            } else {
                0
            }
        }
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")
    part1(input).println()
    part2(input).println()
}
