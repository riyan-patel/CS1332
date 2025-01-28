/*
 * This is a set of unit tests for CS 1332 (Spring 2024) Homework 02: CircularSinglyLinkedList.
 *
 * Rely on these tests at your own risk, but a lot of effort has gone into making
 * these tests as comprehensive as possible.
 *
 * These tests are built with JUnit 5, and will probably not compile on earlier JUnit versions,
 * so ensure you have JUnit 5 added to your project.
 *
 * If you have any questions, suggestions, or improvements, feel free to
 * email jkerrane3@gatech.edu, reply on Piazza, or leave a comment under the gist.
 *
 * The version number will bump up if any suggestions are incorporated, so check
 * back to see if new tests are added! Follows semver 2.0.0, so a bugfix/refactor is a patch, minor is new or
 * changed tests, major is breaking changes 😃.
 *
 * Reminder: It is a honor code violation to re-post these JUnits, so please
 * don't post these elsewhere. However, please feel free to take inspiration from
 * or base your own JUnits off of these if you like!
 *
 * @author James Kerrane
 * @version 1.0.0
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
public class CircularSinglyLinkedListTests {
    @Nested
    class CircularSinglyLinkedListSpecificationTests {
        @Test
        public void shouldStartWithJavaVersion17() {
            String version = System.getProperty("java.version");
            assertTrue(version.startsWith("17"), "Java version should be 17");
        }

        @Test
        public void shouldNotHaveAdditionalPublicMethods() throws NoSuchMethodException {
            Method[] expectedMethods = {
                    CircularSinglyLinkedList.class.getMethod("addAtIndex", int.class, Object.class),
                    CircularSinglyLinkedList.class.getMethod("addToFront", Object.class),
                    CircularSinglyLinkedList.class.getMethod("addToBack", Object.class),
                    CircularSinglyLinkedList.class.getMethod("removeAtIndex", int.class),
                    CircularSinglyLinkedList.class.getMethod("removeFromFront"),
                    CircularSinglyLinkedList.class.getMethod("removeFromBack"),
                    CircularSinglyLinkedList.class.getMethod("get", int.class),
                    CircularSinglyLinkedList.class.getMethod("isEmpty"),
                    CircularSinglyLinkedList.class.getMethod("clear"),
                    CircularSinglyLinkedList.class.getMethod("removeLastOccurrence", Object.class),
                    CircularSinglyLinkedList.class.getMethod("toArray"),
                    CircularSinglyLinkedList.class.getMethod("getHead"),
                    CircularSinglyLinkedList.class.getMethod("size"),
            };

            /*
                expectedMethods and actualMethods need to be checked for
                equality without caring about order, so HashSets are created
                which allow you to compare two sets of objects for
                equality without caring about order.
             */
            Set<Method> actualMethodSet = new HashSet<>();
            Set<Method> expectedMethodSet = new HashSet<>(Arrays.asList(expectedMethods));

            Method[] actualMethods = CircularSinglyLinkedList.class.getDeclaredMethods();
            for (Method actualMethod : actualMethods) {
                // Only add public methods, so private helper methods are allowed
                if (Modifier.isPublic(actualMethod.getModifiers())) {
                    actualMethodSet.add(actualMethod);
                }
            }

            // Print the methods that are in actualMethodSet but not in expectedMethodSet
            Set<Method> extraMethods = new HashSet<>(actualMethodSet);
            extraMethods.removeAll(expectedMethodSet); // This removes the expected methods from the actual method set

            // Convert the extra methods to a string for the assertion message
            StringBuilder extraMethodsMessage = new StringBuilder();
            if (!extraMethods.isEmpty()) {
                extraMethodsMessage.append(
                        "The CircularSinglyLinkedList class should not have additional public methods. "
                                + "Additional public methods found: ");
                for (Method extraMethod : extraMethods) {
                    extraMethodsMessage.append(extraMethod.getName())
                            .append("(")
                            .append(Arrays.toString(extraMethod.getParameterTypes()))
                            .append("), ");
                }
                // Remove the last comma and space for a cleaner output
                extraMethodsMessage.setLength(extraMethodsMessage.length() - 2);
            }

            // Assert equality, including the message with extra methods if any
            assertEquals(expectedMethodSet, actualMethodSet, extraMethodsMessage.toString());
        }

        @Nested
        class ConstructorTests {
            private Constructor<?>[] constructors;

            /**
             * Retrieve all declared constructors of CircularSinglyLinkedList class
             */
            @BeforeEach
            void setUp() {
                constructors = CircularSinglyLinkedList.class.getDeclaredConstructors();
            }

            @Test
            void shouldNotAddAdditionalConstructors() {
                // Assert that there is only one constructor (the default constructor)
                assertEquals(1, constructors.length,
                        "No constructors should be added to CircularSinglyLinkedList.");
            }

            @Test
            void shouldHaveDefaultConstructor() {
                // Assert that the constructor has no parameters (i.e., it's the default constructor)
                assertEquals(0, constructors[0].getParameterCount(),
                        "The constructor should be the default constructor (with no parameters).");
            }
        }

        @Nested
        class InstanceVariablesTests {
            @Test
            public void shouldNotAddOrRemoveInstanceFields() throws NoSuchFieldException {
                Field[] expectedFields = {
                        CircularSinglyLinkedList.class.getDeclaredField("head"),
                        CircularSinglyLinkedList.class.getDeclaredField("size"),
                };

                Field[] actualFields = CircularSinglyLinkedList.class.getDeclaredFields();

                // Create sets for easy comparison
                Set<Field> expectedFieldSet = new HashSet<>(Arrays.asList(expectedFields));
                Set<Field> actualFieldSet = new HashSet<>(Arrays.asList(actualFields));

                // Find any extra fields
                Set<Field> extraFields = new HashSet<>(actualFieldSet);
                extraFields.removeAll(expectedFieldSet);

                // Build the message for additional fields
                StringBuilder extraFieldsMessage = new StringBuilder();
                if (!extraFields.isEmpty()) {
                    extraFieldsMessage.append("Should not add new instance variables. "
                            + "Additional instance fields found: ");
                    for (Field extraField : extraFields) {
                        extraFieldsMessage.append(extraField.getName()).append(", ");
                    }
                    // Remove the last comma and space for cleaner output
                    extraFieldsMessage.setLength(extraFieldsMessage.length() - 2);
                }

                // Assert equality of expected and actual fields, including any extra fields in the message
                assertArrayEquals(expectedFields, actualFields, extraFieldsMessage.toString());
            }

            @Nested
            class HeadTests {
                private Field headField;

                /**
                 * Get head field for testing
                 */
                @BeforeEach
                void setUp() throws NoSuchFieldException {
                    headField = CircularSinglyLinkedList.class.getDeclaredField("head");
                    headField.setAccessible(true);
                }

                @Test void shouldHaveTypeCircularSinglyLinkedListNode() {
                    // Check that the head field is of type CircularSinglyLinkedList
                    assertEquals(CircularSinglyLinkedListNode.class, headField.getType(),
                            "Instance variable 'head' should be of type CircularSinglyLinkedListNode."
                    );
                }

                @Test
                void shouldHavePrivateModifier() {
                    // Check that the head field is private
                    int modifiers = headField.getModifiers();
                    assertTrue(Modifier.isPrivate(modifiers),
                            "Instance variable 'head' should be private.");
                }

                @Test void shouldNotInitialize() throws IllegalAccessException {
                    // Create a new instance of CircularSinglyLinkedList
                    CircularSinglyLinkedList<?> list = new CircularSinglyLinkedList<>();

                    // Access the value of the head field
                    Object headValue = headField.get(list);

                    // Assert that the head field is null
                    assertNull(headValue,
                            "Instance variable 'head' should not be initialized "
                                    + "(should be null) after construction.");
                }
            }

            @Nested
            class SizeTests {
                private Field sizeField;

                /**
                 * Get size field for testing
                 */
                @BeforeEach
                void setUp() throws NoSuchFieldException {
                    sizeField = CircularSinglyLinkedList.class.getDeclaredField("size");
                    sizeField.setAccessible(true);
                }

                @Test void shouldHaveTypeInt() {
                    // Check that the size field is of type int
                    assertEquals(int.class, sizeField.getType(),
                            "Instance variable 'size' should be of type int.");
                }

                @Test
                void shouldHavePrivateModifier() {
                    // Check that the head field is private
                    int modifiers = sizeField.getModifiers();
                    assertTrue(Modifier.isPrivate(modifiers),
                            "Instance variable 'size' should be private.");
                }

                @Test void shouldNotInitialize() throws IllegalAccessException {
                    // Create a new instance of CircularSinglyLinkedList
                    CircularSinglyLinkedList<?> list = new CircularSinglyLinkedList<>();

                    // Assert that the head field is null
                    int initialSize = (int) sizeField.get(list);
                    assertEquals(0, initialSize, "Instance variable 'size' should not be "
                            + "initialized (should be 0) after construction.");
                }
            }
        }
    }

    @Nested
    class AddAtIndexTests {
        private CircularSinglyLinkedList<String> list;

        /**
         * Initialize new CircularSinglyLinkedList for testing
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();

            list.addAtIndex(0, "a"); // head -> a -> head
            list.addAtIndex(1, "c"); // head -> a -> c -> head
            list.addAtIndex(1, "b"); // head -> a -> b -> c -> head
        }

        @Test
        void shouldInsertAtBeginning() {
            list.addAtIndex(0, "x"); // head -> x -> a -> b -> c -> head

            assertEquals(4, list.size(), "Size should be 4 after insertion.");
            assertEquals("x", list.getHead().getData(), "Head should be 'x'.");
            assertEquals("a", list.getHead().getNext().getData(), "Second node should be 'a'.");

            checkCircularIntegrity();
        }

        @Test
        void shouldInsertInMiddle() {
            list.addAtIndex(1, "x"); // head -> a -> x -> b -> c -> head

            assertEquals(4, list.size(), "Size should be 4 after insertion.");
            assertEquals("x", list.getHead().getNext().getData(), "Second node should be 'x'.");
            assertEquals("b", list.getHead().getNext().getNext().getData(), "Third node should be 'b'.");

            checkCircularIntegrity();
        }

        @Test
        void shouldInsertAtEnd() {
            list.addAtIndex(3, "x"); // head -> a -> b -> c -> x -> head

            assertEquals(4, list.size(), "Size should be 4 after insertion.");
            assertEquals("x", list.getHead().getNext().getNext().getNext().getData(), "Fourth node should be 'x'.");
            assertEquals("a", list.getHead().getData(), "Head should still be 'a'.");
            assertEquals("c", list.getHead().getNext().getNext().getData(), "Third node should be 'c'.");

            checkCircularIntegrity();
        }

        /**
         * Check circular integrity
         */
        private void checkCircularIntegrity() {
            CircularSinglyLinkedListNode<String> lastNode = list.getHead();
            for (int i = 0; i < list.size() - 1; i++) {
                lastNode = lastNode.getNext();
            }
            assertEquals(list.getHead(), lastNode.getNext(), "The last node's next should point to the head.");
        }

        @Nested
        class ExceptionTests {
            @Test
            public void shouldThrowExceptionWhenIndexIsNegative() {
                assertThrows(IndexOutOfBoundsException.class, () -> list.addAtIndex(-1, "1332"),
                        "addAtIndex should throw IndexOutOfBoundsException if index < 0");
            }

            @Test
            public void shouldThrowExceptionWhenIndexIsGreaterThanSize() {
                assertThrows(IndexOutOfBoundsException.class, () -> list.addAtIndex(4, "1332"),
                        "addAtIndex should throw IndexOutOfBoundsException if index > size");
            }
        }
    }

    @Nested
    class AddToFrontTests {
        private CircularSinglyLinkedList<String> list;

        /**
         * Initialize CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldThrowExceptionWhenDataIsNull() {
            assertThrows(IllegalArgumentException.class, () -> list.addToFront(null),
                    "addToFront should throw IllegalArgumentException if data is null.");
        }

        @Nested
        class SizeIsZeroTests {
            private String expectedHeadData;

            /**
             * Initialize CircularSinglyLinkedList of size 0
             */
            @BeforeEach
            void setUp() {
                expectedHeadData = "1332";
                list.addToFront(expectedHeadData); // head -> 1332 -> head
            }

            @Test
            public void shouldInitializeHeadToNewData() {
                assertEquals(new CircularSinglyLinkedListNode<>(expectedHeadData).getData(), list.getHead().getData(),
                        "When size is zero, the head should be initialized to a new node with the data.");
            }

            @Test
            public void shouldPointHeadToItself() {
                assertEquals(list.getHead().getNext(), list.getHead(),
                        "When size is zero, the head should be pointing to itself.");
            }

            @Test
            public void shouldIncrementSize() {
                assertEquals(1, list.size(),
                        "When size is zero, the size should be incremented.");
            }
        }

        @Nested
        class SizeIsPositiveTests {
            /**
             * Initialize CircularSinglyLinkedList with positive size.
             */
            @BeforeEach
            void setUp() {
                list.addToFront("1332"); // head -> 1332 -> head
                list.addToFront("1333"); // head -> 1333 -> 1332 -> head
            }

            @Test
            public void shouldUpdateHeadToNewData() {
                assertEquals("1333", list.getHead().getData(),
                        "The head of the list should be set to the most recently added data.");
            }

            @Test
            public void shouldPointNewNodeNextToPreviousHead() {
                assertEquals("1332", list.getHead().getNext().getData(),
                        "The new node's 'next' pointer should point to the previous head.");
            }

            @Test
            public void shouldPointHeadToNewNode() {
                assertEquals("1333", list.getHead().getNext().getNext().getData(),
                        "The head's 'next' should point to the newly added node.");
            }
        }
    }

    @Nested
    class AddToBackTests {
        private CircularSinglyLinkedList<String> list;

        /**
         * Initialize CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldThrowExceptionWhenDataIsNull() {
            assertThrows(IllegalArgumentException.class, () -> list.addToFront(null),
                    "addToBack should throw IllegalArgumentException if data is null.");
        }

        @Test
        public void shouldAddToBackInEmptyList() {
            list.addToBack("a"); // head -> a -> head

            assertEquals(1, list.size(), "Size should be 1 after adding to an empty list.");
            assertEquals("a", list.getHead().getData(), "The head should be 'a'.");
            assertEquals(list.getHead(), list.getHead().getNext(),
                    "The next of head should point to head, maintaining circularity.");
        }

        @Test
        public void shouldAddToBackInSingleElementList() {
            list.addToBack("a"); // head -> a -> head
            list.addToBack("b"); // head -> a -> b -> head

            assertEquals(2, list.size(), "Size should be 2 after adding a second element.");
            assertEquals("b", list.getHead().getNext().getData(), "The last node should be 'b'.");

            // Check circular integrity
            assertEquals(list.getHead(), list.getHead().getNext().getNext(), "The last node should point to head.");
        }

        @Test
        public void shouldAddMultipleItemsToBack() {
            list.addToBack("a"); // head -> a -> head
            list.addToBack("b"); // head -> a -> b -> head
            list.addToBack("c"); // head -> a -> b -> c -> head

            assertEquals(3, list.size(), "Size should be 3 after adding three elements.");
            assertEquals("c", list.getHead().getNext().getNext().getData(), "The last node should be 'c'.");

            // Check circular integrity
            assertEquals(list.getHead(), list.getHead().getNext().getNext().getNext(),
                    "The last node should point to head.");
        }

        @Test
        public void shouldMaintainCircularIntegrityWhenAddingToBack() {
            list.addToBack("a"); // head -> a -> head
            list.addToBack("b"); // head -> a -> b -> head
            list.addToBack("c"); // head -> a -> b -> c -> head
            list.addToBack("d"); // head -> a -> b -> c -> d -> head

            // Check circular integrity
            CircularSinglyLinkedListNode<String> lastNode = list.getHead();
            for (int i = 0; i < list.size() - 1; i++) {
                lastNode = lastNode.getNext();
            }
            assertEquals(list.getHead(), lastNode.getNext(), "The last node's next should point to the head.");
        }

        @Test
        public void shouldReturnCorrectSizeAfterMultipleAdditions() {
            list.addToBack("a"); // head -> a -> head
            list.addToBack("b"); // head -> a -> b -> head
            list.addToBack("c"); // head -> a -> b -> c -> head

            assertEquals(3, list.size(), "Size should be 3 after adding three elements.");
        }

        @Test
        public void shouldThrowExceptionIfDataIsNull() {
            assertThrows(IllegalArgumentException.class, () -> list.addToBack(null),
                    "addToBack should throw IllegalArgumentException if data is null.");
        }
    }

    @Nested
    class RemoveAtIndexTests {
        private CircularSinglyLinkedList<Integer> list;

        /**
         * Create new CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldRemoveFromFrontWhenIndexIsZero() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head

            int removed = list.removeAtIndex(0); // head -> 20 -> 30 -> head

            assertEquals(10, removed, "removeAtIndex(0) should return the first element.");
            assertEquals(2, list.size(),
                    "List size should decrease by 1 after removing the first element.");
            assertEquals(20, list.get(0),
                    "The new first element should now be the previous second element.");
        }

        @Test
        public void shouldRemoveFromBackWhenIndexIsSizeMinusOne() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head

            int removed = list.removeAtIndex(2); // head -> 10 -> 20 -> head

            assertEquals(30, removed, "removeAtIndex(size - 1) should return the last element.");
            assertEquals(2, list.size(),
                    "List size should decrease by 1 after removing the last element.");
            assertEquals(20, list.get(1),
                    "The new last element should now be the previous second-to-last element.");
        }

        @Test
        public void shouldRemoveFromMiddle() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head
            list.addToBack(40); // head -> 10 -> 20 -> 30 -> 40 -> head

            int removed = list.removeAtIndex(2); // head -> 10 -> 20 -> 40 -> head

            assertEquals(30, removed, "removeAtIndex(2) should return the element at index 2.");
            assertEquals(3, list.size(),
                    "List size should decrease by 1 after removing an element from the middle.");
            assertEquals(40, list.get(2),
                    "The element at index 2 should now be the element that was previously at index 3.");
        }

        @Test
        public void shouldRemoveFromMiddleInSingleElementList() {
            list.addToBack(10); // head -> 10 -> head

            int removed = list.removeAtIndex(0); // list becomes empty

            assertEquals(10, removed, "removeAtIndex(0) should return the only element in the list.");
            assertEquals(0, list.size(), "List size should be 0 after removing the only element.");
            assertNull(list.getHead(), "Head should be null after removing the only element.");
        }

        @Test
        public void shouldRemoveMultipleTimes() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head

            // Remove from the middle
            int removed1 = list.removeAtIndex(1); // head -> 10 -> 30 -> head
            assertEquals(20, removed1, "First removal should return the element at index 1.");
            assertEquals(2, list.size(), "List size should decrease by 1 after the first removal.");
            assertEquals(30, list.get(1),
                    "The new last element should now be the previous last element.");

            // Remove from the back
            int removed2 = list.removeAtIndex(1); // head -> 10 -> head
            assertEquals(30, removed2, "Second removal should return the last element.");
            assertEquals(1, list.size(), "List size should decrease by 1 after the second removal.");
            assertEquals(10, list.get(0), "The only remaining element should be 10.");

            // Remove the last element
            int removed3 = list.removeAtIndex(0); // list becomes empty
            assertEquals(10, removed3, "Third removal should return the last remaining element.");
            assertEquals(0, list.size(), "List size should be 0 after removing all elements.");
            assertNull(list.getHead(), "Head should be null after removing all elements.");
        }

        @Nested
        class ExceptionsTests {
            @Test
            public void shouldThrowExceptionWhenIndexIsNegative() {
                assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(-1),
                        "removeAtIndex should throw IndexOutOfBoundsException if index is negative.");
            }

            @Test
            public void shouldThrowExceptionWhenIndexIsEqualToSize() {
                assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(0),
                        "removeAtIndex should throw IndexOutOfBoundsException if index is equal to size.");
            }

            @Test
            public void shouldThrowExceptionWhenIndexIsGreaterThanSize() {
                assertThrows(IndexOutOfBoundsException.class, () -> list.removeAtIndex(1),
                        "removeAtIndex should throw IndexOutOfBoundsException if index is greater than size.");
            }
        }
    }

    @Nested
    class RemoveFromFrontTests {
        private CircularSinglyLinkedList<Integer> list;

        /**
         * Create new CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldThrowExceptionWhenListIsEmpty() {
            assertThrows(NoSuchElementException.class, () -> list.removeFromFront(),
                    "removeFromFront should throw NoSuchElementException if list is empty.");
        }

        @Test
        public void testRemoveFromFrontMultipleElements() {
            list.addToFront(20); // head -> 20 -> head
            list.addToFront(10); // head -> 10 -> 20 -> head

            // Remove the first element
            int removed = list.removeFromFront(); // head -> 20 -> head

            // Verify the removed element and the state of the list
            assertEquals(10, removed);
            assertEquals(1, list.size());
            assertEquals(20, (int) list.get(0));
        }
    }

    @Nested
    class RemoveFromBackTests {
        private CircularSinglyLinkedList<Integer> list;

        /**
         * Create new CircularSinglyLinkedList for testing
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldRemoveLastElementInSingleElementList() {
            list.addToBack(10); // head -> 10 -> head
            int removed = list.removeFromBack(); // list becomes empty

            // Verify the removed element and the state of the list
            assertEquals(10, removed, "removeFromBack() should return the only element in the list.");
            assertEquals(0, list.size(), "List size should be 0 after removing the only element.");
            assertNull(list.getHead(), "Head should be null after removing the only element.");
        }

        @Test
        public void shouldRemoveLastElementInMultipleElementList() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head

            // Remove the last element
            int removed = list.removeFromBack(); // head -> 10 -> 20 -> head

            // Verify the removed element and the state of the list
            assertEquals(30, removed,
                    "removeFromBack() should return the last element in the list.");
            assertEquals(2, list.size(),
                    "List size should decrease by 1 after removing the last element.");
            assertEquals(20, list.get(1),
                    "The new last element should now be the second-to-last element.");

            // Verify circularity
            CircularSinglyLinkedListNode<Integer> lastNode = list.getHead();
            for (int i = 0; i < list.size() - 1; i++) {
                lastNode = lastNode.getNext();
            }
            assertSame(list.getHead(), lastNode.getNext(),
                    "The last node should point to the head, ensuring circularity.");
        }

        @Test
        public void shouldUpdateHeadProperly() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head

            int removed = list.removeFromBack(); // head -> 10 -> head

            // Verify that the head remains unchanged
            assertEquals(20, removed, "removeFromBack() should return the last element.");
            assertEquals(10, list.getHead().getData(),
                    "The head of the list should remain the same.");

            // Verify circularity
            CircularSinglyLinkedListNode<Integer> lastNode = list.getHead();
            for (int i = 0; i < list.size() - 1; i++) {
                lastNode = lastNode.getNext();
            }
            assertSame(list.getHead(), lastNode.getNext(),
                    "The last node should point to the head, ensuring circularity.");
        }

        @Test
        public void shouldRemoveElementsUntilListIsEmpty() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head

            // Remove all elements one by one
            assertEquals(30, list.removeFromBack(),
                    "First removal should return the last element (30).");
            assertEquals(2, list.size(),
                    "List size should decrease by 1 after the first removal.");

            assertEquals(20, list.removeFromBack(),
                    "Second removal should return the next last element (20).");
            assertEquals(1, list.size(),
                    "List size should decrease by 1 after the second removal.");

            assertEquals(10, list.removeFromBack(),
                    "Third removal should return the last remaining element (10).");
            assertEquals(0, list.size(),
                    "List size should be 0 after removing all elements.");
            assertNull(list.getHead(),
                    "Head should be null after removing all elements.");
        }

        @Test
        public void shouldThrowExceptionWhenListIsEmpty() {
            assertThrows(NoSuchElementException.class, () -> list.removeFromBack(),
                    "removeFromBack should throw NoSuchElementException if list is empty.");
        }
    }

    @Nested
    class GetTests {
        private CircularSinglyLinkedList<Integer> list;

        /**
         * Initialize CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldReturnCorrectDataAtIndexZero() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            assertEquals(10, list.get(0), "get(0) should return the first element in the list.");
        }

        @Test
        public void shouldReturnCorrectDataAtLastIndex() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head
            assertEquals(30, list.get(2), "get(2) should return the last element in the list.");
        }

        @Test
        public void shouldReturnCorrectDataForMiddleIndex() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head
            assertEquals(20, list.get(1), "get(1) should return the middle element in the list.");
        }

        @Test
        public void shouldHandleSingleElementList() {
            list.addToBack(10); // head -> 10 -> head
            assertEquals(10, list.get(0),
                    "get(0) should return the only element in a single-element list.");
        }

        @Test
        public void shouldHandleEmptyList() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(0),
                    "get() should throw IndexOutOfBoundsException if the list is empty.");
        }

        @Nested
        class ExceptionTests {
            @Test
            public void shouldThrowExceptionWhenIndexIsNegative() {
                assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1),
                        "Cannot get index of negative value.");
            }

            @Test
            public void shouldThrowExceptionWhenIndexIsEqualToSize() {
                assertThrows(IndexOutOfBoundsException.class, () -> list.get(0),
                        "Cannot get index of equal size value.");
            }

            @Test
            public void shouldThrowExceptionWhenIndexIsGreaterThanSize() {
                assertThrows(IndexOutOfBoundsException.class, () -> list.get(1),
                        "Cannot get index of greater size value.");
            }
        }
    }

    @Nested
    class IsEmptyTests {
        private CircularSinglyLinkedList<String> list;

        /**
         * Create CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldReturnTrueIfEmpty() {
            assertTrue(list.isEmpty(),
                    "isEmpty should return true if the list is empty.");
        }

        @Test
        public void shouldReturnFalseIfListIsNotEmpty() {
            list.addToFront("a");
            assertFalse(list.isEmpty(),
                    "isEmpty should return false if the list is not empty.");
        }
    }

    @Nested
    class ClearTests {
        private CircularSinglyLinkedList<Integer> list;

        /**
         * Create CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void testClearOnEmptyList() {
            // List is initially empty
            assertTrue(list.isEmpty());
            assertEquals(0, list.size());

            // Call clear
            list.clear();

            // After clearing, the list should remain empty
            assertTrue(list.isEmpty());
            assertEquals(0, list.size());
        }

        @Test
        public void testClearOnListWithOneElement() {
            list.addToFront(10);  // head -> 10 -> head

            // Verify the list has one element
            assertFalse(list.isEmpty());
            assertEquals(1, list.size());

            // Call clear
            list.clear();

            // After clearing, the list should be empty
            assertTrue(list.isEmpty());
            assertEquals(0, list.size());
        }

        @Test
        public void testClearOnListWithMultipleElements() {
            list.addToFront(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head

            // Verify the list has two elements
            assertFalse(list.isEmpty());
            assertEquals(2, list.size());

            // Call clear
            list.clear();

            // After clearing, the list should be empty
            assertTrue(list.isEmpty());
            assertEquals(0, list.size());
        }

        @Test
        public void testClearThenAddNewElement() {
            CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
            list.addToFront(10); // head -> 10 -> head
            list.clear();  // Clear the list

            // Add a new element after clearing
            list.addToFront(20); // head -> 20 -> head

            // Verify the new element is in the list
            assertFalse(list.isEmpty());
            assertEquals(1, list.size());
            assertEquals(20, (int) list.get(0));
        }

        @Test
        public void testMultipleClears() {
            CircularSinglyLinkedList<Integer> list = new CircularSinglyLinkedList<>();
            list.addToFront(10);
            list.addToBack(20);

            // Verify the list has two elements
            assertEquals(2, list.size());

            // Call clear
            list.clear();
            assertTrue(list.isEmpty());
            assertEquals(0, list.size());

            // Call clear again
            list.clear();
            assertTrue(list.isEmpty());
            assertEquals(0, list.size());
        }
    }

    @Nested
    class RemoveLastOccurrenceTests {
        private CircularSinglyLinkedList<Integer> list;

        /**
         * Create CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldRemoveLastOccurrenceWhenDataExistsOnce() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head

            int removed = list.removeLastOccurrence(20); // head -> 10 -> 30 -> head

            assertEquals(20, removed,
                    "removeLastOccurrence should return the data that was removed.");
            assertEquals(2, list.size(),
                    "List size should decrease by 1 after removing the last occurrence.");
            assertArrayEquals(new Integer[]{10, 30},
                    list.toArray(), "List should no longer contain the removed element.");
        }

        @Test
        public void shouldRemoveLastOccurrenceWhenDataExistsMultipleTimes() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head
            list.addToBack(20); // head -> 10 -> 20 -> 30 -> 20 -> head

            int removed = list.removeLastOccurrence(20); // head -> 10 -> 20 -> 30 -> head

            assertEquals(20, removed,
                    "removeLastOccurrence should return the last occurrence of the data.");
            assertEquals(3, list.size(),
                    "List size should decrease by 1 after removing the last occurrence.");
            assertArrayEquals(new Integer[]{10, 20, 30}, list.toArray(),
                    "List should only remove the last occurrence of the data.");
        }

        @Test
        public void shouldRemoveLastOccurrenceWhenDataIsAtTheEnd() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head

            int removed = list.removeLastOccurrence(30); // head -> 10 -> 20 -> head

            assertEquals(30, removed,
                    "removeLastOccurrence should return the last element if the data is at the end.");
            assertEquals(2, list.size(),
                    "List size should decrease by 1 after removing the last occurrence.");
            assertArrayEquals(new Integer[]{10, 20}, list.toArray(), "The last element should be removed.");
        }

        @Test
        public void shouldRemoveLastOccurrenceWhenDataIsAtTheFront() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(20); // head -> 10 -> 20 -> head
            list.addToBack(30); // head -> 10 -> 20 -> 30 -> head

            int removed = list.removeLastOccurrence(10); // head -> 20 -> 30 -> head

            assertEquals(10, removed,
                    "removeLastOccurrence should remove the front element if it is the last occurrence.");
            assertEquals(2, list.size(),
                    "List size should decrease by 1 after removing the last occurrence.");
            assertArrayEquals(new Integer[]{20, 30},
                    list.toArray(), "The front element should be removed.");
        }

        @Test
        public void shouldRemoveLastOccurrenceInSingleElementList() {
            list.addToBack(10); // head -> 10 -> head

            int removed = list.removeLastOccurrence(10); // list becomes empty

            assertEquals(10, removed,
                    "removeLastOccurrence should remove the only element if it matches the data.");
            assertEquals(0, list.size(),
                    "List size should be 0 after removing the only element.");
            assertNull(list.getHead(),
                    "Head should be null after removing the only element.");
        }

        @Test
        public void shouldRemoveLastOccurrenceInListWithDuplicates() {
            list.addToBack(10); // head -> 10 -> head
            list.addToBack(10); // head -> 10 -> 10 -> head
            list.addToBack(20); // head -> 10 -> 10 -> 20 -> head
            list.addToBack(10); // head -> 10 -> 10 -> 20 -> 10 -> head

            int removed = list.removeLastOccurrence(10); // head -> 10 -> 10 -> 20 -> head

            assertEquals(10, removed,
                    "removeLastOccurrence should remove the last occurrence of the data.");
            assertEquals(3, list.size(),
                    "List size should decrease by 1 after removing the last occurrence.");
            assertArrayEquals(new Integer[]{10, 10, 20},
                    list.toArray(), "List should retain other occurrences of the data.");
        }

        @Nested
        class ExceptionTests {
            @Test
            public void shouldThrowExceptionIfDataIsNull() {
                assertThrows(IllegalArgumentException.class, () -> list.removeLastOccurrence(null),
                        "removeLastOccurrence should throw IllegalArgumentException when data is null.");
            }

            @Test
            public void shouldThrowExceptionIfDataIsNotFound() {
                list.addToFront(10); // head -> 10 -> head
                list.addToFront(20); // head -> 20 -> 10 -> head
                list.addToFront(30); // head -> 30 -> 20 -> 10 -> head
                assertThrows(NoSuchElementException.class, () -> list.removeLastOccurrence(40),
                        "removeLastOccurrence should throw NoSuchElementException "
                                + "when data is not found in the list.");
            }
        }
    }

    @Nested
    class ToArrayTests {
        private CircularSinglyLinkedList<Integer> list;

        /**
         * Create new CircularSinglyLinkedList for testing.
         */
        @BeforeEach
        void setUp() {
            list = new CircularSinglyLinkedList<>();
        }

        @Test
        public void shouldReturnEmptyArrayOnEmptyList() {
            // Convert empty list to array
            Object[] array = list.toArray();

            // Verify the array is empty
            assertEquals(0, array.length, "Array length should be zero.");
        }

        @Test
        public void shouldRepresentSingleElement() {
            list.addToFront(10);

            // Convert list to array
            Object[] array = list.toArray();

            // Verify the array size is 1 and the content is correct
            assertEquals(1, array.length);
            assertEquals(10, (int) array[0]);
        }

        @Test
        public void shouldRepresentMultipleElements() {
            list.addToFront(10);
            list.addToBack(20);
            list.addToBack(30);

            // Convert list to array
            Object[] array = list.toArray();

            // Verify the array size and contents are correct
            assertEquals(3, array.length);
            assertEquals(10, (int) array[0]);
            assertEquals(20, (int) array[1]);
            assertEquals(30, (int) array[2]);
        }
    }
}
