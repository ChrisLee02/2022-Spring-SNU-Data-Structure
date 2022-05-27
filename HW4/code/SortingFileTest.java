import java.io.*;
import java.util.*;

public class SortingFileTest {


    static long returnBubbleTime(int[] value) {
        long t = System.currentTimeMillis();
        int[] newValue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.
        newValue = DoBubbleSort(newValue);
        return (System.currentTimeMillis() - t);
    }

    static long returnInsertionTime(int[] value) {
        long t = System.currentTimeMillis();
        int[] newValue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.
        newValue = DoInsertionSort(newValue);
        return (System.currentTimeMillis() - t);
    }

    static long returnQuickTime(int[] value) {
        long t = System.currentTimeMillis();
        int[] newValue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.
        newValue = DoQuickSort(newValue);
        return (System.currentTimeMillis() - t);
    }
    static long returnModifiedQuickTime(int[] value) {
        long t = System.currentTimeMillis();
        int[] newValue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.
        newValue = DoModifiedQuickSort(newValue);
        return (System.currentTimeMillis() - t);
    }

    static long returnHeapTime(int[] value) {
        long t = System.currentTimeMillis();
        int[] newValue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.
        newValue = DoHeapSort(newValue);
        return (System.currentTimeMillis() - t);
    }

    static long returnMergeTime(int[] value) {
        long t = System.currentTimeMillis();
        int[] newValue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.
        newValue = DoMergeSort(newValue);
        return (System.currentTimeMillis() - t);
    }
    static long returnWorseMergeTime(int[] value) {
        long t = System.currentTimeMillis();
        int[] newValue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.
        newValue = DoWorseMergeSort(newValue);
        return (System.currentTimeMillis() - t);
    }

    static long returnRadixTime(int[] value) {
        long t = System.currentTimeMillis();
        int[] newValue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.
        newValue = DoRadixSort(newValue);
        return (System.currentTimeMillis() - t);
    }

    static double returnMeanOfList(List<Long> times) {
        double sum = 0;
        for (Long time : times) {
            sum += time;
        }
        return sum / times.size();
    }

    static double returnStdevOfList(List<Long> times, double mean) {
        double sum = 0;
        for (Long time : times) {
            sum += Math.pow(time - mean, 2);
        }
        return Math.sqrt(sum / (times.size() - 1));
    }

    static void BubbleSizeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/size/";
            String resultDir = "code/result/size/BubbleSizeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            Integer[] cases = {1000, 5000, 10000, 50000};//, 100000};//, 500000, 1000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Case];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Case; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnBubbleTime(value));
                }

                System.out.println("버블 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "버블 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void InsertionSizeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/size/";
            String resultDir = "code/result/size/InsertionSizeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            Integer[] cases = {1000, 5000, 10000, 50000};//, 100000}; //, 500000, 1000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Case];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Case; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnInsertionTime(value));
                }

                System.out.println("삽입 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "삽입 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void HeapSizeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/size/";
            String resultDir = "code/result/size/HeapSizeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            Integer[] cases = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Case];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Case; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnHeapTime(value));
                }

                System.out.println("힙 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "힙 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void QuickSizeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/size/";
            String resultDir = "code/result/size/QuickSizeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            Integer[] cases = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Case];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Case; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnQuickTime(value));
                }

                System.out.println("퀵 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "퀵 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void MergeSizeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/size/";
            String resultDir = "code/result/size/MergeSizeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            Integer[] cases = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Case];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Case; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnMergeTime(value));
                }

                System.out.println("개선된 병합 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                //System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "병합 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    static void WorseMergeSizeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/size/";
            String resultDir = "code/result/size/WorseMergeSizeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            Integer[] cases = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Case];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Case; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnWorseMergeTime(value));
                }

                System.out.println("병합 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                //System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "병합 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void RadixSizeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/size/";
            String resultDir = "code/result/size/RadixSizeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            Integer[] cases = {1000, 5000, 10000, 50000, 100000, 500000, 1000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Case];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Case; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnRadixTime(value));
                }

                System.out.println("기수 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "기수 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    static void BubbleRangeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/range/";
            String resultDir = "code/result/range/BubbleRangeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            int Size = 50000;
            Integer[] cases = {100, 1000, 10000, 100000, 1000000, 10000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Size];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Size; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnBubbleTime(value));
                }

                System.out.println("버블 정렬에 대한 범위 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "버블 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void InsertionRangeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/range/";
            String resultDir = "code/result/range/InsertionRangeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            int Size = 50000;
            Integer[] cases = {100, 1000, 10000, 100000, 1000000, 10000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Size];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Size; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnInsertionTime(value));
                }

                System.out.println("삽입 정렬에 대한 범위 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "삽입 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void HeapRangeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/range/";
            String resultDir = "code/result/range/HeapRangeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            int Size = 100000;
            Integer[] cases = {100, 1000, 10000, 100000, 1000000, 10000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Size];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Size; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnHeapTime(value));
                }

                System.out.println("힙 정렬에 대한 범위 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "힙 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void QuickRangeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/range/";
            String resultDir = "code/result/range/QuickRangeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            int Size = 100000;
            Integer[] cases = {100, 1000, 10000, 100000, 1000000, 10000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Size];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Size; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnQuickTime(value));
                }

                System.out.println("퀵 정렬에 대한 범위 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                //System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "퀵 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    static void ModifiedQuickRangeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/range/";
            String resultDir = "code/result/range/QuickRangeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            int Size = 100000;
            Integer[] cases = {100, 1000, 10000, 100000, 1000000, 10000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Size];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Size; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnModifiedQuickTime(value));
                }

                System.out.println("개선된 퀵 정렬에 대한 범위 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
               //System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "퀵 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void MergeRangeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/range/";
            String resultDir = "code/result/range/MergeRangeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            int Size = 100000;
            Integer[] cases = {100, 1000, 10000, 100000, 1000000, 10000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Size];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Size; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnMergeTime(value));
                }

                System.out.println("병합 정렬에 대한 범위 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "병합 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void RadixRangeTest() {
        try {
            int[] value;
            List<Long> times;
            String dir = "code/range/";
            String resultDir = "code/result/range/RadixRangeTest.txt";
            FileWriter resultWriter = new FileWriter(resultDir);
            int Size = 100000;
            Integer[] cases = {100, 1000, 10000, 100000, 1000000, 10000000};
            for (int Case : cases) {
                File path = new File(dir + Case);
                File[] fileList = path.listFiles();
                times = new ArrayList<>();
                for (File file : fileList) {
                    value = new int[Size];
                    Scanner scanner = new Scanner(file);
                    for (int i = 0; i < Size; i++) {
                        value[i] = scanner.nextInt();
                    }
                    times.add(returnRadixTime(value));
                }

                System.out.println("기수 정렬에 대한 범위 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소");
                times.remove(0);
                times.remove(0);
                times.remove(0);
                times.remove(0); //앞부분 비정상적인 값 제거.
                System.out.println(times);
                double mean = returnMeanOfList(times);
                long max = Collections.max(times);
                long min = Collections.min(times);
                double stdev = returnStdevOfList(times, mean);
                System.out.println(mean + " " + stdev + " " + max + " " + min);
                System.out.println("==================================");
                String result = "기수 정렬에 대한 크기 " + Case + "의 정렬 시간 평균, 표준편차, 최대, 최소\n" + mean + " " + stdev + " " + max + " " + min +
                        "\n==================================\n";
                resultWriter.write(result);
            }
            resultWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String args[]) {
        // 입력 받을 숫자들의 배열
        /*QuickSizeTest();
        MergeSizeTest();
        HeapSizeTest();
        RadixSizeTest();


         BubbleSizeTest();
        InsertionSizeTest();

        QuickRangeTest();
        MergeRangeTest();
        HeapRangeTest();
        RadixRangeTest();

        BubbleRangeTest();
        InsertionRangeTest();*/

        /*QuickRangeTest();*/

        /*MergeSizeTest();
        WorseMergeSizeTest();*/

        QuickRangeTest();
        ModifiedQuickRangeTest();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    static int[] DoBubbleSort(int[] value) {
        // TODO : Bubble Sort 를 구현하라.
        // value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
        // 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
        // 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
        // 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
        int n = value.length;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < n - i; j++) {
                if (value[j] > value[j + 1]) {
                    int tmp = value[j + 1];
                    value[j + 1] = value[j];
                    value[j] = tmp;
                }
            }
        }
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    static int[] DoInsertionSort(int[] value) {
        // TODO : Insertion Sort 를 구현하라.
        int n = value.length;
        for (int i = 1; i < n; i++) {
            int ithValue = value[i];
            for (int j = i - 1; j >= -1; j--) {
                if (j == -1 || value[j] <= ithValue) {
                    value[j + 1] = ithValue;
                    break;
                }
                value[j + 1] = value[j];
            }
        }
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    static class Heap {
        private int[] A;
        private int numItems;

        public Heap(int[] B, int numElements) {
            A = B;
            numItems = numElements;
            buildHeap();
        }


        public int deleteMax() throws Exception {
            if (!isEmpty()) {
                int max = A[0];
                A[0] = A[numItems - 1];
                numItems--;
                percolateDown(0);
                return max;
            } else {
                throw new Exception("HeapErr: DeleteMax()-Underflow");
            }
        }


        public void buildHeap() {
            if (numItems >= 2) {
                for (int i = (numItems - 2) / 2; i >= 0; i--) {
                    percolateDown(i);
                }
            }
        }

        public boolean isEmpty() {
            return numItems == 0;
        }

        private void percolateDown(int i) {
            int child = 2 * i + 1;
            int right = 2 * i + 2;
            if (child <= numItems - 1) {
                if (right <= numItems - 1 && A[child] < A[right]) {
                    child = right;
                }
                if (A[i] < A[child]) {
                    int tmp = A[i];
                    A[i] = A[child];
                    A[child] = tmp;
                    percolateDown(child);
                }

            }
            return;
        }

        public void heapSort() {
            buildHeap();
            for (int i = A.length - 1; i >= 1; i--) {
                try {
                    A[i] = deleteMax();
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

    static int[] DoHeapSort(int[] value) {
        // TODO : Heap Sort 를 구현하라.
        SortingExperiment.Heap heap = new SortingExperiment.Heap(value, value.length);
        heap.heapSort();
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    static int[] DoMergeSort(int[] value) {
        // TODO : Merge Sort 를 구현하라.
        int[] B = new int[value.length];
        for (int i = 0; i < value.length; i++) {
            B[i] = value[i];
        }
        betterMergeSort(value, B, 0, value.length - 1);
        return (value);
    }

    static int[] DoWorseMergeSort(int[] value) {
        // TODO : Merge Sort 를 구현하라.
        mergeSort(value, 0, value.length - 1);
        return (value);
    }

    static void betterMergeSort(int[] value, int[] tmp, int s, int e) { //
        if (s < e) { //재귀 호출을 반복해가며 크기가 0또는 1인 부분 배열에서 되돌아가도록 한다.
            int m = (s + e) / 2;
            betterMergeSort(tmp, value, s, m);
            betterMergeSort(tmp, value, m + 1, e);
            // 위의 두 부분배열은 base 조건이나, merge() 를 통해 정렬된 두 배열이 된다.
            betterMerge(tmp, value, s, m, e);
        } else return;
    }

    static void betterMerge(int[] tmp, int[] value, int s, int m, int e) {
        int i = s;
        int j = m + 1;
        int t = s;
        while (i <= m && j <= e) {
            if (tmp[i] <= tmp[j])
                value[t++] = tmp[i++];
            else
                value[t++] = tmp[j++];
        }
        while (i <= m)
            value[t++] = tmp[i++];
        while (j <= e)
            value[t++] = tmp[j++];

    }

    static void mergeSort(int[] value, int s, int e) {
        if (s < e) { //재귀 호출을 반복해가며 크기가 0또는 1인 부분 배열에서 되돌아가도록 한다.
            int m = (s + e) / 2;
            mergeSort(value, s, m);
            mergeSort(value, m + 1, e);
            // 위의 두 부분배열은 base 조건이나, merge() 를 통해 정렬된 두 배열이 된다.
            merge(value, s, m, e);
        } else return;
    }

    static void merge(int[] value, int s, int m, int e) {
        int i = s, j = m + 1, t = 0;
        int[] tmp = new int[e - s + 1]; // s~e -> 0~e-s
        while (i <= m && j <= e) {
            if (value[i] <= value[j]) {
                tmp[t++] = value[i++];
            } else {
                tmp[t++] = value[j++];
            }
        }

        while (i <= m) {
            tmp[t++] = value[i++];
        }

        while (j <= e) {
            tmp[t++] = value[j++];
        }

        i = s;
        t = 0;
        while (i <= e) {
            value[i++] = tmp[t++];
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    static int[] DoQuickSort(int[] value) {
        // TODO : Quick Sort 를 구현하라.
        quickSort(value, 0, value.length - 1);
        return (value);
    }
    static int[] DoModifiedQuickSort(int[] value) {
        // TODO : Quick Sort 를 구현하라.
        modifiedQuickSort(value, 0, value.length - 1);
        return (value);
    }


    static void quickSort(int[] value, int s, int e) {
        if (s < e) {
            int pivotIndex = partition(value, s, e);
            quickSort(value, s, pivotIndex - 1);
            quickSort(value, pivotIndex + 1, e);
        } else return;
    }

    static int partition(int[] value, int s, int e) {
        int pivot = value[e];
        int endOfAreaFirst = s - 1;
        int startOfAreaThird = s;
        // 1, 2, 3구역은 각각 pivot 보다 작은, 큰, 미판별 구역
        while (startOfAreaThird <= e - 1) {
            if (value[startOfAreaThird] < pivot) {
                //  1구역으로 이동
                endOfAreaFirst++; //1구역 한 칸 늘리고
                int tmp = value[startOfAreaThird];
                value[startOfAreaThird] = value[endOfAreaFirst];
                value[endOfAreaFirst] = tmp;
            }
            startOfAreaThird++; //1구역을 늘리지 않고 3구역 시작점을 한 칸 shift 하면 2구역으로 이동하는 것과 동치임.
            //1구역을 한 칸 늘리고, 바꾼 뒤에 shift를 했다면 1구역이 한 칸 커지고, 2구역에서 3구역으로 이동한 element가 2구역으로 포함됨.

            //시험문제에 나왔던, 그 코드를 한 번 적용해보자.
        }

        int tmp = value[e];
        value[e] = value[endOfAreaFirst + 1];
        value[endOfAreaFirst + 1] = tmp;
        // pivot 을 찾은 위치로 이동.
        return endOfAreaFirst + 1;
    }

    static void modifiedQuickSort(int[] value, int s, int e) {
        if (s < e) {
            int pivotIndex = modifiedPartition(value, s, e);
            quickSort(value, s, pivotIndex - 1);
            quickSort(value, pivotIndex + 1, e);
        } else return;
    }

    static int modifiedPartition(int[] value, int s, int e) {
        int pivot = value[e];
        int endOfAreaFirst = s - 1;
        int startOfAreaThird = s;
        // 1, 2, 3구역은 각각 pivot 보다 작은, 큰, 미판별 구역
        while (startOfAreaThird <= e - 1) {
            if (value[startOfAreaThird] < pivot
                || (value[startOfAreaThird] == pivot && Math.random() < 0.5)  ) {
                //  1구역으로 이동
                endOfAreaFirst++; //1구역 한 칸 늘리고
                int tmp = value[startOfAreaThird];
                value[startOfAreaThird] = value[endOfAreaFirst];
                value[endOfAreaFirst] = tmp;
            }
            startOfAreaThird++; //1구역을 늘리지 않고 3구역 시작점을 한 칸 shift 하면 2구역으로 이동하는 것과 동치임.
            //1구역을 한 칸 늘리고, 바꾼 뒤에 shift를 했다면 1구역이 한 칸 커지고, 2구역에서 3구역으로 이동한 element가 2구역으로 포함됨.

            //시험문제에 나왔던, 그 코드를 한 번 적용해보자.
        }

        int tmp = value[e];
        value[e] = value[endOfAreaFirst + 1];
        value[endOfAreaFirst + 1] = tmp;
        // pivot 을 찾은 위치로 이동.
        return endOfAreaFirst + 1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    static int[] findMinMax(int[] value) {
        int[] MinMax = new int[2];
        int min = value[0];
        int max = value[0];
        for (int i = 1; i < value.length; i++) {
            if (min > value[i]) min = value[i];
            if (max < value[i]) max = value[i];
        }
        MinMax[0] = min;
        MinMax[1] = max;
        return MinMax;
    }


    static void countingSort(int[] value, int digit) { //자릿수는 -9~9까지임.
        int n = value.length;
        if (n == 0) return;
        int[] count = new int[19]; // -9~9 -> 0~18로 변환
        int[] tmp = new int[n];
        int divider = (int) Math.pow(10, digit - 1);

        for (int i = 0; i < n; i++) {
            int countIndex = (value[i] / divider) % 10 + 9;
            tmp[i] = value[i]; //DeepCopy
            count[countIndex]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i] + count[i - 1];
        }
        /*for(int i = 0; i<count.length; i++) {
            System.out.print(i-9 +": " + count[i] + " ");
        }
        System.out.println();*/
        //debugging
        for (int i = n - 1; i >= 0; i--) {
            int countIndex = (tmp[i] / divider) % 10 + 9;
            value[--count[countIndex]] = tmp[i];
        }
    }


    static int[] DoRadixSort(int[] value) {
        // TODO : Radix Sort 를 구현하라.
        //정수 최대길이는 최솟값의 길이에서 1 뺀거랑, 최댓값의 길이 중 더 큰놈을 택함
        int[] MinMax = findMinMax(value);
        int min = MinMax[0];
        int max = MinMax[1];
        int len = Math.max((min + "").length() - 1, (max + "").length());
        for (int i = 1; i <= len; i++) {
            countingSort(value, i);
            /*for(int j = 0; j< value.length; j++) {
                System.out.print(value[j] + " ");
            }
            System.out.println();*/ // debugging
        }

        return (value);
    }
}
