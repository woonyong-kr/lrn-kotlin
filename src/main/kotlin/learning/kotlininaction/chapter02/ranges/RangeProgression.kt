package learning.kotlininaction.chapter02.ranges

import learning.kotlininaction.chapter02.colors.Color

fun valuesOf(progression: IntProgression): List<Int> = progression.toList()

fun evenValuesAfterStep(): List<Int> {
    val selected = mutableListOf<Int>()

    for (number in 1..20 step 3) {
        if (number % 2 == 0) {
            selected += number
        }
    }

    return selected
}

fun sampleColorIndices(): List<Int> {
    val colors = listOf(Color.RED, Color.GREEN, Color.BLUE)
    return colors.indices.toList()
}

fun rangeProgressionTrace(): List<String> =
    listOf(
        "1..5: ${valuesOf(1..5)}",
        "0..<5: ${valuesOf(0..<5)}",
        "5 downTo 1: ${valuesOf(5 downTo 1)}",
        "1..12 step 5: ${valuesOf(1..12 step 5)}",
        "12 downTo 1 step 5: ${valuesOf(12 downTo 1 step 5)}",
        "5..1: ${valuesOf(5..1)}",
        "condition after step: ${evenValuesAfterStep()}",
        "color indices: ${sampleColorIndices()}",
    )

fun main() {
    rangeProgressionTrace().forEach(::println)
}
