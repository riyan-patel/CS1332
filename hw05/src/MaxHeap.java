import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
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
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data can't be null");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        backingArray[0] = null;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Data being added cannot be null.");
            }
            backingArray[i + 1] = data.get(i);
        }
        int index = data.size() / 2;
        while (index >= 1) {
            if (index * 2 > size) {
                break;
            }
            int child = calculatePriorityChild(index);
            int x = index;
            while (x * 2 <= size && backingArray[x].compareTo(backingArray[child]) < 0) {
                T temp = backingArray[x];
                backingArray[x] = backingArray[child];
                backingArray[child] = temp;
                x = child;
                child = calculatePriorityChild(x);
            }
            index--;
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length. You can assume that no duplicate data will be passed in.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("can't add null data");
        }
        if (size == backingArray.length - 1) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2 + 1];
            for (int i = 0; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        backingArray[++size] = data;
        addHelper(size, data);
    }
    /**
     * Restores the max heap property by moving the element at the given index up
     * the heap if it is larger than its parent.
     *
     * @param index the index of the element to heap up.
     * @param data the data at the given index used for comparison.
     */
    private void addHelper(int index, T data) {
        if (index == 1) {
            return;
        } else if (data.compareTo(backingArray[index / 2]) < 0) {
            return;
        } else {
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = data;
            addHelper(index / 2, data);
        }
    }


    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty, can't remove");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        removeHelper(1, backingArray[1]);
        return removed;
    }
    /**
     * Restores the max heap property by moving the element at the given index down
     * the heap if it is smaller than its higher priority child.
     *
     * @param index the index of the element to heap down.
     * @param data the data at the given index used for comparison.
     */
    private void removeHelper(int index, T data) {
        if (index * 2 > size) {
            return;
        }
        int child = calculatePriorityChild(index);
        if (backingArray[index].compareTo(backingArray[child]) > 0) {
            return;
        } else {
            T temp = backingArray[index];
            backingArray[index] = backingArray[child];
            backingArray[child] = temp;
            removeHelper(child, backingArray[child]);
        }

    }

    /**
     * Calculates the index of the higher priority child (larger value) in the max heap.
     *
     * @param index the index of the parent node.
     * @return the index of the child with the higher priority.
     */
    private int calculatePriorityChild(int index) {
        if (index * 2 + 1 > size) {
            return index * 2;
        } else {
            return backingArray[index * 2].compareTo(backingArray[index * 2 + 1]) > 0 ? index * 2 : index * 2 + 1;
        }
    }
    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty, can't get max");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
