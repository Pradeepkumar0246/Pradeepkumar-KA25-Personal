package com.concurrency.file;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import com.concurrency.enums.FileStatus;
import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;

/**
 * Processes multiple files in parallel using ExecutorService.
 * Demonstrates:
 * - ExecutorService
 * - Callable
 * - Future
 * - Parallel execution
 */
public class ParallelFileProcessor {

    private static final Logger logger = AppLogger.getLogger(ParallelFileProcessor.class);
    private static final int THREAD_POOL_SIZE = 3;

    /**
     * Processes a list of files in parallel
     * @param files list of file names to process
     * @throws Exception if processing fails
     */
    public void processFiles(List<String> files) throws Exception {
        if (files == null || files.isEmpty()) {
            logger.warn("No files provided for processing");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<FileStatus>> results = new ArrayList<>();

        try {
            // Submit all tasks
            for (String file : files) {
                FileTask task = new FileTask(file);
                results.add(executor.submit(task));
            }

            // Collect results
            for (Future<FileStatus> future : results) {
                FileStatus status = future.get();
                logger.info("File processed with status: {}", status);
            }
            
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
