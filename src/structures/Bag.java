package structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Bag implementation:
 * In this project, the Bag is used as a collection for storing graph adjacency lists. 
 * Each vertex in the graph has a Bag of neighboring vertices, which lets us iterate through a professor's coauthors.
 *
 * A Bag stores items without caring about order. Items can be added, and the
 * Bag can be iterated through, but it does not support removing specific items.
 * This makes it useful for graph edges, where we only need to collect and loop
 * through neighbors.
 *
 * This implementation uses a singly linked list. Adding an item takes O(1)
 * time, and iterating through all items takes O(n) time, where n is the number
 * of items in the Bag.
 *
 * Original authors:
 * Robert Sedgewick
 * Kevin Wayne
 *
 * Adapted by:
 * Max Liu, Joshua Oyadomari-Chun, Sophie Park, and Fred Wang
 *
 * @param <Item> the type of item stored in the Bag
 */
public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of bag
    private int n;               // number of elements in bag

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty bag.
     */
    public Bag() {
        first = null;
        n = 0;
    }

    /**
     * Returns true if this bag is empty.
     *
     * @return {@code true} if this bag is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this bag.
     *
     * @return the number of items in this bag
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to this bag.
     *
     * @param  item the item to add to this bag
     */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }


    /**
     * Returns an iterator that iterates over the items in this bag in arbitrary order.
     *
     * @return an iterator that iterates over the items in this bag in arbitrary order
     */
    public Iterator<Item> iterator()  {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Unit tests the {@code Bag} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
    }

}