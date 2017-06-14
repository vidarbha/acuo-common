package com.acuo.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class SimulationHelper {

    private final Random random;

    public SimulationHelper() {
        random = new Random();
    }

    public double getRandomAmount(Double value) {
        final double nextGaussian = random.nextGaussian();
        double noise = nextGaussian * Math.sqrt(0.2);
        double a = (0.2 * noise);
        return value * (1 + a);
    }

    public boolean getRandomBoolean() {
        final boolean b = random.nextGaussian() < 0.99;
        log.info("random boolean {}", b);
        return b;
    }
}
