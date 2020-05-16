package com.example.amolabs;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CyclicalAlg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclical_alg);
    }
    public void change(View v){
        Fragment fragment= null;
        switch (v.getId()){
            case R.id.blockDiagramCyclicalButton:
                fragment = new FragmentCyclicalBlockDiagram();
                break;
            case R.id.codeCyclicalButton:
                fragment = new FragmentCyclicalCode();
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    public void runCyclicalProgram(View v) {
        Intent intent = new Intent("com.example.amolabs.CyclicalProgram");
        startActivity(intent);
    }
}
