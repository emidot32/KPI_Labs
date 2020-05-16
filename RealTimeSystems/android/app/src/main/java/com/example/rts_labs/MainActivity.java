package com.example.rts_labs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button lab1Btn, lab2Btn, lab3Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lab1Btn = findViewById(R.id.lab1_button);
        lab2Btn = findViewById(R.id.lab2_button);
        lab3Btn = findViewById(R.id.lab3_button);
    }

    public void goToLab3_1(View view){
        Intent lab1Intent = new Intent(this, Lab3_1.class);
        startActivity(lab1Intent);
    }

    public void goToLab3_2(View view){
        Intent lab1Intent = new Intent(this, Lab3_2.class);
        startActivity(lab1Intent);
    }

    public void goToLab3_3(View view){
        Intent lab1Intent = new Intent(this, Lab3_3.class);
        startActivity(lab1Intent);
    }

}
