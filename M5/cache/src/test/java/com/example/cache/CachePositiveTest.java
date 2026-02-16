package com.example.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Positive test cases for the Cache class.
 * These tests verify that the cache behaves correctly under normal conditions.
 */
@DisplayName("Cache Positive Tests")
class CachePositiveTest {

    private Cache<String, Integer> stringIntegerCache;
    private Cache<Integer, String> integerStringCache;

    @BeforeEach
    void setUp() {
        stringIntegerCache = new Cache<>();
        integerStringCache = new Cache<>();
    }

    @Test
    @DisplayName("Should successfully put and get values")
    void testPutAndGet() {
        // Given
        String key = "testKey";
        Integer value = 42;

        // When
        stringIntegerCache.put(key, value);
        Optional<Integer> result = stringIntegerCache.get(key);

        // Then
        assertTrue(result.isPresent());
        assertEquals(value, result.get());
    }

    @Test
    @DisplayName("Should return empty Optional for non-existent key")
    void testGetNonExistentKey() {
        // When
        Optional<Integer> result = stringIntegerCache.get("nonExistent");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should correctly check if key exists")
    void testContainsKey() {
        // Given
        String key = "existingKey";
        stringIntegerCache.put(key, 100);

        // When & Then
        assertTrue(stringIntegerCache.containsKey(key));
        assertFalse(stringIntegerCache.containsKey("nonExistentKey"));
    }

    @Test
    @DisplayName("Should successfully remove existing key")
    void testRemoveExistingKey() {
        // Given
        String key = "keyToRemove";
        Integer value = 200;
        stringIntegerCache.put(key, value);

        // When
        Integer removedValue = stringIntegerCache.remove(key);

        // Then
        assertEquals(value, removedValue);
        assertFalse(stringIntegerCache.containsKey(key));
        assertFalse(stringIntegerCache.get(key).isPresent());
    }

    @Test
    @DisplayName("Should return null when removing non-existent key")
    void testRemoveNonExistentKey() {
        // When
        Integer removedValue = stringIntegerCache.remove("nonExistent");

        // Then
        assertNull(removedValue);
    }

    @Test
    @DisplayName("Should correctly report cache size")
    void testSize() {
        // Initially empty
        assertEquals(0, stringIntegerCache.size());

        // Add items
        stringIntegerCache.put("key1", 1);
        assertEquals(1, stringIntegerCache.size());

        stringIntegerCache.put("key2", 2);
        assertEquals(2, stringIntegerCache.size());

        // Remove item
        stringIntegerCache.remove("key1");
        assertEquals(1, stringIntegerCache.size());
    }

    @Test
    @DisplayName("Should correctly report if cache is empty")
    void testIsEmpty() {
        // Initially empty
        assertTrue(stringIntegerCache.isEmpty());

        // Add item
        stringIntegerCache.put("key", 1);
        assertFalse(stringIntegerCache.isEmpty());

        // Remove item
        stringIntegerCache.remove("key");
        assertTrue(stringIntegerCache.isEmpty());
    }

    @Test
    @DisplayName("Should clear all entries")
    void testClear() {
        // Given
        stringIntegerCache.put("key1", 1);
        stringIntegerCache.put("key2", 2);
        assertEquals(2, stringIntegerCache.size());

        // When
        stringIntegerCache.clear();

        // Then
        assertEquals(0, stringIntegerCache.size());
        assertTrue(stringIntegerCache.isEmpty());
        assertFalse(stringIntegerCache.containsKey("key1"));
        assertFalse(stringIntegerCache.containsKey("key2"));
    }

    @Test
    @DisplayName("Should update existing key with new value")
    void testUpdateExistingKey() {
        // Given
        String key = "updateKey";
        Integer oldValue = 100;
        Integer newValue = 200;

        // When
        stringIntegerCache.put(key, oldValue);
        stringIntegerCache.put(key, newValue);

        // Then
        Optional<Integer> result = stringIntegerCache.get(key);
        assertTrue(result.isPresent());
        assertEquals(newValue, result.get());
        assertEquals(1, stringIntegerCache.size()); // Size should remain 1
    }

    @Test
    @DisplayName("Should handle different data types correctly")
    void testDifferentDataTypes() {
        // Test with Integer keys and String values
        integerStringCache.put(1, "One");
        integerStringCache.put(2, "Two");

        Optional<String> result1 = integerStringCache.get(1);
        Optional<String> result2 = integerStringCache.get(2);

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertEquals("One", result1.get());
        assertEquals("Two", result2.get());
    }

    @Test
    @DisplayName("Should handle null values correctly")
    void testNullValues() {
        // Given
        String key = "nullValueKey";

        // When
        stringIntegerCache.put(key, null);

        // Then
        assertTrue(stringIntegerCache.containsKey(key));
        Optional<Integer> result = stringIntegerCache.get(key);
        assertFalse(result.isPresent()); // Optional should be empty for null values
    }
}