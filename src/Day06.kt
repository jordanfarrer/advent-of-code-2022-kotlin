fun main() {
    val day = "Day06"

    fun findMarkerEnd(input: String, markerLength: Int): Int {
        val packetStartChars = input.windowed(markerLength).first { it.toSet().size == markerLength }
        return input.indexOf(packetStartChars) + markerLength
    }

    fun part1(input: String): Int {
        return findMarkerEnd(input, 4)
    }

    fun part2(input: String): Int {
        return findMarkerEnd(input, 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsString("${day}_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == 7)
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
    check(part2Result == 19)

    val input = readInputAsString(day)
    println(part1(input))
    println(part2(input))
}