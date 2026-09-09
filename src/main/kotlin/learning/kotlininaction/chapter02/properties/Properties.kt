package learning.kotlininaction.chapter02.properties

class Person(
    val name: String,
    age: Int,
) {
    var age: Int = age
        set(value) {
            require(value >= 0) { "age must be non-negative" }
            field = value
        }

    var isActive: Boolean = true
}

class Rectangle(
    val height: Int,
    val width: Int,
) {
    val isSquare: Boolean
        get() = height == width
}

fun propertyTrace(): List<String> {
    val person = Person("Woon", 30)
    person.age = 31

    return listOf(
        "property=${person.name}:${person.age}",
        "isActive=${person.isActive}",
        "isSquare=${Rectangle(5, 5).isSquare}",
    )
}

fun main() = propertyTrace().forEach(::println)
