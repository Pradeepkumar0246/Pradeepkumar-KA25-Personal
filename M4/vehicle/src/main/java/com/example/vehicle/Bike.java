package com.example.vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a bike vehicle with standard mileage calculation.
 * Extends the Vehicle abstract class.
 * 
 * @author Vehicle System
 * @version 1.0
 */
public class Bike extends Vehicle {

    private static final Logger logger = LoggerFactory.getLogger(Bike.class);

    /**
     * Creates a new bike with the specified parameters.
     * 
     * @param model the model name of the bike
     * @param fuelUsed the amount of fuel used in liters
     * @param distance the distance traveled in kilometers
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Bike(String model, double fuelUsed, double distance) {
        super(model, fuelUsed, distance);
        logger.info("Created bike: {}", model);
    }

    /**
     * Calculates the mileage for this bike using standard formula.
     * Bikes typically have better fuel efficiency than cars.
     * 
     * @return the mileage in kilometers per liter
     */
    @Override
    public double calculateMileage() {
        logger.debug("Calculating mileage for bike '{}': distance={}, fuel={}", 
                    getModel(), getDistance(), getFuelUsed());
        
        double mileage = getDistance() / getFuelUsed();
        
        logger.info("Bike '{}' mileage calculated: {} km/l", getModel(), mileage);
        
        return mileage;
    }
    
    @Override
    public String toString() {
        return "Bike{model='" + getModel() + "', mileage=" + calculateMileage() + " km/l}";
    }
}
