public class Main {

    public static void main(String[] args) {

        StringBuilder str = new StringBuilder();


        Thread t1 = new Thread(new Runnable()
        {

            @Override
            public void run() {
                for (int i =0;i<1000;i++)
                    str.append("Thread1 here ");
            }
        });

        Thread t2 = new Thread(new Runnable()
        {
            @Override
            public void run() {
                for (int i =0;i<1000;i++)
                    str.append("Thread2 here ");
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

