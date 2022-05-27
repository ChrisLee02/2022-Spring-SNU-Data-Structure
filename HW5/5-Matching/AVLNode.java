

public class AVLNode<T extends Comparable<T>> {
    public T item;
    public AVLNode<T> left;
    public AVLNode<T> right;
    public AVLNode<T> linkedNode;
    public int height;

    public AVLNode(T newItem) {
        item = newItem;
        left = right = (AVLNode<T>) AVLTree.NIL;
        linkedNode = null;
        height = 1;
    }

    public AVLNode(T newItem, AVLNode<T> leftChild, AVLNode<T> rightChild) {
        item = newItem;
        left = leftChild;
        right = rightChild;
        linkedNode = null;
        height = 1;
    }

    public AVLNode(T newItem, AVLNode<T> leftChild, AVLNode<T> rightChild, int height) {
        item = newItem;
        left = leftChild;
        right = rightChild;
        linkedNode = null;
        height = height;
    }

    public String toString() {
        if(item == null) return null;
        return item.toString();
    }
}
