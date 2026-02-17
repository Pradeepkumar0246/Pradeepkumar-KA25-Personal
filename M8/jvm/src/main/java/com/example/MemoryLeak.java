package com.example;

import java.util.ArrayList;
import java.util.List;

public class MemoryLeak implements Scenario {

    private static final List<byte[]> LEAK = new ArrayList<>();

    @Override
    public void run(long durationMs) throws InterruptedException {

        long end = System.currentTimeMillis() + durationMs;

        while (System.currentTimeMillis() < end) {
            LEAK.add(new byte[3 * 1024 * 1024]); // 3 MB
            System.out.println("[MemoryLeak] Leaked objects: " + LEAK.size());
            Thread.sleep(1000);
        }
    }
}
