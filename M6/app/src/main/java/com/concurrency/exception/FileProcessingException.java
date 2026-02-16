package com.concurrency.exception;

/**
 * Exception thrown when file processing fails
 */
public class FileProcessingException extends RuntimeException {

    /**
     * Creates exception with message
     * @param message error description
     */
    public FileProcessingException(String message) {
        super(message);
    }
    
    /**
     * Creates exception with message and cause
     * @param message error description
     * @param cause original exception
     */
    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
