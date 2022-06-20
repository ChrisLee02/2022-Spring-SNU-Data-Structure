import java.util.ArrayList;
import java.util.List;

public class Route implements Comparable<Route> {
    Integer cost;
    List<String> route;
    Integer transfer;
    Route() {
        route = new ArrayList<>();
    }

    public void calTransfer() {
        transfer = 0;
        for(int i = 0; i< route.size()-1; i++) {
            if(route.get(i).equals(route.get(i+1))) {
                transfer++;
            }
        }
    }

    public void printRoute() {
        for(int i = 0; i< route.size()-1; i++) {
            if(route.get(i).equals(route.get(i+1))) {
                System.out.print("[" + route.get(i) + "] ");
                i++;
            }
            else {
                System.out.print(route.get(i) + " ");
            }
        }
        System.out.println(route.get(route.size()-1));
        System.out.println(cost);
    }

    @Override
    public int compareTo(Route o) {
        if(this.cost == o.cost) {
            return this.transfer.compareTo(o.transfer);
        }
        else {
            return this.cost.compareTo(o.cost);
        }
    }
}
