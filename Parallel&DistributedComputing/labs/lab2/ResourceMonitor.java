package OPP.lab_2020.lab4;

import static OPP.lab_2020.lab4.Main.*;
import java.util.Arrays;

class ResourceMonitor {
    private int d;
    private int[] Z = new int[N];
    private int[] B = new int[N];
    private int[][] MX = new int[N][N];


    synchronized void inputMX() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MX[i][j] = 1;
            }
        }
    }
    synchronized void setB(int[] vector) {
        B = vector;
    }

    synchronized void inputZ(boolean rand) {
        if (rand) inputZ_rand();
        else inputZ_1();
    }
    private synchronized void inputZ_1() {
        for (int i = 0; i < N; i++) {
            Z[i] = 1;
        }
    }
    private synchronized void inputZ_rand() {
        for (int i = 0; i < N; i++) {
            Z[i] = i + 1;
        }
        shuffle(Z);
    }

    synchronized void input_d() {
        d = 1;
    }

    synchronized int[][] copyMX() {
        int[][] MX2 = new int[N][N];
        for (int i = 0; i < N; i++) {
            MX2[i] = Arrays.copyOf(MX[i], N);
        }
        return MX2;
    }


    synchronized int[] copyZ() {
        return Arrays.copyOf(Z, N);
    }
    synchronized int[] copyB() {
        return Arrays.copyOf(B, N);
    }

    synchronized int copy_d() {
        return d;
    }

    synchronized int[] getZ() {
        return Z;
    }
}
