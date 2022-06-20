import java.util.Arrays;

public class Heap<E extends Comparable<E>> implements PQInterface<E> {
    private E[] A;
    private int numItems;

    public Heap(int arraySize) {
        A = (E[]) new Comparable[arraySize];
        numItems = 0;
    }

    public Heap(E[] B, int numElements) {
        A = B;
        numItems = numElements;
    }

    @Override
    public void insert(E newItem) {
        if(numItems < A.length) {
            A[numItems] = newItem;
            percolateUp(numItems);
            numItems++;
        } else {
            System.out.println("Overflow in insert()");
        }
    }

    @Override
    public E deleteMin() {
        if(!isEmpty()) {
            E max = A[0];
            A[0] = A[numItems-1];
            numItems--;
            percolateDown(0);
            return max;
        } else {
            return null;
        }
    }

    @Override
    public E min() {
        return A[0];
    }

    @Override
    public void buildHeap() {
        if(numItems>=2) {
            for(int i = (numItems-2)/2; i>=0; i--) {
                percolateDown(i);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return numItems==0;
    }

    @Override
    public void clear() {
        A = (E[]) new Comparable[A.length];
        numItems = 0;
    }

    private void percolateUp(int i) {
        int parent = (i-1)/2;
        if (parent>=0 && A[i].compareTo(A[parent]) < 0 ) {
            //최상단 노드가 되었거나, 아니면 부모보다 클 때 스탑.
            E tmp = A[i];
            A[i] = A[parent];
            A[parent] = tmp;
            percolateUp(parent);
        }
        return;
    }

    private void percolateDown(int i) {
        int child = 2*i + 1;
        int right = 2*i + 2;
        if(child <= numItems - 1) {
            if(right <= numItems -1 && A[child].compareTo(A[right]) > 0) {
                child = right;
            }
            if( A[i].compareTo(A[child]) > 0  ) {
                E tmp = A[i];
                A[i] = A[child];
                A[child] = tmp;
                percolateDown(child);
            }
            //else return; 해도 되겠지?
        }
        return;
    }

    @Override
    public String toString() {
        return "Heap{" +
                "A=" + Arrays.toString(A) +
                ", numItems=" + numItems +
                '}';
    }
}
