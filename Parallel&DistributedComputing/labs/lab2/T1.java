package OPP.lab_2020.lab4;
import static OPP.lab_2020.lab4.Main.*;

class T1 extends Thread {
    private ResourceMonitor resourceMonitor;
    private SynchMonitor synchMonitor;

    T1(ResourceMonitor resourceMonitor, SynchMonitor synchMonitor) {
        super();
        this.resourceMonitor = resourceMonitor;
        this.synchMonitor = synchMonitor;
    }

    @Override
    public void run() {
        System.out.println("T1 started");
        resourceMonitor.inputZ(randZ);
        inputMatrix(ME);
        synchMonitor.signalInput();
        synchMonitor.waitInput();

        //long start = System.currentTimeMillis();
        B1 = sort(resourceMonitor.getZ(), 0, H);
        synchMonitor.signalSort2();
        synchMonitor.waitMerge2();

        int d1 = resourceMonitor.copy_d();
        int[] Z1 = resourceMonitor.copyZ();
        int[] B1 = resourceMonitor.copyB();
        int[][] MX1 = resourceMonitor.copyMX();

        calculate(d1, Z1, B1, MX1, 0, H);

        synchMonitor.waitCalculate();
        outputVector(A);
        //System.out.println(System.currentTimeMillis() - start);
        System.out.println("T1 finished");
    }
}
