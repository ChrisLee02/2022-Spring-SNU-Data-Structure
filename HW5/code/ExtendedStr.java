import java.util.LinkedList;
import java.util.List;

public class ExtendedStr implements Comparable<ExtendedStr>  {
    public String item;
    public Pair position;
    int hashcode;


    ExtendedStr(String item, Pair position) {
        this.item = item;
        this.position = position;
        int sum = 0;
        for(int i = 0; i<item.length();i++) {
            sum += item.charAt(i);
        }
        hashcode = sum%100;
    }

    @Override
    public int compareTo(ExtendedStr o) {
        return this.item.compareTo(o.item);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ExtendedStr other = (ExtendedStr)o;
        return this.compareTo(other)==0;
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    public int getPosX() {
        return this.position.getX();
    }

    public int getPosY() {
        return this.position.getY();
    }

    @Override
    public String toString() {
        return this.position.toString();
    }

}
