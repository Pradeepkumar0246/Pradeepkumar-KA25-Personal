package com.module3.orderapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.module3.orderapp.exception.InvalidOrderException;
import com.module3.orderapp.exception.PaymentFailedException;
import com.module3.orderapp.model.OrderRecord;

/**
 * Unit tests for OrderService class.
 * Tests both positive and negative scenarios for order processing.
 */
class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @Test
    void testProcess_ValidOrder_Success() {
        // Arrange
        OrderRecord order = new OrderRecord(1, "Laptop", 2, 500.0);
        
        // Act & Assert - Should not throw any exception
        assertDoesNotThrow(() -> orderService.process(order));
    }

    @Test
    void testProcess_ValidOrder_AtPaymentLimit() {
        // Arrange - Exactly at the payment limit
        OrderRecord order = new OrderRecord(2, "Expensive Item", 1, 100000.0);
        
        // Act & Assert - Should not throw any exception
        assertDoesNotThrow(() -> orderService.process(order));
    }

    @Test
    void testProcess_PaymentExceedsLimit_ThrowsException() {
        // Arrange - Order exceeds payment limit
        OrderRecord order = new OrderRecord(3, "Very Expensive Item", 2, 60000.0);
        
        // Act & Assert
        PaymentFailedException exception = assertThrows(
            PaymentFailedException.class, 
            () -> orderService.process(order)
        );
        
        assertTrue(exception.getMessage().contains("Payment limit exceeded"));
        assertTrue(exception.getMessage().contains("3"));
    }

    @Test
    void testProcess_ZeroQuantity_ThrowsInvalidOrderException() {
        // Arrange
        OrderRecord order = new OrderRecord(4, "Product", 0, 100.0);
        
        // Act & Assert
        InvalidOrderException exception = assertThrows(
            InvalidOrderException.class, 
            () -> orderService.process(order)
        );
        
        assertEquals("Invalid quantity", exception.getMessage());
    }

    @Test
    void testProcess_NegativeQuantity_ThrowsInvalidOrderException() {
        // Arrange
        OrderRecord order = new OrderRecord(5, "Product", -5, 100.0);
        
        // Act & Assert
        InvalidOrderException exception = assertThrows(
            InvalidOrderException.class, 
            () -> orderService.process(order)
        );
        
        assertEquals("Invalid quantity", exception.getMessage());
    }

    @Test
    void testProcess_ZeroPrice_ThrowsInvalidOrderException() {
        // Arrange
        OrderRecord order = new OrderRecord(6, "Free Product", 1, 0.0);
        
        // Act & Assert
        InvalidOrderException exception = assertThrows(
            InvalidOrderException.class, 
            () -> orderService.process(order)
        );
        
        assertEquals("Invalid price", exception.getMessage());
    }

    @Test
    void testProcess_NegativePrice_ThrowsInvalidOrderException() {
        // Arrange
        OrderRecord order = new OrderRecord(7, "Product", 1, -50.0);
        
        // Act & Assert
        InvalidOrderException exception = assertThrows(
            InvalidOrderException.class, 
            () -> orderService.process(order)
        );
        
        assertEquals("Invalid price", exception.getMessage());
    }

    @Test
    void testProcess_LargeQuantitySmallPrice_Success() {
        // Arrange
        OrderRecord order = new OrderRecord(8, "Cheap Item", 1000, 50.0);
        
        // Act & Assert - Total is 50,000 which is under limit
        assertDoesNotThrow(() -> orderService.process(order));
    }

    @Test
    void testProcess_SmallQuantityLargePrice_ExceedsLimit() {
        // Arrange
        OrderRecord order = new OrderRecord(9, "Luxury Item", 1, 150000.0);
        
        // Act & Assert
        PaymentFailedException exception = assertThrows(
            PaymentFailedException.class, 
            () -> orderService.process(order)
        );
        
        assertTrue(exception.getMessage().contains("Payment limit exceeded"));
    }

    @Test
    void testProcess_BothQuantityAndPriceInvalid_ThrowsQuantityException() {
        // Arrange - Both quantity and price are invalid, but quantity is checked first
        OrderRecord order = new OrderRecord(10, "Invalid Product", -1, -100.0);
        
        // Act & Assert - Should throw InvalidOrderException for quantity first
        InvalidOrderException exception = assertThrows(
            InvalidOrderException.class, 
            () -> orderService.process(order)
        );
        
        assertEquals("Invalid quantity", exception.getMessage());
    }
}