package day02

import java.io.File

fun main() {
    // A, X: Rock
    // B, Y: Paper
    // C, Z: Scissors

    val moveMap = mapOf(Pair('A', 'X'), Pair('B', 'Y'), Pair('C', 'Z'))
    val scoreMap = mapOf(Pair('X', 1), Pair('Y', 2), Pair('Z', 3))

    val loseMap = mapOf(Pair('A', 'Z'), Pair('B', 'X'), Pair('C', 'Y'))
    val drawMap = mapOf(Pair('A', 'X'), Pair('B', 'Y'), Pair('C', 'Z'))
    val winMap = mapOf(Pair('A', 'Y'), Pair('B', 'Z'), Pair('C', 'X'))

    val result = File("/Users/ilker.uzun/git/advent/advent-2022/src/main/kotlin/day02/input.txt")
        .readLines()
        .filter { it.isNotEmpty() }
        .map {
            val first = it[0]
            when (it[2]) {
                'X' -> "$first ${loseMap[first]}"
                'Y' -> "$first ${drawMap[first]}"
                'Z' -> "$first ${winMap[first]}"
                else -> {
                    ""
                }
            }
        }
        .filter { it.isNotEmpty() }
        .sumOf {
            val self = it[2]
            val opponent = moveMap[it[0]]!!

            val diff = self - opponent
            val draw = diff == 0
            val win = diff == 1 || diff == -2

            var score = if (draw) 3 else (if (win) 6 else 0)
            score += scoreMap[self]!!
            score
        }

    println("Result is $result")
}
