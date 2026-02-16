package com.module3.orderapp.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for OrderRecord model class.
 * Tests both positive and negative scenarios for order data handling.
 */
class OrderRecordTest {

    @Test
    void testOrderRecordCreation_ValidData() {
        // Arrange & Act
        OrderRecord order = new OrderRecord(1, "Laptop", 2, 999.99);
        
        // Assert
        assertEquals(1, order.getOrderId());
        assertEquals("Laptop", order.getProduct());
        assertEquals(2, order.getQuantity());
        assertEquals(999.99, order.getPrice(), 0.01);
    }

    @Test
    void testGetTotalValue_PositiveNumbers() {
        // Arrange
        OrderRecord order = new OrderRecord(1, "Phone", 3, 500.0);
        
        // Act
        double total = order.getTotalValue();
        
        // Assert
        assertEquals(1500.0, total, 0.01);
    }

    @Test
    void testGetTotalValue_ZeroQuantity() {
        // Arrange
        OrderRecord order = new OrderRecord(2, "Tablet", 0, 300.0);
        
        // Act
        double total = order.getTotalValue();
        
        // Assert
        assertEquals(0.0, total, 0.01);
    }

    @Test
    void testGetTotalValue_ZeroPrice() {
        // Arrange
        OrderRecord order = new OrderRecord(3, "Free Sample", 5, 0.0);
        
        // Act
        double total = order.getTotalValue();
        
        // Assert
        assertEquals(0.0, total, 0.01);
    }

    @Test
    void testOrderRecord_NegativeValues() {
        // Arrange & Act
        OrderRecord order = new OrderRecord(-1, "Invalid Product", -5, -100.0);
        
        // Assert - Constructor should accept negative values (validation happens in service)
        assertEquals(-1, order.getOrderId());
        assertEquals("Invalid Product", order.getProduct());
        assertEquals(-5, order.getQuantity());
        assertEquals(-100.0, order.getPrice(), 0.01);
        assertEquals(500.0, order.getTotalValue(), 0.01); // -5 * -100 = 500
    }

    @Test
    void testOrderRecord_EmptyProductName() {
        // Arrange & Act
        OrderRecord order = new OrderRecord(4, "", 1, 50.0);
        
        // Assert
        assertEquals("", order.getProduct());
        assertEquals(50.0, order.getTotalValue(), 0.01);
    }

    @Test
    void testOrderRecord_NullProductName() {
        // Arrange & Act
        OrderRecord order = new OrderRecord(5, null, 2, 25.0);
        
        // Assert
        assertNull(order.getProduct());
        assertEquals(50.0, order.getTotalValue(), 0.01);
    }
}