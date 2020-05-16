package OPP.lab_2020.lab4;

import java.util.Arrays;
import java.util.Random;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                             *
 *            Parallel and Distributed Computing               *
 *                  Laboratory work #4. Java                   *
 *                         Monitors                            *
 *                                                             *
 * Task: A = sort(Z)*MO + d*Z*(MX*ME)                          *
 *                                                             *
 * Komisarov Illia                                             *
 * group IO-71                                                 *
 *                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public class Main {
    static final int N = 1500;
    static final int P = 4;
    static final int H = N / P;
    static int[] A = new int[N];
    static int[][] ME = new int[N][N];
    static int[][] MO = new int[N][N];
    static int[] B1, B2, B3, B4;
    static int[] B12, B34;
    static int[][] MXME = new int[N][N];
    static boolean randZ = false;


    public static void main(String[] args) {
        ResourceMonitor resourceMonitor = new ResourceMonitor();
        SynchMonitor synchMonitor = new SynchMonitor();
        Thread t1 = new T1(resourceMonitor, synchMonitor);
        Thread t2 = new T2(resourceMonitor, synchMonitor);
        Thread t3 = new T3(resourceMonitor, synchMonitor);
        Thread t4 = new T4(resourceMonitor, synchMonitor);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

//        long start = System.currentTimeMillis();
//        int d = 1;
//        int[] Z = new int[N];
//        int[][] MX = new int[N][N];
//        inputVector(Z);
//        inputMatrix(MX);
//        inputMatrix(ME);
//        inputMatrix(MO);
//        int[] B = sort(Z, 0, N);
//        calculate(d, Z, B, MX, 0, N);
//        outputVector(A);
//        System.out.println(System.currentTimeMillis() - start);
    }

    public static int[] sort(int[] vector, int l, int r) {
        int[] newArr = new int[r - l];
        System.arraycopy(vector, l, newArr, 0, r - l);
        for (int i = 0; i < newArr.length; i++) {
            for (int k = i + 1; k < newArr.length; k++) {
                if (newArr[i] > newArr[k]) {
                    int temp = newArr[i];
                    newArr[i] = newArr[k];
                    newArr[k] = temp;
                }
            }
        }
        return newArr;
    }


    static int[] mergeSort(int[] left, int[] right) {
        int a = 0, b = 0;
        int[] merged = new int[left.length + right.length];
        for (int i = 0; i < left.length + right.length; i++) {
            if (b < right.length && a < left.length)
                if (left[a] > right[b])
                    merged[i] = right[b++];
                else
                    merged[i] = left[a++];
            else if (b < right.length)
                merged[i] = right[b++];
            else
                merged[i] = left[a++];
        }
        return merged;
    }

    static void calculate(int copy_d, int[] copyZ, int[] copyB, int[][] copyMX, int l, int r) {
        multMatrix(copyMX, ME, l, r);
        for (int i = l; i < r; i++) {
            int sum1=0;
            int sum2=0;
            for (int j = 0; j < N; j++) {
                sum1 += copyB[j]*MO[j][i];
                sum2 += copyZ[j]*MXME[j][i];
            }
            A[i] = sum1 + copy_d*sum2;
        }
    }

    private static void multMatrix(int[][] matrix1, int[][] matrix2, int l, int r) {
        for (int i = 0; i < N; i++) {
            for (int j = l; j < r; j++) {
                int sum = 0;
                for (int k = 0; k < N; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                MXME[i][j] = sum;
            }
        }
    }

    static void inputMatrix(int[][] matrix) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    static void inputVector(int[] vector) {
        for (int i = 0; i < N; i++) {
            vector[i] = i + 1;
        }
        shuffle(vector);
    }

    static void outputMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }

            System.out.println();
        }
    }

    static void outputVector(int[] vector) {
        System.out.println(Arrays.toString(vector));
    }

    static void shuffle(int[] array) {
        Random rand = new Random();
        int n = array.length;
        while (n > 1) {
            int k = rand.nextInt(n--);
            int temp = array[n];
            array[n] = array[k];
            array[k] = temp;
        }
    }
}
