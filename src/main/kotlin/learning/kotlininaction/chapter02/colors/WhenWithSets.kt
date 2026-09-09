package learning.kotlininaction.chapter02.colors

fun mix(c1: Color, c2: Color): Color =
    when (setOf(c1, c2)) {
        setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
        setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
        setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
        else -> throw IllegalArgumentException("Dirty color: $c1 + $c2")
    }
