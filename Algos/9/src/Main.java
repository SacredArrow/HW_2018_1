import static java.lang.System.exit;

public class Main {
    public static final Object sync = new Object();
    public static int sizeOfAString;
    public static int numberOfThreads = 4;
    public static volatile int[] prefixes;
    public static int[] intArray;
    public static int countedPrefixes = 0;
    static int step;

    public static void main(String[] args) {
        String str1 = "(()))(()";
        sizeOfAString = str1.length();
        str1 = str1.replace('(', '1').replace(')', '0');
        String[] arr = str1.split("");
        intArray = new int[arr.length];
        prefixes = new int[numberOfThreads];
        for (int i = 0; i < arr.length; i++) {
            intArray[i] = Integer.parseInt(arr[i]);
            if (intArray[i] == 0) {
                intArray[i] = - 1;
            }
        }
        Thread[] threads = new Thread[numberOfThreads];
        step = sizeOfAString / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            if (i == numberOfThreads - 1) {
                threads[i] = new Thread(new Run(i * step, sizeOfAString, i));
            } else {
                threads[i] = new Thread(new Run(i * step, (i + 1) * step, i));
            }
            threads[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < sizeOfAString; i++) {
            if (intArray[i] < 0) {
                System.out.println("Mismatch brackets on " + i);
                exit(1);
            }
        }
        if (intArray[sizeOfAString - 1] != 0) {
            System.out.println("Mismatch brackets in the end");
            exit(2);
        }
        System.out.println("Brackets match!");
        exit(0);
    }
}
