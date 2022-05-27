
public interface IndexInterface<Node, T extends Comparable<T>> {
    public void insert(T x);
    public Node search(T x);
    public void delete(T x);
    public boolean isEmpty();
    public void clear();
}
