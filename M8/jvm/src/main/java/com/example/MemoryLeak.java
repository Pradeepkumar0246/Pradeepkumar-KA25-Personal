package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Simulates a memory leak by storing objects in a static collection.
 * Objects are never released, demonstrating memory leak behavior.
 */
public class MemoryLeak implements Scenario {
    // Logger for memory leak scenario
    private static final Logger logger = Logger.getLogger(MemoryLeak.class.getName());
    
    // Static list that holds references permanently (causes memory leak)
    private static final List<byte[]> LEAK = new ArrayList<>();

    /**
     * Runs memory leak simulation by adding objects to static collection.
     * @param durationMs Duration to run the test in milliseconds
     * @throws InterruptedException if thread is interrupted during sleep
     */
    @Override
    public void run(long durationMs) throws InterruptedException {
        try {
            // Calculate end time for the scenario
            long end = System.currentTimeMillis() + durationMs;

            logger.info("Starting MemoryLeak scenario for " + durationMs + "ms");

            // Continuously add objects to static list (memory leak)
            while (System.currentTimeMillis() < end) {
                // Allocate 3 MB and add to static list (never garbage collected)
                LEAK.add(new byte[3 * 1024 * 1024]);
                System.out.println("[MemoryLeak] Leaked objects: " + LEAK.size());
                // Pause between allocations
                Thread.sleep(1000);
            }
            
            logger.warning("MemoryLeak completed. Total leaked objects: " + LEAK.size());
            
        } catch (InterruptedException e) {
            // Handle thread interruption
            logger.log(Level.WARNING, "MemoryLeak interrupted", e);
            throw e;
        } catch (OutOfMemoryError e) {
            // Handle out of memory error
            logger.log(Level.SEVERE, "OutOfMemoryError in MemoryLeak", e);
            System.err.println("[MemoryLeak] Out of memory! Leaked objects: " + LEAK.size());
        }
    }
}
