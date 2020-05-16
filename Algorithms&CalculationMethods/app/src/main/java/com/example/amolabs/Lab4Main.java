package com.example.amolabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lab4Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4_main);
    }

    public void showResults(View v) {
        Intent intent = new Intent(this, ResultsLab4.class);
        try {
            int a = Integer.parseInt(((EditText) findViewById(R.id.Lab4_a)).getText().toString());
            int b = Integer.parseInt(((EditText) findViewById(R.id.Lab4_b)).getText().toString());
            double e = Double.parseDouble(((EditText) findViewById(R.id.Lab4_e)).getText().toString());
            if (a >= b) {
                Toast.makeText(this, "а >= b!", Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("a", a);
                intent.putExtra("b", b);
                intent.putExtra("e", e);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ви ввели некоректні дані!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
