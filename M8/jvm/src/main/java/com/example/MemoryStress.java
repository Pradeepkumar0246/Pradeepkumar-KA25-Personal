package com.example;

import java.util.ArrayList;
import java.util.List;

public class MemoryStress implements Scenario {

    @Override
    public void run(long durationMs) throws InterruptedException {

        long end = System.currentTimeMillis() + durationMs;
        List<byte[]> list = new ArrayList<>();

        while (System.currentTimeMillis() < end) {
            list.add(new byte[5 * 1024 * 1024]); // 5 MB
            System.out.println("[MemoryStress] Allocated chunks: " + list.size());
            Thread.sleep(500);
        }
    }
}
