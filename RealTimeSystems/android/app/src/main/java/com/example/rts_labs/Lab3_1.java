package com.example.rts_labs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Lab3_1 extends AppCompatActivity {
    EditText numET;
    TextView num1TextView, num2TextView, stepByStepView, hintView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_1);
        setTitle("Lab 3.1");
        numET = findViewById(R.id.notPrimeNum);
        num1TextView = findViewById(R.id.primeNum1);
        num2TextView = findViewById(R.id.primeNum2);
        stepByStepView = findViewById(R.id.stepByStepView);
        hintView = findViewById(R.id.hint);

        hintView.setText("    k - iterator variable \n    s = sqrt(num)\n    " +
                "x = s + k\n    y = sqrt(x^2-num)\n    " +
                "a = x+y (num1)\n    b = x-y (num2)");
        stepByStepView.setText(String.format("  %2s%8s%13s%15s%16s\n", "k", "x", "y", "a", "b"));
    }

    public void calculate(View view) {
        num1TextView.setText("    Number 1: ");
        num2TextView.setText("    Number 2: ");
        stepByStepView.setText(String.format("  %2s%9s%14s%15s%15s\n", "k", "x", "y", "a", "b"));
        int num;
        int[] arr;
        try {
            num = Integer.parseInt(numET.getText().toString());
            if (prime(num)) Toast.makeText(this, "Number is prime!", Toast.LENGTH_LONG).show();
            else if (num % 2 == 0) Toast.makeText(this, "Number is even!", Toast.LENGTH_LONG).show();
            else {
                arr = factorizationByFermaAlg(num);
                num1TextView.append(" " + arr[0]);
                num2TextView.append(" " + arr[1]);
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Not a number!", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("DefaultLocale")
    private int[] factorizationByFermaAlg(int num) {
        int[] result = new int[2];
        int k = 0;
        int x = (int) Math.ceil((Math.sqrt(num) + k));
        double y = Math.sqrt(Math.pow(x, 2) - num);
        while (y % 1 != 0) {
            x = (int) (Math.sqrt(num) + ++k);
            y = Math.sqrt(Math.pow(x, 2) - num);
            double a = x + y;
            double b = x - y;
            stepByStepView.append(String.format("  %2d%7d%12.2f%12.2f%12.2f\n", k, x, y, a, b));
        }
        result[0] = (int) (x + y);
        result[1] = (int) (x - y);

        return result;
    }


    private boolean prime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }


}
