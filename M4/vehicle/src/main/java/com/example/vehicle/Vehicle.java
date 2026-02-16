package com.example.vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class representing a vehicle with fuel consumption tracking.
 * Demonstrates inheritance and polymorphism principles.
 * 
 * @author Vehicle System
 * @version 1.0
 */
public abstract class Vehicle {

    private static final Logger logger = LoggerFactory.getLogger(Vehicle.class);
    
    private final String model;
    private final double fuelUsed;     // in liters
    private final double distance;     // in kilometers

    /**
     * Creates a new vehicle with the specified parameters.
     * 
     * @param model the model name of the vehicle
     * @param fuelUsed the amount of fuel used in liters
     * @param distance the distance traveled in kilometers
     * @throws IllegalArgumentException if any parameter is invalid
     */
    protected Vehicle(String model, double fuelUsed, double distance) {
        if (model == null || model.trim().isEmpty()) {
            logger.error("Attempted to create vehicle with null or empty model");
            throw new IllegalArgumentException("Vehicle model cannot be null or empty");
        }
        if (fuelUsed <= 0) {
            logger.error("Attempted to create vehicle with invalid fuel used: {}", fuelUsed);
            throw new IllegalArgumentException("Fuel used must be positive");
        }
        if (distance < 0) {
            logger.error("Attempted to create vehicle with negative distance: {}", distance);
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        
        this.model = model.trim();
        this.fuelUsed = fuelUsed;
        this.distance = distance;
        
        logger.debug("Created vehicle: model='{}', fuelUsed={}, distance={}", 
                    model, fuelUsed, distance);
    }

    /**
     * Gets the model name of this vehicle.
     * 
     * @return the vehicle model
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the amount of fuel used by this vehicle.
     * 
     * @return the fuel used in liters
     */
    public double getFuelUsed() {
        return fuelUsed;
    }

    /**
     * Gets the distance traveled by this vehicle.
     * 
     * @return the distance in kilometers
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Calculates the mileage of this vehicle.
     * Implementation varies by vehicle type.
     * 
     * @return the mileage in kilometers per liter
     */
    public abstract double calculateMileage();
    
    /**
     * Calculates the fuel efficiency rating based on mileage.
     * 
     * @return efficiency rating: "Excellent", "Good", "Average", or "Poor"
     */
    public String getEfficiencyRating() {
        double mileage = calculateMileage();
        logger.debug("Calculating efficiency rating for mileage: {}", mileage);
        
        if (mileage >= 20) {
            return "Excellent";
        } else if (mileage >= 15) {
            return "Good";
        } else if (mileage >= 10) {
            return "Average";
        } else {
            return "Poor";
        }
    }

    @Override
    public String toString() {
        return "Vehicle{model='" + model + "', fuelUsed=" + fuelUsed + ", distance=" + distance + "}";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehicle vehicle = (Vehicle) obj;
        return Double.compare(vehicle.fuelUsed, fuelUsed) == 0 &&
               Double.compare(vehicle.distance, distance) == 0 &&
               model.equals(vehicle.model);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(model, fuelUsed, distance);
    }
}
