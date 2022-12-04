package day04

import java.io.File

fun main() {
    val lines = File("/Users/ilker.uzun/git/advent/advent-2022/src/main/kotlin/day04/input.txt")
        .readLines()

    println(part1(lines))
    println(part2(lines))
}

fun part1(lines: List<String>) = pairs(lines)
    .count { pair -> pair.first.all { pair.second.contains(it) } || pair.second.all { pair.first.contains(it) } }

fun part2(lines: List<String>) = pairs(lines)
    .count { pair -> pair.first.intersect(pair.second).isNotEmpty() }

private fun pairs(lines: List<String>) = lines
    .map { it.split(',') }
    .map { Pair(it[0].split('-'), it[1].split('-')) }
    .map { Pair(it.first[0].toInt()..it.first[1].toInt(), it.second[0].toInt()..it.second[1].toInt()) }
