package com.example.amolabs;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LinearAlg extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_alg);
    }

    public void change(View v){
        Fragment fragment= null;
        switch (v.getId()){
            case R.id.blockDiagramLinearButton:
                fragment = new FragmentLinearBlockDiagram();
                break;
            case R.id.codeLinearButton:
                fragment = new FragmentLinearCode();
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
    public void runLinearProgram(View v) {
        Intent intent = new Intent("com.example.amolabs.LinearProgram");
        startActivity(intent);
    }
}
