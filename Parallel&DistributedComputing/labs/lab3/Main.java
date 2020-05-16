import java.util.Arrays;
import java.util.Random;

class Main {
    static final int N = 1000;
    static final int P = 6;
    static final int H = N / P;
    static int d;
    static int[] A = new int[N];
    static int[] Z = new int[N];
    static int[][] ME = new int[N][N];
    static int[][] MX = new int[N][N];
    static int[][] MO = new int[N][N];
    static int[] B, B1, B2, B3, B4, B5, B6;
    static int[] B12, B34, B56, B1234;
    static int[][] MXME = new int[N][N];
    static boolean randZ = true;

    public static void main(String[] args) {

        //pragma omp parallel
        {
            //pragma omp sections
            {
                //pragma omp section
                {
                    inputVector(Z, randZ);
                    inputMatrix(ME);
                }

                //pragma omp section
                {
                    inputMatrix(MO);
                }

                //pragma omp section
                {
                    d = 1;
                    inputMatrix(MX);
                }
            }

            //pragma omp barrier

            //pragma omp sections
            {

                //pragma omp section
                {
                    B1 = sort(Z, 0, H);
                }

                //pragma omp section
                {
                    B2 = sort(Z, H, 2 * H);
                }

                //pragma omp section
                {
                    B3 = sort(Z, 2 * H, 3 * H);
                }

                //pragma omp section
                {
                    B4 = sort(Z, 3 * H, 4 * H);
                }

                //pragma omp section
                {
                    B5 = sort(Z, 4 * H, 5 * H);
                }

                //pragma omp section
                {
                    B6 = sort(Z, 5 * H, N);
                }
            }

            //pragma omp barrier

            //pragma omp sections
            {

                //pragma omp section
                {
                    B12 = mergeSort(B1, B2);
                }

                //pragma omp section
                {
                    B34 = mergeSort(B3, B4);
                }

                //pragma omp section
                {
                    B56 = mergeSort(B5, B6);
                }
            }

            //pragma omp barrier

            //pragma omp sections
            {

                //pragma omp section
                {
                    B1234 = mergeSort(B12, B34);
                }
            }

            //pragma omp barrier

            //pragma omp sections
            {

                //pragma omp section
                {
                    B = mergeSort(B1234, B56);
                }
            }

            //pragma omp barrier
            int[] copyB;
            int[] copyZ;
            int copy_d;
            int[][] copyMX;
            //pragma omp critical(Copy)
            {
                copy_d = d;
                copyB = copyVector(B);

            }
            //pragma omp atomic
            {
                copyZ = copyVector(Z);
                copyMX = copyMatrix(MX);
            }
            //pragma omp sections
            {

                //pragma omp section
                {
                    calculate(copy_d, copyZ, copyB, copyMX, 0, H);
                }

                //pragma omp section
                {
                    calculate(copy_d, copyZ, copyB, copyMX, H, 2*H);
                }

                //pragma omp section
                {
                    calculate(copy_d, copyZ, copyB, copyMX, 2*H, 3*H);
                }

                //pragma omp section
                {
                    calculate(copy_d, copyZ, copyB, copyMX, 3*H, 4*H);
                }

                //pragma omp section
                {
                    calculate(copy_d, copyZ, copyB, copyMX, 4*H, 5*H);
                }

                //pragma omp section
                {
                    calculate(copy_d, copyZ, copyB, copyMX, 5, N);
                }
            }
            //pragma omp barrier

            //pragma omp sections
            {
                //pragma omp section
                {
                    outputVector(A);
                }
            }
        }
    }


    static int[] sort(int[] vector, int l, int r) {
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
            int sum1 = 0;
            int sum2 = 0;
            for (int j = 0; j < N; j++) {
                sum1 += copyB[j] * MO[j][i];
                sum2 += copyZ[j] * MXME[j][i];
            }
            A[i] = sum1 + copy_d * sum2;
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
    static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            copy[i] = Arrays.copyOf(matrix[i], N);
        }
        return copy;
    }
    static int[] copyVector(int[] vector) {
        return Arrays.copyOf(vector, N);
    }

    static void inputMatrix(int[][] matrix) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = 1;
            }
        }
    }

    static void inputVector(int[] vector, boolean rand) {
        if (rand) inputVectorRand(vector);
        else inputVector1(vector);

    }

    static void inputVectorRand(int[] vector) {
        for (int i = 0; i < N; i++) {
            vector[i] = i + 1;
        }
        shuffle(vector);
    }
    static void inputVector1(int[] vector) {
        for (int i = 0; i < N; i++) {
            vector[i] = 1;
        }
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
