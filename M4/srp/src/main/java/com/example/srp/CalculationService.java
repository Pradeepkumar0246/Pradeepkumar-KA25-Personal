package com.example.srp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service responsible for mathematical calculations.
 * Follows Single Responsibility Principle by handling only calculation logic.
 * 
 * @author SRP Demo
 * @version 1.0
 */
public class CalculationService {

    private static final Logger logger = LoggerFactory.getLogger(CalculationService.class);

    /**
     * Calculates the square of the given value.
     * 
     * @param value the integer value to square
     * @return the square of the input value
     * @throws ArithmeticException if the result would overflow
     */
    public int calculateSquare(int value) {
        logger.debug("Calculating square of value: {}", value);
        
        // Check for potential overflow
        if (value > 0 && value > Integer.MAX_VALUE / value) {
            logger.error("Integer overflow detected for value: {}", value);
            throw new ArithmeticException("Square calculation would cause integer overflow");
        }
        if (value < 0 && value < Integer.MAX_VALUE / value) {
            logger.error("Integer overflow detected for negative value: {}", value);
            throw new ArithmeticException("Square calculation would cause integer overflow");
        }
        
        int result = value * value;
        
        logger.info("Square calculation completed: {} ^ 2 = {}", value, result);
        
        return result;
    }
    
    /**
     * Calculates the cube of the given value.
     * 
     * @param value the integer value to cube
     * @return the cube of the input value
     * @throws ArithmeticException if the result would overflow
     */
    public long calculateCube(int value) {
        logger.debug("Calculating cube of value: {}", value);
        
        long result = (long) value * value * value;
        
        logger.info("Cube calculation completed: {} ^ 3 = {}", value, result);
        
        return result;
    }
    
    /**
     * Calculates the power of a base raised to an exponent.
     * 
     * @param base the base value
     * @param exponent the exponent (must be non-negative)
     * @return the result of base raised to the power of exponent
     * @throws IllegalArgumentException if exponent is negative
     */
    public long calculatePower(int base, int exponent) {
        if (exponent < 0) {
            logger.error("Negative exponent not supported: {}", exponent);
            throw new IllegalArgumentException("Exponent must be non-negative");
        }
        
        logger.debug("Calculating power: {} ^ {}", base, exponent);
        
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        
        logger.info("Power calculation completed: {} ^ {} = {}", base, exponent, result);
        
        return result;
    }
}
