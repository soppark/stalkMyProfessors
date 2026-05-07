package structures;
import java.util.LinkedList;
public class Queue<T> implements Iterable<T> {
    private final LinkedList<T> list = new LinkedList<>();
    public void enqueue(T x) { list.addLast(x); }
    public T dequeue()        { return list.removeFirst(); }
    public boolean isEmpty()  { return list.isEmpty(); }
    public int size()         { return list.size(); }
    public java.util.Iterator<T> iterator() { return list.iterator(); }
}