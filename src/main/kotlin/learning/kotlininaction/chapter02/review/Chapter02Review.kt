package learning.kotlininaction.chapter02.review

import learning.kotlininaction.chapter02.collections.mapMembershipTrace
import learning.kotlininaction.chapter02.colors.colorLearningTrace
import learning.kotlininaction.chapter02.exceptions.exceptionTrace
import learning.kotlininaction.chapter02.expressions.expressionTrace
import learning.kotlininaction.chapter02.fundamentals.fundamentalsTrace
import learning.kotlininaction.chapter02.labels.labeledBreakTrace
import learning.kotlininaction.chapter02.properties.propertyTrace
import learning.kotlininaction.chapter02.ranges.rangeProgressionTrace

private fun section(title: String, lines: List<String>) {
    println("== $title ==")
    lines.forEach(::println)
}

fun main() {
    section("2.1 functions and variables", fundamentalsTrace())
    section("2.2 properties and accessors", propertyTrace())
    section("2.3 Color and when", colorLearningTrace())
    section("2.3 Expr and smart cast", expressionTrace())
    section("2.4 labels", labeledBreakTrace())
    section("2.4 ranges", rangeProgressionTrace())
    section("2.4 map and in", mapMembershipTrace())
    section("2.5 exceptions", exceptionTrace())
}
