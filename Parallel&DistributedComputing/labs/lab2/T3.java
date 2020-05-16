package OPP.lab_2020.lab4;

import static OPP.lab_2020.lab4.Main.*;

class T3 extends Thread {
    private ResourceMonitor resourceMonitor;
    private SynchMonitor synchMonitor;

    T3(ResourceMonitor resourceMonitor, SynchMonitor synchMonitor) {
        super();
        this.resourceMonitor = resourceMonitor;
        this.synchMonitor = synchMonitor;
    }

    @Override
    public void run() {
        System.out.println("T3 started");
        inputMatrix(MO);
        synchMonitor.signalInput();
        synchMonitor.waitInput();

        B3 = sort(resourceMonitor.getZ(), 2*H,  3*H);
        synchMonitor.waitSort3();
        B34 = mergeSort(B3, B4);
        synchMonitor.signalMerge1();
        synchMonitor.waitMerge2();

        int d3 = resourceMonitor.copy_d();
        int[] Z3 = resourceMonitor.copyZ();
        int[] B3 = resourceMonitor.copyB();
        int[][] MX3 = resourceMonitor.copyMX();
        calculate(d3, Z3, B3, MX3, 2*H, 3*H);
        synchMonitor.signalCalculate();
        System.out.println("T3 finished");
    }
}
