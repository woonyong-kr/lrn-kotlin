package learning.kotlininaction.chapter04.equality

import java.util.HashMap
import java.util.HashSet

// Deliberately incomplete: equals uses values, but hashCode is inherited from Any.
class EqualsOnlyClient(val name: String, val postalCode: Int) {
    override fun equals(other: Any?): Boolean =
        other is EqualsOnlyClient && name == other.name && postalCode == other.postalCode
}

// A controlled contract violation: instance hashes 1 and 2 avoid accidental identity-hash collisions.
// This is an invalid key, not an implementation to copy into an application.
class MismatchedHashClient(val name: String, val postalCode: Int, private val instanceHash: Int) {
    override fun equals(other: Any?): Boolean =
        other is MismatchedHashClient && name == other.name && postalCode == other.postalCode

    override fun hashCode(): Int = instanceHash
}

class ValueClient(val name: String, val postalCode: Int) {
    override fun equals(other: Any?): Boolean =
        other is ValueClient && name == other.name && postalCode == other.postalCode

    override fun hashCode(): Int = 31 * name.hashCode() + postalCode
}

data class DataClient(val name: String, val postalCode: Int)

data class MutableKey(var id: Int)

private fun <T : Any> printPair(label: String, stored: T, probe: T) {
    val set = HashSet<T>()
    val map = HashMap<T, String>()
    set.add(stored)
    map[stored] = "saved"
    println(label)
    println("  == ${stored == probe}; === ${stored === probe}")
    println("  hashCode: ${stored.hashCode()} / ${probe.hashCode()}")
    println("  contains(probe)=${set.contains(probe)}; get(probe)=${map[probe]}")
    println("  add(probe)=${set.add(probe)}; set.size=${set.size}")
    println("  put(probe) previous=${map.put(probe, "updated")}; map.size=${map.size}")
}

fun main() {
    println("Kotlin ${KotlinVersion.CURRENT}; JDK ${System.getProperty("java.runtime.version")}")
    println("Contract: equal objects must have equal hashes; equal hashes need not mean equal objects.")
    println("Invalid-key results below are observations of this JVM, not portable guarantees.")
    printPair("equals only (identity hashes can collide)", EqualsOnlyClient("Alice", 100), EqualsOnlyClient("Alice", 100))
    printPair("controlled violation", MismatchedHashClient("Alice", 100, 1), MismatchedHashClient("Alice", 100, 2))
    printPair("matching value equality and hash", ValueClient("Alice", 100), ValueClient("Alice", 100))
    printPair("data class", DataClient("Alice", 100), DataClient("Alice", 100))
    printPair("valid collision: Aa and BB", ValueClient("Aa", 1), ValueClient("BB", 1))

    val key = MutableKey(1)
    val set = HashSet<MutableKey>()
    val map = HashMap<MutableKey, String>()
    set.add(key)
    map[key] = "saved"
    println("mutable key before: id=${key.id}, hash=${key.hashCode()}, contains=${set.contains(key)}, get=${map[key]}")
    key.id = 2
    println("mutable key after: id=${key.id}, hash=${key.hashCode()}, contains=${set.contains(key)}, get=${map[key]}")
    println("  entries remain: set.size=${set.size}, map.size=${map.size}, same key=${map.keys.single() === key}")

    // Use a fresh collection for the safe flow, rather than trying to repair an invalid one.
    val safeKey = MutableKey(1)
    val safeMap = HashMap<MutableKey, String>()
    safeMap[safeKey] = "saved"
    val value = safeMap.remove(safeKey) ?: error("The key must be removed before changing it")
    safeKey.id = 2
    safeMap[safeKey] = value
    println("remove -> change -> reinsert: old=${safeMap[MutableKey(1)]}, new=${safeMap[MutableKey(2)]}")
    println("Prefer immutable keys; data class alone does not make a var property immutable.")
}
