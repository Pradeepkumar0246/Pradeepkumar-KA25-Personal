package com.example;

import com.example.vehicle.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class demonstrating vehicle inheritance and polymorphism.
 * Shows how different vehicle types implement mileage calculation differently.
 * 
 * @author Vehicle System
 * @version 1.0
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting Vehicle Management System");
        
        try {
            List<Vehicle> vehicles = new ArrayList<>();

            // Create different types of vehicles
            vehicles.add(new Car("Honda City", 10, 150));
            vehicles.add(new Bike("Yamaha FZ", 5, 200));
            vehicles.add(new Truck("Tata 407", 20, 180));
            vehicles.add(new Car("Toyota Camry", 12, 180));
            vehicles.add(new Bike("Royal Enfield", 8, 240));
            
            logger.info("Created {} vehicles for analysis", vehicles.size());

            System.out.println("=== Vehicle Mileage Analysis ===");
            
            double totalMileage = 0;
            for (Vehicle vehicle : vehicles) {
                double mileage = vehicle.calculateMileage();
                String efficiency = vehicle.getEfficiencyRating();
                
                System.out.println("Model: " + vehicle.getModel());
                System.out.println("Type: " + vehicle.getClass().getSimpleName());
                System.out.println("Mileage: " + String.format("%.2f", mileage) + " km/l");
                System.out.println("Efficiency: " + efficiency);
                System.out.println("Details: " + vehicle);
                System.out.println("--------------------------------");
                
                totalMileage += mileage;
            }
            
            double averageMileage = totalMileage / vehicles.size();
            System.out.println("\n=== Summary ===");
            System.out.println("Total vehicles analyzed: " + vehicles.size());
            System.out.println("Average mileage: " + String.format("%.2f", averageMileage) + " km/l");
            
            // Find best and worst performers
            Vehicle bestVehicle = vehicles.stream()
                .max((v1, v2) -> Double.compare(v1.calculateMileage(), v2.calculateMileage()))
                .orElse(null);
            
            Vehicle worstVehicle = vehicles.stream()
                .min((v1, v2) -> Double.compare(v1.calculateMileage(), v2.calculateMileage()))
                .orElse(null);
            
            if (bestVehicle != null && worstVehicle != null) {
                System.out.println("Best performer: " + bestVehicle.getModel() + 
                                 " (" + String.format("%.2f", bestVehicle.calculateMileage()) + " km/l)");
                System.out.println("Worst performer: " + worstVehicle.getModel() + 
                                 " (" + String.format("%.2f", worstVehicle.calculateMileage()) + " km/l)");
            }
            
            logger.info("Vehicle analysis completed successfully. Average mileage: {} km/l", 
                       String.format("%.2f", averageMileage));
                       
        } catch (Exception e) {
            logger.error("Error occurred during vehicle analysis", e);
            System.err.println("An error occurred: " + e.getMessage());
        }
        
        logger.info("Vehicle Management System shutdown");
    }
}
