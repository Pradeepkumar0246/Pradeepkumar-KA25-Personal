package com.example.vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for Truck vehicle.
 * Tests both positive and negative scenarios.
 */
class TruckTest {

    private Truck truck;

    @BeforeEach
    void setUp() {
        truck = new Truck("Tata 407", 20.0, 180.0);
    }

    // Positive test cases
    @Test
    @DisplayName("Should create truck with valid parameters")
    void shouldCreateTruckWithValidParameters() {
        Truck newTruck = new Truck("Mahindra Bolero", 25.0, 200.0);
        
        assertEquals("Mahindra Bolero", newTruck.getModel());
        assertEquals(25.0, newTruck.getFuelUsed());
        assertEquals(200.0, newTruck.getDistance());
    }

    @Test
    @DisplayName("Should calculate mileage with efficiency factor")
    void shouldCalculateMileageWithEfficiencyFactor() {
        double baseMileage = 180.0 / 20.0; // 9.0 km/l
        double expectedMileage = baseMileage * 0.8; // 7.2 km/l
        
        assertEquals(expectedMileage, truck.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should calculate mileage for different values")
    void shouldCalculateMileageForDifferentValues() {
        Truck truck1 = new Truck("Model1", 10.0, 100.0); // Base: 10, Adjusted: 8
        Truck truck2 = new Truck("Model2", 15.0, 225.0); // Base: 15, Adjusted: 12
        Truck truck3 = new Truck("Model3", 30.0, 300.0); // Base: 10, Adjusted: 8
        
        assertEquals(8.0, truck1.calculateMileage(), 0.001);
        assertEquals(12.0, truck2.calculateMileage(), 0.001);
        assertEquals(8.0, truck3.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle zero distance correctly")
    void shouldHandleZeroDistanceCorrectly() {
        Truck zeroTruck = new Truck("Zero Distance", 20.0, 0.0);
        
        assertEquals(0.0, zeroTruck.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle small fuel amounts")
    void shouldHandleSmallFuelAmounts() {
        Truck efficientTruck = new Truck("Efficient", 1.0, 15.0); // Base: 15, Adjusted: 12
        
        assertEquals(12.0, efficientTruck.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle large values")
    void shouldHandleLargeValues() {
        Truck largeTruck = new Truck("Large", 500.0, 5000.0); // Base: 10, Adjusted: 8
        
        assertEquals(8.0, largeTruck.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should get efficiency factor correctly")
    void shouldGetEfficiencyFactorCorrectly() {
        assertEquals(0.8, truck.getEfficiencyFactor(), 0.001);
    }

    @Test
    @DisplayName("Should get efficiency rating correctly")
    void shouldGetEfficiencyRatingCorrectly() {
        // Create trucks with different mileage levels
        Truck excellentTruck = new Truck("Excellent", 4.0, 100.0); // Base: 25, Adjusted: 20
        Truck goodTruck = new Truck("Good", 5.0, 93.75); // Base: 18.75, Adjusted: 15
        Truck averageTruck = new Truck("Average", 8.0, 100.0); // Base: 12.5, Adjusted: 10
        Truck poorTruck = new Truck("Poor", 20.0, 100.0); // Base: 5, Adjusted: 4
        
        assertEquals("Excellent", excellentTruck.getEfficiencyRating());
        assertEquals("Good", goodTruck.getEfficiencyRating());
        assertEquals("Average", averageTruck.getEfficiencyRating());
        assertEquals("Poor", poorTruck.getEfficiencyRating());
    }

    @Test
    @DisplayName("Should trim model name")
    void shouldTrimModelName() {
        Truck trimTruck = new Truck("  Ashok Leyland  ", 20.0, 180.0);
        
        assertEquals("Ashok Leyland", trimTruck.getModel());
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void shouldImplementToStringCorrectly() {
        String expected = "Truck{model='Tata 407', mileage=7.2 km/l, factor=0.8}";
        
        assertEquals(expected, truck.toString());
    }

    @Test
    @DisplayName("Should implement equals correctly")
    void shouldImplementEqualsCorrectly() {
        Truck truck1 = new Truck("Tata 407", 20.0, 180.0);
        Truck truck2 = new Truck("Tata 407", 20.0, 180.0);
        Truck truck3 = new Truck("Mahindra", 20.0, 180.0);
        Truck truck4 = new Truck("Tata 407", 25.0, 180.0);
        
        assertEquals(truck1, truck2);
        assertNotEquals(truck1, truck3);
        assertNotEquals(truck1, truck4);
        assertEquals(truck1, truck1);
        assertNotEquals(truck1, null);
        assertNotEquals(truck1, "string");
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void shouldImplementHashCodeCorrectly() {
        Truck truck1 = new Truck("Tata 407", 20.0, 180.0);
        Truck truck2 = new Truck("Tata 407", 20.0, 180.0);
        
        assertEquals(truck1.hashCode(), truck2.hashCode());
    }

    @Test
    @DisplayName("Should demonstrate truck efficiency reduction")
    void shouldDemonstrateTruckEfficiencyReduction() {
        // Compare truck with car having same fuel and distance
        Truck truck = new Truck("Test Truck", 10.0, 100.0);
        Car car = new Car("Test Car", 10.0, 100.0);
        
        double truckMileage = truck.calculateMileage(); // 8.0 km/l (10 * 0.8)
        double carMileage = car.calculateMileage(); // 10.0 km/l
        
        assertEquals(8.0, truckMileage, 0.001);
        assertEquals(10.0, carMileage, 0.001);
        assertTrue(truckMileage < carMileage);
    }

    @Test
    @DisplayName("Should maintain consistent efficiency factor")
    void shouldMaintainConsistentEfficiencyFactor() {
        Truck truck1 = new Truck("Truck1", 10.0, 100.0);
        Truck truck2 = new Truck("Truck2", 20.0, 200.0);
        Truck truck3 = new Truck("Truck3", 5.0, 50.0);
        
        assertEquals(0.8, truck1.getEfficiencyFactor(), 0.001);
        assertEquals(0.8, truck2.getEfficiencyFactor(), 0.001);
        assertEquals(0.8, truck3.getEfficiencyFactor(), 0.001);
    }

    // Negative test cases
    @Test
    @DisplayName("Should throw exception for null model")
    void shouldThrowExceptionForNullModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Truck(null, 20.0, 180.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty model")
    void shouldThrowExceptionForEmptyModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Truck("", 20.0, 180.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only model")
    void shouldThrowExceptionForWhitespaceOnlyModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Truck("   ", 20.0, 180.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for zero fuel")
    void shouldThrowExceptionForZeroFuel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Truck("Tata 407", 0.0, 180.0)
        );
        
        assertEquals("Fuel used must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative fuel")
    void shouldThrowExceptionForNegativeFuel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Truck("Tata 407", -10.0, 180.0)
        );
        
        assertEquals("Fuel used must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative distance")
    void shouldThrowExceptionForNegativeDistance() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Truck("Tata 407", 20.0, -50.0)
        );
        
        assertEquals("Distance cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle precision correctly")
    void shouldHandlePrecisionCorrectly() {
        Truck precisionTruck = new Truck("Precision", 3.0, 10.0);
        double expected = (10.0/3.0) * 0.8;
        
        assertEquals(expected, precisionTruck.calculateMileage(), 0.0001);
    }

    @Test
    @DisplayName("Should handle very small values")
    void shouldHandleVerySmallValues() {
        Truck smallTruck = new Truck("Small", 0.1, 1.0);
        double expected = 10.0 * 0.8; // 8.0
        
        assertEquals(expected, smallTruck.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should verify efficiency factor is applied correctly")
    void shouldVerifyEfficiencyFactorIsAppliedCorrectly() {
        // Test with round numbers for easy verification
        Truck testTruck = new Truck("Test", 5.0, 50.0); // Base: 10, Expected: 8
        
        double baseMileage = 50.0 / 5.0; // 10.0
        double expectedMileage = baseMileage * 0.8; // 8.0
        
        assertEquals(expectedMileage, testTruck.calculateMileage(), 0.001);
        assertEquals(baseMileage * testTruck.getEfficiencyFactor(), testTruck.calculateMileage(), 0.001);
    }
}