package com.example.srp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service responsible for file operations and data persistence.
 * Follows Single Responsibility Principle by handling only file-related operations.
 * 
 * @author SRP Demo
 * @version 1.0
 */
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    /**
     * Saves the given message to storage.
     * In a real application, this would write to a file or database.
     * 
     * @param message the message to save
     * @throws IllegalArgumentException if message is null or empty
     */
    public void save(String message) {
        if (message == null || message.trim().isEmpty()) {
            logger.error("Attempted to save null or empty message");
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        
        logger.debug("Saving message: {}", message);
        
        // In real app, write to file or database
        System.out.println("Saving result: " + message);
        
        logger.info("Message saved successfully: {} characters", message.length());
    }
    
    /**
     * Saves the given message with a timestamp prefix.
     * 
     * @param message the message to save
     * @throws IllegalArgumentException if message is null or empty
     */
    public void saveWithTimestamp(String message) {
        if (message == null || message.trim().isEmpty()) {
            logger.error("Attempted to save null or empty message with timestamp");
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        
        String timestampedMessage = java.time.LocalDateTime.now() + ": " + message;
        
        logger.debug("Saving timestamped message: {}", timestampedMessage);
        
        System.out.println("Saving result: " + timestampedMessage);
        
        logger.info("Timestamped message saved successfully: {} characters", timestampedMessage.length());
    }
    
    /**
     * Appends a message to existing storage.
     * 
     * @param message the message to append
     * @throws IllegalArgumentException if message is null or empty
     */
    public void append(String message) {
        if (message == null || message.trim().isEmpty()) {
            logger.error("Attempted to append null or empty message");
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        
        logger.debug("Appending message: {}", message);
        
        // In real app, append to file or database
        System.out.println("Appending result: " + message);
        
        logger.info("Message appended successfully: {} characters", message.length());
    }
}
