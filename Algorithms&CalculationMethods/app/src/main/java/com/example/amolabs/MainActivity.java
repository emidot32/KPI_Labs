package com.example.amolabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdapterForLabsList adapterForLabsList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.labsList);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        adapterForLabsList = new AdapterForLabsList(getLabs(), getLabsDescription(), MainActivity.this);
        recyclerView.setAdapter(adapterForLabsList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            Intent intent = new Intent("com.example.amolabs.InfoAboutMe");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<String> getLabs(){
        List<String> labsList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            labsList.add("  Лабораторна робота №"+i);
        }
        return labsList;
    }
    private List<String> getLabsDescription(){
        List<String> labDescriptionsList = new ArrayList<>();
        labDescriptionsList.add("     Види алгоритмів та їх властивості. ");
        labDescriptionsList.add("     Порівняння алгоритмів сортування.");
        labDescriptionsList.add("     Інтерполяція функцій.");
        labDescriptionsList.add("     Розв'язання нелінійних рівнянь.");
        labDescriptionsList.add("     Розв'язання СЛАР.");
        return labDescriptionsList;
    }

}
