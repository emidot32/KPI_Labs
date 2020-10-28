package com.systemsoftware.labs.lab1;

public class VirtualPage {
    int pageAddress;
    boolean presenceBit;
    boolean usageBit;
    long timeOfLastUsage;
    boolean modificationBit;
    int processNum;

    public VirtualPage(int pageAddress, boolean presenceBit, boolean usageBit, boolean modificationBit, int processNum) {
        this.pageAddress = pageAddress;
        this.presenceBit = presenceBit;
        this.modificationBit = modificationBit;
        this.usageBit = usageBit;
        this.processNum = processNum;
    }

    @Override
    public String toString() {
        return "VP["+
                 pageAddress + ", "+
                 presenceBit + ", "+
                 usageBit +  ", "+
                 timeOfLastUsage + ", "+
                 modificationBit + ", "+
                 processNum + "]";
    }
}
