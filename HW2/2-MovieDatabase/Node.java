public class Node<T> {
    private T item;
    private Node<T> next;

    public Node(T obj) {
        this.item = obj;
        this.next = null;
    }
    
    public Node(T obj, Node<T> next) {
    	this.item = obj;
    	this.next = next;
    }
    
    public final T getItem() {
    	return item;
    }
    
    public final void setItem(T item) {
    	this.item = item;
    }
    
    public final void setNext(Node<T> next) {
    	this.next = next;
    }
    
    public Node<T> getNext() {
    	return this.next;
    }
    
    public final void insertNext(T obj) {
        Node<T> node = new Node<T>(obj);
        node.setNext(this.next);
        this.next = node;
//		throw new UnsupportedOperationException("not implemented yet");
    }
    
    public final void removeNext() {
        Node<T> node = this.next;
        this.next = node.next;
        node.next = null;
		//throw new UnsupportedOperationException("not implemented yet");
    }
}