import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Riyan Patel
 * @version 1.0
 * @userid rpatel816
 * @GTID 903978548
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * NA
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 * NA
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of
 * this file. If this is left blank, this homework will receive a zero.
 *
 * Agree Here: I agree
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("arr or comparator can't be null");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            T temp = arr[i];
            while (j >= 0 && comparator.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j--];
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        boolean swapsMade = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        while (swapsMade) {
            swapsMade = false;
            int lastSwapped = startIndex;
            for (int i = startIndex; i < endIndex; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swapsMade = true;
                    lastSwapped = i;
                }
            }
            endIndex = lastSwapped;
            if (swapsMade) {

                swapsMade = false;
                lastSwapped = endIndex;
                for (int i = endIndex; i > startIndex; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        swap(arr, i - 1, i);
                        swapsMade = true;
                        lastSwapped = i;
                    }
                }
                startIndex = lastSwapped;
            }
        }
    }
    /**
     * Swap two elements in an array
     *
     * @param arr the array that contains items to swap
     * @param a first item
     * @param b second item
     * @param <T>  data type to sort
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("arr and comparator can't be null");
        }
        if (arr.length > 1) {
            int len = arr.length;
            int midIndex = len / 2;
            T[] left = (T[]) new Object[midIndex];
            T[] right = (T[]) new Object[len - midIndex];

            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int i = 0; i < right.length; i++) {
                right[i] = arr[i + left.length];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(arr, comparator, left, right);
        }
    }
    /**
     * Merges separated arrays
     *
     * @param arr the array to be sorted
     * @param comparator comparator used to compare values
     * @param left left array
     * @param right right Array
     * @param <T> data type to sort
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator, T[] left, T[] right) {
        int i = 0;
        int j = 0;
        for (int k = 0; k < arr.length; k++) {
            if (j >= right.length || (i < left.length
                    && comparator.compare(left[i], right[j]) <= 0)) {
                arr[k] = left[i++];
            } else {
                arr[k] = right[j++];
            }
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null || comparator == null || rand == null || k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        return quickSortStyleSelect(arr, 0, arr.length - 1, k - 1, comparator, rand);
    }
    /**
     * Helper method for kthSelect using Quickselect algorithm.
     *
     * Selects the kth smallest element in the array using randomized pivoting.
     *
     * @param <T>        data type to sort
     * @param arr        the array to search within
     * @param left       the starting index of the search range
     * @param right      the ending index of the search range
     * @param k          the index (0-based) of the desired smallest element
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element in the array
     */
    private static <T> T quickSortStyleSelect(T[] arr, int left, int right,
                                              int k, Comparator<T> comparator, Random rand) {
        while (left <= right) {
            int pivotIndex = rand.nextInt(right - left + 1) + left;
            int pivotFinalIndex = partition(arr, left, right, pivotIndex, comparator);

            if (pivotFinalIndex == k) {
                return arr[pivotFinalIndex];
            } else if (k < pivotFinalIndex) {
                right = pivotFinalIndex - 1;
            } else {
                left = pivotFinalIndex + 1;
            }
        }

        throw new RuntimeException("kthSelect failed unexpectedly.");
    }

    /**
     * Partitions the array around a pivot such that elements less than the pivot
     * are on the left, and elements greater than or equal to the pivot are on the right.
     *
     * @param <T>         data type to sort
     * @param arr         the array to partition
     * @param left        the starting index of the partition range
     * @param right       the ending index of the partition range
     * @param pivotIndex  the index of the pivot element
     * @param comparator  the Comparator used to compare the data in arr
     * @return the final index of the pivot after partitioning
     */
    private static <T> int partition(T[] arr, int left, int right, int pivotIndex, Comparator<T> comparator) {
        T pivot = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (comparator.compare(arr[i], pivot) < 0) {
                swap(arr, i, storeIndex++);
            }
        }

        swap(arr, storeIndex, right);
        return storeIndex;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        int max = 0;
        for (int element : arr) {
            int absVal = Math.abs(element);
            if (absVal > max) {
                max = absVal;
            }
        }

        int k = 0;
        int temp = max;
        while (temp > 0) {
            temp /= 10;
            k++;
        }

        int base = 1;

        for (int i = 0; i < k; i++) {
            List<List<Integer>> buckets = new ArrayList<>();
            for (int b = 0; b < 19; b++) {
                buckets.add(new ArrayList<>());
            }

            for (int num : arr) {
                int b = (num / base % 10) + 9;
                buckets.get(b).add(num);
            }

            int index = 0;
            for (List<Integer> bucket : buckets) {
                for (int num : bucket) {
                    arr[index++] = num;
                }
            }

            base *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("data can't be null");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);

        int[] resultArr = new int[data.size()];

        int index = 0;
        while (!heap.isEmpty()) {
            resultArr[index++] = heap.poll();
        }

        return resultArr;
    }
}
