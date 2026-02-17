package com.example;

import java.lang.management.*;

public class JvmInfoPrinter {

    public static void print() {

        Runtime runtime = Runtime.getRuntime();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

        System.out.println("===== JVM INFO =====");
        System.out.println("JVM Name    : " + runtimeBean.getVmName());
        System.out.println("JVM Version : " + runtimeBean.getVmVersion());
        System.out.println("Uptime (ms) : " + runtimeBean.getUptime());

        System.out.println("\n===== HEAP =====");
        System.out.println("Used     : " + memoryBean.getHeapMemoryUsage().getUsed() / 1024 / 1024 + " MB");
        System.out.println("Max      : " + memoryBean.getHeapMemoryUsage().getMax() / 1024 / 1024 + " MB");

        System.out.println("\n===== NON-HEAP =====");
        System.out.println("Used     : " + memoryBean.getNonHeapMemoryUsage().getUsed() / 1024 / 1024 + " MB");

        System.out.println("\n===== THREADS =====");
        System.out.println("Live Threads : " + threadBean.getThreadCount());
        System.out.println("Peak Threads : " + threadBean.getPeakThreadCount());

        System.out.println("\nProcessors : " + runtime.availableProcessors());
    }
}
