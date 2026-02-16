package com.concurrency.util;

import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;

/**
 * Utility class for thread operations
 */
public class ThreadUtil {
    
    private static final Logger logger = AppLogger.getLogger(ThreadUtil.class);
    
    /**
     * Sleeps for specified milliseconds with proper interrupt handling
     * @param milliseconds time to sleep
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Thread sleep interrupted");
            Thread.currentThread().interrupt();
        }
    }
}