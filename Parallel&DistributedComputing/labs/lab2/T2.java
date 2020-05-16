package OPP.lab_2020.lab4;
import static OPP.lab_2020.lab4.Main.*;

class T2 extends Thread{
    private ResourceMonitor resourceMonitor;
    private SynchMonitor synchMonitor;

    T2(ResourceMonitor resourceMonitor, SynchMonitor synchMonitor) {
        super();
        this.resourceMonitor = resourceMonitor;
        this.synchMonitor = synchMonitor;
    }
    @Override
    public void run() {
        System.out.println("T2 started");
        synchMonitor.waitInput();

        B2 = sort(resourceMonitor.getZ(), H, 2*H);
        synchMonitor.waitSort2();
        B12 = mergeSort(B1, B2);
        synchMonitor.waitMerge1();
        resourceMonitor.setB(mergeSort(B12, B34));
        synchMonitor.signalMerge2();

        int d2 = resourceMonitor.copy_d();
        int[] Z2 = resourceMonitor.copyZ();
        int[] B2 = resourceMonitor.copyB();
        int[][] MX2 = resourceMonitor.copyMX();

        calculate(d2, Z2, B2, MX2, H, 2*H);

        synchMonitor.signalCalculate();
        System.out.println("T2 finished");
    }
}
