import java.util.ArrayList;
import java.util.List;

public class Station implements Comparable<Station> {
    static final int INF = 2000000000;

    public String ID, name, routeName;
    public Integer cost;
    public Station prev;
    public List<Edge> edges;
    Integer transfer;

    Station(String ID, String name, String routeName) {
        this.ID = ID;
        this.name = name;
        this.routeName = routeName;
        edges = new ArrayList<>();
    }

    /*@Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Station) {
            return ID.equals(((Station) o).ID);
        }
        else return false;
    }*/

    @Override
    public int compareTo(Station o) {
        return this.cost.compareTo(o.cost);
    }

    /*@Override
    public String toString() {
        return "Station{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", routeName='" + routeName + '\'' +
                ", cost=" + cost +
                ", prev=" + prev +
                ", edges=" + edges +
                '}';
    }*/

    @Override
    public String toString() {
        return this.ID + " " + this.name + " " + routeName + "호선 " + this.cost;
    }
}
