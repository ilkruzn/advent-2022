package day01

import java.io.File

fun main() {
    val input = File("/Users/ilker.uzun/git/advent/advent-2022/src/main/kotlin/day01/input.txt")
        .readText()

    println(part1(input))
    println(part2(input))
}

fun part1(input: String): Int = caloriesPerElf(input).maxOf { it }

fun part2(input: String) = caloriesPerElf(input).sortedDescending().take(3).sum()

private fun caloriesPerElf(input: String) = input
    .split("\n\n")
    .map { it.split("\n").filter { line -> line.isNotEmpty() }.sumOf { cal -> cal.toInt() } }
