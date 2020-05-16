package com.example.amolabs;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.series.DataPoint;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SortingProgram extends AppCompatActivity {
    String currentAlgorithm;
    EditText editText1;
    EditText editText2;
    ImageButton readButton;
    double[] array;
    public static final String EXTRA_MESSAGE1 = "com.example.amolabs.MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "com.example.amolabs.MESSAGE2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executing_program);
        currentAlgorithm = getIntent().getStringExtra(Lab2Main.EXTRA_MESSAGE);
        editText1 = (EditText) findViewById(R.id.unsortedArray);
        editText2 = (EditText) findViewById(R.id.sortedArray);
        readButton = (ImageButton) findViewById(R.id.readButton);
        array = null;
        ActivityCompat.requestPermissions(SortingProgram.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(SortingProgram.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(SortingProgram.this)
                        .withRequestCode(1)
                        .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                        .start();
            }
        });
    }

    public void sort(View v) {
        TextView textView = (TextView) findViewById(R.id.textView55);
        try {
            array = changeType(editText1.getText().toString().split(","));
            switch (currentAlgorithm) {
                case ("Shaker Sort"):
                    long start1 = System.nanoTime();
                    shakerSort(array);
                    textView.setText(String.format("%s nc.", (System.nanoTime() - start1)));
                    break;
                case ("Quick Sort"):
                    long start2 = System.nanoTime();
                    quickSort(array, 0, array.length-1);
                    textView.setText(String.format("%s nc.", (System.nanoTime() - start2)));
                    break;
                case ("Merge Sort"):
                    long start3 = System.nanoTime();
                    mergeSort(array,  array.length);
                    textView.setText(String.format("%s nc.", (System.nanoTime() - start3)));
                    break;
                case ("Heap Sort"):
                    long start4 = System.nanoTime();
                    heapSort(array);
                    textView.setText(String.format("%s nc.", (System.nanoTime() - start4)));
                    break;
            }
            editText2.setText(Arrays.toString(array));
        } catch (Exception e) {
            Toast.makeText(SortingProgram.this, "Ви ввели некоректні дані!", Toast.LENGTH_LONG).show();
        }
    }

    public static void mergeSort(double[] array, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        double[] l = new double[mid];
        double[] r = new double[n - mid];
        System.arraycopy(array, 0, l,0, mid);
        System.arraycopy(array, mid, r,0, n-mid);
        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(array, l, r, mid, n - mid);
    }

    public static void merge(double[] arr, double[] leftArray, double[] rightArray, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k++] = leftArray[i++];
            }
            else {
                arr[k++] = rightArray[j++];
            }
        }
        while (i < left) {
            arr[k++] = leftArray[i++];
        }
        while (j < right) {
            arr[k++] = rightArray[j++];
        }
    }

    public static double[] changeType(String[] strArray) {
        double[] intArray = new double[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Double.parseDouble(strArray[i].trim());
        }
        return intArray;
    }


    public static DataPoint[] shakerSort(double array[]) {
        long start = System.nanoTime();
        List<DataPoint> list = new ArrayList<>();
        int count1 = 0;
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            for (int i = left; i < right; i++) {
                if (array[i] > array[i + 1]) {
                    double temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }
            right--;
            for (int i = right; i > left; i--) {
                if (array[i] < array[i - 1]) {
                    double temp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = temp;
                }
            }
            left++;
            list.add(new DataPoint(count1, count1 * count1 + count1 * Math.random()));
            //if (count1%50==0){ list.add(new DataPoint(count1, (int)((System.nanoTime()-start)/1000.0)));}
            count1++;
        }
        return list.toArray(new DataPoint[list.size()]);
    }

    public void quickSort(double[] array, int low, int high) {
        long start = System.nanoTime();
        if (array.length == 0)
            return;

        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        double opora = array[middle];
        int i = low, j = high;
        while (i <= j) {
            while (array[i] < opora) {
                i++;
            }
            while (array[j] > opora) {
                j--;
            }
            if (i <= j) {//меняем местами
                double temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        if (low < j)
            quickSort(array, low, j);

        if (high > i)
            quickSort(array, i, high);
    }

    public static void heapSort(double arr[])
    {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end
            double temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }
    public static void heapify(double arr[], int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i)
        {
            double swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    public void openGenWindow(View view) {
        Intent intent = new Intent(this, GenerateRandomArrayWindow.class);
        startActivity(intent);
    }


    public void saveText(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        EditText textBox = (EditText) findViewById(R.id.sortedArray);
        String text = textBox.getText().toString();
        if (!new File(Environment.getExternalStorageDirectory().toString() + "/AMO_Labs/Lab2").exists()){
            new File(Environment.getExternalStorageDirectory().toString() + "/AMO_Labs/Lab2").mkdir();
        }
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/AMO_labs/Lab2/sortedArray.txt");
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(text.getBytes());
            out.close();
            Toast.makeText(this, "Файл збережений в каталог: " +
                    Environment.getExternalStorageDirectory().toString() +
                    "/AMO_Labs/Lab2/", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Щось пішло не так(" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            EditText editText = (EditText) findViewById(R.id.unsortedArray);
            File file = new File(filePath);
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
                Toast.makeText(this, "Щось пішло не так(" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public void showGrapics(View v) {
        Intent intent = new Intent(this, GraphsLab2.class);
        intent.putExtra(SortingProgram.EXTRA_MESSAGE1, editText1.getText().toString());
        intent.putExtra(SortingProgram.EXTRA_MESSAGE2, currentAlgorithm);
        startActivity(intent);
    }
}
