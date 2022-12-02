fun main() {
    val day = ""

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == 24000)
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
    check(part2Result == 45000)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}