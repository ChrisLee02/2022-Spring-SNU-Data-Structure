public interface PQInterface<E> {
    public void insert(E newItem);
    public E deleteMin();
    public E min();
    public void buildHeap();
    public boolean isEmpty();
    public void clear();
}
