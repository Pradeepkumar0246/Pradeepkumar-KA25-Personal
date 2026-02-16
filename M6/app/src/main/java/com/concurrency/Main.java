package com.concurrency;

import java.util.Arrays;
import java.util.List;
import com.concurrency.async.AsyncOrderService;
import com.concurrency.cache.ThreadSafeCache;
import com.concurrency.file.ParallelFileProcessor;
import com.concurrency.notification.NotificationTask;
import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;

/**
 * Main application demonstrating concurrency features:
 * - Parallel file processing
 * - Thread-safe caching
 * - Async order processing
 * - Runnable tasks
 */
public class Main {
    
    private static final Logger logger = AppLogger.getLogger(Main.class); 
    
    public static void main(String[] args) {
        try {
            logger.info("Starting concurrency application");
            
            // 1. Parallel File Processing
            List<String> files = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
            ParallelFileProcessor processor = new ParallelFileProcessor();
            processor.processFiles(files);
            
            // 2. Thread-safe Cache
            ThreadSafeCache cache = new ThreadSafeCache();
            cache.put("user1", "Pradeep");
            cache.put("user2", "Kumar");
            logger.info("Cache value: {}", cache.get("user1"));
            
            // 3. Async Order Processing
            AsyncOrderService orderService = new AsyncOrderService();
            orderService.processOrder();
            
            // 4. Runnable Task
            Thread notificationThread = new Thread(new NotificationTask("Order completed"));
            notificationThread.start();
            notificationThread.join();
            
            logger.info("Application completed successfully");
            
        } catch (Exception e) {
            logger.error("Application failed: {}", e.getMessage(), e);
        }
    }
}
