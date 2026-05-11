package structures;
/******************************************************************************
 *  BST.java - a Binary Search Tree implementation.
 *  Based on the Sedgewick/Wayne textbook code (Algorithms, 4th Edition).
 *
 *  Compile:  javac BST.java
 *  Run:      java BST
 ******************************************************************************/

import java.util.NoSuchElementException;
import java.util.LinkedList;


/**
 * BST = Binary Search Tree.
 *
 * It's basically a sorted map: you can put(key, value), get(key),
 * delete things, and ask for the min/max/floor/ceiling/etc.
 *
 * Keys have to be Comparable so we know which side of the tree to go down.
 * Values can be anything (but can't be null - putting null = delete).
 *
 * Note from textbook: this is the UNBALANCED version. Worst case ops are O(n)
 * if the tree is super lopsided. Average case is more like O(log n).
 *
 * @param <Key>   has to be Comparable so we can sort
 * @param <Value> whatever we want to store
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // the top node of the tree

    /**
     * Inner Node class - each node holds a key, value, pointers to its
     * children, and how many nodes are in the subtree rooted here
     * (the size is what makes rank/select fast).
     */
    private class Node {
        private Key key;           // key we sort by
        private Value val;         // the data we're actually storing
        private Node left, right;  // left = smaller keys, right = bigger keys
        private int size;          // nodes in this subtree (including itself)

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /** make an empty BST */
    public BST() {
    }

    /** @return true if tree is empty */
    public boolean isEmpty() {
        return size() == 0;
    }

    /** @return total number of key-value pairs */
    public int size() {
        return size(root);
    }

    // private helper - size of subtree rooted at node
    // (null check so we don't NPE on missing children)
    private int size(Node node) {
        if (node == null) return 0;
        else return node.size;
    }

    /**
     * Does the tree have this key?
     * Just calls get() and checks if it's null.
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Get the value for this key, or null if it's not in the tree.
     */
    public Value get(Key key) {
        return get(root, key);
    }

    // recursive helper - walks down the tree looking for the key
    private Value get(Node node, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        // smaller -> go left, bigger -> go right, equal -> we found it
        if      (cmp < 0) return get(node.left, key);
        else if (cmp > 0) return get(node.right, key);
        else              return node.val;
    }

    /**
     * Insert (or overwrite) a key-value pair.
     * If you put null as the value, it actually deletes the key instead.
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }

    // recursive helper - same pattern as get, but inserts when it hits null
    private Node put(Node node, Key key, Value val) {
        if (node == null) return new Node(key, val, 1); // base case: new leaf
        int cmp = key.compareTo(node.key);
        if      (cmp < 0) node.left  = put(node.left,  key, val);
        else if (cmp > 0) node.right = put(node.right, key, val);
        else              node.val   = val; // key already exists -> overwrite
        // update size on the way back up the recursion
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }


    /**
     * Yeet the smallest key.
     * @throws NoSuchElementException if the tree is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    // helper - keep going left until there's no left child, then
    // replace that node with its right child
    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * Yeet the largest key.
     * @throws NoSuchElementException if the tree is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
    }

    // mirror image of deleteMin
    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * Delete a specific key. Uses Hibbard deletion (the tricky one).
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if      (cmp < 0) node.left  = delete(node.left,  key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            // found the node to delete
            // easy cases first: one (or zero) child
            if (node.right == null) return node.left;
            if (node.left  == null) return node.right;

            // hard case: two children
            // -> replace this node with its successor (smallest key on the right)
            Node temp = node;
            node = min(temp.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }


    /**
     * Smallest key in the tree (leftmost node).
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    // keep going left forever
    private Node min(Node node) {
        if (node.left == null) return node;
        else                   return min(node.left);
    }

    /**
     * Largest key in the tree (rightmost node).
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    // keep going right forever
    private Node max(Node node) {
        if (node.right == null) return node;
        else                    return max(node.right);
    }

    /**
     * floor(key) = biggest key in the tree that is <= key.
     * Like rounding down to the next thing we actually have.
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node node = floor(root, key);
        if (node == null) throw new NoSuchElementException("argument to floor() is too small");
        else return node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;            // exact hit -> done
        if (cmp <  0) return floor(node.left, key); // need smaller -> go left
        // key is bigger than node.key, so this node is a candidate
        // but maybe there's something even closer on the right
        Node t = floor(node.right, key);
        if (t != null) return t;
        else return node;
    }

    /** Alternate floor() implementation (carries the best-so-far as a parameter) */
    public Key floor2(Key key) {
        Key floor = floor2(root, key, null);
        if (floor == null) throw new NoSuchElementException("argument to floor() is too small");
        else return floor;

    }

    private Key floor2(Node node, Key key, Key champ) {
        if (node == null) return champ;
        int cmp = key.compareTo(node.key);
        if      (cmp  < 0) return floor2(node.left, key, champ);
        else if (cmp  > 0) return floor2(node.right, key, node.key);
        else               return node.key;
    }

    /**
     * ceiling(key) = smallest key in the tree that is >= key.
     * Mirror image of floor (rounding up instead of down).
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node node = ceiling(root, key);
        if (node == null) throw new NoSuchElementException("argument to ceiling() is too large");
        else return node.key;
    }

    private Node ceiling(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp < 0) {
            Node t = ceiling(node.left, key);
            if (t != null) return t;
            else return node;
        }
        return ceiling(node.right, key);
    }

    /**
     * Returns the key with the given rank (0-indexed).
     * rank 0 = smallest, rank size-1 = largest.
     * Uses the subtree size field to navigate.
     */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    private Key select(Node node, int rank) {
        if (node == null) return null;
        int leftSize = size(node.left);
        // if left subtree has more than `rank` nodes, the answer is in there
        if      (leftSize > rank) return select(node.left,  rank);
        // if it has fewer, skip past all of them + this node
        else if (leftSize < rank) return select(node.right, rank - leftSize - 1);
        // exactly equal -> this is the one
        else                      return node.key;
    }

    /**
     * rank(key) = how many keys in the tree are strictly less than this one.
     * Basically the inverse of select.
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    private int rank(Key key, Node node) {
        if (node == null) return 0;
        int cmp = key.compareTo(node.key);
        if      (cmp < 0) return rank(key, node.left);
        // going right: count all the left subtree + this node + whatever's smaller on the right
        else if (cmp > 0) return 1 + size(node.left) + rank(key, node.right);
        else              return size(node.left);
    }

    /**
     * @return all keys in sorted order (use in a for-each loop)
     */
    public Iterable<Key> keys() {
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    /**
     * @return all keys between lo and hi (inclusive), sorted
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    // in-order traversal, but only enqueues keys in [lo, hi]
    private void keys(Node node, Queue<Key> queue, Key lo, Key hi) {
        if (node == null) return;
        int cmplo = lo.compareTo(node.key);
        int cmphi = hi.compareTo(node.key);
        if (cmplo < 0) keys(node.left, queue, lo, hi);          // visit left if there might be valid keys there
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(node.key);  // this node is in range
        if (cmphi > 0) keys(node.right, queue, lo, hi);         // visit right if there might be valid keys there
    }

    /**
     * @return how many keys are in the range [lo, hi]
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        // clever trick: rank(hi) - rank(lo) counts keys in [lo, hi)
        // then add 1 if hi is actually in the tree
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    /**
     * Height of the tree (for debugging - a tall tree means slow operations).
     * One-node tree has height 0; an empty tree has height -1.
     */
    public int height() {
        return height(root);
    }
    private int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Walks the tree level-by-level (BFS) using a queue.
     * Mostly for debugging / visualizing the tree shape.
     */
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node node = queue.dequeue();
            if (node == null) continue;
            keys.enqueue(node.key);
            // add children to the back of the queue -> they'll be visited
            // after everything else on the current level
            queue.enqueue(node.left);
            queue.enqueue(node.right);
        }
        return keys;
    }

    /*************************************************************************
     *  Integrity-check methods for debugging the data structure.
     *  (Commented-out check() uses StdOut which we don't have here.)
     ***************************************************************************/
    // private boolean check() {
    //     if (!isBST())            StdOut.println("Not in symmetric order");
    //     if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
    //     if (!isRankConsistent()) StdOut.println("Ranks not consistent");
    //     return isBST() && isSizeConsistent() && isRankConsistent();
    // }

    // checks the BST property: every left key < node < every right key
    private boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node node, Key min, Key max) {
        if (node == null) return true;
        if (min != null && node.key.compareTo(min) <= 0) return false;
        if (max != null && node.key.compareTo(max) >= 0) return false;
        return isBST(node.left, min, node.key) && isBST(node.right, node.key, max);
    }

    // make sure each node's `size` field matches the actual subtree size
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node node) {
        if (node == null) return true;
        if (node.size != size(node.left) + size(node.right) + 1) return false;
        return isSizeConsistent(node.left) && isSizeConsistent(node.right);
    }

    // make sure rank and select are inverses of each other
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }


    /**
     * Quick unit test for the BST.
     * Same example as the textbook ("SEARCHEXAMPLE") so I can sanity check
     * against the expected output.
     */
    public static void main(String[] args) {
        // Create a BST with String keys and Integer values
        BST<String, Integer> st = new BST<String, Integer>();

        // Insert some key-value pairs (same example as the textbook)
        String[] tokens = { "S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E" };
        for (int i = 0; i < tokens.length; i++) {
            st.put(tokens[i], i);
        }

        // Print keys level by level (top of the tree first)
        System.out.println("Level order:");
        for (String s : st.levelOrder()) {
            System.out.println("  " + s + " " + st.get(s));
        }

        // Print keys in alphabetical (in-order) order
        System.out.println("\nIn-order (sorted):");
        for (String s : st.keys()) {
            System.out.println("  " + s + " " + st.get(s));
        }

        // Basic info
        System.out.println("\nsize    = " + st.size());
        System.out.println("min     = " + st.min());
        System.out.println("max     = " + st.max());
        System.out.println("height  = " + st.height());

        // Lookups
        System.out.println("\nget(\"H\")        = " + st.get("H"));
        System.out.println("contains(\"Z\") = " + st.contains("Z"));

        // Order-statistic tests
        System.out.println("rank(\"M\")     = " + st.rank("M"));
        System.out.println("select(3)      = " + st.select(3));
        System.out.println("floor(\"G\")    = " + st.floor("G"));
        System.out.println("ceiling(\"G\")  = " + st.ceiling("G"));

        // Delete tests
        st.deleteMin();
        st.deleteMax();
        st.delete("E");
        System.out.println("\nAfter deleteMin, deleteMax, delete(\"E\"):");
        for (String s : st.keys()) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
