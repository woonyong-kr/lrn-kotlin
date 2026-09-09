package learning.kotlininaction.chapter02.fundamentals

fun max(a: Int, b: Int): Int = if (a > b) a else b

fun fundamentalsTrace(): List<String> {
    val immutableReference = mutableListOf("RED")
    immutableReference += "BLUE"

    var replaceableReference = listOf("before")
    replaceableReference = listOf("after")

    val language = "Kotlin"
    return listOf(
        "max=${max(3, 7)}",
        "val mutable object=$immutableReference",
        "var replaced=$replaceableReference",
        "template=${language.uppercase()}",
    )
}

fun main() = fundamentalsTrace().forEach(::println)
