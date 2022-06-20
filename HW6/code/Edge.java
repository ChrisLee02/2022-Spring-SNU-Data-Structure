public class Edge implements Comparable<Edge> {

    public Station arrivalPoint;
    public int distance;
    public boolean isTransfer;
    Edge(Station arrivalPoint, int distance) {
        this.distance = distance;
        this.arrivalPoint = arrivalPoint;
        isTransfer = false;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "arrivalPoint=" + arrivalPoint +
                ", distance=" + distance +
                '}';
    }

    @Override
    public int compareTo(Edge o) {
        return isTransfer ? 1 : -1;
    }
}
