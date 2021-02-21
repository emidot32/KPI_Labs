package edu.sp.cw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MatrixReader {
    private final int numberOfTasks;
    private List<Integer> buf = new ArrayList<>();
    private int count = 0;

    public MatrixReader(String file, int numberOfTasks) throws FileNotFoundException {
        this.numberOfTasks = numberOfTasks;
        Scanner input = new Scanner(new FileInputStream(file));
        while (input.hasNext()) {
            buf.add(input.nextInt());
        }
        input.close();
    }

    public int[][] getMatrixFromFile() {
        int[][] matrix = new int[numberOfTasks][numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            for (int j = 0; j < numberOfTasks; j++) {
                matrix[i][j] = buf.get(count++);
            }
        }
//        Arrays.stream(matrix)
//                .forEach(elem -> System.out.println(Arrays.toString(elem)));
        return matrix;
    }

    public int[] getWeightsFromFile() {
        int[] weights = new int[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            weights[i] = buf.get(count++);
        }
        return weights;
    }
}
