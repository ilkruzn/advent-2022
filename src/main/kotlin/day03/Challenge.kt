package day03

import java.io.File

fun main() {
    val lines = File("/Users/ilker.uzun/git/advent/advent-2022/src/main/kotlin/day03/input.txt")
        .readLines()

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>) = lines
    .map {
        Pair(
            it.subSequence(0 until it.length / 2).toSet(),
            it.subSequence(it.length / 2 until it.length).toSet()
        )
    }
    .flatMap { pair -> pair.first.filter { pair.second.contains(it) } }
    .sumOf { if (it >= 'a') it - 'a' + 1 else it - 'A' + 1 + 26 }


private fun part2(lines: List<String>) = (lines.indices step 3)
    .asSequence()
    .flatMap { i ->
        lines[i].asSequence().toSet()
            .filter { lines[i + 1].contains(it) }
            .filter { lines[i + 2].contains(it) }
    }
    .sumOf { if (it >= 'a') it - 'a' + 1 else it - 'A' + 1 + 26 }

