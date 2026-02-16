package com.example.vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for Car vehicle.
 * Tests both positive and negative scenarios.
 */
class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("Honda City", 10.0, 150.0);
    }

    // Positive test cases
    @Test
    @DisplayName("Should create car with valid parameters")
    void shouldCreateCarWithValidParameters() {
        Car newCar = new Car("Toyota Camry", 12.0, 180.0);
        
        assertEquals("Toyota Camry", newCar.getModel());
        assertEquals(12.0, newCar.getFuelUsed());
        assertEquals(180.0, newCar.getDistance());
    }

    @Test
    @DisplayName("Should calculate mileage correctly")
    void shouldCalculateMileageCorrectly() {
        double expectedMileage = 150.0 / 10.0; // 15.0 km/l
        
        assertEquals(expectedMileage, car.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should calculate mileage for different values")
    void shouldCalculateMileageForDifferentValues() {
        Car car1 = new Car("Model1", 5.0, 100.0);
        Car car2 = new Car("Model2", 8.0, 200.0);
        Car car3 = new Car("Model3", 15.0, 300.0);
        
        assertEquals(20.0, car1.calculateMileage(), 0.001);
        assertEquals(25.0, car2.calculateMileage(), 0.001);
        assertEquals(20.0, car3.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle zero distance correctly")
    void shouldHandleZeroDistanceCorrectly() {
        Car zeroCar = new Car("Zero Distance", 10.0, 0.0);
        
        assertEquals(0.0, zeroCar.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle small fuel amounts")
    void shouldHandleSmallFuelAmounts() {
        Car efficientCar = new Car("Efficient", 0.1, 10.0);
        
        assertEquals(100.0, efficientCar.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should handle large values")
    void shouldHandleLargeValues() {
        Car largeCar = new Car("Large", 1000.0, 50000.0);
        
        assertEquals(50.0, largeCar.calculateMileage(), 0.001);
    }

    @Test
    @DisplayName("Should get efficiency rating correctly")
    void shouldGetEfficiencyRatingCorrectly() {
        Car excellentCar = new Car("Excellent", 5.0, 120.0); // 24 km/l
        Car goodCar = new Car("Good", 10.0, 170.0); // 17 km/l
        Car averageCar = new Car("Average", 10.0, 120.0); // 12 km/l
        Car poorCar = new Car("Poor", 20.0, 100.0); // 5 km/l
        
        assertEquals("Excellent", excellentCar.getEfficiencyRating());
        assertEquals("Good", goodCar.getEfficiencyRating());
        assertEquals("Average", averageCar.getEfficiencyRating());
        assertEquals("Poor", poorCar.getEfficiencyRating());
    }

    @Test
    @DisplayName("Should trim model name")
    void shouldTrimModelName() {
        Car trimCar = new Car("  Honda Civic  ", 10.0, 150.0);
        
        assertEquals("Honda Civic", trimCar.getModel());
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void shouldImplementToStringCorrectly() {
        String expected = "Car{model='Honda City', mileage=15.0 km/l}";
        
        assertEquals(expected, car.toString());
    }

    @Test
    @DisplayName("Should implement equals correctly")
    void shouldImplementEqualsCorrectly() {
        Car car1 = new Car("Honda City", 10.0, 150.0);
        Car car2 = new Car("Honda City", 10.0, 150.0);
        Car car3 = new Car("Toyota Camry", 10.0, 150.0);
        Car car4 = new Car("Honda City", 12.0, 150.0);
        
        assertEquals(car1, car2);
        assertNotEquals(car1, car3);
        assertNotEquals(car1, car4);
        assertEquals(car1, car1);
        assertNotEquals(car1, null);
        assertNotEquals(car1, "string");
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void shouldImplementHashCodeCorrectly() {
        Car car1 = new Car("Honda City", 10.0, 150.0);
        Car car2 = new Car("Honda City", 10.0, 150.0);
        
        assertEquals(car1.hashCode(), car2.hashCode());
    }

    // Negative test cases
    @Test
    @DisplayName("Should throw exception for null model")
    void shouldThrowExceptionForNullModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Car(null, 10.0, 150.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty model")
    void shouldThrowExceptionForEmptyModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Car("", 10.0, 150.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only model")
    void shouldThrowExceptionForWhitespaceOnlyModel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Car("   ", 10.0, 150.0)
        );
        
        assertEquals("Vehicle model cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for zero fuel")
    void shouldThrowExceptionForZeroFuel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Car("Honda City", 0.0, 150.0)
        );
        
        assertEquals("Fuel used must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative fuel")
    void shouldThrowExceptionForNegativeFuel() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Car("Honda City", -5.0, 150.0)
        );
        
        assertEquals("Fuel used must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative distance")
    void shouldThrowExceptionForNegativeDistance() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Car("Honda City", 10.0, -50.0)
        );
        
        assertEquals("Distance cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle precision correctly")
    void shouldHandlePrecisionCorrectly() {
        Car precisionCar = new Car("Precision", 3.0, 10.0);
        
        assertEquals(10.0/3.0, precisionCar.calculateMileage(), 0.0001);
    }

    @Test
    @DisplayName("Should handle very small values")
    void shouldHandleVerySmallValues() {
        Car smallCar = new Car("Small", 0.001, 0.01);
        
        assertEquals(10.0, smallCar.calculateMileage(), 0.001);
    }
}