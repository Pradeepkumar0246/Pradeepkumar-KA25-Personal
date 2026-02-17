package com.example;
public class Main {

    public static void main(String[] args) throws Exception {

        long duration = 15_000; // 15 seconds per scenario

        System.out.println("===== JVM PERFORMANCE PLAYGROUND =====");

        JvmInfoPrinter.print();
        Thread.sleep(3000);

        new MemoryStress().run(duration);
        Thread.sleep(3000);

        new MemoryLeak().run(duration);
        Thread.sleep(3000);

        new CpuStress().run(duration);

        System.out.println("===== ALL SCENARIOS COMPLETED =====");
    }
}
