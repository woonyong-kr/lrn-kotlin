package learning.kotlininaction.chapter02.colors

fun describeMeasuredColor(measureColor: () -> Color): String =
    when (val color = measureColor()) {
        Color.RED, Color.ORANGE, Color.YELLOW -> "warm (red = ${color.r})"
        Color.GREEN -> "neutral (green = ${color.g})"
        Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold (blue = ${color.b})"
    }
