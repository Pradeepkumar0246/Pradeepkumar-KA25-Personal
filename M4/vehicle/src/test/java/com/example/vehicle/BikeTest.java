package com.example.vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for Bike vehicle.
 * Tests both positive and negative scenarios.
 */
class BikeTest {

    private Bike bike;

    @BeforeEach
    void setUp() {
        bike = new Bike("Yamaha FZ", 5.0, 200.0);
    }

    // Positive test cases
    @Test
    @DisplayName("Should create bike with valid parameters")
    void shouldCreateBikeWithValidParameters() {
        Bike newBike = new Bike("Royal Enfield", 8.0, 240.0);
        
        assertEquals("Royal Enfield", newBike.getModel());
        assertEquals(8.0, newBike.getFuelUsed());
        assertEquals(240.0, newBike.getDistance());
    }

    @Test
    @DisplayName("Should calculate mileage correctly")
    void shouldCalculateMileageCorrectly() {
        double expectedMileage = 200.0 / 5.0; // 40.0 km/l
        
        assertEquals(expectedMileage, bike.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should calculate mileage for different values")
    void shouldCalculateMileageForDifferentValues() {
        Bike bike1 = new Bike("Model1", 2.0, 80.0);
        Bike bike2 = new Bike("Model2", 4.0, 200.0);
        Bike bike3 = new Bike("Model3", 10.0, 500.0);
        
        assertEquals(40.0, bike1.calculateMileage(), 0.001);
        assertEquals(50.0, bike2.calculateMileage(), 0.001);
        assertEquals(50.0, bike3.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle zero distance correctly")
    void shouldHandleZeroDistanceCorrectly() {
        Bike zeroBike = new Bike("Zero Distance", 5.0, 0.0);
        
        assertEquals(0.0, zeroBike.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle small fuel amounts")
    void shouldHandleSmallFuelAmounts() {
        Bike efficientBike = new Bike("Efficient", 0.5, 30.0);
        
        assertEquals(60.0, efficientBike.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle large values")
    void shouldHandleLargeValues() {
        Bike largeBike = new Bike("Large", 100.0, 4000.0);
        
        assertEquals(40.0, largeBike.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should get efficiency rating correctly")
    void shouldGetEfficiencyRatingCorrectly() {
        Bike excellentBike = new Bike("Excellent", 2.0, 50.0); // 25 km/l
        Bike goodBike = new Bike("Good", 5.0, 85.0); // 17 km/l
        Bike averageBike = new Bike("Average", 8.0, 96.0); // 12 km/l
        Bike poorBike = new Bike("Poor", 10.0, 50.0); // 5 km/l
        
        assertEquals("Excellent", excellentBike.getEfficiencyRating());
        assertEquals("Good", goodBike.getEfficiencyRating());
        assertEquals("Average", averageBike.getEfficiencyRating());
        assertEquals("Poor", poorBike.getEfficiencyRating());
    }

    @Test
    @DisplayName("Should trim model name")
    void shouldTrimModelName() {
        Bike trimBike = new Bike("  Honda CBR  ", 5.0, 200.0);
        
        assertEquals("Honda CBR", trimBike.getModel());
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void shouldImplementToStringCorrectly() {
        String expected = "Bike{model='Yamaha FZ', mileage=40.0 km/l}";
        
        assertEquals(expected, bike.toString());
    }

    @Test
    @DisplayName("Should implement equals correctly")
    void shouldImplementEqualsCorrectly() {
        Bike bike1 = new Bike("Yamaha FZ", 5.0, 200.0);
        Bike bike2 = new Bike("Yamaha FZ", 5.0, 200.0);
        Bike bike3 = new Bike("Honda CBR", 5.0, 200.0);
        Bike bike4 = new Bike("Yamaha FZ", 6.0, 200.0);
        
        assertEquals(bike1, bike2);
        assertNotEquals(bike1, bike3);
        assertNotEquals(bike1, bike4);
        assertEquals(bike1, bike1);
        assertNotEquals(bike1, null);
        assertNotEquals(bike1, "string");
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void shouldImplementHashCodeCorrectly() {
        Bike bike1 = new Bike("Yamaha FZ", 5.0, 200.0);
        Bike bike2 = new Bike("Yamaha FZ", 5.0, 200.0);
        
        assertEquals(bike1.hashCode(), bike2.hashCode());
    }

    @Test
    @DisplayName("Should demonstrate typical bike efficiency")
    void shouldDemonstrateTypicalBikeEfficiency() {
        // Bikes typically have better mileage than cars
        Bike efficientBike = new Bike("Efficient Bike", 3.0, 150.0); // 50 km/l
        
        assertTrue(efficientBike.calculateMileage() > 30.0); // Better than most cars
        assertEquals("Excellent", efficientBike.getEfficiencyRating());
    }

    // Negative test cases
    @Test
    @DisplayName("Should throw exception for null model")
    void shouldThrowExceptionForNullModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Bike(null, 5.0, 200.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty model")
    void shouldThrowExceptionForEmptyModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Bike("", 5.0, 200.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only model")
    void shouldThrowExceptionForWhitespaceOnlyModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Bike("   ", 5.0, 200.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for zero fuel")
    void shouldThrowExceptionForZeroFuel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Bike("Yamaha FZ", 0.0, 200.0)
        );
        
        assertEquals("Fuel used must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative fuel")
    void shouldThrowExceptionForNegativeFuel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Bike("Yamaha FZ", -3.0, 200.0)
        );
        
        assertEquals("Fuel used must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative distance")
    void shouldThrowExceptionForNegativeDistance() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Bike("Yamaha FZ", 5.0, -100.0)
        );
        
        assertEquals("Distance cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle precision correctly")
    void shouldHandlePrecisionCorrectly() {
        Bike precisionBike = new Bike("Precision", 3.0, 10.0);
        
        assertEquals(10.0/3.0, precisionBike.calculateMileage(), 0.0001);
    }

    @Test
    @DisplayName("Should handle very small values")
    void shouldHandleVerySmallValues() {
        Bike smallBike = new Bike("Small", 0.01, 0.5);
        
        assertEquals(50.0, smallBike.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should compare with other vehicle types")
    void shouldCompareWithOtherVehicleTypes() {
        // Create bike and car with same fuel and distance
        Bike bike = new Bike("Test Bike", 5.0, 200.0);
        Car car = new Car("Test Car", 5.0, 200.0);
        
        // Both should have same base mileage calculation
        assertEquals(bike.calculateMileage(), car.calculateMileage(), 0.001);
    }
}