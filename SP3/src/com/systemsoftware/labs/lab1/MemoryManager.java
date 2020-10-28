package com.systemsoftware.labs.lab1;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MemoryManager {
    static final int N = 25;
    static int startPhysicalAddress = 0;
    static HashMap<Integer, VirtualPage> memoryMap = new HashMap<>(N);
    static final long t = 20;
    static Logger logger = Logger.getLogger(MemoryManager.class.getName());

    static void replacePageMain(VirtualPage newVirtualPage) {
        int[] isChanged = new int[]{-1}; //  -1/0/1
        replacePage(newVirtualPage, isChanged);
        if (isChanged[0] != 1) {
            logger.info("first iteration did not change map");
            isChanged[0] = 0;
            replacePage(newVirtualPage, isChanged);
        }
    }

    private static void replacePage(VirtualPage newVirtualPage, int[] isChanged) {
        for (Map.Entry<Integer, VirtualPage> entry : memoryMap.entrySet()) {
            Integer physicalAddress = entry.getKey();
            VirtualPage virtualPage = entry.getValue();
            if (virtualPage.usageBit) {
                System.out.println("No change for: " + virtualPage);
                logger.info("No change for: " + virtualPage);
            } else {
                if (System.currentTimeMillis() - virtualPage.timeOfLastUsage > t) {
                    replace(virtualPage, newVirtualPage, physicalAddress);
                    isChanged[0] = 1;
                    System.out.println("Changed from " + virtualPage + " on " + newVirtualPage);
                    logger.info("Changed from " + virtualPage + " on " + newVirtualPage);
                    break;
                } else {
                    if (isChanged[0] == 0) {
                        replace(virtualPage, newVirtualPage, physicalAddress);
                        isChanged[0] = 1;
                        System.out.println("Changed from " + virtualPage + " on " + newVirtualPage);
                        logger.info("Changed from " + virtualPage + " on " + newVirtualPage);
                        break;
                    }
                }
                replace(virtualPage, newVirtualPage, physicalAddress);
                break;
            }
        }
    }

    private static void replace(VirtualPage virtualPage, VirtualPage newVirtualPage, Integer physicalAddress) {
        virtualPage.usageBit = false;
        virtualPage.presenceBit = false;
        newVirtualPage.presenceBit = true;
        newVirtualPage.usageBit = true;
        newVirtualPage.timeOfLastUsage = System.currentTimeMillis();
        memoryMap.put(physicalAddress, newVirtualPage);
    }

    public static void resetUsageBit() {
        long curTime = System.currentTimeMillis();
        System.out.println(curTime);
        memoryMap.forEach((physicalAddress, virtualPage) -> {
            if ((curTime - virtualPage.timeOfLastUsage) > 600) virtualPage.usageBit=false;});
        System.out.println("UsageBit is reset");
        logger.info("UsageBit is reset");
    }

    public static void print() {
        memoryMap.forEach((physicalAddress, virtualPage) -> {
                    System.out.println("[" + physicalAddress + ": " + virtualPage + "]");
                    logger.info("[" + physicalAddress + ": " + virtualPage + "]");
                }
        );
    }
}
