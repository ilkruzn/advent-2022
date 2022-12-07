package day07

import java.io.File

data class ElfFile(val name: String, val size: Int)
data class ElfDirectory(
    val name: String,
    val directories: MutableList<ElfDirectory>,
    val files: MutableList<ElfFile>,
    val parent: ElfDirectory? = null
) {
    fun size(): Int = files.sumOf { it.size } + directories.sumOf { it.size() }
}

fun main() {
    val root = buildFileSystem()
    part1(root)
    part2(root)
}

private fun part1(root: ElfDirectory) {
    val dirs = mutableListOf<ElfDirectory>()
    findDirsMatchingSize(dirs, root) { it <= 100000 }
    println("Total size of directories greater than 100000 is ${dirs.sumOf { it.size() }}")
}

private fun part2(root: ElfDirectory) {
    val dirs = mutableListOf<ElfDirectory>()
    val freeSpaceRequired = root.size() - 40000000
    findDirsMatchingSize(dirs, root) { it >= freeSpaceRequired }.also { dirs.sortBy { it.size() } }
    println("Total size of directory to delete ${dirs.first().size()}")
}

fun findDirsMatchingSize(result: MutableList<ElfDirectory>, current: ElfDirectory, matcher: (Int) -> Boolean) {
    if (matcher(current.size())) {
        result.add(current)
    }
    current.directories.forEach { findDirsMatchingSize(result, it, matcher) }
}

private fun buildFileSystem(): ElfDirectory {
    val input = File("/Users/ilker.uzun/git/advent/advent-2022/src/main/kotlin/day07/input.txt")
        .readText()
    val lines = input.split("\n").filter { it.isNotEmpty() }
    var currentDir = ElfDirectory("/", mutableListOf(), mutableListOf())
    val root = currentDir
    for (i in 1 until lines.size) {
        val line = lines[i]
        if (line.startsWith("$ ls")) {
            continue // skip 'ls'
        } else if (line.startsWith("dir")) {
            val subDir = ElfDirectory(line.substring(4), mutableListOf(), mutableListOf(), currentDir)
            currentDir.directories.add(subDir)
        } else if (line[0].isDigit()) {
            val fileInfo = line.split(" ")
            currentDir.files.add(ElfFile(fileInfo[1], fileInfo[0].toInt()))
        } else if (line.startsWith("$ cd")) {
            val dirName = line.substring(5)
            currentDir =
                (if (dirName == "..") currentDir.parent!! else currentDir.directories.first { it.name == dirName })
        }
    }

    return root
}
