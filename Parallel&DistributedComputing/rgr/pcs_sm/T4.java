package OPP.rgr.pcs_sm;

import static OPP.rgr.pcs_sm.Main.*;

class T4 extends Thread {
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
        // Введення МR
        resourceMonitor.inputMR();
        // Сигнал про введення MZ
        synchMonitor.signalInput();
        // Чекати на введення в Т1, Т2, Т4
        synchMonitor.waitInput();
        // Копіювання спільних ресурсів
        int[] Z4 = resourceMonitor.copyZ();
        int[] B4 = resourceMonitor.copyB();
        int[][] MR4 = resourceMonitor.copyMR();
        // Обчислення min(B*MZ + Z*(MR*MX)) для H частини
        int a4 = min(calculate(Z4, B4, MR4, 3*H, N), 3*H, N);
        // Обчислення а =  min(а, а1)
        resourceMonitor.min(resourceMonitor.get_a(), a4);
        // Сигнал про обчислення а
        synchMonitor.signalMin();
        System.out.println("T4 finished");
    }
}
