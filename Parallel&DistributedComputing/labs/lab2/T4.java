package OPP.lab_2020.lab4;

import static OPP.lab_2020.lab4.Main.*;

class T4 extends Thread{
    private ResourceMonitor resourceMonitor;
    private SynchMonitor synchMonitor;

    T4(ResourceMonitor resourceMonitor, SynchMonitor synchMonitor) {
        super();
        this.resourceMonitor = resourceMonitor;
        this.synchMonitor = synchMonitor;
    }

    @Override
    public void run() {
        System.out.println("T4 started");
        resourceMonitor.input_d();
        resourceMonitor.inputMX();
        synchMonitor.signalInput();
        synchMonitor.waitInput();

        B4 = sort(resourceMonitor.getZ(), 3*H, N);
        synchMonitor.signalSort3();
        synchMonitor.waitMerge2();

        int d4 = resourceMonitor.copy_d();
        int[] Z4 = resourceMonitor.copyZ();
        int[] B4 = resourceMonitor.copyB();
        int[][] MX4 = resourceMonitor.copyMX();
        calculate(d4, Z4, B4, MX4, 3*H, N);
        synchMonitor.signalCalculate();
        System.out.println("T4 finished");
    }
}
