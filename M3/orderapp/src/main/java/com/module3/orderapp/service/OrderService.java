package com.module3.orderapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.module3.orderapp.exception.InvalidOrderException;
import com.module3.orderapp.exception.PaymentFailedException;
import com.module3.orderapp.model.OrderRecord;

/**
 * Service class responsible for processing orders.
 * Handles order validation and payment processing logic.
 */
@Service
public class OrderService {
    
    // Logger for tracking order processing activities
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    // Payment limit threshold
    private static final double PAYMENT_LIMIT = 100_000.0;

    /**
     * Processes an order by validating it and checking payment limits.
     * 
     * @param order The order to be processed
     * @throws InvalidOrderException if the order is invalid
     * @throws PaymentFailedException if payment exceeds the limit
     */
    public void process(OrderRecord order) throws PaymentFailedException {
        logger.info("Processing order ID: {}", order.getOrderId());
        
        // Validate the order first
        validate(order);
        
        // Calculate total order value
        double total = order.getTotalValue();
        logger.debug("Order {} total value: {}", order.getOrderId(), total);
        
        // Check if payment exceeds the limit
        if (total > PAYMENT_LIMIT) {
            logger.warn("Payment limit exceeded for order {}: {} > {}", 
                       order.getOrderId(), total, PAYMENT_LIMIT);
            throw new PaymentFailedException(
                    "Payment limit exceeded for order " + order.getOrderId()
            );
        }
        
        logger.info("Order {} processed successfully", order.getOrderId());
    }
    
    /**
     * Validates an order to ensure it meets business requirements.
     * 
     * @param order The order to validate
     * @throws InvalidOrderException if the order is invalid
     */
    private void validate(OrderRecord order) {
        logger.debug("Validating order ID: {}", order.getOrderId());
        
        // Check if quantity is positive
        if (order.getQuantity() <= 0) {
            logger.error("Invalid quantity for order {}: {}", 
                        order.getOrderId(), order.getQuantity());
            throw new InvalidOrderException("Invalid quantity");
        }
        
        // Check if price is positive
        if (order.getPrice() <= 0) {
            logger.error("Invalid price for order {}: {}", 
                        order.getOrderId(), order.getPrice());
            throw new InvalidOrderException("Invalid price");
        }
        
        logger.debug("Order {} validation passed", order.getOrderId());
    }
}
