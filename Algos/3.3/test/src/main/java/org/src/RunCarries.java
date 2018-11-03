package org.src;
public class RunCarries implements Runnable {
    int from;
    int to;
    int[] num;
    int sum = 0;
    int[] num2;
    Carry[] carries;
    Carry carry_sum = Carry.M;
    Carry total;

    public RunCarries(int from, int to, int[] num, int[] num2, Carry[] carries) {
        this.from = from;
        this.to = to;
        this.num = num;
        this.num2 = num2;
        this.carries = carries;
    }

    public static Carry getCarry_sum(Carry fst, Carry snd) {
        if (snd != Carry.M) {
            return snd;
        } else {
            return fst;
        }

    }

    @Override
    public void run() {
        for (int i = from; i < to; i++) {
            sum = num[i] + num2[i];
            if (sum < 9) {
                carries[i] = Carry.N;
            } else if (sum == 9) {
                carries[i] = Carry.M;
            } else {
                carries[i] = Carry.C;
            }
            carry_sum = getCarry_sum(carry_sum, carries[i]);

        }
        Main.prefixes[from/Main.step] = carry_sum;
        synchronized (Main.sync) {
            Main.countedPrefixes++;
            if (Main.countedPrefixes == Main.numberOfThreads) {
                Tree head = new Tree(1, Main.numberOfThreads);
                head.create_tree(Main.prefixes, 1, Main.numberOfThreads);
                head.upsweep();
                head.downsweep(Carry.M);
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

        if (from != 0) {
            total = Main.prefixes[from / Main.step];
        } else {
            total = Carry.N;
        }

        carries[from] = getCarry_sum(total, carries[from]);
        for (int i = from + 1; i < to; i++) {
            carries[i] = getCarry_sum(carries[i - 1], carries[i]);
        }
        synchronized (Main.sync) {
            Main.countedPrefixes++;
            if (Main.countedPrefixes == Main.numberOfThreads) {
                Main.sync.notifyAll();
            } else {
                try {
                    Main.sync.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = from; i < to; i++) {
            if (i != 0) {
                Main.result[i] = (num[i] + num2[i] + (Main.carries[i - 1] == Carry.C ? 1 : 0)) % 10;
            } else {
                Main.result[i] = (num[i] + num2[i]) % 10;
            }
        }


    }

}
