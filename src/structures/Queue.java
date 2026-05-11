package structures;
import java.util.LinkedList;

/**
 * Queue - FIFO queue.
 *
 * Implement enqueue / dequeue method and Iterable for loops.
 *
 * Used by BST for level-order traversal and for collecting keys.
 *
 * @param <T> the type of stuff in the queue
 */
public class Queue<T> implements Iterable<T> {
    private final LinkedList<T> list = new LinkedList<>();
    public void enqueue(T x) { list.addLast(x); }
    public T dequeue()        { return list.removeFirst(); }
    public boolean isEmpty()  { return list.isEmpty(); }
    public int size()         { return list.size(); }
    public java.util.Iterator<T> iterator() { return list.iterator(); }
}