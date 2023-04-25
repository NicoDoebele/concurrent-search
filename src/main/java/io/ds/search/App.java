/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.ds.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class App {

    public static List<Integer> results = Collections.synchronizedList(new ArrayList<Integer>());
    public static List<Integer> numbers = new ArrayList<Integer>();

    public static void main(String[] args) {
        final int N = Integer.parseInt(args[0]);
        final int n = Integer.parseInt(args[1]);
        final int p = Integer.parseInt(args[2]);

        List<SearchThread> threads = new ArrayList<SearchThread>();

        for (int i = 0; i < N; i++) {
            numbers.add(ThreadLocalRandom.current().nextInt(N));
        }

        int searchPerThread = N / p;

        for (int i = 0; i < p; i++) {
            int[] range = new int[2];

            int start = searchPerThread * i;
            int end = start + searchPerThread - 1;

            if (N % p != 0 && i == (p - 1)) {
                end += N % p;
            }

            //System.out.println("Thread " + (i + 1) + " will check indexes " + start + " to " + end + ".");

            range[0] = start;
            range[1] = end;

            SearchThread searchThread = new SearchThread(n, range, results, numbers);

            threads.add(searchThread);
        }

        long start = System.currentTimeMillis();

        for (SearchThread thread : threads) {
            thread.start();
        }

        try {
            for (SearchThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        long end = System.currentTimeMillis();

        long duration = end - start;

        //System.out.println("Numbers array is of length " + N + ".");
        //System.out.println("Looking for the number " + n + " in the numbers array: " + numbers + ".");
        System.out.println("Found " + n + " at the following indexes: " + results + ".");
        System.out.println("The process took " + duration + " milliseconds with " + p + " threads.");
    }
}
