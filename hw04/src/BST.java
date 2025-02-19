import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Riyan Patel
 * @version 1.0
 * @userid rpatel816
 * @GTID 903978548
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * N/A
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 *  N/A
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of 
 * this file. If this is left blank, this homework will receive a zero.
 * 
 * Agree Here: I agree
 * 
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!

    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        for (T item: data) {
            if (item == null) {
                throw new IllegalArgumentException("data cannot contain null");
            }
            add(item);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        root = addHelper(root, data);
    }
    /**
     * Private helper method for adding data to the BST recursively.
     *
     * @param node the current node being checked
     * @param data the data to add
     * @return the updated node reference after insertion
     */
    private BSTNode<T> addHelper(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        }

        if (node.getData().compareTo(data) > 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(addHelper(node.getRight(), data));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        BSTNode<T> removed = new BSTNode<>(null);
        root = removeHelper(root, data, removed);
        size--;
        return removed.getData();

    }
    /**
     * Private helper method to remove a node from the BST recursively.
     *
     * @param node the current node being checked
     * @param data the data to remove
     * @param removed a node wrapper to store the removed data
     * @return the updated node reference after removal
     * @throws NoSuchElementException if the data is not found
     */
    private BSTNode<T> removeHelper(BSTNode<T> node, T data, BSTNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("Data not found in the tree");
        }
        if (node.getData().compareTo(data) > 0) {
            node.setLeft(removeHelper(node.getLeft(), data, removed));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(removeHelper(node.getRight(), data, removed));
        } else {
            removed.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }

            if (node.getLeft() == null) {
                return node.getRight();
            }

            if (node.getRight() == null) {
                return node.getLeft();
            }

            BSTNode<T> succesor = getSuccesor(node.getRight());
            node.setData(succesor.getData());
            node.setRight(removeHelper(node.getRight(), succesor.getData(), new BSTNode<>(null)));
        }

        return node;
    }
    /**
     * Private helper method to find the successor of a node in the BST.
     *
     * The successor is the smallest node in the right subtree.
     *
     * @param node the starting node for successor search
     * @return the successor node
     */
    private BSTNode<T> getSuccesor(BSTNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        return getHelper(root, data);

    }
    /**
     * Private helper method to retrieve data from the BST recursively.
     *
     * @param node the current node being checked
     * @param data the data to find
     * @return the data found in the tree
     * @throws NoSuchElementException if the data is not found
     */
    private T getHelper(BSTNode<T> node, T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("data not found in tree");
        }

        if (node.getData().equals(data)) {
            return node.getData();
        }
        if (node.getData().compareTo(data) > 0) {
            return getHelper(node.getLeft(), data);
        } else {
            return getHelper(node.getRight(), data);
        }
    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        return containsHelper(root, data);
    }
    /**
     * Private helper method to check whether the BST contains a given data value.
     *
     * @param node the current node being checked
     * @param data the data to search for
     * @return true if the data exists in the BST, false otherwise
     */
    private boolean containsHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (node.getData().equals(data)) {
            return true;
        }
        if (node.getData().compareTo(data) > 0) {
            return containsHelper(node.getLeft(), data);
        } else {
            return containsHelper(node.getRight(), data);
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> resultList = new ArrayList<>();
        preorderHelper(root, resultList);
        return resultList;
    }
    /**
     * Private helper method to perform a pre-order traversal of the BST.
     *
     * @param node the current node being visited
     * @param resultList the list to store traversal results
     */
    private void preorderHelper(BSTNode<T> node, List<T> resultList) {
        if (node == null) {
            return;
        }
        resultList.add(node.getData());
        preorderHelper(node.getLeft(), resultList);
        preorderHelper(node.getRight(), resultList);
    }



    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> resultList = new ArrayList<>();
        inorderHelper(root, resultList);
        return resultList;
    }
    /**
     * Private helper method to perform an in-order traversal of the BST.
     *
     * @param node the current node being visited
     * @param resultList the list to store traversal results
     */
    private void inorderHelper(BSTNode<T> node, List<T> resultList) {
        if (node == null) {
            return;
        }
        inorderHelper(node.getLeft(), resultList);
        resultList.add(node.getData());
        inorderHelper(node.getRight(), resultList);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> resultList = new ArrayList<>();
        postorderHelper(root, resultList);
        return resultList;
    }
    /**
     * Private helper method to perform a post-order traversal of the BST.
     *
     * @param node the current node being visited
     * @param resultList the list to store traversal results
     */
    private void postorderHelper(BSTNode<T> node, List<T> resultList) {
        if (node == null) {
            return;
        }
        postorderHelper(node.getLeft(), resultList);
        postorderHelper(node.getRight(), resultList);
        resultList.add(node.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> resultList = new ArrayList<>();
        queue.add(root);
        if (root == null) {
            return resultList;
        }
        while (!queue.isEmpty()) {
            BSTNode<T> curr = queue.remove();
            resultList.add(curr.getData());

            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.add(curr.getRight());
            }
        }
        return resultList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);

    }
    /**
     * Private helper method to compute the height of the BST recursively.
     *
     * @param node the current node being evaluated
     * @return the height of the current node
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int left = heightHelper(node.getLeft());
        int right = heightHelper(node.getRight());
        return Math.max(left, right) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("k must be between 0 and size");
        }
        List<T> result = new LinkedList<>();
        reverseInorder(root, k, result);
        return result;
    }
    /**
     * Private helper method to perform a reverse in-order traversal to find
     * the k-largest elements in the BST.
     *
     * @param node the current node being visited
     * @param k the number of largest elements to retrieve
     * @param result the list storing k largest elements
     */
    private void reverseInorder(BSTNode<T> node, int k, List<T> result) {
        if (node == null || result.size() >= k) {
            return;
        }

        reverseInorder(node.getRight(), k, result);

        if (result.size() < k) {
            result.add(0, node.getData());
        }

        if (result.size() < k) {
            reverseInorder(node.getLeft(), k, result);
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
