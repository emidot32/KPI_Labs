package OPP.rgr.pcs_sm;

import static OPP.rgr.pcs_sm.Main.*;


class T2 extends Thread {
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
        // Введення B, Z
        resourceMonitor.inputZ();
        resourceMonitor.inputB();
        // Сигнал про введення B, Z
        synchMonitor.signalInput();
        // Чекати на введення в Т1, Т3, Т4
        synchMonitor.waitInput();
        // Копіювання спільних ресурсів
        int[] Z2 = resourceMonitor.copyZ();
        int[] B2 = resourceMonitor.copyB();
        int[][] MR2 = resourceMonitor.copyMR();
        // Обчислення min(B*MZ + Z*(MR*MX)) для H частини
        int a2 = min(calculate(Z2, B2, MR2, H, 2*H), H, 2*H);
        // Обчислення а =  min(а, а1)
        resourceMonitor.min(resourceMonitor.get_a(), a2);
        // Сигнал про обчислення а
        synchMonitor.signalMin();
        System.out.println("T2 finished");
    }
}
