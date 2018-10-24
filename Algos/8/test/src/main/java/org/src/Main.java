package org.src;
public class Main {
    public static int NUMBER_OF_THREADS = 4;
    public static int NUMBER_OF_PAIRS = 4;
    public static Pair[] pairs;
    public static volatile Pair[] ress;
    public static volatile int result;

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        ress = new Pair[NUMBER_OF_THREADS];
        pairs = new Pair[]{new Pair(0, 5), new Pair(1, 2), new Pair(3, 4), new Pair(2, 6)};
        int step = NUMBER_OF_PAIRS / NUMBER_OF_THREADS;
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            if (i == NUMBER_OF_THREADS - 1) {
                threads[i] = new Thread(new Run(i * step, NUMBER_OF_PAIRS, i));
            } else {
                threads[i] = new Thread(new Run(i * step, (i + 1) * step, i));
            }
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Tree tree = new Tree(1, NUMBER_OF_THREADS);
        tree.create_tree(1, NUMBER_OF_THREADS);
        result = tree.upsweep().b;
//        System.out.println(result);


    }
}
