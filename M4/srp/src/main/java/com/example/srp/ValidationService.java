package com.example.srp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service responsible for validating input values.
 * Follows Single Responsibility Principle by handling only validation logic.
 * 
 * @author SRP Demo
 * @version 1.0
 */
public class ValidationService {

    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    /**
     * Validates if the given value is positive (greater than zero).
     * 
     * @param value the integer value to validate
     * @return true if value is positive, false otherwise
     */
    public boolean isValid(int value) {
        logger.debug("Validating value: {}", value);
        
        boolean result = value > 0;
        
        if (result) {
            logger.info("Validation passed for value: {}", value);
        } else {
            logger.warn("Validation failed for value: {} (must be positive)", value);
        }
        
        return result;
    }
    
    /**
     * Validates if the given value is within a specified range.
     * 
     * @param value the integer value to validate
     * @param min minimum allowed value (inclusive)
     * @param max maximum allowed value (inclusive)
     * @return true if value is within range, false otherwise
     */
    public boolean isInRange(int value, int min, int max) {
        if (min > max) {
            logger.error("Invalid range: min ({}) is greater than max ({})", min, max);
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value");
        }
        
        logger.debug("Validating value {} is in range [{}, {}]", value, min, max);
        
        boolean result = value >= min && value <= max;
        
        if (result) {
            logger.info("Range validation passed for value: {} in range [{}, {}]", value, min, max);
        } else {
            logger.warn("Range validation failed for value: {} (not in range [{}, {}])", value, min, max);
        }
        
        return result;
    }
}
