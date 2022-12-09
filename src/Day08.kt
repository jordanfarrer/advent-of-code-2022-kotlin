@file:Suppress("DuplicatedCode")

fun main() {
    val day = "Day08"

    fun parseMap(input: List<String>): List<Tree> {
        val trees = mutableListOf<Tree>()
        input.forEachIndexed { row, line ->
            line.toCharArray().forEachIndexed { col, treeHeight ->
                trees.add(Tree(row, col, treeHeight.digitToInt()))
            }
        }
        return trees
    }

    fun findVisibleTrees(sortedTrees: Iterable<Tree>, startingHeight: Int = -1): List<Tree> {
        var currentMaxHeight = startingHeight
        val visibleTrees = mutableListOf<Tree>()
        sortedTrees.forEach {
            if (it.height > currentMaxHeight) {
                visibleTrees.add(it)
                currentMaxHeight = it.height
            }
        }

        return visibleTrees.toList()
    }

    fun findViewingDistance(sortedTrees: Iterable<Tree>, originHeight: Int): Int {
        var visible = 0
        for (tree in sortedTrees) {
            visible++
            if (tree.height >= originHeight) break
        }
        return visible
    }

    fun findScenicScore(trees: Iterable<Tree>, row: Int, col: Int): Int {
        val scores = mutableListOf<Int>()
        val tree = trees.find { it.row == row && it.col == col } ?: error("Can't find tree")
        val treesUp = trees.filter { it.col == col && it.row < row }.sortedByDescending { it.row }
        val treesLeft = trees.filter { it.row == row && it.col < col }.sortedByDescending { it.col }
        val treesRight = trees.filter { it.row == row && it.col > col }.sortedBy { it.col }
        val treesDown = trees.filter { it.col == col && it.row > row }.sortedBy { it.row }

        scores.add(findViewingDistance(treesUp, tree.height))
        scores.add(findViewingDistance(treesLeft, tree.height))
        scores.add(findViewingDistance(treesRight, tree.height))
        scores.add(findViewingDistance(treesDown, tree.height))

        return scores.reduce { acc, i -> acc * i }
    }

    @Suppress("unused")
    fun display(trees: Iterable<Tree>, visible: Iterable<Tree>) {
        val rows = trees.maxOf { it.row }
        val cols = trees.maxOf { it.col }
        for (row in 0..rows) {
            var line = ""
            for (col in 0..cols) {
                val tree = trees.find { it.row == row && it.col == col } ?: error("Can't find tree")
                val isVisible = visible.any { it.row == row && it.col == col }
                if (isVisible) {
                    line += tree.height
                } else {
                    line += " "
                }
            }
            println(line)
        }
    }

    fun part1(input: List<String>): Int {
        val trees = parseMap(input)
        val rows = trees.maxOf { it.row }
        val cols = trees.maxOf { it.col }

        val visibleTrees = mutableSetOf<Tree>()
        // add perimeter
        visibleTrees.addAll(visibleTrees.filter { it.row == 0 || it.row == rows || it.col == 0 || it.col == cols })

        // columns (up and down)
        for (col in 0..cols) {
            val treesInColFromTop = trees.filter { it.col == col }.sortedBy { it.row }
            visibleTrees.addAll(findVisibleTrees(treesInColFromTop))
            val treesInColFromBottom = trees.filter { it.col == col }.sortedByDescending { it.row }
            visibleTrees.addAll(findVisibleTrees((treesInColFromBottom)))
        }

        for (row in 0..rows) {
            val treesInRowFromLeft = trees.filter { it.row == row }.sortedBy { it.col }
            visibleTrees.addAll(findVisibleTrees(treesInRowFromLeft))
            val treesInRowFromRight = trees.filter { it.row == row }.sortedByDescending { it.col }
            visibleTrees.addAll(findVisibleTrees(treesInRowFromRight))
        }

//        display(trees, visibleTrees)

        return visibleTrees.size
    }

    fun part2(input: List<String>): Int {
        val trees = parseMap(input)
        return trees.maxOf { findScenicScore(trees, it.row, it.col) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == 21)
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
    check(part2Result == 8)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

data class Tree(val row: Int, val col: Int, val height: Int)