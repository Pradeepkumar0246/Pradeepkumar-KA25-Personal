package com.example;

import com.example.vehicle.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Integration test class for the complete vehicle management system.
 * Tests end-to-end workflows and polymorphism demonstration.
 */
class VehicleSystemIntegrationTest {

    private List<Vehicle> vehicles;

    @BeforeEach
    void setUp() {
        vehicles = new ArrayList<>();
        vehicles.add(new Car("Honda City", 10.0, 150.0));
        vehicles.add(new Bike("Yamaha FZ", 5.0, 200.0));
        vehicles.add(new Truck("Tata 407", 20.0, 180.0));
        vehicles.add(new Car("Toyota Camry", 12.0, 180.0));
        vehicles.add(new Bike("Royal Enfield", 8.0, 240.0));
    }

    @Test
    @DisplayName("Should demonstrate polymorphism with different vehicle types")
    void shouldDemonstratePolymorphismWithDifferentVehicleTypes() {
        // All vehicles should be able to calculate mileage through polymorphism
        for (Vehicle vehicle : vehicles) {
            assertNotNull(vehicle.calculateMileage());
            assertTrue(vehicle.calculateMileage() >= 0);
            assertNotNull(vehicle.getModel());
            assertNotNull(vehicle.getEfficiencyRating());
        }
    }

    @Test
    @DisplayName("Should calculate correct mileage for each vehicle type")
    void shouldCalculateCorrectMileageForEachVehicleType() {
        Car car = (Car) vehicles.get(0); // Honda City
        Bike bike = (Bike) vehicles.get(1); // Yamaha FZ
        Truck truck = (Truck) vehicles.get(2); // Tata 407
        
        // Car: 150/10 = 15.0 km/l
        assertEquals(15.0, car.calculateMileage(), 0.001);
        
        // Bike: 200/5 = 40.0 km/l
        assertEquals(40.0, bike.calculateMileage(), 0.001);
        
        // Truck: (180/20) * 0.8 = 7.2 km/l
        assertEquals(7.2, truck.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle mixed vehicle operations correctly")
    void shouldHandleMixedVehicleOperationsCorrectly() {
        double totalMileage = 0;
        int excellentCount = 0;
        int goodCount = 0;
        int averageCount = 0;
        int poorCount = 0;
        
        for (Vehicle vehicle : vehicles) {
            double mileage = vehicle.calculateMileage();
            totalMileage += mileage;
            
            String rating = vehicle.getEfficiencyRating();
            switch (rating) {
                case "Excellent" -> excellentCount++;
                case "Good" -> goodCount++;
                case "Average" -> averageCount++;
                case "Poor" -> poorCount++;
            }
        }
        
        double averageMileage = totalMileage / vehicles.size();
        
        // Verify calculations
        assertTrue(averageMileage > 0);
        assertEquals(5, excellentCount + goodCount + averageCount + poorCount);
        
        // Expected: 2 Excellent (bikes), 2 Good (cars), 0 Average, 1 Poor (truck)
        assertEquals(2, excellentCount); // Bikes with 40 and 30 km/l
        assertEquals(2, goodCount); // Cars with 15 km/l
        assertEquals(0, averageCount);
        assertEquals(1, poorCount); // Truck with 7.2 km/l
    }

    @Test
    @DisplayName("Should find best and worst performing vehicles")
    void shouldFindBestAndWorstPerformingVehicles() {
        Vehicle bestVehicle = vehicles.stream()
            .max((v1, v2) -> Double.compare(v1.calculateMileage(), v2.calculateMileage()))
            .orElse(null);
        
        Vehicle worstVehicle = vehicles.stream()
            .min((v1, v2) -> Double.compare(v1.calculateMileage(), v2.calculateMileage()))
            .orElse(null);
        
        assertNotNull(bestVehicle);
        assertNotNull(worstVehicle);
        
        // Best should be Yamaha FZ (40 km/l)
        assertEquals("Yamaha FZ", bestVehicle.getModel());
        assertEquals(40.0, bestVehicle.calculateMileage(), 0.001);
        assertTrue(bestVehicle instanceof Bike);
        
        // Worst should be Tata 407 (7.2 km/l)
        assertEquals("Tata 407", worstVehicle.getModel());
        assertEquals(7.2, worstVehicle.calculateMileage(), 0.001);
        assertTrue(worstVehicle instanceof Truck);
    }

    @Test
    @DisplayName("Should demonstrate inheritance hierarchy correctly")
    void shouldDemonstrateInheritanceHierarchyCorrectly() {
        for (Vehicle vehicle : vehicles) {
            // All vehicles should be instances of Vehicle
            assertTrue(vehicle instanceof Vehicle);
            
            // Check specific types
            if (vehicle instanceof Car) {
                assertTrue(vehicle.getClass() == Car.class);
            } else if (vehicle instanceof Bike) {
                assertTrue(vehicle.getClass() == Bike.class);
            } else if (vehicle instanceof Truck) {
                assertTrue(vehicle.getClass() == Truck.class);
            }
        }
    }

    @Test
    @DisplayName("Should handle vehicle comparison correctly")
    void shouldHandleVehicleComparisonCorrectly() {
        // Create vehicles with same parameters for comparison
        Car car1 = new Car("Test Model", 10.0, 100.0);
        Car car2 = new Car("Test Model", 10.0, 100.0);
        Bike bike = new Bike("Test Model", 10.0, 100.0);
        
        // Same type, same parameters should be equal
        assertEquals(car1, car2);
        assertEquals(car1.hashCode(), car2.hashCode());
        
        // Different types should not be equal even with same parameters
        assertNotEquals(car1, bike);
        
        // Same mileage calculation for same parameters
        assertEquals(car1.calculateMileage(), bike.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle efficiency ratings across vehicle types")
    void shouldHandleEfficiencyRatingsAcrossVehicleTypes() {
        // Create vehicles with specific mileage targets
        Car excellentCar = new Car("Excellent Car", 5.0, 120.0); // 24 km/l
        Bike goodBike = new Bike("Good Bike", 10.0, 170.0); // 17 km/l
        Truck averageTruck = new Truck("Average Truck", 8.0, 100.0); // 10 km/l (8*0.8)
        Car poorCar = new Car("Poor Car", 20.0, 100.0); // 5 km/l
        
        assertEquals("Excellent", excellentCar.getEfficiencyRating());
        assertEquals("Good", goodBike.getEfficiencyRating());
        assertEquals("Average", averageTruck.getEfficiencyRating());
        assertEquals("Poor", poorCar.getEfficiencyRating());
    }

    @Test
    @DisplayName("Should handle edge cases in vehicle operations")
    void shouldHandleEdgeCasesInVehicleOperations() {
        // Zero distance vehicles
        Car zeroDistanceCar = new Car("Zero Car", 10.0, 0.0);
        assertEquals(0.0, zeroDistanceCar.calculateMileage(), 0.001);
        assertEquals("Poor", zeroDistanceCar.getEfficiencyRating());
        
        // Very efficient vehicles
        Bike superBike = new Bike("Super Bike", 1.0, 50.0);
        assertEquals(50.0, superBike.calculateMileage(), 0.001);
        assertEquals("Excellent", superBike.getEfficiencyRating());
        
        // Truck efficiency factor verification
        Truck testTruck = new Truck("Test Truck", 10.0, 100.0);
        assertEquals(8.0, testTruck.calculateMileage(), 0.001); // 10 * 0.8
        assertEquals(0.8, testTruck.getEfficiencyFactor(), 0.001);
    }

    @Test
    @DisplayName("Should maintain data integrity across operations")
    void shouldMaintainDataIntegrityAcrossOperations() {
        for (Vehicle vehicle : vehicles) {
            // Original values should remain unchanged after calculations
            String originalModel = vehicle.getModel();
            double originalFuel = vehicle.getFuelUsed();
            double originalDistance = vehicle.getDistance();
            
            // Perform multiple operations
            vehicle.calculateMileage();
            vehicle.getEfficiencyRating();
            vehicle.toString();
            
            // Values should remain the same
            assertEquals(originalModel, vehicle.getModel());
            assertEquals(originalFuel, vehicle.getFuelUsed(), 0.001);
            assertEquals(originalDistance, vehicle.getDistance(), 0.001);
        }
    }

    @Test
    @DisplayName("Should handle large scale vehicle operations")
    void shouldHandleLargeScaleVehicleOperations() {
        List<Vehicle> largeFleet = new ArrayList<>();
        
        // Create a large fleet
        for (int i = 0; i < 100; i++) {
            largeFleet.add(new Car("Car" + i, 10.0 + i, 150.0 + i * 10));
            largeFleet.add(new Bike("Bike" + i, 5.0 + i, 200.0 + i * 5));
            largeFleet.add(new Truck("Truck" + i, 20.0 + i, 180.0 + i * 8));
        }
        
        assertEquals(300, largeFleet.size());
        
        // Process all vehicles
        double totalMileage = largeFleet.stream()
            .mapToDouble(Vehicle::calculateMileage)
            .sum();
        
        assertTrue(totalMileage > 0);
        
        // Count by efficiency rating
        long excellentCount = largeFleet.stream()
            .filter(v -> "Excellent".equals(v.getEfficiencyRating()))
            .count();
        
        assertTrue(excellentCount > 0);
    }

    @Test
    @DisplayName("Should validate business rules consistently")
    void shouldValidateBusinessRulesConsistently() {
        // Rule: Trucks should have lower mileage than cars/bikes with same fuel/distance
        Car car = new Car("Test Car", 10.0, 100.0);
        Bike bike = new Bike("Test Bike", 10.0, 100.0);
        Truck truck = new Truck("Test Truck", 10.0, 100.0);
        
        double carMileage = car.calculateMileage(); // 10.0
        double bikeMileage = bike.calculateMileage(); // 10.0
        double truckMileage = truck.calculateMileage(); // 8.0
        
        assertEquals(carMileage, bikeMileage, 0.001);
        assertTrue(truckMileage < carMileage);
        assertTrue(truckMileage < bikeMileage);
        
        // Rule: All vehicles should have positive mileage for positive inputs
        assertTrue(carMileage > 0);
        assertTrue(bikeMileage > 0);
        assertTrue(truckMileage > 0);
    }
}