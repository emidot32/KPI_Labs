package com.example.rts_labs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lab3_3 extends AppCompatActivity {
    TextView xResults, executingTimeTextView;
    int[] coefs = new int[4];
    int y, descsNum;
    int[][] population;
    int[] result = new int[4];
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_3);
        setTitle("Lab 3.3");

        xResults = findViewById(R.id.xResults);
        executingTimeTextView = findViewById(R.id.executingTime);
        xResults.setText(String.format("    %-10s%-10s%-10s%-10s", "x1=", "x2=", "x3=", "x4="));
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calculate(View view){
        xResults.setText(String.format("    %-10s%-10s%-10s%-10s", "x1=", "x2=", "x3=", "x4="));
        executingTimeTextView.setText("    Executing time: ");
        if (getInputData()){
            population = new int[descsNum][4];
            long startTime = System.nanoTime();
            fillStartPopulation();
            try {
                result = calcRoots();
                long executingTime  = System.nanoTime()-startTime;
                xResults.setText(String.format("    x1=%-6dx2=%-6dx3=%-6dx4=%-6d", result[0], result[1], result[2], result[3]));
                executingTimeTextView.append(executingTime/1000 + " mcs");
            } catch (StackOverflowError e) {
                Toast.makeText(this, "Impossible to find roots!", Toast.LENGTH_LONG).show();
            }

        }
    }

    // Рекурсивно змінюємо покоління, поки не будуть знайдені корені.
    @RequiresApi(api = Build.VERSION_CODES.N)
    private int[] calcRoots(){
        double delta = 0;
        int[] parentFitness = calcFitness(population);
        for (int i = 0; i < descsNum; i++) {
            if (parentFitness[i] == 0){
                return population[i];
            }
            delta += 1/(double)parentFitness[i];
        }
        int[] survivalRate = new int[descsNum];
        for (int i = 0; i < descsNum; i++) {
            survivalRate[i] = (int) (((1 / (double)parentFitness[i]) / delta) * 100);
        }
        int[][] descendants = crossover(survivalRate);
        int[] descendantFitness = calcFitness(descendants);
        // Якщо середня приспособлюванність у нащадків більше ніж у батьків, то наступне покоління буде нащадками.
        if (Arrays.stream(descendantFitness).average().getAsDouble() >
                Arrays.stream(parentFitness).average().getAsDouble()){
            population = descendants;
        } // Якщо менше - то поточне покоління мутує.
        else population = mutation();

        return calcRoots();
    }

    // Піддає мутації поточне покоління
    private int[][] mutation(){
        int[][] mutant = new int[descsNum][4];
        for (int i = 0; i < descsNum; i++) {
            double rand = random.nextDouble();
            mutant[i] = population[i];
            if (rand > 0.5) {
                mutant[i][random.nextInt(4)] = random.nextInt(y);
            }
        }
        return mutant;
    }


    private int[] calcFitness(int[][] arr) {
        int[] fitness = new int[descsNum];
        for (int i = 0; i < descsNum; i++) {
            int sum = 0;
            for (int j = 0; j < 4; j++) {
                sum+=coefs[j]*arr[i][j];
            }
            fitness[i] = Math.abs(sum - y);
        }
        return fitness;
    }

    // Метод кросоверу. Схрещуємо батьків для отримання наступного покоління
    @RequiresApi(api = Build.VERSION_CODES.N)
    private int[][] crossover(int[] survRate){
        List<Integer> appropriateIndexes = appropriateIndexes(survRate);
        int[][] descendants = new int[descsNum][4];
        for (int i = 0; i < descsNum; ) {
            int[] parent1 = population[appropriateIndexes.get(random.nextInt(appropriateIndexes.size()))];
            int[] parent2 = population[appropriateIndexes.get(random.nextInt(appropriateIndexes.size()))];
            if (parent1 != parent2) {
                descendants[i] = divide(parent1, parent2);
                i++;
            }
        }
        return descendants;
    }
    // Вибираємо індекси тих "батьків", у яких шанс на виживання найбільший.
    // З них буде формуватися наступне покоління
    private List<Integer> appropriateIndexes(int[] survRate){
        List<Integer> appropriateIndexes = new ArrayList<>();
        int[] survRateCopy = Arrays.copyOf(survRate, descsNum);
        while (appropriateIndexes.size() != descsNum/2){
            int max = survRateCopy[0];
            int index = 0;
            for (int i = 1; i < descsNum; i++) {
                if (survRateCopy[i] > max) {
                    max = survRate[i];
                    index = i;
                }
            }
            appropriateIndexes.add(index);
            survRateCopy[index] = -1;
        }
        return appropriateIndexes;
    }
    // Випадково вибираємо розділову лінію для створення нащадку
    private int[] divide(int[] parent1, int[] parent2) {
        int rand = random.nextInt(3);
        int[] child = new int[4];
        switch (rand) {
            case 0:
                child[0] = parent1[0];
                child[1] = parent2[1];
                child[2] = parent2[2];
                child[3] = parent2[3];
                break;
            case 1:
                child[0] = parent1[0];
                child[1] = parent1[1];
                child[2] = parent2[2];
                child[3] = parent2[3];
                break;
            case 2:
                child[0] = parent1[0];
                child[1] = parent1[1];
                child[2] = parent1[2];
                child[3] = parent2[3];
                break;
        }
        return child;
    }

    private boolean getInputData(){
        try{
            coefs[0] = getIntValue(R.id.aEditText);
            coefs[1] = getIntValue(R.id.bEditText);
            coefs[2] = getIntValue(R.id.cEditText);
            coefs[3] = getIntValue(R.id.dEditText);
            y = getIntValue(R.id.yEditText);

        }catch (NumberFormatException e){
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            descsNum = getIntValue(R.id.numberOfDescendants);
            if (descsNum < 0) {
                Toast.makeText(this, "Number of descendants can not be less then 0!", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberFormatException e){
            descsNum = 25;
            Toast.makeText(this, "Number of descendants = 25!", Toast.LENGTH_LONG).show();
        }
        return true;
    }
    // Заповнюємо випадковими значеннями початкову популяцію (в межах від 0 до у/2)
    private void fillStartPopulation(){
        for (int i = 0; i < descsNum; i++) {
            for (int j = 0; j < 4; j++) {
                population[i][j] = random.nextInt(y/2);
            }
        }
    }

    private int getIntValue(int id){
        return Integer.parseInt(((EditText)findViewById(id)).getText().toString());
    }
}
