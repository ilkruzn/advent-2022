package day08

import java.io.File

class Tree(
    val height: Int,
    var maxLeft: Int,
    var maxRight: Int,
    var maxUp: Int,
    var maxDown: Int,
    var scenicScore: Int = 1
) {
    val isVisible: Boolean
        get() = height > maxLeft || height > maxRight || height > maxUp || height > maxDown
}

fun main() {
    val trees = File("/Users/ilker.uzun/git/advent/advent-2022/src/main/kotlin/day08/input.txt")
        .readLines()
        .map { it.toCharArray().map { ch -> ch.digitToInt() } }
        .map { row -> row.map { Tree(it, -1, -1, -1, -1) } }

    solve(trees)

}

fun solve(trees: List<List<Tree>>) {
    // visit rows
    trees.indices.forEach { i ->
        val row = trees[i]

        updateMaximums(row, 0, mutableListOf(-1, -1)) { tree, left, right ->
            tree.maxLeft = left
            tree.maxRight = right
        }

        scenicScore(row) { tree, score1, score2 -> tree.scenicScore *= score1 * score2 }

    }

    // visit columns
    trees[0].indices.forEach { i ->
        val column = trees.map { row -> row[i] }

        updateMaximums(column, 0, mutableListOf(-1, -1)) { tree, up, down ->
            tree.maxUp = up
            tree.maxDown = down
        }

        scenicScore(column) { tree, score1, score2 -> tree.scenicScore *= score1 * score2 }

    }

    val visibleTrees = trees.sumOf { it.count { tree -> tree.isVisible } }
    val maxScenicScore = trees.maxOf { row -> row.maxOf { it.scenicScore } }
    println("Number of visible trees $visibleTrees")
    println("Max scenic score of a tree is $maxScenicScore")
}

fun updateMaximums(trees: List<Tree>, pos: Int, maximums: MutableList<Int>, setter: (Tree, Int, Int) -> Unit) {
    val tree = trees[pos]
    val max1 = maximums[0]
    maximums[0] = tree.height.coerceAtLeast(maximums[0])
    if (pos + 1 < trees.size) {
        updateMaximums(trees, pos + 1, maximums, setter)
    }

    val max2 = maximums[1]
    maximums[1] = tree.height.coerceAtLeast(maximums[1])
    setter(tree, max1, max2)
}

fun scenicScore(trees: List<Tree>, setter: (Tree, Int, Int) -> Unit) {
    for (i in trees.indices) {
        var distance1 = 0
        var distance2 = 0

        val tree = trees[i]
        for (j in i - 1 downTo 0) {
            if (trees[j].height < tree.height) {
                distance1++
            } else if (trees[j].height >= tree.height) {
                distance1++
                break
            }
        }

        for (j in i + 1 until trees.size) {
            if (trees[j].height < tree.height) {
                distance2++
            } else if (trees[j].height >= tree.height) {
                distance2++
                break
            }
        }
        setter(tree, distance1, distance2)
    }
}
