package com.example.amolabs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import external_libs.estimator.ExpressionEstimatorException;

public class Lab3Graphics extends AppCompatActivity {
    private int a;
    private int b;
    private int n;
    private String currentGraphic;
    private String function;
    GraphView graph1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_graphics1);
        a = getIntent().getIntExtra("a", -2);
        b = getIntent().getIntExtra("b", 5);
        n = getIntent().getIntExtra("n", 10);
        currentGraphic = getIntent().getStringExtra("currentGraphic");
        function = getIntent().getStringExtra("function");
        makeGraphics();
    }

    void makeGraphics() {
        graph1 = (GraphView) findViewById(R.id.Lab3graph1);
        graph1.getViewport().setScalable(true);
        graph1.getViewport().setScrollable(true);
        graph1.getViewport().setScalableY(true);
        graph1.getViewport().setScrollableY(true);
        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMinX(a);
        graph1.getViewport().setMaxX(b);
        graph1.getViewport().setYAxisBoundsManual(true);

        double precision = 0.05;
        double[] xt = new double[n];
        double[] yt = new double[n];
        double x = a;
        DataPoint[] linePoints = new DataPoint[(int) ((b - a) / precision)];
        DataPoint[] pointPoints = new DataPoint[n];
        DataPoint[] interpolationPoints = new DataPoint[(int) ((b - a) / precision)];
        external_libs.estimator.ExpressionEstimator estimator = new external_libs.estimator.ExpressionEstimator();
        try {
            estimator.compile(function);
            for (int i = 0; i < n; i++) {
                double x1 = a + ((double) (b - a) / n) * i;
                xt[i] = x1;
                yt[i] = estimator.calculate(new double[]{x1});
                pointPoints[i] = new DataPoint(x1, yt[i]);
            }
            double min = estimator.calculate(new double[]{a});
            double max = estimator.calculate(new double[]{b});
            for (int j = 0; j < (int) ((b - a) / precision); j++) {
                double y = estimator.calculate(new double[]{x});
                if (y<min){ min = y; }
                if (y>max){ max = y; }
                linePoints[j] = new DataPoint(x, y);
                interpolationPoints[j] = new DataPoint(x, getInterpolationNewton(x, xt, n, yt));
                x += precision;
            }
            graph1.getViewport().setMinY(min - 0.2*min);
            graph1.getViewport().setMaxY(max + 0.2*max);
        }catch (ExpressionEstimatorException e){
            Toast.makeText(this, "Ви ввели некоректний вираз.", Toast.LENGTH_SHORT).show();
        }
        LineGraphSeries<DataPoint> lSeries = new LineGraphSeries<>(linePoints);
        lSeries.setAnimated(true);
        lSeries.setTitle("Theoretical");
        PointsGraphSeries<DataPoint> pSeries = new PointsGraphSeries<>(pointPoints);
        pSeries.setShape(PointsGraphSeries.Shape.POINT);
        pSeries.setColor(Color.GREEN);
        pSeries.setTitle("Table Points");
        LineGraphSeries<DataPoint> interpolationSeries = new LineGraphSeries<>(interpolationPoints);
        interpolationSeries.setAnimated(true);
        interpolationSeries.setTitle("Interpolation");
        interpolationSeries.setColor(Color.RED);

        graph1.addSeries(lSeries);
        graph1.addSeries(pSeries);
        graph1.addSeries(interpolationSeries);
        graph1.setLegendRenderer(new LegendRenderer(graph1));
        graph1.getLegendRenderer().setVisible(true);
        graph1.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

    public static double getInterpolationNewton(double x, double[] xt, int r, double[] ft) {
        double result = ft[0];
        double buf = 1;
        for (int k = 1; k < r; k++) {
            double tempSum = 0;
            for (int i = 0; i <= k; i++) {
                double temp = 1;
                for (int j = 0; j < i; j++) {
                    temp = temp * (xt[i] - xt[j]);
                }
                for (int j = i + 1; j <= k; j++) {
                    temp = temp * (xt[i] - xt[j]);
                }
                temp = ft[i] / temp;
                tempSum += temp;
            }
            buf = buf * (x - xt[k - 1]);
            result = result + tempSum * buf;
        }
        return result;
    }
    static double min(double[] arr){
        double min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i]<min){
                min = arr[i];
            }
        }
        return min;
    }
    static double max(double[] arr){
        double max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i]>max){
                max = arr[i];
            }
        }
        return max;
    }
}
