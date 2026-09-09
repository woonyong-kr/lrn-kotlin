package learning.kotlininaction.chapter02.exceptions

fun parseNumber(text: String): Int? =
    try {
        text.toInt()
    } catch (_: NumberFormatException) {
        null
    }

fun propagationTrace(text: String): List<String> {
    val trace = mutableListOf<String>()
    try {
        trace += "try"
        text.toInt()
        trace += "after conversion"
    } catch (_: NumberFormatException) {
        trace += "catch"
    } finally {
        trace += "finally"
    }
    trace += "after try"
    return trace
}

fun exceptionTrace(): List<String> =
    listOf(
        "42=${parseNumber("42")}",
        "Kotlin=${parseNumber("Kotlin")}",
        "flow=${propagationTrace("Kotlin")}",
        "result failure=${runCatching { "Kotlin".toInt() }.isFailure}",
    )

fun main() = exceptionTrace().forEach(::println)
