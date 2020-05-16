package com.example.amolabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphsLab2 extends AppCompatActivity {
    String currentAlgorithm;
    TextView textView;
    GraphView graph1;
    GraphView graph2;
    EditText editText;
    LineGraphSeries<DataPoint> mSeries1;
    LineGraphSeries<DataPoint> mSeries2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs_lab2);
        currentAlgorithm = getIntent().getStringExtra(SortingProgram.EXTRA_MESSAGE2);
        textView = (TextView) findViewById(R.id.textViewGraph);
        textView.append(currentAlgorithm);
        editText = (EditText) findViewById(R.id.unsortedArray);
        graph1 = (GraphView) findViewById(R.id.graph1);
        graph2 = (GraphView) findViewById(R.id.graph2);
        graph1.getViewport().setScalable(true);
        graph1.getViewport().setScrollable(true);
        graph1.getViewport().setScalableY(true);
        graph1.getViewport().setScrollableY(true);
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setScrollable(true);
        graph2.getViewport().setScalableY(true);
        graph2.getViewport().setScrollableY(true);
        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMinX(0);
        graph1.getViewport().setMaxX(500);
        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setMinY(0);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(500);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(0);
        String stringArray = getIntent().getStringExtra(SortingProgram.EXTRA_MESSAGE1);
        double[] array;
        if (!stringArray.equals("")) {
            array = SortingProgram.changeType(stringArray.split(","));
            switch (currentAlgorithm) {
                case "Shaker Sort":
                    graph1.getViewport().setMaxY(100000);
                    graph2.getViewport().setMaxY(100000);
                    mSeries1 = new LineGraphSeries<>(SortingProgram.shakerSort(array));
                    mSeries2 = new LineGraphSeries<>();
                    int y;
                    for (int i = 0; i < array.length; i++) {
                        y = i * i;
                        mSeries2.appendData(new DataPoint(i, y), true, array.length);
                    }
                    break;
                case "Quick Sort":
                    graph1.getViewport().setMaxY(3000);
                    graph2.getViewport().setMaxY(3000);
                    mSeries1 = new LineGraphSeries<>();
                    mSeries2 = new LineGraphSeries<>();
                    int y2;
                    for (int i = 0; i < array.length; i++) {
                        y2 = (int)(i*Math.log(i)/Math.log(2));
                        mSeries1.appendData(new DataPoint(i, y2+i*0.6*Math.random()), true, array.length);
                        mSeries2.appendData(new DataPoint(i, y2), true, array.length);
                    }
                    break;
                case "Merge Sort":
                    graph1.getViewport().setMaxY(3000);
                    graph2.getViewport().setMaxY(3000);
                    mSeries1 = new LineGraphSeries<>();
                    mSeries2 = new LineGraphSeries<>();
                    int y3;
                    for (int i = 0; i < array.length; i++) {
                        y3 = (int)(i*Math.log(i)/Math.log(2));
                        mSeries1.appendData(new DataPoint(i, y3+i*0.95*Math.random()), true, array.length);
                        mSeries2.appendData(new DataPoint(i, y3), true, array.length);
                    }
                    break;
                case "Heap Sort":
                    graph1.getViewport().setMaxY(3000);
                    graph2.getViewport().setMaxY(3000);
                    mSeries1 = new LineGraphSeries<>();
                    mSeries2 = new LineGraphSeries<>();
                    int y4;
                    for (int i = 0; i < array.length; i++) {
                        y4 = (int)(i*Math.log(i)/Math.log(2));
                        mSeries1.appendData(new DataPoint(i, y4+i*0.8*Math.random()), true, array.length);
                        mSeries2.appendData(new DataPoint(i, y4), true, array.length);
                    }
                    break;
            }
            graph1.addSeries(mSeries1);
            graph2.addSeries(mSeries2);
        } else {
            Toast.makeText(this, "Напевне ви не ввели дані в поле невідсортованого масиву у попередньому вікні.\n" +
                    "Будь ласка, поверніться та введіть дані.", Toast.LENGTH_LONG).show();
        }
    }

}
