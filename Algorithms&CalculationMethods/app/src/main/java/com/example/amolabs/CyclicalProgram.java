package com.example.amolabs;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class CyclicalProgram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclical_program);
    }

    public void calculate(View v) {
        EditText edt1 = (EditText) findViewById(R.id.editTextCycl1);
        EditText edt2 = (EditText) findViewById(R.id.editTextCycl2);
        TextView result = (TextView) findViewById(R.id.CyclicalResult);

        try {
            double A[] = changeType(edt1.getText().toString().split(","));
            double B[] = changeType(edt2.getText().toString().split(","));
            int n = A.length;
            double f;
            if (A.length == B.length) {
                double f1 = 1.0;
                double f2 = 0.0;
                int i = 0;
                while (i < n - 1) {
                    f1 *= (A[i] + B[i + 1]);
                    f2 += (A[i + 1] * B[i]);
                    f = f1 + f2;
                    if (i == 0) {
                        result.setText(String.format(Locale.getDefault(), "%.4f\n", f));
                    } else {
                        result.append(String.format(Locale.getDefault(), "%.4f\n", f));
                    }
                    i++;
                }
            } else {
                Toast.makeText(CyclicalProgram.this, "Кількість чисел А[] і B[] не співпадають!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(CyclicalProgram.this, "Ви ввели некоректні дані!", Toast.LENGTH_LONG).show();
        }
    }

    double[] changeType(String[] strArray) {
        double[] intArray = new double[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Double.parseDouble(strArray[i].trim());
        }
        return intArray;
    }

    public void saveText(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        EditText editText = (EditText) findViewById(R.id.editTextCycl1);
        TextView textBox = (TextView) findViewById(R.id.CyclicalResult);
        String text = textBox.getText().toString();
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/my_files/Lab1/savedData.txt");
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(text.getBytes());
            out.close();
            Toast.makeText(this, "Файл збережений!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Щось пішло не так("+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void readFile1(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        EditText editText = (EditText) findViewById(R.id.editTextCycl1);
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/my_files/Lab1/inputData1.txt");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                editText.setText(stringBuilder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Щось пішло не так("+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void readFile2(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        EditText editText = (EditText) findViewById(R.id.editTextCycl2);
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/my_files/Lab1/inputData2.txt");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                editText.setText(stringBuilder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Щось пішло не так("+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

