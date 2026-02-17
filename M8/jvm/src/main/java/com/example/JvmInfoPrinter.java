package com.example;

import java.lang.management.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Utility class to print JVM runtime information including memory, threads, and system details.
 */
public class JvmInfoPrinter {
    // Logger for JVM information printing
    private static final Logger logger = Logger.getLogger(JvmInfoPrinter.class.getName());

    /**
     * Prints comprehensive JVM information to console.
     * Includes JVM details, heap/non-heap memory usage, thread counts, and processor info.
     */
    public static void print() {
        try {
            // Get JVM management beans for monitoring
            Runtime runtime = Runtime.getRuntime();
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

            // Print JVM basic information
            System.out.println("===== JVM INFO =====");
            System.out.println("JVM Name    : " + runtimeBean.getVmName());
            System.out.println("JVM Version : " + runtimeBean.getVmVersion());
            System.out.println("Uptime (ms) : " + runtimeBean.getUptime());

            // Print heap memory usage
            System.out.println("\n===== HEAP =====");
            System.out.println("Used     : " + memoryBean.getHeapMemoryUsage().getUsed() / 1024 / 1024 + " MB");
            System.out.println("Max      : " + memoryBean.getHeapMemoryUsage().getMax() / 1024 / 1024 + " MB");

            // Print non-heap memory usage
            System.out.println("\n===== NON-HEAP =====");
            System.out.println("Used     : " + memoryBean.getNonHeapMemoryUsage().getUsed() / 1024 / 1024 + " MB");

            // Print thread information
            System.out.println("\n===== THREADS =====");
            System.out.println("Live Threads : " + threadBean.getThreadCount());
            System.out.println("Peak Threads : " + threadBean.getPeakThreadCount());

            // Print processor count
            System.out.println("\nProcessors : " + runtime.availableProcessors());
            
            logger.info("JVM information printed successfully");
            
        } catch (Exception e) {
            // Handle any errors during JVM info retrieval
            logger.log(Level.SEVERE, "Error printing JVM information", e);
            System.err.println("Failed to retrieve JVM information: " + e.getMessage());
        }
    }
}
