package com.example.amolabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Locale;

public class LinearProgram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_program);
    }
    public void calculate(View v){
        EditText edt1 = (EditText)findViewById(R.id.editText1);
        EditText edt2 = (EditText)findViewById(R.id.editText2);
        EditText edt3 = (EditText)findViewById(R.id.editText3);
        TextView result = (TextView)findViewById(R.id.LinearResult);
        try{
        double a = Double.parseDouble(edt1.getText().toString());
        double b = Double.parseDouble(edt2.getText().toString());
        double c = Double.parseDouble(edt3.getText().toString());
            result.setText(String.format(Locale.getDefault(),"%.4f", Math.cbrt(5 + c*Math.sqrt(b+5*Math.sqrt(a)))));
        }
        catch (Exception e){
            Toast.makeText(LinearProgram.this, "Ви ввели некоректні дані!", Toast.LENGTH_LONG).show();
        }
    }
}
