package com.example;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Simulates CPU stress by performing continuous mathematical calculations.
 * Maxes out CPU core to test performance under high CPU load.
 */
public class CpuStress implements Scenario {
    // Logger for CPU stress scenario
    private static final Logger logger = Logger.getLogger(CpuStress.class.getName());

    /**
     * Runs CPU stress test by performing continuous calculations.
     * @param durationMs Duration to run the test in milliseconds
     */
    @Override
    public void run(long durationMs) {
        try {
            // Calculate end time for the scenario
            long end = System.currentTimeMillis() + durationMs;

            logger.info("Starting CpuStress scenario for " + durationMs + "ms");

            // Tight loop performing continuous calculations
            while (System.currentTimeMillis() < end) {
                // Perform CPU-intensive calculation
                Math.sqrt(System.nanoTime());
            }

            System.out.println("[CpuStress] Finished");
            logger.info("CpuStress completed successfully");
            
        } catch (Exception e) {
            // Handle any unexpected errors
            logger.log(Level.SEVERE, "Error in CpuStress", e);
            System.err.println("[CpuStress] Error: " + e.getMessage());
        }
    }
}
