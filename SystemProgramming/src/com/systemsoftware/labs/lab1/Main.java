package com.systemsoftware.labs.lab1;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main {
    static final int SIZE = 4;
    static Random random = new Random();
    static List<Process> processes = new ArrayList<>();
    static Logger logger = Logger.getLogger("Main");
    static FileHandler fh;

    public static void main(String[] args) {
        loggerConf(logger);
        loggerConf(MemoryManager.logger);
        loggerConf(Process.logger);

        init();
        MemoryManager.print();
        Scanner in = new Scanner(System.in);
        int iter = 0;
        while (true){
            processes.forEach(process -> {
                if (random.nextDouble() < 0.5) process.getPhysicalPage();
            });
            MemoryManager.resetUsageBit();
            MemoryManager.print();
            iter++;
            if (iter%20 == 0){
                System.out.println("Enter 'stop' for break loop up: ");
                if ("stop".equals(in.nextLine())) break;
            }
        }
    }

    static void init(){
        for (int i = 0; i < 4; i++) {
            processes.add(new Process(7+random.nextInt(5), i));
        }
        processes.forEach(process -> {
            try {
                process.initGetPhysicalPage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void loggerConf(Logger logger){
        try {
            logger.setUseParentHandlers(false);
            // This block configure the logger with handler and formatter
            fh = new FileHandler("/media/disk_d/Programming/KPI_Projects/SP3/src/main/java/com/systemsoftware/labs/lab1/logs.txt");
            logger.addHandler(fh);
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getSourceMethodName()
                            + "() : "
                            + record.getMessage() + "\n";
                }
            });


        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
