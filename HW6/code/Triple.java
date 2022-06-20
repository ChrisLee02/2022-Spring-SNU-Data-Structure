public class Triple<T, U, V extends Comparable<V>> implements Comparable<Triple<T, U, V>> {

    public final T first;
    public final U second;
    public final V third;

    public Triple(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }


    @Override
    public int compareTo(Triple<T, U, V> o) {
        return third.compareTo(o.third);
    }

}

