import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * This is a basic set of unit tests for LinearProbingHashMap.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs, tkapoor30
 * @version 1.0
 */
public class LinearProbingHashMapStudentTest {

    private static final int TIMEOUT = 200;
    private LinearProbingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new LinearProbingHashMap<>();

    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPut() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[4].setRemoved(true);
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals("A", map.get(1));
        assertEquals("B", map.get(2));
        assertEquals("C", map.get(3));
        assertEquals("D", map.get(4));
        assertEquals("E", map.get(5));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(6));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E)]
        map.resizeBackingTable(6);
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[6];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testNoIllegalCast() {
        LinearProbingHashMap<String, String> stringMap = new LinearProbingHashMap<>();
        stringMap.put("a", "a");
        stringMap.containsKey("a");
        stringMap.get("a");
        stringMap.remove("a");
    }

    // ###### MY TESTS ########
    @Test
    public void testAllExceptions() {
        assertThrows(IllegalArgumentException.class, () -> {
            map.put(null, "A");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            map.put(1, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            map.put(null, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            map.remove(null);
        });

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(3);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            map.get(null);
        });

        assertThrows(NoSuchElementException.class, () -> {
            map.get(3);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            map.containsKey(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            map.resizeBackingTable(-1);
        });
    }

    @Test
    public void testPutOnEmptyMap() {
        map.put(0, "A");
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(1, map.size());
    }

    @Test
    public void testPutOnSameHash() {
        map.put(0, "A");
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(1, map.size());

        map.put(13, "A");
        assertEquals(new LinearProbingMapEntry<>(13, "A"), table[1]);
        assertEquals(2, map.size());
    }


    @Test
    public void testPutOnRemovedSameKey() {
        assertNull(map.put(0, "A"));
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(1, map.size());

        assertNull(map.put(13, "A"));
        assertEquals(new LinearProbingMapEntry<>(13, "A"), table[1]);
        assertEquals(2, map.size());

        assertNull(map.put(2, "B"));
        assertNull(map.put(7, "C"));
        assertEquals(new LinearProbingMapEntry<>(2, "B"), table[2]);
        assertEquals(new LinearProbingMapEntry<>(7, "C"), table[7]);
        assertEquals(4, map.size());

        String removedValue = map.remove(2);
        assertEquals(removedValue, "B");
        assertEquals(3, map.size());
        assertTrue(table[2].isRemoved());

        assertNull(map.put(2, "C"));
        assertEquals(new LinearProbingMapEntry<>(2, "C"), table[2]);
        assertFalse(table[2].isRemoved());
        assertEquals(4, map.size());

    }

    @Test
    public void testPutOnDELMarkerAfterReachingNull() {
        assertNull(map.put(0, "A"));
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(1, map.size());

        assertNull(map.put(13, "A"));
        assertEquals(new LinearProbingMapEntry<>(13, "A"), table[1]);
        assertEquals(2, map.size());

        assertNull(map.put(2, "B"));
        assertNull(map.put(7, "C"));
        assertEquals(new LinearProbingMapEntry<>(2, "B"), table[2]);
        assertEquals(new LinearProbingMapEntry<>(7, "C"), table[7]);
        assertEquals(4, map.size());

        String removedValue = map.remove(2);
        assertEquals(removedValue, "B");
        assertEquals(3, map.size());
        assertTrue(table[2].isRemoved());

        removedValue = map.remove(13);
        assertEquals(removedValue, "A");
        assertEquals(2, map.size());
        assertTrue(table[1].isRemoved());


        assertNull(map.put(26, "CCC"));
        assertEquals(3, map.size());
        assertFalse(table[1].isRemoved());
        assertEquals(new LinearProbingMapEntry<>(26, "CCC"), table[1]);
    }

    @Test
    public void testPutOnSameKeyNotRemoved() {
        assertNull(map.put(0, "A"));
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(1, map.size());

        assertNull(map.put(13, "A"));
        assertEquals(new LinearProbingMapEntry<>(13, "A"), table[1]);
        assertEquals(2, map.size());

        assertNull(map.put(2, "B"));
        assertNull(map.put(7, "C"));
        assertEquals(new LinearProbingMapEntry<>(2, "B"), table[2]);
        assertEquals(new LinearProbingMapEntry<>(7, "C"), table[7]);
        assertEquals(4, map.size());

        String removedValue = map.remove(13);
        assertEquals(removedValue, "A");
        assertEquals(3, map.size());
        assertTrue(table[1].isRemoved());

        assertEquals("B", map.put(2, "C"));
        assertEquals(new LinearProbingMapEntry<>(2, "C"), table[2]);
        assertEquals("C", table[2].getValue());
        assertEquals(3, map.size());
    }

    @Test
    public void testFirstResizeWorks() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(6, "G"));
        assertNull(map.put(7, "H"));
        assertEquals(8, map.size());

        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(13, table.length);

        map.put(8, "I");
        table = map.getTable();
        assertEquals(27, table.length);

        map.put(22, "Hi");
        assertEquals(10, map.size());

        assertNotNull(table[0]);
        assertNotNull(table[1]);
        assertNotNull(table[2]);
        assertNotNull(table[3]);
        assertNotNull(table[4]);
        assertNotNull(table[5]);
        assertNotNull(table[6]);
        assertNotNull(table[7]);
        assertNotNull(table[8]);
        assertNull(table[9]);
        assertNull(table[10]);
        assertNull(table[11]);
        assertNull(table[12]);
        assertNull(table[13]);
        assertNull(table[14]);
        assertNull(table[15]);
        assertNull(table[16]);
        assertNull(table[17]);
        assertNull(table[18]);
        assertNull(table[19]);
        assertNull(table[20]);
        assertNull(table[21]);
        assertNotNull(table[22]);
        assertNull(table[23]);
        assertNull(table[24]);
        assertNull(table[25]);
        assertNull(table[26]);

        assertEquals("Hi", table[22].getValue());
        assertEquals("I", table[8].getValue());

    }

    @Test
    public void testResizeWithRemovedEntries() {
        assertNull(map.put(0, "A")); // 1
        assertNull(map.put(1, "B")); // 2
        assertEquals("B", map.remove(1)); // 1
        assertNull(map.put(2, "C")); // 2
        assertNull(map.put(3, "D")); // 3
        assertEquals("D", map.remove(3)); // 2
        assertNull(map.put(4, "E")); // 3
        assertNull(map.put(5, "F")); // 4
        assertEquals("F", map.remove(5)); // 3
        assertNull(map.put(6, "G")); // 4
        assertNull(map.put(7, "H")); // 5
        assertNull(map.put(21, "in8")); // 6
        assertNull(map.put(9, "Z")); // 7
        assertNull(map.put(13, "replaceDEL"));

        assertEquals(8, map.size());

        /*
        [ <0, "A">, <13, "replaceDEL">, <2, "C">, DEL, <4, "E">, DEL, <6, "G">, <7, "H">, <21, "in8">, <9, "Z">,
        null, null, null]
         */

        map.put(27, "resize"); // triggers resize
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(27, table.length);

        // check that elements are correctly placed in backing array
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(new LinearProbingMapEntry<>(13, "replaceDEL"), table[13]);
        assertEquals(new LinearProbingMapEntry<>(27, "resize"), table[1]);
        assertEquals(new LinearProbingMapEntry<>(2, "C"), table[2]);
        assertNull(table[3]);
        assertEquals(new LinearProbingMapEntry<>(4, "E"), table[4]);
        assertNull(table[5]);
        assertEquals(new LinearProbingMapEntry<>(6, "G"), table[6]);
        assertEquals(new LinearProbingMapEntry<>(7, "H"), table[7]);
        assertEquals(new LinearProbingMapEntry<>(21, "in8"), table[21]);
        assertEquals(new LinearProbingMapEntry<>(9, "Z"), table[9]);


        assertEquals(9, map.size());

    }

    @Test
    public void testRemoveOnResizedMap() {
        assertNull(map.put(0, "A")); // 1
        assertNull(map.put(1, "B")); // 2
        assertEquals("B", map.remove(1)); // 1
        assertNull(map.put(2, "C")); // 2
        assertNull(map.put(3, "D")); // 3
        assertEquals("D", map.remove(3)); // 2
        assertNull(map.put(4, "E")); // 3
        assertNull(map.put(5, "F")); // 4
        assertEquals("F", map.remove(5)); // 3
        assertNull(map.put(6, "G")); // 4
        assertNull(map.put(7, "H")); // 5
        assertNull(map.put(21, "in8")); // 6
        assertNull(map.put(9, "Z")); // 7
        assertNull(map.put(13, "replaceDEL"));

        assertEquals(8, map.size());

        /*
        [ <0, "A">, <13, "replaceDEL">, <2, "C">, DEL, <4, "E">, DEL, <6, "G">, <7, "H">, <21, "in8">, <9, "Z">,
        null, null, null]
         */

        map.put(27, "resize"); // triggers resize
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(27, table.length);

        // check that elements are correctly placed in backing array
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(new LinearProbingMapEntry<>(13, "replaceDEL"), table[13]);
        assertEquals(new LinearProbingMapEntry<>(27, "resize"), table[1]);
        assertEquals(new LinearProbingMapEntry<>(2, "C"), table[2]);
        assertNull(table[3]);
        assertEquals(new LinearProbingMapEntry<>(4, "E"), table[4]);
        assertNull(table[5]);
        assertEquals(new LinearProbingMapEntry<>(6, "G"), table[6]);
        assertEquals(new LinearProbingMapEntry<>(7, "H"), table[7]);
        assertEquals(new LinearProbingMapEntry<>(21, "in8"), table[21]);
        assertEquals(new LinearProbingMapEntry<>(9, "Z"), table[9]);

        assertEquals(9, map.size());

        String removedValue = map.remove(27);
        assertEquals(removedValue, "resize");
        assertTrue(table[1].isRemoved());

        assertEquals(8, map.size());

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(27);
        });

        assertThrows(NoSuchElementException.class, () -> {
            map.remove(120490);
        });

    }

    @Test
    public void testRemoveOnEmptyMap() {
        assertThrows(NoSuchElementException.class, () -> {
            map.remove(0);
        });
    }

    @Test
    public void testGetOnResizedMap() {
        assertNull(map.put(0, "A")); // 1
        assertNull(map.put(1, "B")); // 2
        assertEquals("B", map.remove(1)); // 1
        assertNull(map.put(2, "C")); // 2
        assertNull(map.put(3, "D")); // 3
        assertEquals("D", map.remove(3)); // 2
        assertNull(map.put(4, "E")); // 3
        assertNull(map.put(5, "F")); // 4
        assertEquals("F", map.remove(5)); // 3
        assertNull(map.put(6, "G")); // 4
        assertNull(map.put(7, "H")); // 5
        assertNull(map.put(21, "in8")); // 6
        assertNull(map.put(9, "Z")); // 7
        assertNull(map.put(13, "replaceDEL"));

        assertEquals(8, map.size());

        /*
        [ <0, "A">, <13, "replaceDEL">, <2, "C">, DEL, <4, "E">, DEL, <6, "G">, <7, "H">, <21, "in8">, <9, "Z">,
        null, null, null]
         */

        map.put(27, "resize"); // triggers resize
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(27, table.length);

        // check that elements are correctly placed in backing array
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(new LinearProbingMapEntry<>(13, "replaceDEL"), table[13]);
        assertEquals(new LinearProbingMapEntry<>(27, "resize"), table[1]);
        assertEquals(new LinearProbingMapEntry<>(2, "C"), table[2]);
        assertNull(table[3]);
        assertEquals(new LinearProbingMapEntry<>(4, "E"), table[4]);
        assertNull(table[5]);
        assertEquals(new LinearProbingMapEntry<>(6, "G"), table[6]);
        assertEquals(new LinearProbingMapEntry<>(7, "H"), table[7]);
        assertEquals(new LinearProbingMapEntry<>(21, "in8"), table[21]);
        assertEquals(new LinearProbingMapEntry<>(9, "Z"), table[9]);


        assertEquals(9, map.size());

        String valueGot = map.get(21);
        assertEquals(valueGot, "in8");

        map.remove(13);
        assertTrue(table[13].isRemoved());

        assertThrows(NoSuchElementException.class, () -> {
            map.get(13);
        });

        assertThrows(NoSuchElementException.class, () -> {
            map.get(200);
        });
    }

    @Test
    public void testGetOnEmptyMap() {
        assertThrows(NoSuchElementException.class, () -> {
            map.get(0);
        });
    }

    @Test
    public void testContainsKeyOnEmptyMap() {
        assertFalse(map.containsKey(0));
    }

    @Test
    public void testContainsKeyOnResizedMap() {
        assertNull(map.put(0, "A")); // 1
        assertNull(map.put(1, "B")); // 2
        assertEquals("B", map.remove(1)); // 1
        assertNull(map.put(2, "C")); // 2
        assertNull(map.put(3, "D")); // 3
        assertEquals("D", map.remove(3)); // 2
        assertNull(map.put(4, "E")); // 3
        assertNull(map.put(5, "F")); // 4
        assertEquals("F", map.remove(5)); // 3
        assertNull(map.put(6, "G")); // 4
        assertNull(map.put(7, "H")); // 5
        assertNull(map.put(21, "in8")); // 6
        assertNull(map.put(9, "Z")); // 7
        assertNull(map.put(13, "replaceDEL"));

        assertEquals(8, map.size());

        /*
        [ <0, "A">, <13, "replaceDEL">, <2, "C">, DEL, <4, "E">, DEL, <6, "G">, <7, "H">, <21, "in8">, <9, "Z">,
        null, null, null]
         */

        map.put(27, "resize"); // triggers resize
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(27, table.length);

        // check that elements are correctly placed in backing array
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(new LinearProbingMapEntry<>(13, "replaceDEL"), table[13]);
        assertEquals(new LinearProbingMapEntry<>(27, "resize"), table[1]);
        assertEquals(new LinearProbingMapEntry<>(2, "C"), table[2]);
        assertNull(table[3]);
        assertEquals(new LinearProbingMapEntry<>(4, "E"), table[4]);
        assertNull(table[5]);
        assertEquals(new LinearProbingMapEntry<>(6, "G"), table[6]);
        assertEquals(new LinearProbingMapEntry<>(7, "H"), table[7]);
        assertEquals(new LinearProbingMapEntry<>(21, "in8"), table[21]);
        assertEquals(new LinearProbingMapEntry<>(9, "Z"), table[9]);

        assertEquals(9, map.size());

        assertTrue(map.containsKey(21));
        assertTrue(map.containsKey(0));
        assertTrue(map.containsKey(13));
        assertTrue(map.containsKey(27));
        assertTrue(map.containsKey(2));
        assertTrue(map.containsKey(4));
        assertTrue(map.containsKey(6));
        assertTrue(map.containsKey(7));
        assertTrue(map.containsKey(9));

        map.remove(21);
        assertTrue(table[21].isRemoved());

        assertFalse(map.containsKey(21));

    }


    @Test
    public void testKeySetOnEmptyMap() {
        Set<Integer> expectedKeySet = new HashSet<>();
        assertEquals(expectedKeySet, map.keySet());
    }

    @Test
    public void testKeySetOnResizedMap() {
        assertNull(map.put(0, "A")); // 1
        assertNull(map.put(1, "B")); // 2
        assertEquals("B", map.remove(1)); // 1
        assertNull(map.put(2, "C")); // 2
        assertNull(map.put(3, "D")); // 3
        assertEquals("D", map.remove(3)); // 2
        assertNull(map.put(4, "E")); // 3
        assertNull(map.put(5, "F")); // 4
        assertEquals("F", map.remove(5)); // 3
        assertNull(map.put(6, "G")); // 4
        assertNull(map.put(7, "H")); // 5
        assertNull(map.put(21, "in8")); // 6
        assertNull(map.put(9, "Z")); // 7
        assertNull(map.put(13, "replaceDEL"));

        assertEquals(8, map.size());

        /*
        [ <0, "A">, <13, "replaceDEL">, <2, "C">, DEL, <4, "E">, DEL, <6, "G">, <7, "H">, <21, "in8">, <9, "Z">,
        null, null, null]
         */

        map.put(27, "resize"); // triggers resize
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(27, table.length);

        // check that elements are correctly placed in backing array
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(new LinearProbingMapEntry<>(13, "replaceDEL"), table[13]);
        assertEquals(new LinearProbingMapEntry<>(27, "resize"), table[1]);
        assertEquals(new LinearProbingMapEntry<>(2, "C"), table[2]);
        assertNull(table[3]);
        assertEquals(new LinearProbingMapEntry<>(4, "E"), table[4]);
        assertNull(table[5]);
        assertEquals(new LinearProbingMapEntry<>(6, "G"), table[6]);
        assertEquals(new LinearProbingMapEntry<>(7, "H"), table[7]);
        assertEquals(new LinearProbingMapEntry<>(21, "in8"), table[21]);
        assertEquals(new LinearProbingMapEntry<>(9, "Z"), table[9]);

        assertEquals(9, map.size());

        HashSet<Integer> keySetExpected = (HashSet<Integer>) map.keySet();
        assertEquals(9, keySetExpected.size());

        assertTrue(keySetExpected.contains(0));
        assertTrue(keySetExpected.contains(2));
        assertTrue(keySetExpected.contains(4));
        assertTrue(keySetExpected.contains(6));
        assertTrue(keySetExpected.contains(7));
        assertTrue(keySetExpected.contains(9));
        assertTrue(keySetExpected.contains(13));
        assertTrue(keySetExpected.contains(21));
        assertTrue(keySetExpected.contains(27));

        HashSet<Integer> myKeys = new HashSet<>();
        myKeys.add(0);

        myKeys.add(2);
        myKeys.add(4);
        myKeys.add(6);
        myKeys.add(7);
        myKeys.add(9);
        myKeys.add(13);
        myKeys.add(21);

        myKeys.add(27);



        assertEquals(keySetExpected, myKeys);

    }

    @Test
    public void testValuesOnEmptyMap() {
        ArrayList<String> valuesList = new ArrayList<>();
        assertEquals(valuesList, map.values());
    }


    @Test
    public void testValuesOnResizedMap() {
        assertNull(map.put(0, "A")); // 1
        assertNull(map.put(1, "B")); // 2
        assertEquals("B", map.remove(1)); // 1
        assertNull(map.put(2, "C")); // 2
        assertNull(map.put(3, "D")); // 3
        assertEquals("D", map.remove(3)); // 2
        assertNull(map.put(4, "E")); // 3
        assertNull(map.put(5, "F")); // 4
        assertEquals("F", map.remove(5)); // 3
        assertNull(map.put(6, "G")); // 4
        assertNull(map.put(7, "H")); // 5
        assertNull(map.put(21, "in8")); // 6
        assertNull(map.put(9, "Z")); // 7
        assertNull(map.put(13, "replaceDEL"));

        assertEquals(8, map.size());

        /*
        [ <0, "A">, <13, "replaceDEL">, <2, "C">, DEL, <4, "E">, DEL, <6, "G">, <7, "H">, <21, "in8">, <9, "Z">,
        null, null, null]
         */

        map.put(27, "resize"); // triggers resize
        LinearProbingMapEntry[] table = map.getTable();
        assertEquals(27, table.length);

        // check that elements are correctly placed in backing array
        assertEquals(new LinearProbingMapEntry<>(0, "A"), table[0]);
        assertEquals(new LinearProbingMapEntry<>(13, "replaceDEL"), table[13]);
        assertEquals(new LinearProbingMapEntry<>(27, "resize"), table[1]);
        assertEquals(new LinearProbingMapEntry<>(2, "C"), table[2]);
        assertNull(table[3]);
        assertEquals(new LinearProbingMapEntry<>(4, "E"), table[4]);
        assertNull(table[5]);
        assertEquals(new LinearProbingMapEntry<>(6, "G"), table[6]);
        assertEquals(new LinearProbingMapEntry<>(7, "H"), table[7]);
        assertEquals(new LinearProbingMapEntry<>(21, "in8"), table[21]);
        assertEquals(new LinearProbingMapEntry<>(9, "Z"), table[9]);
        assertEquals(9, map.size());

        ArrayList<String> expectedValuesList = (ArrayList<String>) map.values();
        assertEquals(9, expectedValuesList.size());

        ArrayList<String> myValues = new ArrayList<>();
        myValues.add("A");
        myValues.add("resize");
        myValues.add("C");
        myValues.add("E");
        myValues.add("G");
        myValues.add("H");
        myValues.add("Z");
        myValues.add("replaceDEL");
        myValues.add("in8");


        assertEquals(expectedValuesList, myValues);


    }



}