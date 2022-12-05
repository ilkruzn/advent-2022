package day05

import java.io.File

fun main() {
    val input = File("/Users/ilker.uzun/git/advent/advent-2022/src/main/kotlin/day05/input.txt")
        .readText()
    val allLines = input.split("\n\n")
    val arrangementLines = allLines[0].split("\n")
    val moveLines = allLines[1].split("\n")

    // part 1
    solve(arrangementLines, moveLines)

    println()

    // part 2
    solve(arrangementLines, moveLines, true)

}

private fun solve(
    arrangementLines: List<String>,
    moveLines: List<String>,
    bulkMove: Boolean = false
) {
    val stacks = buildInitialState(arrangementLines)
    executeMoves(stacks, moveLines, bulkMove)
    stacks.forEach { print(it.first()) }
}

fun buildInitialState(arrangementLines: List<String>): List<ArrayDeque<Char>> {
    val stackCount = 9
    val result = List(stackCount) { ArrayDeque<Char>() }

    arrangementLines
        .take(arrangementLines.size - 1)
        .forEach {
            for (i in 0..it.length step 4) {
                val ch = it[i + 1]
                if (ch != ' ') {
                    result[i / 4].add(ch)
                }
            }
        }

    return result
}

fun executeMoves(stacks: List<ArrayDeque<Char>>, moveLines: List<String>, bulkMove: Boolean = false) {
    val regex = Regex("""move (?<count>\d+) from (?<from>\d+) to (?<to>\d+)""")
    moveLines
        .mapNotNull { regex.find(it) }
        .map { it.groups }
        .forEach {
            val from = stacks[it["from"]?.value?.toInt()?.minus(1)!!]
            val to = stacks[it["to"]?.value?.toInt()?.minus(1)!!]
            val count = it["count"]?.value?.toInt()!!
            val temp = ArrayDeque<Char>()
            repeat(count) { temp.add(from.removeFirst()) }
            repeat(count) { to.addFirst(if (bulkMove) temp.removeLast() else temp.removeFirst()) }
        }
}
