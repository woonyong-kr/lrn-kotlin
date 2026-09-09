package learning.kotlininaction.chapter02.expressions

sealed interface Expr
data class Num(val value: Int) : Expr
data class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.left) + eval(e.right)
    }

fun smartCastReassignmentTrace(): List<String> {
    val trace = mutableListOf<String>()
    var expression: Expr = Num(10)

    if (expression is Num) {
        trace += "before=${expression.value}"
        expression = Sum(Num(1), Num(2))
    }

    trace += "after=${eval(expression)}"
    return trace
}

fun expressionTrace(): List<String> {
    val expression = Sum(Num(1), Sum(Num(2), Num(3)))
    return listOf(
        "eval=${eval(expression)}",
        *smartCastReassignmentTrace().toTypedArray(),
    )
}

fun main() = expressionTrace().forEach(::println)
