package com.example.rts_labs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class Lab3_2 extends AppCompatActivity {
    final double P = 4.0;
    final int[][] points = {{0, 6},
            {1, 5},
            {3, 3},
            {2, 4}};

    double speedOfLearning;
    long timeDeadline;
    int iterationDeadLine;
    double w1, w2;
    TextView executingTimeView, weightsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_2);
        setTitle("Lab 3.2");

        executingTimeView = findViewById(R.id.executionTime3_2);
        weightsView = findViewById(R.id.weights);
        weightsView.setText(String.format("    %-12s%-12s", "w1=", "w2="));
    }


    public void calculate(View view) {
        weightsView.setText(String.format("    %-12s%-12s", "w1=", "w2="));
        executingTimeView.setText("    Executing time: ");
        inputData();
        double y;
        double delta;
        int iter = 0;
        boolean flag = false;
        long start = System.nanoTime();

        int index = -1;
        while (iter++ < iterationDeadLine
                && (System.nanoTime() - start) < timeDeadline) {
            index = (index == 3) ? -1 : index;
            index++;

            y = points[index][0] * w1 + points[index][1] * w2;

            if (isFit()) {
                flag = true;
                break;
            }

            delta = P - y;
            w1 += delta * points[index][0] * speedOfLearning;
            w2 += delta * points[index][1] * speedOfLearning;
        }

        if (flag) {
            weightsView.setText(String.format("    w1=%-9.3f w2=%-9.3f", w1, w2));
            long executingTime = System.nanoTime()-start;
            executingTimeView.append(executingTime/1000 + " mcs");
        } else {
            weightsView.setText("   Weights were not calculated until the deadline");
            executingTimeView.append("None");
        }
    }

    private boolean isFit() {
        return P < points[0][0] * w1 + points[0][1] * w2
                && P < points[1][0] * w1 + points[1][1] * w2
                && P > points[2][0] * w1 + points[2][1] * w2
                && P > points[3][0] * w1 + points[3][1] * w2;
    }

    private void inputData() {
        speedOfLearning = Double.parseDouble(((Spinner) findViewById(R.id.spinnerSpeed)).getSelectedItem().toString());
        timeDeadline = (long)(Double.parseDouble(((Spinner) findViewById(R.id.spinnerTimeDeadline)).getSelectedItem().toString()) * 1000000000);
        iterationDeadLine = Integer.parseInt(((Spinner) findViewById(R.id.spinnerIterationDeadline)).getSelectedItem().toString());
    }
}
