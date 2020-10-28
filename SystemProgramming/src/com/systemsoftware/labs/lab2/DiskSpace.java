package com.systemsoftware.labs.lab2;

import java.util.Arrays;


public class DiskSpace {
    final static int BLOCK_SIZE = 4;
    final static int NUMBER_OF_BLOCKS = 32;
    static IEntityPart[] blockFileTable = new IEntityPart[NUMBER_OF_BLOCKS];

    static int getStartIndex(int size){
        for (int i = 0; i < blockFileTable.length; i++){
            if (i+size>=blockFileTable.length) return -1;
            boolean flag = true;
            for (int j = i; j < i+size; j++) {
                if (blockFileTable[j] != null) {
                    flag = false;
                    break;
                }
            }
            if (flag) return i;
        }
        return -1;
    }

    static void print(){
        Arrays.stream(blockFileTable).forEach(System.out::println);
    }
}
