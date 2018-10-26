import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static int sizeOfANumber = 8;
    public static int numberOfThreads = 4;
    public static volatile Carry[] prefixes;
    static volatile Carry[] carries;
    static volatile int countedPrefixes;
    public static final Object sync = new Object();
    static int step;
    static volatile int[] result;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
//        String str1 = "37569867";
//        String str2 = "18913675";
        String str1 = "99999999";
        String str2 = "00000001";
        str1 = new StringBuilder(str1).reverse().toString();
        str2 = new StringBuilder(str2).reverse().toString();
        RunCarries[] runs = new RunCarries[numberOfThreads];
        Thread[] threads = new Thread[numberOfThreads];
        int[] num1 = new int[sizeOfANumber];
        int[] num2 = new int[sizeOfANumber];
        carries = new Carry[sizeOfANumber];
        result = new int[sizeOfANumber];
        prefixes = new Carry[numberOfThreads];
        for (int i = 0; i < str1.length(); i++) {
            num1[i] = str1.charAt(i) - '0';
            num2[i] = str2.charAt(i) - '0';
        }


        for (int i = 0; i < numberOfThreads; i++) {
            step = sizeOfANumber / numberOfThreads;
            if (i == numberOfThreads - 1) {
                runs[i] = new RunCarries(i * step, sizeOfANumber, num1, num2, carries);
            } else {
                runs[i] = new RunCarries(i * step, step * (i + 1), num1, num2, carries);
            }
            threads[i] = new Thread(runs[i]);
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }

//        for (int i = 0; i < Main.sizeOfANumber; i++) {
//            System.out.print(Main.carries[i]);
//        }
//        System.out.println();
        for (int i = sizeOfANumber - 1; i >= 0; i--) {
            System.out.print(result[i]);
        }
//        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//        for (Thread t : threadSet) {
//            t.stop();
//        }
//        System.out.println();
//        for (int i = 0; i < Main.numberOfThreads; i++) {
//            System.out.print(Main.prefixes.get(i));
//        }
    }
}
