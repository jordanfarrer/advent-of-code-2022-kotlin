fun main() {
    val day = "Day07"

    fun parseCommands(input: List<String>): FsDirectory {
        val fs = FsDirectory(null, "", "", mutableListOf(), mutableListOf())

        var currentDir = fs
        input.forEach { command ->
            val parts = command.split(" ")
            if (parts.size < 2) error("Not valid line")
            if (parts[0] == "$") {
                if (parts[1] == "ls") {
                    // no-op
                } else if (parts[1] == "cd") {
                    if (parts[2] == "/") {
                        // no-op
                    } else if (parts[2] == "..") {
                        currentDir = currentDir.parent ?: error("Can't change dir up, no parent directory")
                    } else {
                        currentDir = currentDir.dirs.find { it.name == parts[2] }
                            ?: error("Can't find directory ${parts[2]} in ${currentDir.dirs}")
                    }
                } else {
                    error("Unknown command: ${parts[1]}")
                }
            } else {
                // result
                if (parts[0] == "dir") {
                    // directory
                    val name = parts[1]
                    currentDir.addDirectory(name, mutableListOf(), mutableListOf())
                } else {
                    val size = parts[0].toInt()
                    val name = parts[1]
                    currentDir.addFile(name, size)
                }
            }
        }

        return fs
    }

    fun part1(input: List<String>): Int {
        val dir = parseCommands(input)
//        displayFsDir(dir)
        return dir.filterChildDirs { it.getSize() <= 100000 }.sumOf { it.getSize() }
    }

    fun part2(input: List<String>): Int {
        val dir = parseCommands(input)
        val diskSpace = 70000000
        val requiredDiskSpace = 30000000
        val unusedSpace = diskSpace - dir.getSize()
        val allSubDirs = dir.getAllSubDirs().sortedBy { it.getSize() }
        val targetDir = allSubDirs.find { (it.getSize() + unusedSpace) > requiredDiskSpace }
            ?: error("Can't find dir with appropriate space")
        return targetDir.getSize()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    val part1Result = part1(testInput)
    println("Part 1 (test): $part1Result")
    check(part1Result == 95437)
    val part2Result = part2(testInput)
    println("Part 2 (test): $part2Result")
//    check(part2Result == 45000)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

// FILES
data class FsFile(val parent: FsDirectory, val name: String, val size: Int)

// DIRS
data class FsDirectory(
    val parent: FsDirectory?,
    val containingPath: String,
    val name: String,
    val files: MutableList<FsFile>,
    val dirs: MutableList<FsDirectory>
) {
    override fun toString(): String {
        return "${getFullPath()} (dir), ${dirs.size} dirs, ${files.size} files"
    }
}
fun FsDirectory.getFullPath() = "${this.containingPath}/${this.name}"
fun FsDirectory.addFile(name: String, size: Int) {
    this.files.add(FsFile(this, name, size))
}

fun FsDirectory.getAllSubDirs(): List<FsDirectory> {
    return this.dirs + this.dirs.flatMap { it.getAllSubDirs() }
}

fun FsDirectory.filterChildDirs(predicate: (FsDirectory) -> Boolean): List<FsDirectory> {
    val allSubDirs = this.getAllSubDirs()
    return allSubDirs.filter(predicate)
}


fun FsDirectory.addDirectory(
    name: String,
    files: MutableList<FsFile>,
    dirs: MutableList<FsDirectory>
) {
    this.dirs.add(FsDirectory(this, this.getFullPath(), name, files, dirs))
}

fun FsDirectory.getSize(): Int {
    return this.files.sumOf { it.size } + this.dirs.sumOf { it.getSize() }
}