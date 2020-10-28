package com.systemsoftware.labs.lab1;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;


import static com.systemsoftware.labs.lab1.Main.SIZE;
import static com.systemsoftware.labs.lab1.Main.random;
import static com.systemsoftware.labs.lab1.MemoryManager.*;

public class Process {
    private int numberOfPages;
    VirtualPage[] virtualMemory;
    int addressStart;
    int addressEnd;
    int processNum;
    static Logger logger = Logger.getLogger(Process.class.getName());

    public Process(int numberOfPages, int processNum) {
        this.numberOfPages = numberOfPages;
        this.processNum = processNum;
        this.addressStart = 0;
        this.addressEnd = SIZE * numberOfPages;
        this.virtualMemory = new VirtualPage[numberOfPages];
        for (int i = 0; i < numberOfPages; i++) {
            virtualMemory[i] = new VirtualPage(i * SIZE, false,
                    false, false, processNum);
        }
    }

    void initGetPhysicalPage() throws InterruptedException {
        System.out.println("initGetPhysicalPage for process: " + processNum);
        logger.info("initGetPhysicalPage for process: " + processNum);
        if (memoryMap.size() < N) {
            int startIndex = new Random().nextInt((int) (numberOfPages * 0.3));
            for (int i = startIndex; i < numberOfPages; i++) {
                Thread.sleep(random.nextInt(50));
                virtualMemory[i].presenceBit = true;
                virtualMemory[i].usageBit = true;
                virtualMemory[i].timeOfLastUsage = System.currentTimeMillis();
                if (memoryMap.size() < N) {
                    memoryMap.put(startPhysicalAddress, virtualMemory[i]);
                    startPhysicalAddress += SIZE;
                }
            }
        }
    }

    void getPhysicalPage() {
        System.out.println("getPhysicalPage for process: " + processNum);
        logger.info("getPhysicalPage for process: " + processNum);
        Arrays.stream(virtualMemory)
                .filter(virtualPage -> (!virtualPage.usageBit))
                .findFirst()
                .ifPresent(virtualPage -> {
                            System.out.println(virtualPage);
                            logger.info(virtualPage + "");
                            MemoryManager.replacePageMain(virtualPage);
                        }
                );
    }

    @Override
    public String toString() {
        return "Process{" +
                "numberOfPages=" + numberOfPages +
                ", addressStart=" + addressStart +
                ", addressEnd=" + addressEnd +
                ", processNum=" + processNum +
                '}';
    }
}
