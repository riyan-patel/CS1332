import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
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
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data can't be null");
        }
        size = 0;
        for (T item: data) {
            this.add(item);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        root = addHelper(data, root);
    }
    /**
     * Recursively adds data to the AVL tree while maintaining balance.
     *
     * @param data the data to insert
     * @param node the current subtree root
     * @return the updated subtree root
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(data, node.getRight()));
        } else {
            return node;
        }
        calculateHeight(node);
        return balance(node);

    }
    /**
     * Updates the height and balance factor of a node.
     *
     * @param node the node to update
     */
    private void calculateHeight(AVLNode<T> node) {
        int lHeight = height(node.getLeft());
        int rHeight = height(node.getRight());
        node.setHeight(Math.max(lHeight, rHeight) + 1);
        node.setBalanceFactor(lHeight - rHeight);
    }

    /**
     * Balances the AVL tree at the given node if unbalanced.
     *
     * @param node the node to balance
     * @return the balanced subtree root
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        } else if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }
        return node;
    }

    /**
     * Performs a right rotation on the given node.
     *
     * @param node the node to rotate
     * @return the new subtree root after rotation
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> rotate = node.getLeft();
        node.setLeft(rotate.getRight());
        rotate.setRight(node);
        calculateHeight(node);
        calculateHeight(rotate);
        return rotate;
    }

    /**
     * Performs a left rotation on the given node.
     *
     * @param node the node to rotate
     * @return the new subtree root after rotation
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> rotate = node.getRight();
        node.setRight(rotate.getLeft());
        rotate.setLeft(node);
        calculateHeight(node);
        calculateHeight(rotate);
        return rotate;
    }


    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        return removed.getData();
    }
    /**
     * Recursively removes a node while maintaining AVL balance.
     *
     * @param data the data to remove
     * @param node the current subtree root
     * @param removed a holder node to store the removed data
     * @return the updated subtree root
     * @throws NoSuchElementException if the data is not found
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("Node can't be found");
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), removed));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(data, node.getRight(), removed));
        } else {
            size--;
            removed.setData(node.getData());
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> child = new AVLNode<>(null);
                node.setLeft(findPredecessor(node.getLeft(), child));
                node.setData(child.getData());
            }
        }
        calculateHeight(node);
        return balance(node);
    }

    /**
     * Finds the in-order predecessor (largest value in left subtree).
     *
     * @param node The left subtree root
     * @param predecessorHolder Node to store the predecessor data
     * @return The modified subtree after removing the predecessor
     */
    private AVLNode<T> findPredecessor(AVLNode<T> node, AVLNode<T> predecessorHolder) {
        if (node.getRight() == null) {
            predecessorHolder.setData(node.getData());
            return node.getLeft();
        }
        node.setRight(findPredecessor(node.getRight(), predecessorHolder));
        calculateHeight(node);
        return balance(node);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        return getHelper(data, root);
    }

    /**
     * Recursively searches for a node containing the given data.
     *
     * @param data the data to search for
     * @param node the current subtree root
     * @return the data stored in the tree that matches the input data
     * @throws NoSuchElementException if the data is not found
     */
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Data is not found");
        }
        if (data.compareTo(node.getData()) > 0) {
            return getHelper(data, node.getRight());
        } else if (data.compareTo(node.getData()) < 0) {
            return getHelper(data, node.getLeft());
        } else {
            return node.getData();
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        return containsHelper(root, data);
    }

    /**
     * Recursively checks if the given data exists in the tree.
     *
     * @param node the current subtree root
     * @param data the data to search for
     * @return true if found, false otherwise
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.getData()) < 0) {
            return containsHelper(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return containsHelper(node.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Finds and retrieves the median data of the passed in AVL.
     *
     * This method will not need to traverse the entire tree to
     * function properly, so you should only traverse enough branches of the tree
     * necessary to find the median data and only do so once. Failure to do so will
     * result in an efficiency penalty. Additionally, note that this should be completed
     * with O(1) additional memory, not including the memory used by the call stack.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *
     * findMedian() should return 40
     *
     * @throws java.util.NoSuchElementException if the tree is empty or contains an even number of data
     * @return the median data of the AVL
     */
    public T findMedian() {
        if (size == 0 || size % 2 == 0) {
            throw new NoSuchElementException("Tree is empty or has an even number of elements.");
        }
        int medianIndex = (size / 2) + 1;
        return findMedianHelper(root, medianIndex);
    }
    /**
     * Recursively finds the k-th smallest element in the AVL tree.
     *
     * @param node the current root node
     * @param medianIndex the rank of the element to find (1-based index)
     * @return the k-th smallest element in the tree
     */
    private T findMedianHelper(AVLNode<T> node, int medianIndex) {
        int leftSubtreeSize = getSubtreeSize(node.getLeft());

        if (leftSubtreeSize + 1 == medianIndex) {
            return node.getData();
        } else if (leftSubtreeSize + 1 > medianIndex) {
            return findMedianHelper(node.getLeft(), medianIndex);
        } else {
            return findMedianHelper(node.getRight(), medianIndex - (leftSubtreeSize + 1));
        }
    }

    /**
     * Calculates the number of nodes in a given subtree.
     *
     * @param node the root of the subtree
     * @return the number of nodes in the subtree
     */
    private int getSubtreeSize(AVLNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + getSubtreeSize(node.getLeft()) + getSubtreeSize(node.getRight());
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Returns the height of a given node.
     *
     * @param node the node whose height is to be retrieved
     * @return the height of the node, or -1 if the node is null
     */
    private int height(AVLNode<T> node) {
        return node != null ? node.getHeight() : -1;
    }
    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}