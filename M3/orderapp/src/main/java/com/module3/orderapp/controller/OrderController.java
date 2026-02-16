package com.module3.orderapp.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.module3.orderapp.exception.InvalidOrderException;
import com.module3.orderapp.exception.PaymentFailedException;
import com.module3.orderapp.model.OrderRecord;
import com.module3.orderapp.service.CsvFileService;
import com.module3.orderapp.service.OrderService;

/**
 * REST Controller for handling order processing requests.
 * Provides endpoints to process orders from CSV files.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    // Logger for tracking controller activities
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // Service dependencies injected via constructor
    private final CsvFileService csvService;
    private final OrderService orderService;

    /**
     * Constructor for dependency injection.
     * 
     * @param csvService Service for reading CSV files
     * @param orderService Service for processing orders
     */
    public OrderController(CsvFileService csvService, OrderService orderService) {
        this.csvService = csvService;
        this.orderService = orderService;
    }

    /**
     * Processes orders from the CSV file and returns processing results.
     * 
     * @return HTML formatted string containing processing results
     */
    @GetMapping("/process")
    public String processOrders() {
        logger.info("Starting order processing request");
        
        StringBuilder response = new StringBuilder();

        // Read orders from CSV file
        Map<String, List<?>> result = csvService.readOrders("/data/orders.csv");

        // Extract valid orders and malformed entries
        @SuppressWarnings("unchecked")
        List<OrderRecord> orders = (List<OrderRecord>) result.get("orders");

        @SuppressWarnings("unchecked")
        List<String> malformed = (List<String>) result.get("malformed");

        logger.info("Found {} valid orders and {} malformed entries", 
                   orders.size(), malformed.size());

        // Add malformed entries to response
        malformed.forEach(err -> {
            logger.warn("Malformed entry: {}", err);
            response.append(err).append("<br>");
        });

        // Process each valid order
        orders.forEach(order -> {
            try {
                orderService.process(order);
                String successMsg = "Order " + order.getOrderId() + " processed successfully";
                logger.info(successMsg);
                response.append(successMsg).append("<br>");

            } catch (InvalidOrderException ex) {
                String errorMsg = "INVALID_ORDER for " + order.getOrderId();
                logger.error(errorMsg + ": {}", ex.getMessage());
                response.append(errorMsg).append("<br>");

            } catch (PaymentFailedException ex) {
                String errorMsg = "PAYMENT_FAILED for " + order.getOrderId();
                logger.error(errorMsg + ": {}", ex.getMessage());
                response.append(errorMsg).append("<br>");
            }
        });

        logger.info("Order processing request completed");
        return response.toString();
    }
}
