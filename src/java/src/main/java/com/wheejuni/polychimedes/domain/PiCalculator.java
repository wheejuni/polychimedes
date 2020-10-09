package com.wheejuni.polychimedes.domain;

import java.util.List;
import java.util.Random;

public class PiCalculator implements Runnable {

    private final List<Double> results;
    private final int size;
    private final int count;

    public PiCalculator(List<Double> results, int size, int count) {
        this.results = results;
        this.size = size;
        this.count = count;
    }

    @Override
    public void run() {
        Random randomGenerator = new Random();
        int hitCount = 0;

        for(int i = 0; i < count; i++) {
            int xPos = randomGenerator.nextInt(size);
            int yPos = randomGenerator.nextInt(size);

            double distanceFromOrigin = Math.sqrt((Math.pow(xPos, 2.0) + Math.pow(yPos, 2.0)));

            if(distanceFromOrigin <= size) {
                hitCount++;
            }
        }

        results.add((double)(hitCount / count));
    }
}
