package io.ds.search;

import java.util.List;

public class SearchThread extends Thread {

    private int n;
    private int[] range;
    private List<Integer> results;
    private List<Integer> numbers;

    public SearchThread(int n, int[] range, List<Integer> results, List<Integer> numbers) {
        this.n = n;
        this.range = range;
        this.results = results;
        this.numbers = numbers;
    }

    @Override
    public void run() {
        for(int i = range[0]; i <= range[1]; i++) {
            if (numbers.get(i) == n) results.add(i);
        }
    }
}
