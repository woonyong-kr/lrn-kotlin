package learning.kotlininaction.chapter02.ranges

import learning.kotlininaction.chapter02.colors.Color
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RangeProgressionTest {
    @Test
    fun `closed and open-ended ranges differ at the end boundary`() {
        assertEquals(listOf(1, 2, 3, 4, 5), valuesOf(1..5))
        assertEquals(listOf(0, 1, 2, 3, 4), valuesOf(0..<5))
    }

    @Test
    fun `downTo selects descending direction`() {
        assertEquals(listOf(5, 4, 3, 2, 1), valuesOf(5 downTo 1))
        assertEquals(emptyList(), valuesOf(5..1))
    }

    @Test
    fun `step moves from the first value and does not force the end value`() {
        assertEquals(listOf(1, 6, 11), valuesOf(1..12 step 5))
        assertEquals(listOf(12, 7, 2), valuesOf(12 downTo 1 step 5))

        val fromOne = 1..100 step 5
        val multiplesOfFive = 5..100 step 5

        assertEquals(1, fromOne.first)
        assertEquals(96, fromOne.last)
        assertEquals(5, multiplesOfFive.first)
        assertEquals(100, multiplesOfFive.last)
    }

    @Test
    fun `condition is evaluated separately after step chooses visited values`() {
        assertEquals(listOf(4, 10, 16), evenValuesAfterStep())
    }

    @Test
    fun `step must be positive`() {
        assertFailsWith<IllegalArgumentException> {
            1..20 step 0
        }
    }

    @Test
    fun `open-ended range and indices avoid the collection size`() {
        val colors = listOf(Color.RED, Color.GREEN, Color.BLUE)

        assertEquals(listOf(0, 1, 2), valuesOf(0..<colors.size))
        assertEquals(colors.indices.toList(), sampleColorIndices())
        assertFailsWith<IndexOutOfBoundsException> {
            for (index in 0..colors.size) {
                colors[index]
            }
        }
    }
}
