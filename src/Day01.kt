fun main() {
    fun getElves(input: List<String>): List<Elf> {
        var elfIndex = 0
        val elves = mutableListOf<Elf>()
        for (line in input) {
            if (line == "") {
                elfIndex++
            } else {
                var elf = elves.find { it.index == elfIndex }
                if (elf == null) {
                    elf = Elf(elfIndex, mutableListOf())
                    elves.add(elf)
                }
                elf.items.add(line.toInt())
            }
        }
        return elves
    }

    fun part1(input: List<String>): Int {
        val elves = getElves(input)
        return elves.maxOf { it.getTotalCalories() }
    }

    fun part2(input: List<String>): Int {
        val elves = getElves(input)
        return elves.sortedByDescending { it.getTotalCalories() }.take(3).sumOf { it.getTotalCalories() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == 24000)
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
    check(part2Result == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

data class Elf(val index: Int, val items: MutableList<Int>)

fun Elf.getTotalCalories() = this.items.sum()