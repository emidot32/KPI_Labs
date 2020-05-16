package com.example.amolabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Lab2Main extends AppCompatActivity {
    String clickedButton;
    public static final String EXTRA_MESSAGE = "com.example.amolabs.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosingsortingalgorithm);
    }

    public void runExecutingProgram(View view) {
        clickedButton = ((Button)findViewById(view.getId())).getText().toString();
        System.out.println(clickedButton);
        Intent intent = new Intent(this, SortingProgram.class);
        intent.putExtra(Lab2Main.EXTRA_MESSAGE, clickedButton);
        startActivity(intent);
    }


}

