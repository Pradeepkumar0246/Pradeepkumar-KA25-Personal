package com.module3.orderapp.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.module3.orderapp.exception.InvalidOrderException;
import com.module3.orderapp.exception.PaymentFailedException;
import com.module3.orderapp.model.OrderRecord;
import com.module3.orderapp.service.CsvFileService;
import com.module3.orderapp.service.OrderService;

/**
 * Unit tests for OrderController class.
 * Tests both positive and negative scenarios for order processing endpoints.
 */
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private CsvFileService csvFileService;

    @Mock
    private OrderService orderService;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(csvFileService, orderService);
    }

    @Test
    void testProcessOrders_SuccessfulProcessing() {
        // Arrange
        OrderRecord order1 = new OrderRecord(1, "Laptop", 1, 1000.0);
        OrderRecord order2 = new OrderRecord(2, "Mouse", 2, 25.0);
        
        Map<String, List<?>> csvResult = new HashMap<>();
        csvResult.put("orders", Arrays.asList(order1, order2));
        csvResult.put("malformed", Arrays.asList());
        
        when(csvFileService.readOrders("/data/orders.csv")).thenReturn(csvResult);
        
        // Act
        String result = orderController.processOrders();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Order 1 processed successfully"));
        assertTrue(result.contains("Order 2 processed successfully"));
        
        verify(csvFileService).readOrders("/data/orders.csv");
        try {
            verify(orderService).process(order1);
            verify(orderService).process(order2);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testProcessOrders_WithMalformedEntries() {
        // Arrange
        OrderRecord validOrder = new OrderRecord(1, "Phone", 1, 500.0);
        
        Map<String, List<?>> csvResult = new HashMap<>();
        csvResult.put("orders", Arrays.asList(validOrder));
        csvResult.put("malformed", Arrays.asList("MALFORMED LINE: invalid,data", "MALFORMED LINE: another,bad,line"));
        
        when(csvFileService.readOrders("/data/orders.csv")).thenReturn(csvResult);
        
        // Act
        String result = orderController.processOrders();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("MALFORMED LINE: invalid,data"));
        assertTrue(result.contains("MALFORMED LINE: another,bad,line"));
        assertTrue(result.contains("Order 1 processed successfully"));
        
        try {
            verify(orderService).process(validOrder);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testProcessOrders_InvalidOrderException() {
        // Arrange
        OrderRecord invalidOrder = new OrderRecord(3, "Invalid Product", 0, 100.0);
        
        Map<String, List<?>> csvResult = new HashMap<>();
        csvResult.put("orders", Arrays.asList(invalidOrder));
        csvResult.put("malformed", Arrays.asList());
        
        when(csvFileService.readOrders("/data/orders.csv")).thenReturn(csvResult);
        try {
            doThrow(new InvalidOrderException("Invalid quantity")).when(orderService).process(invalidOrder);
        } catch (Exception e) {
            // This should not happen in test setup
        }
        
        // Act
        String result = orderController.processOrders();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("INVALID_ORDER for 3"));
        
        try {
            verify(orderService).process(invalidOrder);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testProcessOrders_PaymentFailedException() {
        // Arrange
        OrderRecord expensiveOrder = new OrderRecord(4, "Expensive Item", 1, 200000.0);
        
        Map<String, List<?>> csvResult = new HashMap<>();
        csvResult.put("orders", Arrays.asList(expensiveOrder));
        csvResult.put("malformed", Arrays.asList());
        
        when(csvFileService.readOrders("/data/orders.csv")).thenReturn(csvResult);
        try {
            doThrow(new PaymentFailedException("Payment limit exceeded")).when(orderService).process(expensiveOrder);
        } catch (Exception e) {
            // This should not happen in test setup
        }
        
        // Act
        String result = orderController.processOrders();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("PAYMENT_FAILED for 4"));
        
        try {
            verify(orderService).process(expensiveOrder);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testProcessOrders_MixedResults() {
        // Arrange
        OrderRecord validOrder = new OrderRecord(1, "Valid Product", 2, 50.0);
        OrderRecord invalidOrder = new OrderRecord(2, "Invalid Product", -1, 100.0);
        OrderRecord expensiveOrder = new OrderRecord(3, "Expensive Product", 1, 150000.0);
        
        Map<String, List<?>> csvResult = new HashMap<>();
        csvResult.put("orders", Arrays.asList(validOrder, invalidOrder, expensiveOrder));
        csvResult.put("malformed", Arrays.asList("MALFORMED LINE: bad,data"));
        
        when(csvFileService.readOrders("/data/orders.csv")).thenReturn(csvResult);
        try {
            doNothing().when(orderService).process(validOrder);
            doThrow(new InvalidOrderException("Invalid quantity")).when(orderService).process(invalidOrder);
            doThrow(new PaymentFailedException("Payment limit exceeded")).when(orderService).process(expensiveOrder);
        } catch (Exception e) {
            // This should not happen in test setup
        }
        
        // Act
        String result = orderController.processOrders();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("MALFORMED LINE: bad,data"));
        assertTrue(result.contains("Order 1 processed successfully"));
        assertTrue(result.contains("INVALID_ORDER for 2"));
        assertTrue(result.contains("PAYMENT_FAILED for 3"));
        
        try {
            verify(orderService).process(validOrder);
            verify(orderService).process(invalidOrder);
            verify(orderService).process(expensiveOrder);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testProcessOrders_EmptyOrdersList() {
        // Arrange
        Map<String, List<?>> csvResult = new HashMap<>();
        csvResult.put("orders", Arrays.asList());
        csvResult.put("malformed", Arrays.asList());
        
        when(csvFileService.readOrders("/data/orders.csv")).thenReturn(csvResult);
        
        // Act
        String result = orderController.processOrders();
        
        // Assert
        assertNotNull(result);
        assertEquals("", result); // Should be empty string when no orders or malformed entries
        
        verify(csvFileService).readOrders("/data/orders.csv");
        verifyNoInteractions(orderService);
    }

    @Test
    void testProcessOrders_OnlyMalformedEntries() {
        // Arrange
        Map<String, List<?>> csvResult = new HashMap<>();
        csvResult.put("orders", Arrays.asList());
        csvResult.put("malformed", Arrays.asList("MALFORMED LINE: bad1", "MALFORMED LINE: bad2"));
        
        when(csvFileService.readOrders("/data/orders.csv")).thenReturn(csvResult);
        
        // Act
        String result = orderController.processOrders();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("MALFORMED LINE: bad1"));
        assertTrue(result.contains("MALFORMED LINE: bad2"));
        
        verify(csvFileService).readOrders("/data/orders.csv");
        verifyNoInteractions(orderService);
    }
}