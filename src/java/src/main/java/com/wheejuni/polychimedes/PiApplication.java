package com.wheejuni.polychimedes;

import com.wheejuni.polychimedes.domain.PiCalculator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class PiApplication {

    public static void main(String[] args) throws NumberFormatException {
        int size = Integer.parseInt(args[0]);
        int count = Integer.parseInt(args[1]);

        CountDownLatch latch = new CountDownLatch(4);
        List<Double> threadProcessedResult = new CopyOnWriteArrayList<>();

        IntStream.range(0, 4)
                .forEach(i -> {
                    Thread calculateThread = new Thread(new PiCalculator(latch, threadProcessedResult, size / 2, count));
                    calculateThread.start();
                });

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        double result = threadProcessedResult.stream().mapToDouble(Double::valueOf).sum();

        System.out.println("result: " + result);
    }
}
