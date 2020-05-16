package com.example.amolabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lab3Main extends AppCompatActivity {
    String clickedButton;
    String function;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_main);
    }

    public void showGraphics(View v) {
        clickedButton = ((Button) findViewById(v.getId())).getText().toString();
        function = ((EditText) findViewById(R.id.function)).getText().toString();
        Intent intent = new Intent(this, Lab3Graphics.class);
        try {
            int a = Integer.parseInt(((EditText) findViewById(R.id.Lab3_a)).getText().toString());
            int b = Integer.parseInt(((EditText) findViewById(R.id.Lab3_b)).getText().toString());
            int n = Integer.parseInt(((EditText) findViewById(R.id.Lab3_n)).getText().toString());
            if (a >= b) {
                Toast.makeText(this, "а >= b!", Toast.LENGTH_SHORT).show();
            } else {
                intent.putExtra("a", a);
                intent.putExtra("b", b);
                intent.putExtra("n", n);
                intent.putExtra("currentGraphic", clickedButton);
                intent.putExtra("function", function);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ви ввели некоректні дані!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
