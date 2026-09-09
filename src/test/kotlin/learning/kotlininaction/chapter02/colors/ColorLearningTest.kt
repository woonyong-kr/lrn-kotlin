package learning.kotlininaction.chapter02.colors

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ColorLearningTest {
    @Test
    fun `enum constant keeps its RGB data and behavior`() {
        assertEquals(255, Color.BLUE.rgb())
        assertEquals(0xFFFF00, Color.YELLOW.rgb())
    }

    @Test
    fun `exhaustive when categorizes every Color`() {
        val actual = Color.entries.associateWith(::getWarmth)

        assertEquals("warm", actual.getValue(Color.RED))
        assertEquals("warm", actual.getValue(Color.ORANGE))
        assertEquals("warm", actual.getValue(Color.YELLOW))
        assertEquals("neutral", actual.getValue(Color.GREEN))
        assertEquals("cold", actual.getValue(Color.BLUE))
        assertEquals("cold", actual.getValue(Color.INDIGO))
        assertEquals("cold", actual.getValue(Color.VIOLET))
    }

    @Test
    fun `when subject variable reads the sensor once`() {
        var calls = 0

        val description = describeMeasuredColor {
            calls += 1
            Color.ORANGE
        }

        assertEquals(1, calls)
        assertEquals("warm (red = 255)", description)
    }

    @Test
    fun `Set equality makes color mixing independent of argument order`() {
        assertEquals(Color.ORANGE, mix(Color.RED, Color.YELLOW))
        assertEquals(Color.ORANGE, mix(Color.YELLOW, Color.RED))
        assertEquals(Color.GREEN, mix(Color.BLUE, Color.YELLOW))
        assertEquals(Color.GREEN, mix(Color.YELLOW, Color.BLUE))
    }

    @Test
    fun `when without subject preserves the same mixing rules`() {
        val supportedPairs =
            listOf(
                Color.RED to Color.YELLOW,
                Color.YELLOW to Color.RED,
                Color.YELLOW to Color.BLUE,
                Color.BLUE to Color.YELLOW,
                Color.BLUE to Color.VIOLET,
                Color.VIOLET to Color.BLUE,
            )

        supportedPairs.forEach { (c1, c2) ->
            assertEquals(mix(c1, c2), mixWithoutSubject(c1, c2))
        }
    }

    @Test
    fun `unsupported combinations fail explicitly`() {
        assertFailsWith<IllegalArgumentException> {
            mix(Color.RED, Color.BLUE)
        }
        assertFailsWith<IllegalArgumentException> {
            mixWithoutSubject(Color.RED, Color.BLUE)
        }
    }
}
