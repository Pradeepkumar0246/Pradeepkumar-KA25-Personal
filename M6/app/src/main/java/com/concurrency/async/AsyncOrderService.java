package com.concurrency.async;

import java.util.concurrent.CompletableFuture;

import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;

/**
 * Simulates async service calls for order processing:
 * - Payment service
 * - Inventory service
 * - Notification service
 */
public class AsyncOrderService {

    private static final Logger logger = AppLogger.getLogger(AsyncOrderService.class);

    /**
     * Processes an order by calling payment and inventory services asynchronously
     */
    public void processOrder() {
        logger.info("Starting order processing");

        CompletableFuture<String> paymentService =
                CompletableFuture.supplyAsync(() -> {
                    sleep();
                    logger.info("Payment completed");
                    return "PAYMENT_OK";
                });

        CompletableFuture<String> inventoryService =
                CompletableFuture.supplyAsync(() -> {
                    sleep();
                    logger.info("Inventory updated");
                    return "INVENTORY_OK";
                });

        // Combine both services
        CompletableFuture<Void> finalResult =
                paymentService.thenCombine(inventoryService,
                        (payment, inventory) -> payment + " & " + inventory)
                        .thenAccept(result ->
                                logger.info("Order processed successfully: {}", result)
                        );

        finalResult.join();
    }

    /**
     * Simulates processing delay
     */
    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted during processing");
            Thread.currentThread().interrupt();
        }
    }
}
