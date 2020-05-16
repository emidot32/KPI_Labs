package OPP.rgr.pcs_sm;

import static OPP.rgr.pcs_sm.Main.*;
import java.util.Arrays;

// Монітор для доступу до спільних ресурсів
class ResourceMonitor {
    private int a = Integer.MAX_VALUE;
    private int[] Z = new int[N];
    private int[] B = new int[N];
    private int[][] MR = new int[N][N];


    synchronized void inputMR() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MR[i][j] = 1;
            }
        }
    }

    synchronized void inputZ() {
        for (int i = 0; i < N; i++) {
            Z[i] = 1;
        }
    }
    synchronized void inputB() {
        for (int i = 0; i < N; i++) {
            B[i] = 1;
        }
    }

    synchronized int[][] copyMR() {
        int[][] MX2 = new int[N][N];
        for (int i = 0; i < N; i++) {
            MX2[i] = Arrays.copyOf(MR[i], N);
        }
        return MX2;
    }

    synchronized int[] copyZ(){
        return Arrays.copyOf(Z, N);
    }

    synchronized int[] copyB(){
        return Arrays.copyOf(B, N);
    }

    synchronized void output_a(){
        System.out.println(a);
    }
    synchronized int get_a(){
        return a;
    }

    synchronized void min(int a, int a_i) {
        if (a_i < a) this.a = a_i;
    }
}
