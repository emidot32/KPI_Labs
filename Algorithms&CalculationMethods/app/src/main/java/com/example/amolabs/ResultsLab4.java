package com.example.amolabs;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultsLab4 extends AppCompatActivity {
    int a;
    int b;
    double e;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_lab4);
        a = getIntent().getIntExtra("a", -2);
        b = getIntent().getIntExtra("b", 5);
        e = getIntent().getDoubleExtra("e", 0.0001);
        calcResults();
    }

    @SuppressLint("DefaultLocale")
    private void calcResults(){
        List<Double> allX = new ArrayList<>();
        allX.add((double)b);
        allX.add(b - (Math.pow(b, 3)-b-3)/(3*Math.pow(b, 2)-1));
        int i = 1;
        while (Math.abs(allX.get(i)-allX.get((i-1)))>e){
            double x = allX.get(i);
            allX.add(x - (Math.pow(x, 3)-x-3)/(3*Math.pow(x, 2)-1));
            i++;
        }
        double result = allX.get(i);
        TextView textViewResult = (TextView)findViewById(R.id.x1);
        textViewResult.setText(String.format("%.5f", result));
        graph = (GraphView) findViewById(R.id.Lab4graph);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(a);
        graph.getViewport().setMaxX(b);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(Math.pow(a, 3)-a-3);
        graph.getViewport().setMaxY(Math.pow(b, 3)-b-3);
        double precision = 0.05;
        double x = a;
        DataPoint[] linePoints = new DataPoint[(int) ((b - a) / precision)];
        System.out.println((int) ((b - a) / precision));
        for (int j = 0; j < (int) ((b - a) / precision); j++) {
            linePoints[j] = new DataPoint(x, Math.pow(x, 3)-x-3);
            System.out.println(linePoints[j].toString());
            x+=precision;
        }
        LineGraphSeries<DataPoint> lSeries = new LineGraphSeries<>(linePoints);
        PointsGraphSeries<DataPoint> pSeries = new PointsGraphSeries<>(new DataPoint[]
                {new DataPoint(result, (double)Math.pow(result, 3)-result-3)});
        lSeries.setAnimated(true);
        lSeries.setTitle("Graphic");
        pSeries.setShape(PointsGraphSeries.Shape.POINT);
        pSeries.setColor(Color.RED);
        pSeries.setTitle("Our x");
        graph.addSeries(lSeries);
        graph.addSeries(pSeries);
        graph.setLegendRenderer(new LegendRenderer(graph));
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }
}
