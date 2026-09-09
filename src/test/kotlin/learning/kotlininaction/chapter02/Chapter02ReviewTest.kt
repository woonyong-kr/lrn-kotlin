package learning.kotlininaction.chapter02

import learning.kotlininaction.chapter02.collections.binaryRepresentations
import learning.kotlininaction.chapter02.collections.membershipTrace
import learning.kotlininaction.chapter02.exceptions.parseNumber
import learning.kotlininaction.chapter02.exceptions.propagationTrace
import learning.kotlininaction.chapter02.expressions.Num
import learning.kotlininaction.chapter02.expressions.Sum
import learning.kotlininaction.chapter02.expressions.eval
import learning.kotlininaction.chapter02.expressions.smartCastReassignmentTrace
import learning.kotlininaction.chapter02.fundamentals.fundamentalsTrace
import learning.kotlininaction.chapter02.properties.Person
import learning.kotlininaction.chapter02.properties.Rectangle
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Chapter02ReviewTest {
    @Test
    fun `val reference can still point to a mutable object`() {
        assertEquals("val mutable object=[RED, BLUE]", fundamentalsTrace()[1])
    }

    @Test
    fun `custom setter validates while computed getter has no stored field`() {
        val person = Person("Woon", 30)
        person.age = 31
        assertEquals(31, person.age)
        assertFailsWith<IllegalArgumentException> { person.age = -1 }
        assertTrue(Rectangle(5, 5).isSquare)
        assertFalse(Rectangle(5, 4).isSquare)
    }

    @Test
    fun `boolean is property uses JVM isActive and setActive names`() {
        val methodNames = Person::class.java.methods.map { it.name }.toSet()
        assertTrue("isActive" in methodNames)
        assertTrue("setActive" in methodNames)
        assertFalse("setIsActive" in methodNames)
    }

    @Test
    fun `when evaluates an exhaustive sealed expression tree`() {
        assertEquals(6, eval(Sum(Num(1), Sum(Num(2), Num(3)))))
    }

    @Test
    fun `var can be reassigned after smart cast and then has the declared type`() {
        assertEquals(listOf("before=10", "after=3"), smartCastReassignmentTrace())
    }

    @Test
    fun `map destructuring reads entries without deciding their ordering`() {
        assertEquals(
            listOf("A=1000001", "B=1000010", "C=1000011"),
            binaryRepresentations(),
        )
    }

    @Test
    fun `in meaning follows the right hand type`() {
        assertEquals(
            listOf(
                "string range=true",
                "set=false",
                "substring=true",
                "not digit=true",
            ),
            membershipTrace(),
        )
    }

    @Test
    fun `catch creates a fallback value and finally still runs`() {
        assertEquals(42, parseNumber("42"))
        assertEquals(null, parseNumber("Kotlin"))
        assertEquals(
            listOf("try", "catch", "finally", "after try"),
            propagationTrace("Kotlin"),
        )
    }
}
