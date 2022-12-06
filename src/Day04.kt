fun main() {
    val day = "Day04"

    fun isFullyContained(sectionPairs: List<Int>): Boolean {
        val (firstStart, firstEnd, secondStart, secondEnd) = sectionPairs
        return (firstStart >= secondStart && firstEnd <= secondEnd) || (secondStart >= firstStart && secondEnd <= firstEnd)
    }

    fun hasOverlap(sectionPairs: List<Int>): Boolean {
        val (firstStart, firstEnd, secondStart, secondEnd) = sectionPairs
        val firstRange = firstStart..firstEnd
        val secondRange = secondStart..secondEnd
        return firstRange.intersect(secondRange).any()
    }

    fun part1(input: List<String>): Int {
        return input.map { pair -> pair.split(',','-').map { it.toInt() } }.count { isFullyContained(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { pair -> pair.split(',','-').map { it.toInt() } }.count { hasOverlap(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == 2)
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
//    check(part2Result == 4)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}