fun main() {
    val day = "Day05"

    fun parseInstructions(input: List<String>): List<Instruction> {
        val instructionRegex = Regex(".*?(\\d+).*?(\\d+).*?(\\d+)")
        return input.map { line ->
            val match = instructionRegex.find(line)
            val quantity = match?.groups?.get(1)?.value?.toInt() ?: error("Unable to match quantity")
            val fromStack = match.groups[2]?.value?.toInt() ?: error("Unable to match from stack")
            val toStack = match.groups[3]?.value?.toInt() ?: error("Unable to match to stack")
            Instruction(quantity, fromStack, toStack)
        }
    }

    fun parseStartingStack(input: List<String>): CargoStacks {
        val stackCount = input.maxOf { it.chunked(4).size }
        val stacks = mutableListOf<MutableList<String>>()
        for (i in 1..stackCount) {
            stacks.add(mutableListOf())
        }

        input.reversed().forEach { line ->
            line.chunked(4).map { it.trim() }.forEachIndexed { index, column ->
                if (column.isNotBlank()) {
                    stacks[index].push(column.replace("[", "").replace("]", ""))
                }
            }
        }

        return stacks
    }

    fun executeInstructionsForSingleMove(stacks: CargoStacks, instructions: Iterable<Instruction>): CargoStacks {
        instructions.forEach {
            for (i in 1..it.quantity) {
                val item =
                    stacks[it.fromStack - 1].pop() ?: error("Unable to find item $it, $stacks")
                stacks[it.toStack - 1].push(item)
            }
        }
        return stacks
    }

    fun executeInstructionsForMultiMove(stacks: CargoStacks, instructions: Iterable<Instruction>): CargoStacks {
        instructions.forEach {
            val movedItems = stacks[it.fromStack - 1].takeLast(it.quantity)
            stacks[it.toStack - 1].addAll(movedItems)
            stacks[it.fromStack - 1] =
                stacks[it.fromStack - 1].take(stacks[it.fromStack - 1].size - it.quantity).toMutableList()
        }
        return stacks
    }

    fun part1(input: List<String>): String {
        val splitIndex = input.indexOfFirst { it.isBlank() }
        val stacks = parseStartingStack(input.take(splitIndex - 1))
        val instructions = parseInstructions(input.takeLast(input.size - splitIndex - 1))
        executeInstructionsForSingleMove(stacks, instructions)
        return stacks.joinToString(separator = "", transform = { it.peek() ?: "" })
    }

    fun part2(input: List<String>): String {
        val splitIndex = input.indexOfFirst { it.isBlank() }
        val stacks = parseStartingStack(input.take(splitIndex - 1))
        val instructions = parseInstructions(input.takeLast(input.size - splitIndex - 1))
        executeInstructionsForMultiMove(stacks, instructions)
        return stacks.joinToString(separator = "", transform = { it.peek() ?: "" })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == "CMZ")
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
    check(part2Result == "MCD")

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

data class Instruction(val quantity: Int, val fromStack: Int, val toStack: Int)
typealias CargoStacks = MutableList<MutableList<String>>