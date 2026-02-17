package com.example;

public class CpuStress implements Scenario {

    @Override
    public void run(long durationMs) {

        long end = System.currentTimeMillis() + durationMs;

        while (System.currentTimeMillis() < end) {
            Math.sqrt(System.nanoTime());
        }

        System.out.println("[CpuStress] Finished");
    }
}
