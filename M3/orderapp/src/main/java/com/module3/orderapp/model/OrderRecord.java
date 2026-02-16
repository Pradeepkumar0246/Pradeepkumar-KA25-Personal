package com.module3.orderapp.model;

/**
 * Represents an order record with order details.
 * This class is used to store order information including ID, product, quantity, and price.
 */
public class OrderRecord {
    
    // Unique identifier for the order
    private int orderId;
    
    // Name of the product being ordered
    private String product;
    
    // Quantity of the product ordered (must be positive)
    private int quantity;
    
    // Price per unit of the product
    private double price;

    /**
     * Constructor to create a new OrderRecord.
     * 
     * @param orderId Unique identifier for the order
     * @param product Name of the product
     * @param quantity Number of items ordered
     * @param price Price per unit
     */
    public OrderRecord(int orderId, String product, int quantity, double price) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Gets the order ID.
     * @return The unique order identifier
     */
    public int getOrderId() { return orderId; }
    
    /**
     * Gets the product name.
     * @return The name of the product
     */
    public String getProduct() { return product; }
    
    /**
     * Gets the quantity ordered.
     * @return The number of items ordered
     */
    public int getQuantity() { return quantity; }
    
    /**
     * Gets the price per unit.
     * @return The price per unit of the product
     */
    public double getPrice() { return price; }
    
    /**
     * Calculates the total order value.
     * @return The total cost (quantity * price)
     */
    public double getTotalValue() {
        return quantity * price;
    }
}
