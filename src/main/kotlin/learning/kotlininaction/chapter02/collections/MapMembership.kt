package learning.kotlininaction.chapter02.collections

import java.util.TreeMap

fun binaryRepresentations(): List<String> {
    val map = TreeMap<Char, String>()
    for (letter in 'A'..'C') {
        map[letter] = letter.code.toString(radix = 2)
    }
    return map.map { (letter, binary) -> "$letter=$binary" }
}

fun membershipTrace(): List<String> =
    listOf(
        "string range=${"Kotlin" in "Java".."Scala"}",
        "set=${"Kotlin" in setOf("Java", "Scala")}",
        "substring=${"Kotlin" in "I like Kotlin"}",
        "not digit=${'A' !in '0'..'9'}",
    )

fun mapMembershipTrace(): List<String> =
    binaryRepresentations() + membershipTrace()

fun main() = mapMembershipTrace().forEach(::println)
