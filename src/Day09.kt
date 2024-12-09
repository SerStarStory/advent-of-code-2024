fun main() {
    fun buildFileMap(input: String): Pair<Int, MutableList<Int>> {
        var fileId = 0
        val fileMap = mutableListOf<Int>()
        var isBlock = false
        input.forEach { c ->
            isBlock = !isBlock
            fileMap.addAll(generateSequence { if (isBlock) fileId else -1 }.take(c - '0').toList())
            if (isBlock) fileId++
        }
        return (fileId - 1) to fileMap
    }

    fun part1(input: String): Long {
        val fileMap = buildFileMap(input).second
        var lastPos = fileMap.lastIndex
        return fileMap.foldIndexed(0) { i, acc, c ->
            if (i > lastPos) acc else acc + i * (if (c == -1) {
                while (fileMap[lastPos] == -1) lastPos--
                fileMap[lastPos--]
            } else c)
        }
    }

    fun part2(input: String): Long {
        val (maxFileId, fileMap) = buildFileMap(input)
        val fileIdToBlockSize = fileMap.groupingBy { it }.eachCount().toMutableMap()
        fileIdToBlockSize -= -1
        fileIdToBlockSize -= 0
        fun tryFindLocationFor(blocks: Int): Int {
            for (i in fileMap.indices) {
                if (fileMap[i] != -1) continue
                var c = 0
                for (j in i..<fileMap.size) {
                    if (fileMap[j] != -1) break
                    if (++c >= blocks) return i
                }
            }
            return -1
        }
        var doWork = true
        while (doWork) {
            doWork = false
            for (i in maxFileId downTo 1) {
                val bs = fileIdToBlockSize[i] ?: continue
                val index = tryFindLocationFor(bs)
                fileIdToBlockSize -= i
                if (index == -1) continue
                if (fileMap.indexOf(i) < index) continue
                doWork = true
                val index1 = fileMap.indexOf(i)
                repeat(bs) {
                    fileMap[index + it] = i
                    fileMap[index1 + it] = -1
                }
                break
            }
        }
        return fileMap.foldIndexed(0) { i, acc, c ->
            if (c == -1) acc else acc + i * c
        }
    }

    // Read the input from the `src/Day.txt` file.
    val input = readInput("Day")[0]
    part1(input).println()
    part2(input).println()
}
