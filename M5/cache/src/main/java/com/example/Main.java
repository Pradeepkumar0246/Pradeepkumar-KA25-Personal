package com.example;

import com.example.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Main class demonstrating the usage of the Cache implementation.
 * This class shows various cache operations including put, get, and size operations.
 * 
 * @author Cache Team
 * @version 1.0
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Main method to demonstrate cache functionality.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        logger.info("Starting cache demonstration");

        try {
            // Create cache instances for different data types
            Cache<String, Integer> scoreCache = new Cache<>();
            Cache<Integer, String> userCache = new Cache<>();

            logger.info("Created cache instances");

            // Demonstrate put operations
            logger.info("Adding entries to score cache");
            scoreCache.put("math", 90);
            scoreCache.put("science", 85);

            logger.info("Adding entries to user cache");
            userCache.put(1, "Pradeep");
            userCache.put(2, "Rahul");

            // Demonstrate get operations with Optional handling
            logger.info("Retrieving values from caches");
            Optional<Integer> mathScore = scoreCache.get("math");
            mathScore.ifPresentOrElse(
                score -> {
                    System.out.println("Math Score: " + score);
                    logger.info("Successfully retrieved math score: {}", score);
                },
                () -> logger.warn("Math score not found")
            );

            Optional<String> user = userCache.get(1);
            user.ifPresentOrElse(
                name -> {
                    System.out.println("User 1: " + name);
                    logger.info("Successfully retrieved user: {}", name);
                },
                () -> logger.warn("User 1 not found")
            );

            // Demonstrate size operations
            System.out.println("Score Cache Size: " + scoreCache.size());
            System.out.println("User Cache Size: " + userCache.size());
            
            logger.info("Cache demonstration completed successfully");
            
        } catch (Exception e) {
            logger.error("Error during cache demonstration: {}", e.getMessage(), e);
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
