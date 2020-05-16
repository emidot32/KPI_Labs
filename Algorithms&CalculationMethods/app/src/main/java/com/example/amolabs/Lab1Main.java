package com.example.amolabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lab1Main extends AppCompatActivity {
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_first_page);
        addListenerOnButton();
    }
    public void addListenerOnButton(){
        btn1 = (Button)findViewById(R.id.linearButton);
        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.amolabs.LinearAlg");
                        startActivity(intent);
                    }
                }
        );
        btn2 = (Button)findViewById(R.id.ramifiedButton);
        btn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.amolabs.RamifiedAlg");
                        startActivity(intent);
                    }
                }
        );
        btn3 = (Button)findViewById(R.id.cyclicalButton);
        btn3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.amolabs.CyclicalAlg");
                        startActivity(intent);
                    }
                }
        );
    }
}
