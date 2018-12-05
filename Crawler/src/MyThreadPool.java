import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool implements Executor {
    Thread[] threads;
    PoolTask[] runs;
    int number_of_threads;
    volatile boolean isRunning;
    private MyConcurrentQueue<Runnable> q;
    private Lock lock = new ReentrantLock();

    public MyThreadPool(int number_of_threads, MyConcurrentQueue<Runnable> q) {
        this.number_of_threads = number_of_threads;
        this.q = q;
        threads = new Thread[number_of_threads];
        runs = new PoolTask[number_of_threads];
        isRunning = true;
        for (int i = 0; i < number_of_threads; i++) {
            runs[i] = new PoolTask();
            threads[i] = new Thread(runs[i]);
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (isRunning) {
            q.offer(runnable);
        }

    }

    public void shutdown() {
        isRunning = false;
    }

    public int getActiveCount() {
        int activeThreads = 0;
        for (PoolTask r : runs) {
            if (r.isActive) {
                activeThreads++;
            }
        }
        return activeThreads;
    }

    private class PoolTask implements Runnable {
        boolean isActive = false;

        @Override
        public void run() {
            while (isRunning) {
                Runnable elem = q.poll();
                if (elem != null) {
                    isActive = true;
                    elem.run();
                    lock.lock();
                    if (Main.pool.getActiveCount() == 1 && q.isEmpty()) {
                        Main.pool.shutdown();
                    }
                    isActive = false;
                    lock.unlock();
                }
            }
        }
    }


}
