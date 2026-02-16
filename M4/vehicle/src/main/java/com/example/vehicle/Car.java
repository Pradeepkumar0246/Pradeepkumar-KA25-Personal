package com.example.vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a car vehicle with standard mileage calculation.
 * Extends the Vehicle abstract class.
 * 
 * @author Vehicle System
 * @version 1.0
 */
public class Car extends Vehicle {

    private static final Logger logger = LoggerFactory.getLogger(Car.class);

    /**
     * Creates a new car with the specified parameters.
     * 
     * @param model the model name of the car
     * @param fuelUsed the amount of fuel used in liters
     * @param distance the distance traveled in kilometers
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Car(String model, double fuelUsed, double distance) {
        super(model, fuelUsed, distance);
        logger.info("Created car: {}", model);
    }

    /**
     * Calculates the mileage for this car using standard formula.
     * 
     * @return the mileage in kilometers per liter
     */
    @Override
    public double calculateMileage() {
        logger.debug("Calculating mileage for car '{}': distance={}, fuel={}", 
                    getModel(), getDistance(), getFuelUsed());
        
        double mileage = getDistance() / getFuelUsed();   // km per liter
        
        logger.info("Car '{}' mileage calculated: {} km/l", getModel(), mileage);
        
        return mileage;
    }
    
    @Override
    public String toString() {
        return "Car{model='" + getModel() + "', mileage=" + calculateMileage() + " km/l}";
    }
}
