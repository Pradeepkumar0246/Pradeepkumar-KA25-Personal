package com.example;

import com.example.srp.ValidationService;
import com.example.srp.CalculationService;
import com.example.srp.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class demonstrating the Single Responsibility Principle (SRP).
 * Each service class has a single, well-defined responsibility:
 * - ValidationService: Input validation
 * - CalculationService: Mathematical calculations
 * - FileService: Data persistence
 * 
 * @author SRP Demo
 * @version 1.0
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting SRP demonstration");
        
        try {
            // Initialize services following SRP
            ValidationService validationService = new ValidationService();
            CalculationService calculationService = new CalculationService();
            FileService fileService = new FileService();
            
            logger.info("Services initialized successfully");

            // Test values
            int[] testValues = {5, -3, 0, 10};
            
            for (int value : testValues) {
                logger.debug("Processing value: {}", value);
                
                if (validationService.isValid(value)) {
                    int squareResult = calculationService.calculateSquare(value);
                    long cubeResult = calculationService.calculateCube(value);
                    
                    fileService.save("Square of " + value + " is: " + squareResult);
                    fileService.saveWithTimestamp("Cube of " + value + " is: " + cubeResult);
                    
                    // Test range validation
                    if (validationService.isInRange(value, 1, 100)) {
                        long powerResult = calculationService.calculatePower(value, 3);
                        fileService.append("Power calculation: " + value + "^3 = " + powerResult);
                    }
                } else {
                    logger.warn("Skipping invalid value: {}", value);
                    System.out.println("Invalid value: " + value);
                }
            }
            
            logger.info("SRP demonstration completed successfully");
            
        } catch (Exception e) {
            logger.error("Error occurred during SRP demonstration", e);
            System.err.println("An error occurred: " + e.getMessage());
        }
        
        logger.info("SRP demonstration shutdown");
    }
}
