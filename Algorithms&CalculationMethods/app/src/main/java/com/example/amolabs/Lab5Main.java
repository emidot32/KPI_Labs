package com.example.amolabs;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Lab5Main extends AppCompatActivity {
    int rows;
    int columns;
    TableLayout tableLayout;
    TextView answers;
    ImageButton readButton;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab5_main);
        rows = 3;
        columns = 3;
        answers = (TextView) findViewById(R.id.resultLab5);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        readButton = (ImageButton) findViewById(R.id.readButton);
        createTable(rows, columns);
        ActivityCompat.requestPermissions(Lab5Main.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(Lab5Main.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(Lab5Main.this)
                        .withRequestCode(1)
                        .withFilter(Pattern.compile(".*\\..*")) // Filtering files and directories by file name using regexp
                        .start();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void calculate(View v) {
        double[][] xMatrix = new double[rows][columns];
        double[] yArray = new double[rows];
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 2 * columns; j++) {
                if (j % 2 == 0) {
                    String elem = ((EditText) ((TableRow) tableLayout.
                            getChildAt(i)).getChildAt(j)).getText().toString();
                    if (elem.length() == 0) {
                        xMatrix[i][j / 2] = 0;
                    } else {
                        try {
                            xMatrix[i][j / 2] = Double.parseDouble(elem);
                        } catch (java.lang.NumberFormatException e) {
                            Toast.makeText(this, "Ви ввели некоректні дані!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < rows; i++) {
            String elem = ((EditText) ((TableRow) tableLayout.getChildAt(i))
                    .getChildAt(2 * columns)).getText().toString();
            if (elem.length() == 0) {
                yArray[i] = 0;
            } else {
                try {
                    yArray[i] = Double.parseDouble(elem);
                } catch (java.lang.NumberFormatException e) {
                    Toast.makeText(this, "Ви ввели некоректні дані!", Toast.LENGTH_LONG).show();
                }
            }
        }
        RealMatrix coefficients = new Array2DRowRealMatrix(xMatrix, false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(yArray, false);
        try {
            RealVector solution = solver.solve(constants);
            answers.setText("");
            for (int i = 0; i < rows; i++) {
                answers.append(String.format("x%d = %.5f\n", i + 1, solution.getEntry(i)));
            }
        } catch (org.apache.commons.math3.linear.SingularMatrixException e) {
            Toast.makeText(this, "Неможливо розрахувати корені.\n" +
                    "Скоріше за все матриця не сумісна.", Toast.LENGTH_LONG).show();
        }
    }

    public void minusX(View v) {
        if (rows == 2) {
            Toast.makeText(this, "Матриця не може бути менше ніж 2х2!", Toast.LENGTH_SHORT).show();
        } else {
            tableLayout = (TableLayout) findViewById(R.id.tableLayout);
            tableLayout.removeAllViews();
            rows--;
            columns--;
            createTable(rows, columns);
        }

    }

    public void plusX(View v) {
        if (rows == 6) {
            Toast.makeText(this, "Ви будете дуже довго вводити значення...", Toast.LENGTH_SHORT).show();
        } else {
            tableLayout = (TableLayout) findViewById(R.id.tableLayout);
            tableLayout.removeAllViews();
            rows++;
            columns++;
            createTable(rows, columns);
        }
    }

    @SuppressLint("DefaultLocale")
    private void createTable(int rows_, int columns_) {
        for (int i = 0; i < rows_; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for (int j = 0; j <= 2 * columns_; j++) {
                if (j % 2 == 0) {
                    EditText elem = new EditText(this);
                    elem.setWidth(130);
                    elem.setTextSize(17);
                    elem.setHeight(45);
                    elem.setHint("0");
                    elem.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    elem.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    tableRow.addView(elem, j);
                } else {
                    TextView textView = new TextView(this);
                    textView.setTextSize(18);
                    if (j == 2 * columns_ - 1) {
                        textView.setText(String.format("x%d = ", +j / 2 + 1));
                    } else {
                        textView.setText(String.format("x%d+", +j / 2 + 1));
                    }
                    tableRow.addView(textView, j);
                }
            }
            tableLayout.addView(tableRow, i);
        }
    }

    public void clearMatrix(View v) {
        tableLayout.removeAllViews();
        createTable(rows, columns);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ",";
            try {
                br = new BufferedReader(new FileReader(filePath));
                int i = 0;
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(cvsSplitBy);
                    System.out.println(Arrays.toString(row));
                    if (row.length == rows + 1) {
                        for (int j = 0; j < row.length; j++) {
                            EditText elem = ((EditText) ((TableRow) tableLayout.getChildAt(i))
                                    .getChildAt(2 * j));
                            elem.setText(row[j]);
                        }
                        i++;
                    } else {
                        Toast.makeText(this, "Шейпи файлу та СЛАР не співпадають!", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (IOException e) {
                Toast.makeText(this, "Щось пішло не так(.\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
