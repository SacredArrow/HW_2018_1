package org.src;
public class RunAngles implements Runnable {
    int from;
    int to;
    int number;

    public RunAngles(int from, int to, int number) {
        this.from = from;
        this.to = to;
        this.number = number;
    }

    @Override
    public void run() {
        double sum = 0;
        for (int i = from; i < to; i++) {
            sum += Main.angles[i];
        }

        Main.prefixes[number] = sum;
        synchronized (Main.sync) {
            Main.countedPrefixes++;
            if (Main.countedPrefixes == Main.NUMBER_OF_THREADS) {
                TreeAngle head = new TreeAngle(1, Main.NUMBER_OF_THREADS);
                head.create_tree(1, Main.NUMBER_OF_THREADS);
                head.upsweep();
                head.downsweep(0);
                Main.countedPrefixes = 0;
                Main.sync.notifyAll();
            } else {
                try {
                    Main.sync.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        double total;
        if (from != 0) {
            total = Main.prefixes[number];
        } else {
            total = 0;
        }
        Main.angles[from] = (total + Main.angles[from]) % 360;
        for (int i = from + 1; i < to; i++) {
            Main.angles[i] = (Main.angles[i - 1] + Main.angles[i]) % 360;
        }


    }
}
