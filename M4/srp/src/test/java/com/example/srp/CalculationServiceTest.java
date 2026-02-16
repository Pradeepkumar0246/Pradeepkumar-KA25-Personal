package com.example.srp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for CalculationService.
 * Tests both positive and negative scenarios.
 */
class CalculationServiceTest {

    private CalculationService calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationService();
    }

    // Positive test cases for calculateSquare()
    @Test
    @DisplayName("Should calculate square of positive numbers correctly")
    void shouldCalculateSquareOfPositiveNumbersCorrectly() {
        assertEquals(1, calculationService.calculateSquare(1));
        assertEquals(4, calculationService.calculateSquare(2));
        assertEquals(25, calculationService.calculateSquare(5));
        assertEquals(100, calculationService.calculateSquare(10));
    }

    @Test
    @DisplayName("Should calculate square of zero correctly")
    void shouldCalculateSquareOfZeroCorrectly() {
        assertEquals(0, calculationService.calculateSquare(0));
    }

    @Test
    @DisplayName("Should calculate square of negative numbers correctly")
    void shouldCalculateSquareOfNegativeNumbersCorrectly() {
        assertEquals(1, calculationService.calculateSquare(-1));
        assertEquals(4, calculationService.calculateSquare(-2));
        assertEquals(25, calculationService.calculateSquare(-5));
        assertEquals(100, calculationService.calculateSquare(-10));
    }

    @Test
    @DisplayName("Should handle large numbers within safe range")
    void shouldHandleLargeNumbersWithinSafeRange() {
        assertEquals(10000, calculationService.calculateSquare(100));
        assertEquals(1000000, calculationService.calculateSquare(1000));
    }

    // Negative test cases for calculateSquare()
    @Test
    @DisplayName("Should throw exception for positive overflow")
    void shouldThrowExceptionForPositiveOverflow() {
        int largeValue = 50000; // This will cause overflow when squared
        
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculationService.calculateSquare(largeValue)
        );
        
        assertEquals("Square calculation would cause integer overflow", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative overflow")
    void shouldThrowExceptionForNegativeOverflow() {
        int largeNegativeValue = -50000; // This will cause overflow when squared
        
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculationService.calculateSquare(largeNegativeValue)
        );
        
        assertEquals("Square calculation would cause integer overflow", exception.getMessage());
    }

    // Positive test cases for calculateCube()
    @Test
    @DisplayName("Should calculate cube of positive numbers correctly")
    void shouldCalculateCubeOfPositiveNumbersCorrectly() {
        assertEquals(1L, calculationService.calculateCube(1));
        assertEquals(8L, calculationService.calculateCube(2));
        assertEquals(125L, calculationService.calculateCube(5));
        assertEquals(1000L, calculationService.calculateCube(10));
    }

    @Test
    @DisplayName("Should calculate cube of zero correctly")
    void shouldCalculateCubeOfZeroCorrectly() {
        assertEquals(0L, calculationService.calculateCube(0));
    }

    @Test
    @DisplayName("Should calculate cube of negative numbers correctly")
    void shouldCalculateCubeOfNegativeNumbersCorrectly() {
        assertEquals(-1L, calculationService.calculateCube(-1));
        assertEquals(-8L, calculationService.calculateCube(-2));
        assertEquals(-125L, calculationService.calculateCube(-5));
        assertEquals(-1000L, calculationService.calculateCube(-10));
    }

    @Test
    @DisplayName("Should handle large numbers for cube calculation")
    void shouldHandleLargeNumbersForCubeCalculation() {
        assertEquals(1000000L, calculationService.calculateCube(100));
        assertEquals(1000000000L, calculationService.calculateCube(1000));
    }

    // Positive test cases for calculatePower()
    @Test
    @DisplayName("Should calculate power correctly for positive base and exponent")
    void shouldCalculatePowerCorrectlyForPositiveBaseAndExponent() {
        assertEquals(1L, calculationService.calculatePower(2, 0)); // Any number to power 0 is 1
        assertEquals(2L, calculationService.calculatePower(2, 1));
        assertEquals(4L, calculationService.calculatePower(2, 2));
        assertEquals(8L, calculationService.calculatePower(2, 3));
        assertEquals(32L, calculationService.calculatePower(2, 5));
    }

    @Test
    @DisplayName("Should calculate power correctly for negative base")
    void shouldCalculatePowerCorrectlyForNegativeBase() {
        assertEquals(1L, calculationService.calculatePower(-2, 0));
        assertEquals(-2L, calculationService.calculatePower(-2, 1));
        assertEquals(4L, calculationService.calculatePower(-2, 2)); // Negative * Negative = Positive
        assertEquals(-8L, calculationService.calculatePower(-2, 3)); // Negative * Positive = Negative
        assertEquals(16L, calculationService.calculatePower(-2, 4));
    }

    @Test
    @DisplayName("Should calculate power correctly for zero base")
    void shouldCalculatePowerCorrectlyForZeroBase() {
        assertEquals(1L, calculationService.calculatePower(0, 0)); // 0^0 = 1 by convention
        assertEquals(0L, calculationService.calculatePower(0, 1));
        assertEquals(0L, calculationService.calculatePower(0, 5));
    }

    @Test
    @DisplayName("Should calculate power correctly for base 1")
    void shouldCalculatePowerCorrectlyForBaseOne() {
        assertEquals(1L, calculationService.calculatePower(1, 0));
        assertEquals(1L, calculationService.calculatePower(1, 1));
        assertEquals(1L, calculationService.calculatePower(1, 100));
    }

    @Test
    @DisplayName("Should handle large exponents")
    void shouldHandleLargeExponents() {
        assertEquals(1024L, calculationService.calculatePower(2, 10));
        assertEquals(1L, calculationService.calculatePower(1, 1000));
    }

    // Negative test cases for calculatePower()
    @Test
    @DisplayName("Should throw exception for negative exponent")
    void shouldThrowExceptionForNegativeExponent() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculationService.calculatePower(2, -1)
        );
        
        assertEquals("Exponent must be non-negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for very negative exponent")
    void shouldThrowExceptionForVeryNegativeExponent() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculationService.calculatePower(5, -10)
        );
        
        assertEquals("Exponent must be non-negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for minimum integer exponent")
    void shouldThrowExceptionForMinimumIntegerExponent() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculationService.calculatePower(2, Integer.MIN_VALUE)
        );
        
        assertEquals("Exponent must be non-negative", exception.getMessage());
    }

    // Edge cases
    @Test
    @DisplayName("Should handle boundary values correctly")
    void shouldHandleBoundaryValuesCorrectly() {
        // Test with maximum safe integer for square
        int maxSafeForSquare = (int) Math.sqrt(Integer.MAX_VALUE);
        assertDoesNotThrow(() -> calculationService.calculateSquare(maxSafeForSquare));
        
        // Test with maximum integer values for cube (using long return type)
        assertDoesNotThrow(() -> calculationService.calculateCube(Integer.MAX_VALUE));
        assertDoesNotThrow(() -> calculationService.calculateCube(Integer.MIN_VALUE));
    }

    @Test
    @DisplayName("Should maintain precision for calculations")
    void shouldMaintainPrecisionForCalculations() {
        // Test precision with specific values
        assertEquals(144, calculationService.calculateSquare(12));
        assertEquals(1728L, calculationService.calculateCube(12));
        assertEquals(144L, calculationService.calculatePower(12, 2));
        assertEquals(1728L, calculationService.calculatePower(12, 3));
    }
}