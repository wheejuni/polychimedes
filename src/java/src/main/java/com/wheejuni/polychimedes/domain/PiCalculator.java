package com.wheejuni.polychimedes.domain;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class PiCalculator implements Runnable {

    private final CountDownLatch latch;
    private final List<Double> results;
    private final int size;
    private final int count;

    public PiCalculator(CountDownLatch latch, List<Double> results, int size, int count) {
        this.latch = latch;
        this.results = results;
        this.size = size;
        this.count = count;
    }

    @Override
    public void run() {
        Random randomGenerator = new Random();
        double hitCount = 0.0;

        for(int i = 0; i < count; i++) {
            int xPos = randomGenerator.nextInt(size);
            int yPos = randomGenerator.nextInt(size);

            double distanceFromOrigin = Math.sqrt((Math.pow(xPos, 2.0) + Math.pow(yPos, 2.0)));

            if(distanceFromOrigin <= size) {
                hitCount++;
            }
        }

        results.add((hitCount / (double) count));
        latch.countDown();
    }
}
