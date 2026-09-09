package learning.kotlininaction.chapter02.colors

fun colorLearningTrace(): List<String> {
    var sensorCalls = 0
    val measured = describeMeasuredColor {
        sensorCalls += 1
        Color.ORANGE
    }

    return listOf(
        "2.3.1 BLUE.rgb() = ${Color.BLUE.rgb()}",
        "2.3.2 getWarmth(BLUE) = ${getWarmth(Color.BLUE)}",
        "2.3.3 sensorCalls = $sensorCalls, result = $measured",
        "2.3.4 mix(YELLOW, RED) = ${mix(Color.YELLOW, Color.RED)}",
        "2.3.5 mixWithoutSubject(BLUE, YELLOW) = " +
            mixWithoutSubject(Color.BLUE, Color.YELLOW),
    )
}

fun main() = colorLearningTrace().forEach(::println)
