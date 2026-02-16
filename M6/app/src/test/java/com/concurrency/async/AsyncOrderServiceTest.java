package com.concurrency.async;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

/**
 * Tests for AsyncOrderService
 */
public class AsyncOrderServiceTest {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testProcessOrder() {
        AsyncOrderService service = new AsyncOrderService();
        
        // Should complete without throwing exception
        service.processOrder();
    }
}