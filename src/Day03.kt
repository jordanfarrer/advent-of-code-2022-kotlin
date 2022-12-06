fun main() {
    val day = "Day03"

    fun findItemInBoth(first: Iterable<Char>, second: Iterable<Char>): Char {
        return first.intersect(second.toSet()).first()
    }

    fun findCommonItem(groups: List<Iterable<Char>>): Char {
        return groups.reduce { a, b -> a.intersect(b.toSet()) }.first()
    }

    fun getPriorityForItem(item: Char): Int {
        return if (item.isUpperCase()) item.code - 'A'.code + 27 else item.code - 'a'.code + 1
    }

    fun part1(input: List<String>): Int {
        return input.map { line ->
            findItemInBoth(
                line.take(line.length / 2).asIterable(),
                line.takeLast(line.length / 2).asIterable()
            )
        }.sumOf { getPriorityForItem(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.asIterable() }.chunked(3).map { findCommonItem(it) }.sumOf { getPriorityForItem(it) }
    }

    println("a: ${'a'.code}")
    println("A: ${'A'.code}")

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == 157)
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
    check(part2Result == 70)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}