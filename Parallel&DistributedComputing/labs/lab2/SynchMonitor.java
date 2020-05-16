package OPP.lab_2020.lab4;

class SynchMonitor {
    private int flagInput = 0;
    private int flagSort3 = 0;
    private int flagSort2 = 0;
    private int flagMerge1 = 0;
    private int flagMerge2 = 0;
    private int flagCalculate = 0;


    synchronized void signalInput() {
        flagInput++;
        notifyAll();
    }

    synchronized void waitInput() {
        while (flagInput != 3) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void signalSort2() {
        flagSort2++;
        notifyAll();
    }

    synchronized void waitSort2() {
        while (flagSort2 != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    synchronized void signalSort3() {
        flagSort3++;
        notifyAll();
    }

    synchronized void waitSort3() {
        while (flagSort3 != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void signalMerge1() {
        flagMerge1++;
        notifyAll();
    }

    synchronized void waitMerge1() {
        while (flagMerge1 != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void signalCalculate() {
        synchronized (this) {
            flagCalculate++;
            notifyAll();
        }
    }

    void waitCalculate() {
        synchronized (this) {
            while (flagCalculate != 3) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    synchronized void signalMerge2() {
        flagMerge2++;
        notifyAll();
    }

    synchronized void waitMerge2() {
        while (flagMerge2 != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
