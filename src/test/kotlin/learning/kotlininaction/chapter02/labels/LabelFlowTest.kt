package learning.kotlininaction.chapter02.labels

import kotlin.test.Test
import kotlin.test.assertEquals

class LabelFlowTest {
    @Test
    fun `break with label exits the selected outer loop`() {
        assertEquals(
            listOf(
                "enter loop: row=1, column=1",
                "complete loop: row=1, column=1",
                "enter loop: row=1, column=2",
                "complete loop: row=1, column=2",
                "enter loop: row=1, column=3",
                "complete loop: row=1, column=3",
                "complete outer row=1",
                "enter loop: row=2, column=1",
                "complete loop: row=2, column=1",
                "enter loop: row=2, column=2",
                "break@outer",
                "after outer loop",
            ),
            labeledBreakTrace(),
        )
    }

    @Test
    fun `return with label ends only the current lambda call`() {
        assertEquals(
            listOf(
                "enter lambda: A",
                "complete lambda: A",
                "enter lambda: SKIP",
                "return@visit: SKIP",
                "enter lambda: B",
                "complete lambda: B",
                "after forEach",
            ),
            labeledReturnTrace(),
        )
    }

    @Test
    fun `qualified this selects the intended receiver`() {
        assertEquals(
            listOf(
                "this@receiver=lambda receiver",
                "this@LabelOwner=outer instance",
                "after run",
            ),
            LabelOwner("outer instance").qualifiedThisTrace(),
        )
    }
}
