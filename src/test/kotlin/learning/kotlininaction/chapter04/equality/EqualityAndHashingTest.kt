package learning.kotlininaction.chapter04.equality

import java.util.HashMap
import java.util.HashSet
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class EqualityAndHashingTest {
    @Test
    fun `different hashes for equal keys break lookup and deduplication on this JVM`() {
        val stored = MismatchedHashClient("Alice", 100, 1)
        val probe = MismatchedHashClient("Alice", 100, 2)
        assertEquals(stored, probe)
        assertNotSame(stored, probe)
        assertNotEquals(stored.hashCode(), probe.hashCode())

        val set = HashSet<MismatchedHashClient>()
        val map = HashMap<MismatchedHashClient, String>()
        set.add(stored)
        map[stored] = "saved"
        // Invalid keys have no portable collection guarantee; these record the JDK 21 failure.
        assertFalse(set.contains(probe))
        assertNull(map[probe])
        assertTrue(set.add(probe))
        assertEquals(2, set.size)
        assertNull(map.put(probe, "updated"))
        assertEquals(2, map.size)
    }

    @Test
    fun `matching equality and hash fields allow interchangeable keys`() {
        val stored = ValueClient("Alice", 100)
        val probe = ValueClient("Alice", 100)
        assertEquals(stored, probe)
        assertNotSame(stored, probe)
        assertEquals(stored.hashCode(), probe.hashCode())
        assertNotEquals(stored, ValueClient("Alice", 101))
        assertNotEquals(stored, ValueClient("Bob", 100))
        assertFalse(stored.equals(null))
        assertFalse(stored.equals("Alice"))

        val set = HashSet<ValueClient>()
        val map = HashMap<ValueClient, String>()
        set.add(stored)
        map[stored] = "saved"
        assertTrue(set.contains(probe))
        assertFalse(set.add(probe))
        assertEquals(1, set.size)
        assertEquals("saved", map[probe])
        assertEquals("saved", map.put(probe, "updated"))
        assertEquals(1, map.size)
        assertEquals("updated", map[stored])
    }

    @Test
    fun `data class generates a matching pair from primary constructor properties`() {
        val stored = DataClient("Alice", 100)
        val probe = DataClient("Alice", 100)
        assertEquals(stored, probe)
        assertNotSame(stored, probe)
        assertEquals(stored.hashCode(), probe.hashCode())
        assertNotEquals(stored, stored.copy(postalCode = 101))
        val set = HashSet<DataClient>().apply { add(stored) }
        val map = HashMap<DataClient, String>().apply { put(stored, "saved") }
        assertTrue(set.contains(probe))
        assertFalse(set.add(probe))
        assertEquals(1, set.size)
        assertEquals("saved", map[probe])
    }

    @Test
    fun `equal hashes do not merge unequal keys`() {
        val first = ValueClient("Aa", 1)
        val second = ValueClient("BB", 1)
        assertNotEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
        val set = HashSet<ValueClient>().apply { add(first) }
        val map = HashMap<ValueClient, String>().apply { put(first, "first") }
        assertFalse(set.contains(second))
        assertNull(map[second])
        assertTrue(set.add(second))
        map[second] = "second"
        assertEquals(2, set.size)
        assertEquals(2, map.size)
        assertEquals("first", map[ValueClient("Aa", 1)])
        assertEquals("second", map[ValueClient("BB", 1)])
    }

    @Test
    fun `mutating an inserted equality field loses lookup on this JVM`() {
        val key = MutableKey(1)
        val set = HashSet<MutableKey>().apply { add(key) }
        val map = HashMap<MutableKey, String>().apply { put(key, "saved") }
        assertTrue(set.contains(key))
        assertEquals("saved", map[key])
        assertEquals(1, key.hashCode())
        key.id = 2
        assertEquals(2, key.hashCode())
        assertFalse(set.contains(key))
        assertFalse(set.contains(MutableKey(2)))
        assertNull(map[key])
        assertNull(map[MutableKey(2)])
        // A failed lookup is not proof that the entry was deleted.
        assertEquals(1, set.size)
        assertEquals(1, map.size)
        assertSame(key, set.single())
        assertSame(key, map.keys.single())
        assertEquals("saved", map.entries.single().value)
    }

    @Test
    fun `removing before changing and reinserting preserves lookup`() {
        val key = MutableKey(1)
        val set = HashSet<MutableKey>().apply { add(key) }
        val map = HashMap<MutableKey, String>().apply { put(key, "saved") }
        assertTrue(set.remove(key))
        val saved = map.remove(key)
        assertEquals("saved", saved)
        key.id = 2
        set.add(key)
        map[key] = saved!!
        assertFalse(set.contains(MutableKey(1)))
        assertTrue(set.contains(MutableKey(2)))
        assertNull(map[MutableKey(1)])
        assertEquals("saved", map[MutableKey(2)])
        assertEquals(1, set.size)
        assertEquals(1, map.size)
    }
}
