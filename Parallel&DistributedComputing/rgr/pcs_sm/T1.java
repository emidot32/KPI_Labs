package OPP.rgr.pcs_sm;

import static OPP.rgr.pcs_sm.Main.*;

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
        // Введення МХ
        inputMatrix(MX);
        // Сигнал про введення МХ
        synchMonitor.signalInput();
        // Чекати на введення в Т2-Т4
        synchMonitor.waitInput();
        // Копіювання спільних ресурсів
        int[] Z1 = resourceMonitor.copyZ();
        int[] B1 = resourceMonitor.copyB();
        int[][] MR1 = resourceMonitor.copyMR();
        // Обчислення min(B*MZ + Z*(MR*MX)) для H частини
        int a1 = min(calculate(Z1, B1, MR1, 0, H), 0, H);
        // Обчислення а =  min(а, а1)
        resourceMonitor.min(resourceMonitor.get_a(), a1);
        // Чекати на обчислення a в Т2-Т4
        synchMonitor.waitMin();
        // Вивід а
        resourceMonitor.output_a();
        System.out.format("Executing time: %d\n",System.currentTimeMillis() - start);
        System.out.println("T1 finished");
    }
}
