import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MakeRandomValue {

    static void makeSizeData() {
        try {
            String dir = "code/size/";
            int minimum = -100000000;
            int maximum = 100000000;
            Integer[] cases = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};

            for (int Case: cases) {
                System.out.println(Case);
                for(int i = 1; i<=100; i++) {
                    Random rand = new Random(i);    // 난수 인스턴스를 생성한다. 시드값 고정
                    FileWriter fileWriter = new FileWriter(dir + Case + "/" + i + ".txt");
                    for (int j = 0; j<Case-1; j++) {
                        fileWriter.write(rand.nextInt(maximum - minimum + 1) + minimum + "\n");
                    }
                    fileWriter.write(rand.nextInt(maximum - minimum + 1) + minimum+"");
                    fileWriter.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void makeRangeData() {
        try {
            String dir = "code/range/";
            int Size = 100000;
            Integer[] cases = {100, 1000, 10000, 100000, 1000000, 10000000};

            for (int Case: cases) {
                System.out.println(Case);
                int minimum = -1 * Case/2;
                int maximum = Case/2;
                for(int i = 1; i<=100; i++) {
                    Random rand = new Random(i);    // 난수 인스턴스를 생성한다. 시드값 고정
                    FileWriter fileWriter = new FileWriter(dir + Case + "/" + i + ".txt");
                    for (int j = 0; j<Size-1; j++) {
                        fileWriter.write(rand.nextInt(maximum - minimum + 1) + minimum + "\n");
                    }
                    fileWriter.write(rand.nextInt(maximum - minimum + 1) + minimum+"");
                    fileWriter.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }





    public static void main(String[] args) {
        makeSizeData();
        makeRangeData();
    }


}
