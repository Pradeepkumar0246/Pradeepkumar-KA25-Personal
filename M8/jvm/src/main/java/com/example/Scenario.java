package com.example;

/**
 * Interface defining the contract for all performance test scenarios.
 * Each scenario must implement the run method with a specified duration.
 */
public interface Scenario {
    /**
     * Executes the scenario for the specified duration.
     * @param durationMs Duration to run the scenario in milliseconds
     * @throws Exception if any error occurs during execution
     */
    void run(long durationMs) throws Exception;
}