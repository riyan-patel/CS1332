import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is a basic set of unit tests for Sorting.
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
 * @author CS 1332 TAs, Thinh Nguyen
 * @version 1.0
 */
public class SortingStudentTest {

    private static final int TIMEOUT = 200;
    private IntegerWrapper[] integers;
    private IntegerWrapper[] sortedIntegers;
    private ComparatorPlus<IntegerWrapper> comp;

    @Before
    public void setUp() {
        /*
            Initial array:
                index 0: 5
                index 1: 4
                index 2: 2
                index 3: 3
                index 4: 6
                index 5: 1
                index 6: 0
                index 7: 7
         */

        /*
            Sorted array:
                index 0: 0
                index 1: 1
                index 2: 2
                index 3: 3
                index 4: 4
                index 5: 5
                index 6: 6
                index 7: 7
         */

        integers = new IntegerWrapper[8];
        integers[0] = new IntegerWrapper(5);
        integers[1] = new IntegerWrapper(4);
        integers[2] = new IntegerWrapper(2);
        integers[3] = new IntegerWrapper(3);
        integers[4] = new IntegerWrapper(6);
        integers[5] = new IntegerWrapper(1);
        integers[6] = new IntegerWrapper(0);
        integers[7] = new IntegerWrapper(7);

        sortedIntegers = new IntegerWrapper[integers.length];
        for (int i = 0; i < sortedIntegers.length; i++) {
            sortedIntegers[i] = new IntegerWrapper(i);
        }

        comp = IntegerWrapper.getComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(integers, comp);
        assertArrayEquals(sortedIntegers, integers);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 19 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortWithEmpty() {
        IntegerWrapper[] emptyTest = new IntegerWrapper[0];
        IntegerWrapper[] emptySorted = new IntegerWrapper[0];
        Sorting.insertionSort(emptyTest, comp);
        assertArrayEquals(emptySorted, emptyTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortWithOne() {
        IntegerWrapper[] oneTest = new IntegerWrapper[1];
        IntegerWrapper[] oneSorted = new IntegerWrapper[1];
        oneTest[0] = new IntegerWrapper(5);
        oneSorted[0] = new IntegerWrapper(5);
        Sorting.insertionSort(oneTest, comp);
        assertArrayEquals(oneSorted, oneTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 0);
    }


    @Test(timeout = TIMEOUT)
    public void testInsertionSortWithDuplicates() {
        IntegerWrapper[] duplicateTest = new IntegerWrapper[3];
        IntegerWrapper[] duplicateSorted = new IntegerWrapper[3];
        IntegerWrapper element = new IntegerWrapper(5);
        duplicateTest[0] = element;
        duplicateTest[1] = element;
        duplicateTest[2] = element;

        duplicateSorted[0] = element;
        duplicateSorted[1] = element;
        duplicateSorted[2] = element;

        Sorting.insertionSort(duplicateTest, comp);
        assertArrayEquals(duplicateSorted, duplicateTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 2);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortStable() {
        IntegerWrapper[] stableTest = new IntegerWrapper[4];
        IntegerWrapper[] stableSorted = new IntegerWrapper[4];

        IntegerWrapper element1 = new IntegerWrapper(1);
        IntegerWrapper element2 = new IntegerWrapper(2);
        IntegerWrapper element3 = new IntegerWrapper(2);
        IntegerWrapper element4 = new IntegerWrapper(3);

        stableTest[0] = element4;
        stableTest[1] = element3;
        stableTest[2] = element2;
        stableTest[3] = element1;

        Sorting.insertionSort(stableTest, comp);

        stableSorted[0] = element1;
        stableSorted[1] = element3;
        stableSorted[2] = element2;
        stableSorted[3] = element4;

        assertArrayEquals(stableSorted, stableTest);
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortAdaptive() {
        IntegerWrapper[] adaptiveTest = new IntegerWrapper[4];
        IntegerWrapper[] adaptiveSorted = new IntegerWrapper[4];

        IntegerWrapper element1 = new IntegerWrapper(1);
        IntegerWrapper element2 = new IntegerWrapper(2);
        IntegerWrapper element3 = new IntegerWrapper(3);
        IntegerWrapper element4 = new IntegerWrapper(4);

        adaptiveTest[0] = element1;
        adaptiveTest[1] = element2;
        adaptiveTest[2] = element3;
        adaptiveTest[3] = element4;

        Sorting.insertionSort(adaptiveTest, comp);

        adaptiveSorted[0] = element1;
        adaptiveSorted[1] = element2;
        adaptiveSorted[2] = element3;
        adaptiveSorted[3] = element4;

        assertArrayEquals(adaptiveSorted, adaptiveTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 3);
    }

    // replace with your partition method if you have one, otherwise just delete this
    // also temporarily change your partition method to public
//    @Test(timeout = TIMEOUT)
//    public void testPartition() {
//        Random random = new Random();
//
//        IntegerWrapper[] testArr = new IntegerWrapper[1000];
//        for (int i = 0; i < testArr.length; i++) {
//            testArr[i] = new IntegerWrapper(random.nextInt());
//        }
//
//        boolean flag = false;
//        for (int i = 0; i < testArr.length; i++) {
//            int newPivotId = Sorting.partition(0, i, testArr.length - 1, testArr, comp);
//            for (int j = 0; j < newPivotId; j++) {
//                if (testArr[j].value > testArr[newPivotId].value) {
//                    flag = true;
//                }
//            }
//            for (int j = newPivotId + 1; j < testArr.length; j++) {
//                if (testArr[j].value < testArr[newPivotId].value) {
//                    flag = true;
//                }
//            }
//        }
//
//        assertEquals(false, flag);
//    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSortWorst() {
        IntegerWrapper[] adaptiveTest = new IntegerWrapper[4];
        IntegerWrapper[] adaptiveSorted = new IntegerWrapper[4];

        IntegerWrapper element1 = new IntegerWrapper(1);
        IntegerWrapper element2 = new IntegerWrapper(2);
        IntegerWrapper element3 = new IntegerWrapper(3);
        IntegerWrapper element4 = new IntegerWrapper(4);

        adaptiveTest[0] = element4;
        adaptiveTest[1] = element3;
        adaptiveTest[2] = element2;
        adaptiveTest[3] = element1;

        Sorting.insertionSort(adaptiveTest, comp);

        adaptiveSorted[0] = element1;
        adaptiveSorted[1] = element2;
        adaptiveSorted[2] = element3;
        adaptiveSorted[3] = element4;

        assertArrayEquals(adaptiveSorted, adaptiveTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertionSortException1() {
        IntegerWrapper[] exceptionTest = null;
        Sorting.insertionSort(exceptionTest, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertionSortException2() {
        IntegerWrapper[] exceptionTest = new IntegerWrapper[2];
        comp = null;
        Sorting.insertionSort(exceptionTest, comp);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(integers, comp);
        assertArrayEquals(sortedIntegers, integers);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 15 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortWithEmpty() {
        IntegerWrapper[] emptyTest = new IntegerWrapper[0];
        IntegerWrapper[] emptySorted = new IntegerWrapper[0];
        Sorting.mergeSort(emptyTest, comp);
        assertArrayEquals(emptySorted, emptyTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortWithOne() {
        IntegerWrapper[] oneTest = new IntegerWrapper[1];
        IntegerWrapper[] oneSorted = new IntegerWrapper[1];
        oneTest[0] = new IntegerWrapper(5);
        oneSorted[0] = new IntegerWrapper(5);
        Sorting.mergeSort(oneTest, comp);
        assertArrayEquals(oneSorted, oneTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSortStable() {
        IntegerWrapper[] stableTest = new IntegerWrapper[4];
        IntegerWrapper[] stableSorted = new IntegerWrapper[4];

        IntegerWrapper element1 = new IntegerWrapper(1);
        IntegerWrapper element2 = new IntegerWrapper(2);
        IntegerWrapper element3 = new IntegerWrapper(2);
        IntegerWrapper element4 = new IntegerWrapper(3);

        stableTest[0] = element4;
        stableTest[1] = element3;
        stableTest[2] = element2;
        stableTest[3] = element1;

        Sorting.mergeSort(stableTest, comp);

        stableSorted[0] = element1;
        stableSorted[1] = element3;
        stableSorted[2] = element2;
        stableSorted[3] = element4;

        assertArrayEquals(stableSorted, stableTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() != 0 && comp.getCount() <= 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortException1() {
        IntegerWrapper[] exceptionTest = null;
        Sorting.mergeSort(exceptionTest, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortException2() {
        IntegerWrapper[] exceptionTest = new IntegerWrapper[2];
        comp = null;
        Sorting.mergeSort(exceptionTest, comp);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelect() {
        int randomSeed = 4;
        assertEquals(new IntegerWrapper(0), Sorting.kthSelect(1,
                integers, comp, new Random(randomSeed)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 8 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectDifferentData() {
        int randomSeed = 4;
        IntegerWrapper[] testArr = new IntegerWrapper[7];
        IntegerWrapper element1 = new IntegerWrapper(-15);
        IntegerWrapper element2 = new IntegerWrapper(-7);
        IntegerWrapper element3 = new IntegerWrapper(-1);
        IntegerWrapper element4 = new IntegerWrapper(0);
        IntegerWrapper element5 = new IntegerWrapper(3);
        IntegerWrapper element6 = new IntegerWrapper(28);
        IntegerWrapper element7 = new IntegerWrapper(40);

        testArr[0] = element3;
        testArr[1] = element1;
        testArr[2] = element2;
        testArr[3] = element5;
        testArr[4] = element6;
        testArr[5] = element4;
        testArr[6] = element7;

        assertEquals(element1, Sorting.kthSelect(1, testArr, comp, new Random(randomSeed)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 21 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelectDifferentk() {
        int randomSeed = 4;
        IntegerWrapper[] testArr = new IntegerWrapper[7];
        IntegerWrapper element1 = new IntegerWrapper(-15);
        IntegerWrapper element2 = new IntegerWrapper(-7);
        IntegerWrapper element3 = new IntegerWrapper(-1);
        IntegerWrapper element4 = new IntegerWrapper(0);
        IntegerWrapper element5 = new IntegerWrapper(3);
        IntegerWrapper element6 = new IntegerWrapper(28);
        IntegerWrapper element7 = new IntegerWrapper(40);

        testArr[0] = element3;
        testArr[1] = element1;
        testArr[2] = element2;
        testArr[3] = element5;
        testArr[4] = element6;
        testArr[5] = element4;
        testArr[6] = element7;

        assertEquals(element7, Sorting.kthSelect(7, testArr, comp, new Random(randomSeed)));
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 20 && comp.getCount() != 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKthSelectException1() {
        integers = null;
        IntegerWrapper result = Sorting.kthSelect(1, integers, comp, new Random(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKthSelectException2() {
        comp = null;
        IntegerWrapper result = Sorting.kthSelect(1, integers, comp, new Random(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKthSelectException3() { // there is no eligible choice for k if array is empty
        integers = new IntegerWrapper[0];
        IntegerWrapper result = Sorting.kthSelect(1, integers, comp, new Random(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKthSelectionException4() {
        IntegerWrapper result = Sorting.kthSelect(0, integers, comp, new Random(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testKthSelectionException5() {
        IntegerWrapper result = Sorting.kthSelect(99, integers, comp, new Random(4));
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        List<Integer> unsortedList = new ArrayList<>();
        for (int i : unsortedArray) {
            unsortedList.add(i);
        }
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        int[] actualArray = Sorting.heapSort(unsortedList);
        assertArrayEquals(sortedArray, actualArray);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortWithEmpty() {
        List<Integer> emptyTest = new ArrayList<>();
        int[] emptySorted = new int[0];
        int[] actual = Sorting.heapSort(emptyTest);
        assertArrayEquals(emptySorted, emptySorted);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortWithOne() {
        int[] oneTest = new int[] {3};
        List<Integer> testList = new ArrayList<>();
        testList.add(oneTest[0]);
        int[] oneSorted = new int[] {3};
        int[] actual = Sorting.heapSort(testList);
        assertArrayEquals(oneSorted, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortWithDuplicates() {
        int[] duplicateTest = new int[] {5, 5, 5};

        List<Integer> testList = new ArrayList<>();

        for (int i : duplicateTest) {
            testList.add(i);
        }

        int[] duplicateSorted = new int[] {5, 5, 5};
        int[] actual = Sorting.heapSort(testList);
        assertArrayEquals(duplicateSorted, actual);
    }

    @Test(timeout = TIMEOUT)
    public void testHeapSortAlreadySorted() {
        int[] sortedTest = new int[] {-12, -5, -3, 0, 1, 2, 14, 91};

        List<Integer> testList = new ArrayList<>();

        for (int i : sortedTest) {
            testList.add(i);
        }

        int[] sortedResult = new int[] {-12, -5, -3, 0, 1, 2, 14, 91};
        int[] actual = Sorting.heapSort(testList);

        assertArrayEquals(sortedResult, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHeapSortException() {
        List<Integer> exceptionTest = null;
        Sorting.heapSort(exceptionTest);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[]{54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[]{-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSortWithEmpty() {
        int[] emptyTest = new int[0];
        int[] emptySorted = new int[0];
        Sorting.lsdRadixSort(emptyTest);
        assertArrayEquals(emptySorted, emptyTest);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSortWithOne() {
        int[] oneTest = new int[] {3};
        int[] oneSorted = new int[] {3};
        Sorting.lsdRadixSort(oneTest);
        assertArrayEquals(oneSorted, oneTest);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSortWithDuplicates() {
        int[] duplicateTest = new int[] {5, 5, 5};
        int[] duplicateSorted = new int[] {5, 5, 5};

        Sorting.lsdRadixSort(duplicateTest);
        assertArrayEquals(duplicateSorted, duplicateTest);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSortAlreadySorted() {
        int[] sortedTest = new int[] {-12, -5, -3, 0, 1, 2, 14, 91};
        int[] sortedResult = new int[] {-12, -5, -3, 0, 1, 2, 14, 91};

        Sorting.lsdRadixSort(sortedTest);
        assertArrayEquals(sortedResult, sortedTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLsdRadixSortException() {
        int[] exceptionTest = null;
        Sorting.lsdRadixSort(exceptionTest);
    }


    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Sorting.cocktailSort(integers, comp);
        assertArrayEquals(sortedIntegers, integers);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 21 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortWithEmpty() {
        IntegerWrapper[] emptyTest = new IntegerWrapper[0];
        IntegerWrapper[] emptySorted = new IntegerWrapper[0];
        Sorting.cocktailSort(emptyTest, comp);
        assertArrayEquals(emptyTest, emptySorted);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortWithOne() {
        IntegerWrapper[] oneTest = new IntegerWrapper[1];
        IntegerWrapper[] oneSorted = new IntegerWrapper[1];
        oneTest[0] = new IntegerWrapper(5);
        oneSorted[0] = new IntegerWrapper(5);
        Sorting.cocktailSort(oneTest, comp);
        assertArrayEquals(oneTest, oneSorted);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortWithDuplicates() {
        IntegerWrapper[] duplicateTest = new IntegerWrapper[8];
        IntegerWrapper[] duplicateSorted = new IntegerWrapper[8];
        IntegerWrapper element = new IntegerWrapper(1);
        duplicateTest[0] = element;
        duplicateTest[1] = element;
        duplicateTest[2] = element;
        duplicateTest[3] = element;
        duplicateTest[4] = element;
        duplicateTest[5] = element;
        duplicateTest[6] = element;
        duplicateTest[7] = element;

        duplicateSorted[0] = element;
        duplicateSorted[1] = element;
        duplicateSorted[2] = element;
        duplicateSorted[3] = element;
        duplicateSorted[4] = element;
        duplicateSorted[5] = element;
        duplicateSorted[6] = element;
        duplicateSorted[7] = element;

        Sorting.cocktailSort(duplicateTest, comp);
        assertArrayEquals(duplicateSorted, duplicateTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 7);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortAlreadySorted() {
        IntegerWrapper[] adaptiveTest = new IntegerWrapper[4];
        IntegerWrapper[] adaptiveSorted = new IntegerWrapper[4];

        IntegerWrapper element1 = new IntegerWrapper(1);
        IntegerWrapper element2 = new IntegerWrapper(2);
        IntegerWrapper element3 = new IntegerWrapper(3);
        IntegerWrapper element4 = new IntegerWrapper(4);

        adaptiveTest[0] = element1;
        adaptiveTest[1] = element2;
        adaptiveTest[2] = element3;
        adaptiveTest[3] = element4;

        Sorting.cocktailSort(adaptiveTest, comp);

        adaptiveSorted[0] = element1;
        adaptiveSorted[1] = element2;
        adaptiveSorted[2] = element3;
        adaptiveSorted[3] = element4;

        assertArrayEquals(adaptiveSorted, adaptiveTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 3);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortStable() {
        IntegerWrapper[] stableTest = new IntegerWrapper[4];
        IntegerWrapper[] stableSorted = new IntegerWrapper[4];

        IntegerWrapper element1 = new IntegerWrapper(1);
        IntegerWrapper element2 = new IntegerWrapper(2);
        IntegerWrapper element3 = new IntegerWrapper(2);
        IntegerWrapper element4 = new IntegerWrapper(3);

        stableTest[0] = element4;
        stableTest[1] = element3;
        stableTest[2] = element2;
        stableTest[3] = element1;

        Sorting.cocktailSort(stableTest, comp);

        stableSorted[0] = element1;
        stableSorted[1] = element3;
        stableSorted[2] = element2;
        stableSorted[3] = element4;

        assertArrayEquals(stableSorted, stableTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 6);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortWorst() {
        IntegerWrapper[] adaptiveTest = new IntegerWrapper[4];
        IntegerWrapper[] adaptiveSorted = new IntegerWrapper[4];

        IntegerWrapper element1 = new IntegerWrapper(1);
        IntegerWrapper element2 = new IntegerWrapper(2);
        IntegerWrapper element3 = new IntegerWrapper(3);
        IntegerWrapper element4 = new IntegerWrapper(4);

        adaptiveTest[0] = element4;
        adaptiveTest[1] = element3;
        adaptiveTest[2] = element2;
        adaptiveTest[3] = element1;

        Sorting.cocktailSort(adaptiveTest, comp);

        adaptiveSorted[0] = element1;
        adaptiveSorted[1] = element2;
        adaptiveSorted[2] = element3;
        adaptiveSorted[3] = element4;

        assertArrayEquals(adaptiveSorted, adaptiveTest);
        assertTrue("Number of comparisons :" + comp.getCount(), comp.getCount() == 6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCocktailSortException1() {
        IntegerWrapper[] exceptionTest = null;
        Sorting.cocktailSort(exceptionTest, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCocktailSortException2() {
        IntegerWrapper[] exceptionTest = new IntegerWrapper[2];
        comp = null;
        Sorting.cocktailSort(exceptionTest, comp);
    }

    /**
     * Class for testing proper Sorting.
     */
    private static class IntegerWrapper {
        private Integer value;

        /**
         * Create an IntegerWrapper.
         *
         * @param value integer value
         */
        public IntegerWrapper(Integer value) {
            this.value = value;
        }

        /**
         * Get the value
         *
         * @return value of the integer
         */
        public Integer getValue() {
            return value;
        }

        /**
         * Set the value of the IntegerWrapper.
         *
         * @param value the new value
         */
        public void setValue(Integer value) {
            this.value = value;
        }

        /**
         * Create a comparator that compares the wrapped values.
         *
         * @return comparator that compares the wrapped values
         */
        public static ComparatorPlus<IntegerWrapper> getComparator() {
            return new ComparatorPlus<>() {
                @Override
                public int compare(IntegerWrapper int1,
                                   IntegerWrapper int2) {
                    incrementCount();
                    return int1.getValue().compareTo(int2.getValue());
                }
            };
        }

        @Override
        public String toString() {
            return "Value: " + value;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof IntegerWrapper
                    && ((IntegerWrapper) other).value.equals(this.value);
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}