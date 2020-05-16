package OPP.rgr.pcs_sm;

import static OPP.rgr.pcs_sm.Main.*;

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
        // Введення МZ
        inputMatrix(MZ);
        // Сигнал про введення MZ
        synchMonitor.signalInput();
        // Чекати на введення в Т1, Т2, Т4
        synchMonitor.waitInput();
        // Копіювання спільних ресурсів
        int[] Z3 = resourceMonitor.copyZ();
        int[] B3 = resourceMonitor.copyB();
        int[][] MR3 = resourceMonitor.copyMR();
        // Обчислення min(B*MZ + Z*(MR*MX)) для H частини
        int a3 = min(calculate(Z3, B3, MR3, 2*H, 3*H), 2*H, 3*H);
        // Обчислення а =  min(а, а1)
        resourceMonitor.min(resourceMonitor.get_a(), a3);
        // Сигнал про обчислення а
        synchMonitor.signalMin();
        System.out.println("T3 finished");
    }
}
