package OPP.rgr.pcs_sm;

// Монітор для синхронізації введення та обчислення а = min(a, a_i)
public class SynchMonitor {
    private int flagInput = 0;
    private int flagMin = 0;

    synchronized void signalInput() {
        flagInput++;
        notifyAll();
    }
    // Чекати поки лічильник не буде дорівнювати 4
    synchronized void waitInput() {
        while (flagInput != 4) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void signalMin() {
        flagMin++;
        notifyAll();
    }

    synchronized void waitMin() {
        while (flagMin != 3) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
