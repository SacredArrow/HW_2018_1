import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {

        StringBuilder str = new StringBuilder();
        ReentrantLock locker = new ReentrantLock();

        Thread t1 = new Thread(new Runnable()
        {

            @Override
            public void run() {
                locker.lock();
                try {
                    for (int i = 0; i < 1000; i++)
                        str.append("Thread1 here ");
                } catch (Exception e) {
                    throw e;
                }
                finally {
                    locker.unlock();
                }
            }
        });

        Thread t2 = new Thread(new Runnable()
        {

            @Override
            public void run() {
                locker.lock();
                try {
                    for (int i = 0; i < 1000; i++)
                        str.append("Thread2 here ");
                } catch (Exception e) {
                    throw e;
                }
                finally {
                    locker.unlock();
                }
            }
        });
        t1.start();
        t2.start();
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str.toString().replace("Thread1 here", "").replace("Thread2 here", ""));
    }
}

