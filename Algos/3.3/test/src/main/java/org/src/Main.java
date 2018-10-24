package org.src;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;
public class Main {
    public static int sizeOfANumber = 1024;
    public static int numberOfThreads = 4;
    public static volatile Carry[] prefixes;
    static volatile Carry[] carries;
    static volatile int countedPrefixes;
    static int step;
    static volatile int[] result;
    public static void test () throws InterruptedException {
        System.out.println("point1");
        String str1 = "7075188752686264156726456900154650936222840366053792209577783104871351070843433458680170123497414631058986564904875784817815492629082523213693437279113862307435012450463943666900482360529162822665753872643283115760706561404498173332704087824472504818549521312492948901613245488124004604312604570224443198142482127594506557262035776065772293005644303133084409941281228390268365162465901433810216257005748858338773363231215353480816018264581849658787670072899569171853541831217738371055829231467725516018400414643181869830473336139446138707709974605330536156266771079873971182206173488144389711847853484686858290409937871543637962094272898887743885406187779979156101665971447523412491924147657721389923103654583978941523230323540853081433027502805642296489727898104597925533690699352303234757839401095329512856350562871171612523046452709388053697460882065869582856034231446085502981786209809571895457297814840491271668682584566746798232594261322880045550408782674399735500866895386247879214347843690142195537296226809629385165";
//        String str2 = "18913675";
//        str1 = new StringBuilder(str1).reverse().toString();
//        str2 = new StringBuilder(str2).reverse().toString();
        RunCarries[] runs = new RunCarries[numberOfThreads];
        Thread[] threads = new Thread[numberOfThreads];
        int[] num1 = new int[sizeOfANumber];
        int[] num2 = new int[sizeOfANumber];
        carries = new Carry[sizeOfANumber];
        result = new int[sizeOfANumber];
        prefixes = new Carry[numberOfThreads];
//        for (int i = 0; i < str1.length(); i++) {
//            num1[i] = str1.charAt(i) - '0';
//            num2[i] = str2.charAt(i) - '0';
//        }
        for (int i = 0; i<sizeOfANumber; i++) {
            num1[i] = new Random().nextInt(10);
            num2[i] = new Random().nextInt(10);

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
//        for (int i = sizeOfANumber - 1; i >= 0; i--) {
//            System.out.print(result[i]);
//        }
//        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//        for (Thread t : threadSet) {
//            t.interrupt();
//        }
//        Main.prefixes.notifyAll();
        System.out.println("Finished");
        return;
//        System.out.println();
//        for (int i = 0; i < Main.numberOfThreads; i++) {
//            System.out.print(Main.prefixes.get(i));
//        }
//        return;
    }
    public static void main(String[] args) throws InterruptedException {
    test();
    }
}
