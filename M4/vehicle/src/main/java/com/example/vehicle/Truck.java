package com.example.vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a truck vehicle with adjusted mileage calculation.
 * Trucks typically have lower fuel efficiency due to their weight and size.
 * Extends the Vehicle abstract class.
 * 
 * @author Vehicle System
 * @version 1.0
 */
public class Truck extends Vehicle {

    private static final Logger logger = LoggerFactory.getLogger(Truck.class);
    private static final double TRUCK_EFFICIENCY_FACTOR = 0.8; // 20% reduction in efficiency

    /**
     * Creates a new truck with the specified parameters.
     * 
     * @param model the model name of the truck
     * @param fuelUsed the amount of fuel used in liters
     * @param distance the distance traveled in kilometers
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Truck(String model, double fuelUsed, double distance) {
        super(model, fuelUsed, distance);
        logger.info("Created truck: {}", model);
    }

    /**
     * Calculates the mileage for this truck with efficiency adjustment.
     * Trucks generally have lower mileage due to their weight and design.
     * 
     * @return the adjusted mileage in kilometers per liter
     */
    @Override
    public double calculateMileage() {
        logger.debug("Calculating mileage for truck '{}': distance={}, fuel={}", 
                    getModel(), getDistance(), getFuelUsed());
        
        double baseMileage = getDistance() / getFuelUsed();
        double adjustedMileage = baseMileage * TRUCK_EFFICIENCY_FACTOR;
        
        logger.info("Truck '{}' mileage calculated: {} km/l (base: {}, factor: {})", 
                   getModel(), adjustedMileage, baseMileage, TRUCK_EFFICIENCY_FACTOR);
        
        return adjustedMileage;
    }
    
    /**
     * Gets the efficiency factor used for truck mileage calculation.
     * 
     * @return the efficiency factor (0.8 for 20% reduction)
     */
    public double getEfficiencyFactor() {
        return TRUCK_EFFICIENCY_FACTOR;
    }
    
    @Override
    public String toString() {
        return "Truck{model='" + getModel() + "', mileage=" + calculateMileage() + " km/l, factor=" + TRUCK_EFFICIENCY_FACTOR + "}";
    }
}
