package com.concurrency.notification;

import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;

/**
 * Demonstrates Runnable interface for tasks that don't return a result.
 * Used for fire-and-forget operations like notifications.
 */
public class NotificationTask implements Runnable {

    private static final Logger logger = AppLogger.getLogger(NotificationTask.class);
    private final String message;

    public NotificationTask(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        logger.info("Sending notification: {}", message);
        try {
            Thread.sleep(500);
            logger.info("Notification sent: {}", message);
        } catch (InterruptedException e) {
            logger.error("Notification interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
