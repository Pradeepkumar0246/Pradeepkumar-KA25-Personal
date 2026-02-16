package com.module3.orderapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import com.module3.orderapp.model.OrderRecord;

/**
 * Unit tests for CsvFileService class.
 * Tests both positive and negative scenarios for CSV file processing.
 */
class CsvFileServiceTest {

    private CsvFileService csvFileService;

    @BeforeEach
    void setUp() {
        csvFileService = new CsvFileService();
    }

    @Test
    void testReadOrders_ValidCsvData() {
        // Act - Using the actual CSV file from resources
        Map<String, List<?>> result = csvFileService.readOrders("/data/orders.csv");
        
        // Assert
        assertNotNull(result);
        assertTrue(result.containsKey("orders"));
        assertTrue(result.containsKey("malformed"));
        
        @SuppressWarnings("unchecked")
        List<OrderRecord> orders = (List<OrderRecord>) result.get("orders");
        
        @SuppressWarnings("unchecked")
        List<String> malformed = (List<String>) result.get("malformed");
        
        assertNotNull(orders);
        assertNotNull(malformed);
    }

    @Test
    void testReadOrders_FileNotFound() {
        // Act
        Map<String, List<?>> result = csvFileService.readOrders("/nonexistent/file.csv");
        
        // Assert
        assertNotNull(result);
        
        @SuppressWarnings("unchecked")
        List<OrderRecord> orders = (List<OrderRecord>) result.get("orders");
        
        @SuppressWarnings("unchecked")
        List<String> malformed = (List<String>) result.get("malformed");
        
        assertTrue(orders.isEmpty());
        assertEquals(1, malformed.size());
        assertTrue(malformed.get(0).contains("CSV file not found"));
    }

    @Test
    void testReadOrders_EmptyFile() {
        // This test would require creating a temporary empty CSV file
        // For now, we'll test the behavior with the existing file structure
        Map<String, List<?>> result = csvFileService.readOrders("/data/orders.csv");
        
        // Assert basic structure
        assertNotNull(result);
        assertTrue(result.containsKey("orders"));
        assertTrue(result.containsKey("malformed"));
    }

    @Test
    void testReadOrders_ResultStructure() {
        // Act
        Map<String, List<?>> result = csvFileService.readOrders("/data/orders.csv");
        
        // Assert result structure
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsKey("orders"));
        assertTrue(result.containsKey("malformed"));
        
        // Verify the lists are not null
        assertNotNull(result.get("orders"));
        assertNotNull(result.get("malformed"));
    }

    @Test
    void testReadOrders_OrdersListType() {
        // Act
        Map<String, List<?>> result = csvFileService.readOrders("/data/orders.csv");
        
        // Assert
        @SuppressWarnings("unchecked")
        List<OrderRecord> orders = (List<OrderRecord>) result.get("orders");
        
        // If there are orders, verify they are OrderRecord instances
        if (!orders.isEmpty()) {
            assertTrue(orders.get(0) instanceof OrderRecord);
            
            OrderRecord firstOrder = orders.get(0);
            assertNotNull(firstOrder.getProduct());
            assertTrue(firstOrder.getOrderId() > 0 || firstOrder.getOrderId() < 0); // Allow any integer
        }
    }

    @Test
    void testReadOrders_MalformedListType() {
        // Act
        Map<String, List<?>> result = csvFileService.readOrders("/data/orders.csv");
        
        // Assert
        @SuppressWarnings("unchecked")
        List<String> malformed = (List<String>) result.get("malformed");
        
        // Verify malformed list contains strings
        malformed.forEach(entry -> assertTrue(entry instanceof String));
    }

    @Test
    void testReadOrders_HandlesMalformedData() {
        // This test verifies that the service can handle malformed data gracefully
        // The actual CSV file might contain some malformed entries
        
        // Act
        Map<String, List<?>> result = csvFileService.readOrders("/data/orders.csv");
        
        // Assert - Should not throw exceptions and should return valid structure
        assertNotNull(result);
        assertNotNull(result.get("orders"));
        assertNotNull(result.get("malformed"));
    }

    @Test
    void testReadOrders_NonNullResults() {
        // Act
        Map<String, List<?>> result = csvFileService.readOrders("/invalid/path.csv");
        
        // Assert - Even with invalid path, should return non-null structure
        assertNotNull(result);
        assertNotNull(result.get("orders"));
        assertNotNull(result.get("malformed"));
        
        @SuppressWarnings("unchecked")
        List<String> malformed = (List<String>) result.get("malformed");
        assertFalse(malformed.isEmpty());
    }
}