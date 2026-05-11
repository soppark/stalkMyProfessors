package structures;
import java.util.LinkedList;

/**
 * Queue - super simple FIFO queue.
 *
 * Just wraps a LinkedList so I have nice enqueue / dequeue method names
 * instead of addLast / removeFirst. Also implements Iterable so I can
 * use it in for-each loops.
 *
 * Used by BST for level-order traversal and for collecting keys.
 *
 * @param <T> the type of stuff in the queue
 */
public class Queue<T> implements Iterable<T> {
    // the actual storage - LinkedList gives O(1) add/remove on both ends
    private final LinkedList<T> list = new LinkedList<>();

    /** add to the back of the queue */
    public void enqueue(T x) { list.addLast(x); }

    /** take off the front of the queue */
    public T dequeue()        { return list.removeFirst(); }

    /** true if nothing in the queue */
    public boolean isEmpty()  { return list.isEmpty(); }

    /** how many things are in the queue */
    public int size()         { return list.size(); }

    /** lets us use for-each loops on the queue */
    public java.util.Iterator<T> iterator() { return list.iterator(); }
}
