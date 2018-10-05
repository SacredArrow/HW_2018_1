package org.src;
import java.util.concurrent.locks.ReentrantLock;

public class Main {



    public static void tasTest() {

        StringBuilder str = new StringBuilder();
        TASLock tas = new TASLock();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    tas.lock();
                    try {
                        str.append("Thread1 here ");
                    } catch (Exception e) {
                        throw e;
                    }
                    finally {
                        tas.unlock();
                    }

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    tas.lock();
                    try {
                        str.append("Thread2 here ");
                    } catch (Exception e) {
                        throw e;
                    }
                    finally {
                        tas.unlock();
                    }

                }
            }
        });
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    tas.lock();
                    try {
                        str.append("Thread3 here ");
                    } catch (Exception e) {
                        throw e;
                    }
                    finally {
                        tas.unlock();
                    }

                }
            }
        });
        Thread t4 = new Thread(new Runnable() {

            @Override
            public void run() {

                    for (int i = 0; i < 1000000; i++) {
                        tas.lock();
                        try {
                            str.append("Thread4 here ");
                        } catch (Exception e) {
                            throw e;
                        }
                        finally {
                            tas.unlock();
                        }

                    }
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
//        System.out.print(str.toString().replace("Thread1 here", "").replace("Thread2 here", ""));
    }

    public static void ttasTest() {

        TTASLock ttas = new TTASLock();
        StringBuilder str = new StringBuilder();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    ttas.lock();
                    try {
                        str.append("Thread1 here ");
                    } catch (Exception e) {
                        throw e;
                    }
                    finally {
                        ttas.unlock();
                    }

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    ttas.lock();
                    try {
                        str.append("Thread2 here ");
                    } catch (Exception e) {
                        throw e;
                    }
                    finally {
                        ttas.unlock();
                    }

                }
            }
        });
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    ttas.lock();
                    try {
                        str.append("Thread3 here ");
                    } catch (Exception e) {
                        throw e;
                    }
                    finally {
                        ttas.unlock();
                    }

                }
            }
        });
        Thread t4 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    ttas.lock();
                    try {
                        str.append("Thread4 here ");
                    } catch (Exception e) {
                        throw e;
                    }
                    finally {
                        ttas.unlock();
                    }

                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
//        System.out.print(str.toString().replace("Thread1 here", "").replace("Thread2 here", ""));
    }

    public static void main(String[] args) {
        tasTest();
        ttasTest();
//        ReentrantLock locker = new ReentrantLock();
//        Peterson peterson = new Peterson();




    }
}

