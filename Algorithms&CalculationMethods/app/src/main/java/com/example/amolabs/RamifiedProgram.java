package com.example.amolabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class RamifiedProgram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramified_program);
    }

    public void calculate(View v) {
        EditText edt1 = (EditText) findViewById(R.id.editTextRam1);
        EditText edt2 = (EditText) findViewById(R.id.editTextRam2);
        TextView result = (TextView) findViewById(R.id.RamifiedResult);
        double doubleResult;
        try {
            double k = Double.parseDouble(edt1.getText().toString());
            double d = Double.parseDouble(edt2.getText().toString());
            if (k > 10) {
                doubleResult = Math.sqrt(k * Math.sqrt(d * d) + d * Math.sqrt(k * k));
            } else doubleResult = (k + d) * (k + d);
            result.setText(String.format(Locale.getDefault(),"%.4f", doubleResult));
        } catch (Exception e) {
            Toast.makeText(RamifiedProgram.this, "Ви ввели некоректні дані!", Toast.LENGTH_LONG).show();
        }
    }
}
