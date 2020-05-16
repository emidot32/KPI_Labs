package OPP.rgr.pcs_sm;

import java.util.Arrays;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                             *
 *            Паралельні та розподілені обчислення             *
 *   РГР. Паралельна комп'ютерна система зі спільною пам'яттю  *
 *                         Java                                *
 *                       Варіант 9                             *
 *                                                             *
 * Задача: a = min(B*MZ + Z*(MR*MX))                           *
 *                                                             *
 * Комісаров Ілля                                              *
 * Група IO-71                                                 *
 *                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public class Main {
    static final int N = 1000;
    static final int P = 4;
    static final int H = N / P;
    static int[][] MX = new int[N][N];
    static int[][] MZ = new int[N][N];
    static long start;

    public static void main(String[] args) {
        // Створюємо монітори та всі потоки та запускаємо
        ResourceMonitor resourceMonitor = new ResourceMonitor();
        SynchMonitor synchMonitor = new SynchMonitor();
        Thread t1 = new T1(resourceMonitor, synchMonitor);
        Thread t2 = new T2(resourceMonitor, synchMonitor);
        Thread t3 = new T3(resourceMonitor, synchMonitor);
        Thread t4 = new T4(resourceMonitor, synchMonitor);

        start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    static int[] calculate(int[] copyZ, int[] copyB, int[][] copyMR, int l, int r) {
        // Обчислення B*MZ + Z*(MR*MX)
        int[] result = new int[N];
        for (int i = l; i < r; i++) {
            int sum1 = 0;
            int sum2 = 0;
            for (int j = 0; j < N; j++) {
                int sum3 = 0;
                for (int z = 0; z < N; z++) {
                    sum3 += MX[i][z] * copyMR[z][j];
                }
                sum1 += sum3 * copyZ[j];
                sum2 += copyB[j]*MZ[i][j];
            }
            result[i] = sum1 + sum2;
        }
        return result;
    }


    static int min(int[] vector, int l, int r) {
        // Обчислення min для частини вектора
        int min = vector[l];
        for (int i = l+1; i < r; i++) {
            if (vector[i] < min) {
                min = vector[i];
            }
        }
        return min;
    }


    static void inputMatrix(int[][] matrix) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    static void outputVector(int[] vector) {
        System.out.println(Arrays.toString(vector));
    }

}
