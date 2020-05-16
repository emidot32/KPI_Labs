package com.example.amolabs;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateRandomArrayWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_random_array_window);
    }

    public void writeFile(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        EditText editText1 = (EditText) findViewById(R.id.editTextMin);
        EditText editText2 = (EditText) findViewById(R.id.editTextMax);
        EditText editText3 = (EditText) findViewById(R.id.editTextLength);
        EditText editText4 = (EditText) findViewById(R.id.editTextName);
        int min, max, len;
        String name = "generatedArray.txt";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            min = Integer.parseInt(editText1.getText().toString());
            max = Integer.parseInt(editText2.getText().toString());
            len = Integer.parseInt(editText3.getText().toString());
            name = editText4.getText().toString();
            for(int i = 0; i < len; i++){
                stringBuilder.append((int)(Math.random() * (max-min)+1) + min);
                stringBuilder.append(",");
            }
        }catch (Exception e){
            Toast.makeText(this, "Вы ввели некоректні дані!", Toast.LENGTH_SHORT).show();
        }
        if (!new File(Environment.getExternalStorageDirectory().toString()+"/AMO_Labs/Lab2").exists()){
            (new File(Environment.getExternalStorageDirectory().toString()+"/AMO_labs/Lab2")).mkdir();
        }
        else {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/AMO_labs/Lab2/" + name);

            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(stringBuilder.toString().getBytes());
                out.close();
                Toast.makeText(this, "Файл збережений в каталог: " +
                        Environment.getExternalStorageDirectory().toString() +
                        "/AMO_labs/Lab2/", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Щось пішло не так(" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
