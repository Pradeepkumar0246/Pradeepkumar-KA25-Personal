package com.concurrency.file;

import java.util.concurrent.Callable;

import com.concurrency.enums.FileStatus;
import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;

/**
 * Represents a file processing task that can be executed in parallel.
 * Uses Callable interface to return processing results.
 */
public class FileTask implements Callable<FileStatus> {

    private static final Logger logger = AppLogger.getLogger(FileTask.class);

    private final String fileName;

    /**
     * Creates a new file processing task
     * @param fileName name of the file to process
     */
    public FileTask(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Processes the file and returns the status
     * @return FileStatus indicating processing result
     * @throws Exception if processing fails
     */
    @Override
    public FileStatus call() throws Exception {
        if (fileName == null || fileName.trim().isEmpty()) {
            logger.error("Invalid file name provided");
            return FileStatus.FAILED;
        }

        logger.info("Processing file: {}", fileName);

        try {
            // Simulate file processing time
            Thread.sleep(1000);
            logger.info("Completed file: {}", fileName);
            return FileStatus.COMPLETED;
            
        } catch (InterruptedException e) {
            logger.error("File processing interrupted: {}", fileName);
            Thread.currentThread().interrupt();
            return FileStatus.FAILED;
        }
    }
}
