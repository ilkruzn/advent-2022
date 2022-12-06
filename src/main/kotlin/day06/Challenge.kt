package day06

import java.io.File

fun main() {
    val input = File("/Users/ilker.uzun/git/advent/advent-2022/src/main/kotlin/day06/input.txt")
        .readText()

    println(detect(input, 4))
    println(detect(input, 14))
}

fun detect(input: String, matchLength: Int): Int {
    return (0..input.length - matchLength)
        .asSequence()
        .map { input.subSequence(it, it + matchLength) }
        .map { it.toSet() }
        .indexOfFirst { it.size == matchLength }
        .plus(matchLength)
}
