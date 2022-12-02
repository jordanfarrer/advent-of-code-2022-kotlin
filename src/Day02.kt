fun main() {
    val day = "Day02"

    val winValue = 6
    val loseValue = 0
    val drawValue = 3

    val rock = Choice("Rock", "X", theirChoice = "A", myPointValue = 1)
    val paper = Choice("Paper", "Y", theirChoice = "B", myPointValue = 2)
    val scissors = Choice("Scissors", "Z", theirChoice = "C", myPointValue = 3)
    val choices = listOf(rock, paper, scissors)

    val rules = listOf(
        GameRule(rock, rock, drawValue),
        GameRule(rock, paper, loseValue),
        GameRule(rock, scissors, winValue),
        GameRule(paper, rock, winValue),
        GameRule(paper, paper, drawValue),
        GameRule(paper, scissors, loseValue),
        GameRule(scissors, rock, loseValue),
        GameRule(scissors, paper, winValue),
        GameRule(scissors, scissors, drawValue)
    )

    fun part1(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val (theirChoiceValue, myChoiceValue) = line.split(" ")
            val myChoice = choices.find { it.myChoice == myChoiceValue } ?: throw Exception("Can't find matching my choice for $myChoiceValue")
            val theirChoice = choices.find { it.theirChoice == theirChoiceValue } ?: throw Exception("Can't find matching their choice for $myChoiceValue")
            val matchingRule = rules.find { it.me == myChoice && it.them == theirChoice } ?: throw Exception("Can't find matching rule")

            result += myChoice.myPointValue + matchingRule.outcome
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        input.forEach { line ->
            val (theirChoice, desiredOutcomeValue) = line.split(" ")
            val matchingOutcome = when (desiredOutcomeValue) {
                "X" -> loseValue
                "Y" -> drawValue
                "Z" -> winValue
                else -> throw Exception("No matching outcome found")
            }

            val matchingRule = rules.find { it.outcome == matchingOutcome && it.them.theirChoice == theirChoice } ?: throw Exception("Unable to find matching rule")

            result += matchingOutcome + matchingRule.me.myPointValue
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == 15)
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
    check(part2Result == 12)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

data class Choice(val name: String, val myChoice: String, val theirChoice: String, val myPointValue: Int)
data class GameRule(val me: Choice, val them: Choice, val outcome: Int)
