import java.util.Random;

public class Test {
    public static void main(String[] args) {
        testTime();
    }
    public static void testTime() {
        int[] lengthShort = {10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000}; // 모든 정렬에 대해 테스트하기 위한 배열의 길이
        int[] lengthLong = {100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000}; // 빠른 정렬에 대해 테스트하기 위한 배열의 길이
        int[] maxAbsValue = {100, 1000, 10000, 100000, 1000000, 10000000, 100000000}; // radix sort 를 테스트하기 위한 숫자의 절대값의 최대값

        int[] value;
        int[] newvalue;
        Random rand = new Random();
        long t;

        System.out.println("---start testing for short array---");
        for (int length : lengthShort) {
            System.out.println("test for length: "+length);
            for (int i=1; i<=10; i++) {
                System.out.println("test#"+i);

                value = new int[length];
                for (int j = 0; j < value.length; j++)
                    value[j] = rand.nextInt( 10000) - 10000;

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoBubbleSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoInsertionSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoHeapSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoMergeSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoQuickSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);
            }
        }

        System.out.println("---start testing for long array---");
        for (int length : lengthLong) {
            System.out.println("test for length: "+length);
            for (int i=1; i<=10; i++) {
                System.out.println("test#"+i);

                value = new int[length];
                for (int j = 0; j < value.length; j++)
                    value[j] = rand.nextInt( 10000) - 10000;

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoHeapSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoMergeSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoQuickSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);
            }
        }

        System.out.println("---start testing radix sort---");
        for (int bound : maxAbsValue) {
            System.out.println("test for max abs value: "+bound);
            for (int i=1; i<=10; i++) {
                System.out.println("test#"+i);

                value = new int[100000];
                for (int j = 0; j < value.length; j++)
                    value[j] = rand.nextInt( 2*bound) - bound;

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoQuickSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);

                newvalue = value.clone();
                t = System.currentTimeMillis();
                DoRadixSort(newvalue);
                System.out.println(System.currentTimeMillis() - t);
            }
        }
    }
}
