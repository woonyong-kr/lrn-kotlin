package learning.kotlininaction.chapter02.colors

fun mixWithoutSubject(c1: Color, c2: Color): Color =
    when {
        (c1 == Color.RED && c2 == Color.YELLOW) ||
            (c1 == Color.YELLOW && c2 == Color.RED) -> Color.ORANGE

        (c1 == Color.YELLOW && c2 == Color.BLUE) ||
            (c1 == Color.BLUE && c2 == Color.YELLOW) -> Color.GREEN

        (c1 == Color.BLUE && c2 == Color.VIOLET) ||
            (c1 == Color.VIOLET && c2 == Color.BLUE) -> Color.INDIGO

        else -> throw IllegalArgumentException("Dirty color: $c1 + $c2")
    }
