package learning.kotlininaction.chapter02.labels

fun labeledBreakTrace(): List<String> {
    val trace = mutableListOf<String>()
    var row = 1

    outer@ while (row <= 3) {
        var column = 1

        while (column <= 3) {
            trace += "enter loop: row=$row, column=$column"

            if (row == 2 && column == 2) {
                trace += "break@outer"
                break@outer
            }

            trace += "complete loop: row=$row, column=$column"
            column++
        }

        trace += "complete outer row=$row"
        row++
    }

    trace += "after outer loop"
    return trace
}

fun labeledReturnTrace(): List<String> {
    val trace = mutableListOf<String>()

    listOf("A", "SKIP", "B").forEach visit@{ item ->
        trace += "enter lambda: $item"

        if (item == "SKIP") {
            trace += "return@visit: $item"
            return@visit
        }

        trace += "complete lambda: $item"
    }

    trace += "after forEach"
    return trace
}

class LabelOwner(
    private val ownerName: String,
) {
    fun qualifiedThisTrace(): List<String> {
        val trace = mutableListOf<String>()

        "lambda receiver".run receiver@{
            trace += "this@receiver=$this"
            trace += "this@LabelOwner=${this@LabelOwner.ownerName}"
        }

        trace += "after run"
        return trace
    }
}

private fun printTrace(
    title: String,
    trace: List<String>,
) {
    println("TRACE: $title")
    trace.forEach(::println)
}

fun main() {
    printTrace("break@outer", labeledBreakTrace())
    printTrace("return@visit", labeledReturnTrace())
    printTrace("qualified this", LabelOwner("outer instance").qualifiedThisTrace())
}
