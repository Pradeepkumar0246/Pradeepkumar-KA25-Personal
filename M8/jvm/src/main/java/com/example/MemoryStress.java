package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Simulates memory stress by allocating large byte arrays repeatedly.
 * Tests garbage collection and heap management under memory pressure.
 */
public class MemoryStress implements Scenario {
    // Logger for memory stress scenario
    private static final Logger logger = Logger.getLogger(MemoryStress.class.getName());

    /**
     * Runs memory stress test by allocating 5MB chunks repeatedly.
     * @param durationMs Duration to run the test in milliseconds
     * @throws InterruptedException if thread is interrupted during sleep
     */
    @Override
    public void run(long durationMs) throws InterruptedException {
        try {
            // Calculate end time for the scenario
            long end = System.currentTimeMillis() + durationMs;
            // List to hold allocated memory chunks
            List<byte[]> list = new ArrayList<>();

            logger.info("Starting MemoryStress scenario for " + durationMs + "ms");

            // Continuously allocate memory until duration expires
            while (System.currentTimeMillis() < end) {
                // Allocate 5 MB byte array
                list.add(new byte[5 * 1024 * 1024]);
                System.out.println("[MemoryStress] Allocated chunks: " + list.size());
                // Pause between allocations
                Thread.sleep(500);
            }
            
            logger.info("MemoryStress completed. Total chunks allocated: " + list.size());
            
        } catch (InterruptedException e) {
            // Handle thread interruption
            logger.log(Level.WARNING, "MemoryStress interrupted", e);
            throw e;
        } catch (OutOfMemoryError e) {
            // Handle out of memory error
            logger.log(Level.SEVERE, "OutOfMemoryError in MemoryStress", e);
            System.err.println("[MemoryStress] Out of memory!");
        }
    }
}
