package com.example;

import com.example.srp.ValidationService;
import com.example.srp.CalculationService;
import com.example.srp.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Integration test class for the complete SRP demonstration system.
 * Tests end-to-end workflows and service integration following SRP principles.
 */
class SRPSystemIntegrationTest {

    private ValidationService validationService;
    private CalculationService calculationService;
    private FileService fileService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        validationService = new ValidationService();
        calculationService = new CalculationService();
        fileService = new FileService();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should handle complete SRP workflow for valid input")
    void shouldHandleCompleteSRPWorkflowForValidInput() {
        int value = 5;
        
        // Step 1: Validation
        assertTrue(validationService.isValid(value));
        
        // Step 2: Calculation
        int squareResult = calculationService.calculateSquare(value);
        assertEquals(25, squareResult);
        
        // Step 3: File operations
        String message = "Square of " + value + " is: " + squareResult;
        assertDoesNotThrow(() -> fileService.save(message));
        
        // Verify output
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + message));
    }

    @Test
    @DisplayName("Should handle workflow with invalid input")
    void shouldHandleWorkflowWithInvalidInput() {
        int invalidValue = -5;
        
        // Step 1: Validation should fail
        assertFalse(validationService.isValid(invalidValue));
        
        // Step 2: Should still be able to calculate (business logic decision)
        int squareResult = calculationService.calculateSquare(invalidValue);
        assertEquals(25, squareResult); // (-5)^2 = 25
        
        // Step 3: Should still be able to save
        String message = "Square of " + invalidValue + " is: " + squareResult;
        assertDoesNotThrow(() -> fileService.save(message));
    }

    @Test
    @DisplayName("Should demonstrate SRP with multiple operations")
    void shouldDemonstrateSRPWithMultipleOperations() {
        int[] testValues = {3, 4, 5};
        
        for (int value : testValues) {
            // Each service handles its single responsibility
            boolean isValid = validationService.isValid(value);
            assertTrue(isValid);
            
            boolean isInRange = validationService.isInRange(value, 1, 10);
            assertTrue(isInRange);
            
            int square = calculationService.calculateSquare(value);
            long cube = calculationService.calculateCube(value);
            long power = calculationService.calculatePower(value, 2);
            
            assertEquals(square, power); // square should equal power of 2
            
            // Save results using different methods
            fileService.save("Square: " + square);
            fileService.append("Cube: " + cube);
            fileService.saveWithTimestamp("Power: " + power);
        }
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Square: 9"));
        assertTrue(output.contains("Cube: 27"));
        assertTrue(output.contains("Power: 9"));
    }

    @Test
    @DisplayName("Should handle edge cases across all services")
    void shouldHandleEdgeCasesAcrossAllServices() {
        // Test with zero
        int zero = 0;
        assertFalse(validationService.isValid(zero));
        assertEquals(0, calculationService.calculateSquare(zero));
        assertDoesNotThrow(() -> fileService.save("Zero result: 0"));
        
        // Test with boundary values
        assertTrue(validationService.isInRange(1, 1, 1));
        assertEquals(1, calculationService.calculateSquare(1));
        assertEquals(1L, calculationService.calculateCube(1));
        assertEquals(1L, calculationService.calculatePower(1, 100));
        
        assertDoesNotThrow(() -> fileService.saveWithTimestamp("Boundary test completed"));
    }

    @Test
    @DisplayName("Should maintain service independence")
    void shouldMaintainServiceIndependence() {
        // Each service should work independently
        
        // Validation service works alone
        assertTrue(validationService.isValid(10));
        assertFalse(validationService.isValid(-10));
        
        // Calculation service works alone
        assertEquals(100, calculationService.calculateSquare(10));
        assertEquals(-1000L, calculationService.calculateCube(-10));
        
        // File service works alone
        assertDoesNotThrow(() -> fileService.save("Independent test"));
        assertDoesNotThrow(() -> fileService.append("More data"));
        
        // Services don't depend on each other's state
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Independent test"));
        assertTrue(output.contains("More data"));
    }

    @Test
    @DisplayName("Should handle error scenarios gracefully")
    void shouldHandleErrorScenariosGracefully() {
        // Validation service error handling
        assertThrows(IllegalArgumentException.class, 
            () -> validationService.isInRange(5, 10, 1));
        
        // Calculation service error handling
        assertThrows(ArithmeticException.class, 
            () -> calculationService.calculateSquare(50000));
        
        assertThrows(IllegalArgumentException.class, 
            () -> calculationService.calculatePower(2, -1));
        
        // File service error handling
        assertThrows(IllegalArgumentException.class, 
            () -> fileService.save(null));
        
        assertThrows(IllegalArgumentException.class, 
            () -> fileService.append(""));
        
        assertThrows(IllegalArgumentException.class, 
            () -> fileService.saveWithTimestamp("   "));
    }

    @Test
    @DisplayName("Should demonstrate proper separation of concerns")
    void shouldDemonstrateSeparationOfConcerns() {
        int value = 7;
        
        // Validation concern: Is the input valid?
        boolean isPositive = validationService.isValid(value);
        boolean isInSmallRange = validationService.isInRange(value, 1, 10);
        boolean isInLargeRange = validationService.isInRange(value, 1, 100);
        
        assertTrue(isPositive);
        assertTrue(isInSmallRange);
        assertTrue(isInLargeRange);
        
        // Calculation concern: What are the mathematical results?
        int square = calculationService.calculateSquare(value);
        long cube = calculationService.calculateCube(value);
        long powerOf3 = calculationService.calculatePower(value, 3);
        
        assertEquals(49, square);
        assertEquals(343L, cube);
        assertEquals(343L, powerOf3);
        
        // Persistence concern: How do we store the results?
        assertDoesNotThrow(() -> {
            fileService.save("Validation results: positive=" + isPositive);
            fileService.append("Calculation results: square=" + square);
            fileService.saveWithTimestamp("Final results: cube=" + cube);
        });
        
        // Verify all operations completed successfully
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("positive=true"));
        assertTrue(output.contains("square=49"));
        assertTrue(output.contains("cube=343"));
    }

    @Test
    @DisplayName("Should handle complex workflow with multiple validations")
    void shouldHandleComplexWorkflowWithMultipleValidations() {
        int[] values = {-2, 0, 3, 15, 100};
        
        for (int value : values) {
            boolean isPositive = validationService.isValid(value);
            boolean isInRange = validationService.isInRange(value, 1, 50);
            
            if (isPositive && isInRange) {
                // Only process valid values in range
                int square = calculationService.calculateSquare(value);
                long cube = calculationService.calculateCube(value);
                
                fileService.save("Valid processing: " + value + " -> square=" + square);
                fileService.append("Cube result: " + cube);
            } else {
                // Log invalid values
                String reason = !isPositive ? "not positive" : "out of range";
                fileService.saveWithTimestamp("Skipped " + value + ": " + reason);
            }
        }
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Valid processing: 3"));
        assertTrue(output.contains("Valid processing: 15"));
        assertTrue(output.contains("Skipped -2: not positive"));
        assertTrue(output.contains("Skipped 0: not positive"));
        assertTrue(output.contains("Skipped 100: out of range"));
    }
}