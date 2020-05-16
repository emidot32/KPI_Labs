import mpi.*;
import java.util.Arrays;


/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                                                             *
 *            Parallel and distributed computing               *
 *       RGR. Parallel computer system with local memory       *
 *                       Java, MPI                             *
 *                                                             *
 * Task: a = min(B*MZ + Z*(MR*MX))                             *
 *                                                             *
 * @author: Illia Komisarov                                    *
 *                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public class RGR_MPI {
    static final int N = 3000;
    static final int P = 4;
    static final int H = N/P;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        MPI.Init(args);
        // Створюємо граф відповідно до структури ПКС за варіантом
        int[] indexes = new int[] { 1, 4, 5, 6 };
        int[] edges = new int[] { 1, 0,2,3, 1, 1 };
        Graphcomm graph = MPI.COMM_WORLD.Create_graph(indexes, edges, false);
        // Буфери передачі та отримання повідомлень
        int[][] sendBufferMatrix = new int[P*N][N];
        int[][] recvBufferMatrix = new int[N][N];
        int[] sendBufferVector = new int[2*P*N];
        int[] recvBufferVector = new int[2*N];

        int[][] MZ_send = new int[N][N];
        int[][] MR_send = new int[N][N];
        int[][] MX_send = new int[N][N];

        int[][] MZ_recv = new int[H][N];
        int[][] MX_recv = new int[H][N];
        int[][] MR_recv = new int[N][N];

        int[] B_send = new int[N];
        int[] Z_send = new int[N];

        int[] B_recv = new int[N];
        int[] Z_recv = new int[N];

        int[] a = new int[]{Integer.MAX_VALUE};

        // Введення, передача та отримання MX
        sendAndRecvMatrix(0, graph, MX_send, MX_recv, sendBufferMatrix, recvBufferMatrix, H);

        // Введення, передача та отримання B, Z
        if (graph.Rank() == 1) {
            // Введення векторів B, Z
            inputVectors(B_send, Z_send);
            int i = 0;
            // Запис векторів в буфер для передачі
            for (int node = 0; node < graph.Size(); node++) {
                for (int i2=0; i2 < N; i2++, i++) {
                    sendBufferVector[i] = B_send[i2];
                }
                for (int i2=0; i2 < N; i2++, i++) {
                    sendBufferVector[i] = Z_send[i2];
                }
            }
        }
        // Передача векторів усім процесам
        graph.Scatter(sendBufferVector, 0, 2*N, MPI.INT, recvBufferVector, 0,
                2*N, MPI.INT, 1);
        // Запис векторів з буфера для подальшої роботи
        int l = 0;
        for (; l < N; l++) {
            B_recv[l] = recvBufferVector[l];
        }
        for (int i2 = 0; i2 < N; i2++, l++) {
            Z_recv[i2] = recvBufferVector[l];
        }

        // Введення, передача та отримання MZ
        sendAndRecvMatrix(2, graph, MZ_send, MZ_recv, sendBufferMatrix, recvBufferMatrix, H);
        // Введення, передача та отримання MR
        sendAndRecvMatrix(3, graph, MR_send, MR_recv, sendBufferMatrix, recvBufferMatrix, N);
        // Обчислення min(B*MZ + Z*(MR*MX)) для H частини
        int[] a_i = new int[]{min(calculate(Z_recv, B_recv, MR_recv, MX_recv, MZ_recv))};
        // Збираємо а_і з усіх процесів, знаходимо серед них мінімальне та передаємо в перший процес для виведення
        graph.Reduce(a_i, 0, a, 0, 1, MPI.INT, MPI.MIN, 0);
        // Вивід а
        if (graph.Rank() == 0) System.out.println("a = "+a[0]);

        MPI.Finalize();
        System.out.format("Executing time: %d\n", System.currentTimeMillis() - start);
    }

    static void sendAndRecvMatrix(int rank, Graphcomm graph, int[][] sendMatrix, int[][] recvMatrix, int[][] sendBuf,
                                  int[][] recvBuf, int size){
        if (graph.Rank() == rank) {
            // Ведення матриці
            inputMatrix(sendMatrix);
            int i = 0;
            // Запис матриці в буфер для передачі
            for (int node = 0; node < graph.Size(); node++) {
                for (int k = 0; k < N; i++, k++) {
                    for (int j = 0; j < N; j++) {
                        sendBuf[i][j] = sendMatrix[k][j];
                    }
                }
            }
        }
        // Передача матриці (або H частини) усім процесам
        graph.Scatter(sendBuf, 0, size, MPI.OBJECT, recvBuf, 0,
                size, MPI.OBJECT, rank);
        // Запис матриці з буфера для подальшої роботи
        for (int i = 0; i < recvMatrix.length; i++) {
            for (int j = 0; j < N; j++) {
                recvMatrix[i][j] = recvBuf[i][j];
            }
        }
    }

    static int[] calculate(int[] Z, int[] B, int[][] MR, int[][] MX, int[][] MZ) {
        // Обчислення B*MZ + Z*(MR*MX)
        int[] result = new int[H];
        for (int i = 0; i < H; i++) {
            int sum1 = 0;
            int sum2 = 0;
            for (int j = 0; j < N; j++) {
                int sum3 = 0;
                for (int z = 0; z < N; z++) {
                    sum3 += MX[i][z] * MR[z][j];
                }
                sum1 += sum3 * Z[j];
                sum2 += B[j]*MZ[i][j];
            }
            result[i] = sum1 + sum2;
        }
        return result;
    }

    static int min(int[] vector) {
        // Обчислення min для частини вектора
        int min = vector[0];
        for (int i = 1; i < H; i++) {
            if (vector[i] < min) {
                min = vector[i];
            }
        }
        return min;
    }

    static int[] createArr(){
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = i+1;
        }
        return arr;
    }

    static int[] partOfArr(int[] arr, int l, int r){
        int[] part = new int[H];
        int o = 0;
        for (int i = l; i < r; i++, o++) {
            part[o] = arr[i];
        }
        return part;
    }

    static void inputMatrix(int[][] matrix) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    static void inputVectors(int[] vector1, int[] vector2) {
        for (int i = 0; i < N; i++) {
            vector1[i] = 1;
            vector2[i] = 1;
        }
    }
    static void outputMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
    static void outputVector(int[] vector) {
        System.out.println(Arrays.toString(vector));
    }

}
