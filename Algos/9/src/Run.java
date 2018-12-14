public class Run implements Runnable {
    int from;
    int to;
    int number;

    public Run(int from, int to, int number) {
        this.from = from;
        this.to = to;
        this.number = number;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = from; i < to; i++) {
            sum += Main.intArray[i];
        }


        Main.prefixes[number] = sum;
        synchronized (Main.sync) {
            Main.countedPrefixes++;
            if (Main.countedPrefixes == Main.numberOfThreads) {
                Tree head = new Tree(1, Main.numberOfThreads);
                head.create_tree(1, Main.numberOfThreads);
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
        int total;
        if (from != 0) {
            total = Main.prefixes[number];
        } else {
            total = 0;
        }
        Main.intArray[from] = total + Main.intArray[from];
        for (int i = from + 1; i < to; i++) {
            Main.intArray[i] = Main.intArray[i - 1] + Main.intArray[i];
        }


    }
}
