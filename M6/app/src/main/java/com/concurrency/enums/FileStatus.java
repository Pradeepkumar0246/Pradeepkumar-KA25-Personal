package com.concurrency.enums;

/**
 * Status of file processing operations
 */
public enum FileStatus {
    /** File is waiting to be processed */
    PENDING,
    
    /** File is currently being processed */
    PROCESSING,
    
    /** File processing completed successfully */
    COMPLETED,
    
    /** File processing failed */
    FAILED
}
