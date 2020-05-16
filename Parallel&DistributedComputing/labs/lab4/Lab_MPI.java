import mpi.*;

import java.util.Arrays;
import java.util.Random;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                             *
 *            Parallel and Distributed Computing               *
 *                  Laboratory work #4(7). Java                *
 *             MPI. Send/Recv messages                         *
 *                                                             *
 * Task: A = (B*MC)*(MO*MC) + min(Z)*R                         *
 *                                                             *
 * Komisarov Illia                                             *
 * group IO-71                                                 *
 *                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public class Lab_MPI {
    private final static int N = 3000;
    private final static int P = 6;
    private final static int H = N / P;
    private final static boolean rand_Z = false;


    public static void main(String[] args) throws Exception {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int H_numberForMC_R = 0, H_numberForZ = 0, H_numberForA=0;

        switch (rank) {
            case 0: {
                H_numberForMC_R = 2*H;
                H_numberForZ = H;
                H_numberForA = H;
                break;
            }
            case 1: {
                H_numberForMC_R = 3*H;
                H_numberForZ = H;
                H_numberForA = H;
                break;
            }
            case 2: {
                H_numberForMC_R = N;
                H_numberForZ = 2*H;
                H_numberForA = 2*H;
                break;
            }
            case 3: {
                H_numberForMC_R = 2*H;
                H_numberForZ = 3*H;
                H_numberForA = 3*H;
                break;
            }
            case 4: {
                H_numberForMC_R = H;
                H_numberForZ = N;
                H_numberForA = N;
                break;
            }
            case 5: {
                H_numberForMC_R = H;
                H_numberForZ = 2*H;
                H_numberForA = 2*H;
                break;
            }
        }

        int[] MC = new int[H_numberForMC_R * N];
        int[] MO = new int[N * N];
        int[] R = new int[H_numberForMC_R];
        int[] B = new int[N];
        int[] T = new int[N];
        int[] Z = new int[H_numberForZ];
        int[] A = new int[H_numberForA];
        int[] a = {N+1};
        int[] a_i = {N+1};

        switch (rank){
            case 0:
                inputMatrix(MO);
                inputVector(B);
                // Передати МО та В Т2 та Т6
                MPI.COMM_WORLD.Send(MO, 0, N*N, MPI.INT, 1, 0);
                MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, 1, 0);
                MPI.COMM_WORLD.Send(MO, 0, N*N, MPI.INT, 5, 0);
                MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, 5, 0);
                // Прийняти МС2Н та R2H від Т2
                MPI.COMM_WORLD.Recv(MC, 0, MC.length, MPI.INT, 1, 0);
                MPI.COMM_WORLD.Recv(R, 0, R.length, MPI.INT, 1, 0);
                // Передати МСН та RH задачі Т6
                MPI.COMM_WORLD.Send(MC, H * N, H * N, MPI.INT, 5, 0);
                MPI.COMM_WORLD.Send(R, H, H, MPI.INT, 5, 0);
                // Прийняти ZH від Т6
                MPI.COMM_WORLD.Recv(Z, 0, Z.length, MPI.INT, 5, 0);
                // Обчислення a = min(ZH)
                a_i = min(Z);
                // Прийняти а від Т2
                MPI.COMM_WORLD.Recv(a, 0, 1, MPI.INT, 1, 0);
                a = min(a, a_i);
                // Прийняти а6 від Т6
                int[] a6 = {N + 1};
                MPI.COMM_WORLD.Recv(a6, 0, 1, MPI.INT, 5, 0);
                a = min(a, a6);
                // Передати a задачі T2 та Т6
                MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 1, 0);
                MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 5, 0);
                // Обчислення TH = B*MCH
                calculateT(T, B, MC);
                // Прийняти TH від задачі Т6
                MPI.COMM_WORLD.Recv(T, H, H, MPI.INT, 5, 0);
                // Передати T2H задачі Т2
                MPI.COMM_WORLD.Send(T, 0, 2 * H, MPI.INT, 1, 0);
                // Прийняти Т від задачі Т2
                MPI.COMM_WORLD.Recv(T, 0, N, MPI.INT, 1, 0);
                // Передати Т задачі Т6
                MPI.COMM_WORLD.Send(T, 0, N, MPI.INT, 5, 0);
                // Обчислення AH = T*(MO*MCH) + a*RH
                calculateA(A, MO, MC, T, R, a);
                // Передати AH задачі Т6
                MPI.COMM_WORLD.Send(A, 0, H, MPI.INT, 5, 0);
                break;
            case 1:
                // Прийняти МО, В від задачі Т1
                MPI.COMM_WORLD.Recv(MO, 0, MO.length, MPI.INT, 0, 0);
                MPI.COMM_WORLD.Recv(B, 0, B.length, MPI.INT, 0, 0);
                // Передати МО, В задачі Т3
                MPI.COMM_WORLD.Send(MO, 0, N*N, MPI.INT, 2, 0);
                MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, 2, 0);
                // Прийняти МC3H, R3H від задачі Т3
                MPI.COMM_WORLD.Recv(MC, 0, MC.length, MPI.INT, 2, 0);
                MPI.COMM_WORLD.Recv(R, 0, R.length, MPI.INT, 2, 0);
                // Передати МС2Н та R2H задачі Т1
                MPI.COMM_WORLD.Send(MC, H * N, 2*H * N, MPI.INT, 0, 0);
                MPI.COMM_WORLD.Send(R, H, 2*H, MPI.INT, 0, 0);
                // Прийняти ZH від Т3
                MPI.COMM_WORLD.Recv(Z, 0, Z.length, MPI.INT, 2, 0);
                // Обчислення a = min(ZH)
                a_i = min(Z);
                // Прийняти а від Т3
                MPI.COMM_WORLD.Recv(a, 0, 1, MPI.INT, 2, 0);
                a = min(a, a_i);
                // Передати а Т1
                MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 0, 0);
                // Прийняти а від Т1
                MPI.COMM_WORLD.Recv(a, 0, 1, MPI.INT, 0, 0);
                // Передати а Т3
                MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 2, 0);
                // Обчислення TH = B*MCH
                calculateT(T, B, MC);
                // Прийняти T2H від задачі Т1
                MPI.COMM_WORLD.Recv(T, H, 2*H, MPI.INT, 0, 0);
                // Прийняти T3H задачі Т3
                MPI.COMM_WORLD.Recv(T, 3*H, 3*H, MPI.INT, 2, 0);
                //Передати Т задачі Т1
                MPI.COMM_WORLD.Send(T, 0, N, MPI.INT, 0, 0);
                // Передатb Т задачі Т3
                MPI.COMM_WORLD.Send(T, 0, N, MPI.INT, 2, 0);
                
                // Обчислення AH = T*(MO*MCH) + a*RH
                calculateA(A, MO, MC, T, R, a);
                // Передати AH задачі Т3
                MPI.COMM_WORLD.Send(A, 0, H, MPI.INT, 2, 0);
                break;
            case 2:
                inputMatrix(MC);
                inputVector(R);
                // Прийняти МО, В від задачі Т2
                MPI.COMM_WORLD.Recv(MO, 0, MO.length, MPI.INT, 1, 0);
                MPI.COMM_WORLD.Recv(B, 0, B.length, MPI.INT, 1, 0);
                // Передати МО, В задачі Т4
                MPI.COMM_WORLD.Send(MO, 0, N*N, MPI.INT, 3, 0);
                MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, 3, 0);
                // Передати МС3H, R3H задачі Т2
                MPI.COMM_WORLD.Send(MC, H * N, 3 * H * N, MPI.INT, 1, 0);
                MPI.COMM_WORLD.Send(R, H, 3 * H, MPI.INT, 1, 0);
                // Передати МС2H, R2H задачі Т4
                MPI.COMM_WORLD.Send(MC, H * N, 2 * H * N, MPI.INT, 3, 0);
                MPI.COMM_WORLD.Send(R, H, 2 * H, MPI.INT, 3, 0);
                // Прийняти Z2H від Т4
                MPI.COMM_WORLD.Recv(Z, 0, Z.length, MPI.INT, 3, 0);
                // Передати ZH задачі Т2
                MPI.COMM_WORLD.Send(Z, H, H, MPI.INT, 1, 0);
                // Обчислення a = min(ZH)
                a_i = min(Z);
                // Прийняти а від Т4
                MPI.COMM_WORLD.Recv(a, 0, 1, MPI.INT, 3, 0);
                a = min(a, a_i);
                // Передати а Т2
                MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 1, 0);
                // Прийняти а від Т2
                MPI.COMM_WORLD.Recv(a, 0, 1, MPI.INT, 1, 0);
                // Передати а задачі Т4
                MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 3, 0);
                // Обчислення TH = B*MCH
                calculateT(T, B, MC);
                // Прийняти T2H від задачі Т4
                MPI.COMM_WORLD.Recv(T, H, 2*H, MPI.INT, 3, 0);
                // Передати T3H задачі Т2
                MPI.COMM_WORLD.Send(T, 0, 3*H, MPI.INT, 1, 0);
                //Прийняти Т від задачі Т2
                MPI.COMM_WORLD.Recv(T, 0, N, MPI.INT, 1, 0);
                // Передатb Т задачі Т4
                MPI.COMM_WORLD.Send(T, 0, N, MPI.INT, 3, 0);
                // Обчислення AH = T*(MO*MCH) + a*RH
                calculateA(A, MO, MC, T, R, a);
                // Прийняти АH від задачі Т2
                MPI.COMM_WORLD.Recv(A, H, H, MPI.INT, 1, 0);
                // Передати A2H задачі Т4
                MPI.COMM_WORLD.Send(A, 0, 2*H, MPI.INT, 3, 0);
                break;
            case 3:
                // Прийняти МО, В від задачі Т3
                MPI.COMM_WORLD.Recv(MO, 0, MO.length, MPI.INT, 2, 0);
                MPI.COMM_WORLD.Recv(B, 0, B.length, MPI.INT, 2, 0);
                // Прийняти МC2H, R2H від задачі Т3
                MPI.COMM_WORLD.Recv(MC, 0, MC.length, MPI.INT, 2, 0);
                MPI.COMM_WORLD.Recv(R, 0, R.length, MPI.INT, 2, 0);
                // Передати МСH, RH задачі Т5
                MPI.COMM_WORLD.Send(MC, H * N, H * N, MPI.INT, 4, 0);
                MPI.COMM_WORLD.Send(R, H, H, MPI.INT, 4, 0);
                // Прийняти Z3H від Т5
                MPI.COMM_WORLD.Recv(Z, 0, Z.length, MPI.INT, 4, 0);
                // Передати Z2H задачі Т3
                MPI.COMM_WORLD.Send(Z, H, 2*H, MPI.INT, 2, 0);
                // Обчислення a = min(ZH)
                a_i = min(Z);
                // Прийняти а5 від Т5
                int[] a5 = {N +1};
                MPI.COMM_WORLD.Recv(a5, 0, 1, MPI.INT, 4, 0);
                a = min(a5, a_i);
                // Передати а Т3
                MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 2, 0);
                // Прийняти а від Т3
                MPI.COMM_WORLD.Recv(a, 0, 1, MPI.INT, 2, 0);
                // Передати а Т5
                MPI.COMM_WORLD.Send(a, 0, 1, MPI.INT, 4, 0);
                // Обчислення TH = B*MCH
                calculateT(T, B, MC);
                // Прийняти TH від задачі Т5
                MPI.COMM_WORLD.Recv(T, H, H, MPI.INT, 4, 0);
                // Передати T2H задачі Т3
                MPI.COMM_WORLD.Send(T, 0, 2*H, MPI.INT, 2, 0);
                //Прийняти Т від задачі Т3
                MPI.COMM_WORLD.Recv(T, 0, N, MPI.INT, 2, 0);
                // Передатb Т задачі Т5
                MPI.COMM_WORLD.Send(T, 0, N, MPI.INT, 4, 0);
                // Обчислення AH = T*(MO*MCH) + a*RH
                calculateA(A, MO, MC, T, R, a);
                // Прийняти А2H від задачі Т3
                MPI.COMM_WORLD.Recv(A, H, 2*H, MPI.INT, 2, 0);
                // Передати A3H задачі Т5
                MPI.COMM_WORLD.Send(A, 0, 3*H, MPI.INT, 4, 0);
                break;
            case 4:
                inputVector(Z);
                // Прийняти МО, В від задачі Т6
                MPI.COMM_WORLD.Recv(MO, 0, MO.length, MPI.INT, 5, 0);
                MPI.COMM_WORLD.Recv(B, 0, B.length, MPI.INT, 5, 0);
                // Прийняти МCH, RH від задачі Т4
                MPI.COMM_WORLD.Recv(MC, 0, MC.length, MPI.INT, 3, 0);
                MPI.COMM_WORLD.Recv(R, 0, R.length, MPI.INT, 3, 0);
                // Передати Z3H задачі Т4
                MPI.COMM_WORLD.Send(Z, H, 3 * H, MPI.INT, 3, 0);
                // Передати Z2H задачі Т6
                MPI.COMM_WORLD.Send(Z, H, 2 * H, MPI.INT, 5, 0);
                // Обчислення a = min(ZH)
                a_i = min(Z);
                // Передати а_i задачі Т4
                MPI.COMM_WORLD.Send(a_i, 0, 1, MPI.INT, 3, 0);
                // Прийняти а від Т4
                MPI.COMM_WORLD.Recv(a, 0, 1, MPI.INT, 3, 0);
                // Обчислення TH = B*MCH
                calculateT(T, B, MC);
                // Передати TH задачі Т4
                MPI.COMM_WORLD.Send(T, 0, H, MPI.INT, 3, 0);
                //Прийняти Т від задачі Т4
                MPI.COMM_WORLD.Recv(T, 0, N, MPI.INT, 3, 0);
                // Обчислення AH = T*(MO*MCH) + a*RH
                calculateA(A, MO, MC, T, R, a);
                // Прийняти А3H від задачі Т4
                MPI.COMM_WORLD.Recv(A, H, 3*H, MPI.INT, 3, 0);
                // Прийняти А2H від задачі Т6
                MPI.COMM_WORLD.Recv(A, 4*H, 2*H, MPI.INT, 5, 0);
                outputVector(A);
                break;
            case 5:
                // Прийняти МО, В від задачі Т1
                MPI.COMM_WORLD.Recv(MO, 0, MO.length, MPI.INT, 0, 0);
                MPI.COMM_WORLD.Recv(B, 0, B.length, MPI.INT, 0, 0);
                // Передати МО, В задачі Т5
                MPI.COMM_WORLD.Send(MO, 0, N*N, MPI.INT, 4, 0);
                MPI.COMM_WORLD.Send(B, 0, N, MPI.INT, 4, 0);
                // Прийняти МCH, RH від задачі Т1
                MPI.COMM_WORLD.Recv(MC, 0, MC.length, MPI.INT, 0, 0);
                MPI.COMM_WORLD.Recv(R, 0, R.length, MPI.INT, 0, 0);
                // Прийняти Z2H від Т5
                MPI.COMM_WORLD.Recv(Z, 0, Z.length, MPI.INT, 4, 0);
                // Передати ZH задачі Т1
                MPI.COMM_WORLD.Send(Z, H, H, MPI.INT, 0, 0);
                a_i = min(Z);
                // Передати а_i задачі Т1
                MPI.COMM_WORLD.Send(a_i, 0, 1, MPI.INT, 0, 0);
                // Прийняти а від Т1
                MPI.COMM_WORLD.Recv(a, 0, 1, MPI.INT, 0, 0);
                // Обчислення TH = B*MCH
                calculateT(T, B, MC);
                // Передати TH задачі Т1
                MPI.COMM_WORLD.Send(T, 0, H, MPI.INT, 0, 0);
                //Прийняти Т від задачі Т1
                MPI.COMM_WORLD.Recv(T, 0, N, MPI.INT, 0, 0);
                // Обчислення AH = T*(MO*MCH) + a*RH
                calculateA(A, MO, MC, T, R, a);
                // Прийняти АH від задачі Т3
                MPI.COMM_WORLD.Recv(A, H, H, MPI.INT, 0, 0);
                // Передати A3H задачі Т5
                MPI.COMM_WORLD.Send(A, 0, 2*H, MPI.INT, 4, 0);
                break;
        }

        System.out.println("Task " + (rank + 1) + " is finished");
        MPI.Finalize();
    }

    static void calculateT(int[] result_T, int[] B, int[] MC_H){
        for (int i = 0; i < H; i++) {
            int sum = 0;
            for (int j = 0; j < N; j++) {
                sum = sum + B[j] * MC_H[i * H + j];
            }
            result_T[i] += sum;
        }
    }

    static void calculateA(int[] result_A, int[] MO, int[] MC, int[] T, int[] R, int[] a){
        for (int i = 0; i < H; i++) {
            int sum1 = 0;
            for (int j = 0; j < N; j++) {
                int sum2 = 0;
                for (int z = 0; z < N; z++) {
                    sum2 += MC[i * N + z] * MO[z * N + j];
                }
                sum1 += sum2 * T[i] ;
            }
            result_A[i] = sum1 + R[i]*a[0];
        }
    }

    static int[] min(int[] vector) {
        int min = vector[0];
        for (int i = 1; i < H; i++) {
            if (vector[i] < min) {
                min = vector[i];
            }
        }
        return new int[]{min};
    }

    static int[] min(int[] a, int[] a_i) {
        return a[0] < a_i[0] ? a : a_i;
    }

    static void inputMatrix(int[] matrix) {
        for (int i = 0; i < N*N; i++) {
            matrix[i] = 1;
        }
    }
    static void inputVector(int[] vector) {
        if (rand_Z)
            inputVectorRandom(vector);
        else
            inputVectorBy1(vector);
    }

    static void inputVectorRandom(int[] vector) {
        for (int i = 0; i < N; i++) {
            vector[i] = i + 1;
        }
        shuffle(vector);
    }

    static void inputVectorBy1(int[] vector){
        for (int i = 0; i < vector.length; i++) {
            vector[i] = 1;
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
