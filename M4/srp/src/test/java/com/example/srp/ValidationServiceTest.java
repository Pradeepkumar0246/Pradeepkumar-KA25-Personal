package com.example.srp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for ValidationService.
 * Tests both positive and negative scenarios.
 */
class ValidationServiceTest {

    private ValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new ValidationService();
    }

    // Positive test cases for isValid()
    @Test
    @DisplayName("Should return true for positive values")
    void shouldReturnTrueForPositiveValues() {
        assertTrue(validationService.isValid(1));
        assertTrue(validationService.isValid(5));
        assertTrue(validationService.isValid(100));
        assertTrue(validationService.isValid(Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("Should return false for zero")
    void shouldReturnFalseForZero() {
        assertFalse(validationService.isValid(0));
    }

    @Test
    @DisplayName("Should return false for negative values")
    void shouldReturnFalseForNegativeValues() {
        assertFalse(validationService.isValid(-1));
        assertFalse(validationService.isValid(-5));
        assertFalse(validationService.isValid(-100));
        assertFalse(validationService.isValid(Integer.MIN_VALUE));
    }

    // Positive test cases for isInRange()
    @Test
    @DisplayName("Should return true for values within range")
    void shouldReturnTrueForValuesWithinRange() {
        assertTrue(validationService.isInRange(5, 1, 10));
        assertTrue(validationService.isInRange(1, 1, 10)); // Lower boundary
        assertTrue(validationService.isInRange(10, 1, 10)); // Upper boundary
        assertTrue(validationService.isInRange(0, -5, 5));
        assertTrue(validationService.isInRange(-3, -10, -1));
    }

    @Test
    @DisplayName("Should return false for values outside range")
    void shouldReturnFalseForValuesOutsideRange() {
        assertFalse(validationService.isInRange(0, 1, 10)); // Below range
        assertFalse(validationService.isInRange(11, 1, 10)); // Above range
        assertFalse(validationService.isInRange(-6, -5, 5)); // Below negative range
        assertFalse(validationService.isInRange(6, -5, 5)); // Above positive range
    }

    @Test
    @DisplayName("Should handle single value range")
    void shouldHandleSingleValueRange() {
        assertTrue(validationService.isInRange(5, 5, 5));
        assertFalse(validationService.isInRange(4, 5, 5));
        assertFalse(validationService.isInRange(6, 5, 5));
    }

    @Test
    @DisplayName("Should handle negative ranges")
    void shouldHandleNegativeRanges() {
        assertTrue(validationService.isInRange(-5, -10, -1));
        assertTrue(validationService.isInRange(-10, -10, -1)); // Lower boundary
        assertTrue(validationService.isInRange(-1, -10, -1)); // Upper boundary
        assertFalse(validationService.isInRange(-11, -10, -1)); // Below range
        assertFalse(validationService.isInRange(0, -10, -1)); // Above range
    }

    @Test
    @DisplayName("Should handle large ranges")
    void shouldHandleLargeRanges() {
        assertTrue(validationService.isInRange(0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertTrue(validationService.isInRange(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertTrue(validationService.isInRange(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    // Negative test cases for isInRange()
    @Test
    @DisplayName("Should throw exception when min is greater than max")
    void shouldThrowExceptionWhenMinIsGreaterThanMax() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> validationService.isInRange(5, 10, 1)
        );
        
        assertEquals("Minimum value cannot be greater than maximum value", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for invalid range with negative values")
    void shouldThrowExceptionForInvalidRangeWithNegativeValues() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> validationService.isInRange(-5, -1, -10)
        );
        
        assertEquals("Minimum value cannot be greater than maximum value", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for extreme invalid range")
    void shouldThrowExceptionForExtremeInvalidRange() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> validationService.isInRange(0, Integer.MAX_VALUE, Integer.MIN_VALUE)
        );
        
        assertEquals("Minimum value cannot be greater than maximum value", exception.getMessage());
    }

    // Edge cases
    @Test
    @DisplayName("Should handle boundary values correctly")
    void shouldHandleBoundaryValuesCorrectly() {
        // Test with maximum integer values
        assertTrue(validationService.isValid(Integer.MAX_VALUE));
        assertFalse(validationService.isValid(Integer.MIN_VALUE));
        
        // Test range with maximum values
        assertTrue(validationService.isInRange(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertTrue(validationService.isInRange(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE));
    }

    @Test
    @DisplayName("Should handle zero in different contexts")
    void shouldHandleZeroInDifferentContexts() {
        assertFalse(validationService.isValid(0));
        assertTrue(validationService.isInRange(0, -1, 1));
        assertTrue(validationService.isInRange(0, 0, 0));
        assertFalse(validationService.isInRange(0, 1, 5));
    }
}