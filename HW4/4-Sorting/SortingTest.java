import java.io.*;
import java.util.*;


public class SortingTest {
    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            boolean isRandom = false;    // 입력받은 배열이 난수인가 아닌가?
            int[] value;    // 입력 받을 숫자들의 배열
            String nums = br.readLine();    // 첫 줄을 입력 받음
            if (nums.charAt(0) == 'r') {
                // 난수일 경우
                isRandom = true;    // 난수임을 표시

                String[] nums_arg = nums.split(" ");

                int numsize = Integer.parseInt(nums_arg[1]);    // 총 갯수
                int rminimum = Integer.parseInt(nums_arg[2]);    // 최소값
                int rmaximum = Integer.parseInt(nums_arg[3]);    // 최대값

                Random rand = new Random();    // 난수 인스턴스를 생성한다.

                value = new int[numsize];    // 배열을 생성한다.
                for (int i = 0; i < value.length; i++)    // 각각의 배열에 난수를 생성하여 대입
                    value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
            } else {
                // 난수가 아닐 경우
                int numsize = Integer.parseInt(nums);

                value = new int[numsize];    // 배열을 생성한다.
                for (int i = 0; i < value.length; i++)    // 한줄씩 입력받아 배열원소로 대입
                    value[i] = Integer.parseInt(br.readLine());
            }

            // 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
            while (true) {
                int[] newvalue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.

                String command = br.readLine();

                long t = System.currentTimeMillis();
                switch (command.charAt(0)) {
                    case 'B':    // Bubble Sort
                        newvalue = DoBubbleSort(newvalue);
                        break;
                    case 'I':    // Insertion Sort
                        newvalue = DoInsertionSort(newvalue);
                        break;
                    case 'H':    // Heap Sort
                        newvalue = DoHeapSort(newvalue);
                        break;
                    case 'M':    // Merge Sort
                        newvalue = DoMergeSort(newvalue);
                        break;
                    case 'Q':    // Quick Sort
                        newvalue = DoQuickSort(newvalue);
                        break;
                    case 'R':    // Radix Sort
                        newvalue = DoRadixSort(newvalue);
                        break;
                    case 'X':
                        return;    // 프로그램을 종료한다.
                    default:
                        throw new IOException("잘못된 정렬 방법을 입력했습니다.");
                }
                if (isRandom) {
                    // 난수일 경우 수행시간을 출력한다.
                    System.out.println((System.currentTimeMillis() - t) + " ms");
                } else {
                    // 난수가 아닐 경우 정렬된 결과값을 출력한다.
                    for (int i = 0; i < newvalue.length; i++) {
                        System.out.println(newvalue[i]);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoBubbleSort(int[] value) {
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
    private static int[] DoInsertionSort(int[] value) {
        // TODO : Insertion Sort 를 구현하라.
        int n = value.length;
        for (int i = 1; i < n; i++) {
            int ithValue = value[i];
            for (int j = i - 1; j >= -1; j--) {
                if (j==-1 || value[j] <= ithValue) {
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

    private static int[] DoHeapSort(int[] value) {
        // TODO : Heap Sort 를 구현하라.
        Heap heap = new Heap(value, value.length);
        heap.heapSort();
        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] DoMergeSort(int[] value) {
        // TODO : Merge Sort 를 구현하라.
        mergeSort(value, 0, value.length - 1);
        return (value);
    }

    private static void mergeSort(int[] value, int s, int e) {
        if (s < e) { //재귀 호출을 반복해가며 크기가 0또는 1인 부분 배열에서 되돌아가도록 한다.
            int m = (s + e) / 2;
            mergeSort(value, s, m);
            mergeSort(value, m + 1, e);
            // 위의 두 부분배열은 base 조건이나, merge() 를 통해 정렬된 두 배열이 된다.
            merge(value, s, m, e);
        } else return;
    }

    private static void merge(int[] value, int s, int m, int e) {
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
    private static int[] DoQuickSort(int[] value) {
        // TODO : Quick Sort 를 구현하라.
        quickSort(value, 0, value.length - 1);
        return (value);
    }

    private static void quickSort(int[] value, int s, int e) {
        if(s<e) {
            int pivotIndex = partition(value, s, e);
            quickSort(value, s, pivotIndex-1);
            quickSort(value, pivotIndex+1, e);
        }
        else return;
    }

    private static int partition(int[] value, int s, int e) {
        int pivot = value[e];
        int endOfAreaFirst = s-1;
        int startOfAreaThird = s;
        // 1, 2, 3구역은 각각 pivot 보다 작은, 큰, 미판별 구역
        while(startOfAreaThird <= e-1) {
            if(value[startOfAreaThird] < pivot) { //  1구역으로 이동
                endOfAreaFirst++; //1구역 한 칸 늘리고
                int tmp = value[startOfAreaThird];
                value[startOfAreaThird] = value[endOfAreaFirst];
                value[endOfAreaFirst] = tmp;
            }
            startOfAreaThird++; //1구역을 늘리지 않고 3구역 시작점을 한 칸 shift 하면 2구역으로 이동하는 것과 동치임.
            //1구역을 한 칸 늘리고, 바꾼 뒤에 shift를 했다면 1구역이 한 칸 커지고, 2구역에서 3구역으로 이동한 element가 2구역으로 포함됨.
        }

        int tmp = value[e];
        value[e] = value[endOfAreaFirst+1];
        value[endOfAreaFirst+1] = tmp;
        // pivot 을 찾은 위치로 이동.
        return endOfAreaFirst+1;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] findMinMax(int[] value) {
        int[] MinMax = new int[2];
        int min = value[0];
        int max = value[0];
        for(int i = 1; i<value.length; i++) {
            if(min > value[i]) min = value[i];
            if(max < value[i]) max = value[i];
        }
        MinMax[0] = min;
        MinMax[1] = max;
        return MinMax;
    }


    private static void countingSort(int[] value, int digit) { //자릿수는 -9~9까지임.
        int n = value.length;
        if(n==0) return;
        int[] count = new int[19]; // -9~9 -> 0~18로 변환
        int[] tmp = new int[n];
        int divider = (int) Math.pow(10, digit-1);

        for(int i = 0; i<n; i++) {
            int countIndex = (value[i] / divider)%10 + 9;
            tmp[i] = value[i]; //DeepCopy
            count[countIndex]++;
        }
        for(int i = 1; i<count.length; i++) {
            count[i] = count[i] + count[i-1];
        }
        /*for(int i = 0; i<count.length; i++) {
            System.out.print(i-9 +": " + count[i] + " ");
        }
        System.out.println();*/
        //debugging
        for(int i = n-1; i>=0; i--) {
            int countIndex = (tmp[i] / divider)%10 + 9;
            value[--count[countIndex]] = tmp[i];
        }
    }


    private static int[] DoRadixSort(int[] value) {
        // TODO : Radix Sort 를 구현하라.
        //정수 최대길이는 최솟값의 길이에서 1 뺀거랑, 최댓값의 길이 중 더 큰놈을 택함
        int[] MinMax = findMinMax(value);
        int min = MinMax[0];
        int max = MinMax[1];
        int len = Math.max((min + "").length() - 1, (max + "").length());
        for(int i = 1; i<=len; i++) {
            countingSort(value, i);
            /*for(int j = 0; j< value.length; j++) {
                System.out.print(value[j] + " ");
            }
            System.out.println();*/ // debugging
        }

        return (value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
}
