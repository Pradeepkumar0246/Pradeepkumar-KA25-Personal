package com.example;

import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Main entry point for JVM Performance Playground.
 * Orchestrates execution of memory and CPU stress test scenarios.
 */
public class Main {
    // Logger instance for application-wide logging
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Main method that runs all performance test scenarios sequentially.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Duration for each scenario in milliseconds
        long duration = 15_000; // 15 seconds per scenario

        try {
            logger.info("Starting JVM Performance Playground");
            System.out.println("===== JVM PERFORMANCE PLAYGROUND =====");

            // Display initial JVM information
            logger.info("Printing JVM information");
            JvmInfoPrinter.print();
            Thread.sleep(3000);

            // Run Memory Stress scenario
            logger.info("Starting MemoryStress scenario");
            new MemoryStress().run(duration);
            logger.info("MemoryStress scenario completed");
            Thread.sleep(3000);

            // Run Memory Leak scenario
            logger.info("Starting MemoryLeak scenario");
            new MemoryLeak().run(duration);
            logger.info("MemoryLeak scenario completed");
            Thread.sleep(3000);

            // Run CPU Stress scenario
            logger.info("Starting CpuStress scenario");
            new CpuStress().run(duration);
            logger.info("CpuStress scenario completed");

            System.out.println("===== ALL SCENARIOS COMPLETED =====");
            logger.info("All scenarios completed successfully");

        } catch (InterruptedException e) {
            // Handle thread interruption
            logger.log(Level.SEVERE, "Scenario execution interrupted", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            logger.log(Level.SEVERE, "Unexpected error during scenario execution", e);
            System.err.println("Error: " + e.getMessage());
        }
    }
}
