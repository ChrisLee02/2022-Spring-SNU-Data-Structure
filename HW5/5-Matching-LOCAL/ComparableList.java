import java.util.LinkedList;

public class ComparableList<T extends Comparable<T>> extends LinkedList<T> implements Comparable<ComparableList<T>> {


    @Override
    public int compareTo(ComparableList<T> o) {
        return this.get(0).compareTo(o.get(0));
    }
}
