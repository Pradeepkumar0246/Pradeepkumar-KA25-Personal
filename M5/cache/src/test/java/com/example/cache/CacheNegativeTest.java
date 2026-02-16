package com.example.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Negative test cases for the Cache class.
 * These tests verify that the cache handles error conditions and edge cases correctly.
 */
@DisplayName("Cache Negative Tests")
class CacheNegativeTest {

    private Cache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new Cache<>();
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when putting null key")
    void testPutNullKey() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> cache.put(null, 42)
        );
        assertEquals("Key cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when getting with null key")
    void testGetNullKey() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> cache.get(null)
        );
        assertEquals("Key cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when checking containsKey with null key")
    void testContainsKeyNullKey() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> cache.containsKey(null)
        );
        assertEquals("Key cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when removing null key")
    void testRemoveNullKey() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> cache.remove(null)
        );
        assertEquals("Key cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle empty cache operations correctly")
    void testEmptyCacheOperations() {
        // Empty cache should return appropriate values
        assertEquals(0, cache.size());
        assertTrue(cache.isEmpty());
        assertFalse(cache.containsKey("anyKey"));
        assertFalse(cache.get("anyKey").isPresent());
        assertNull(cache.remove("anyKey"));
    }

    @Test
    @DisplayName("Should handle multiple clear operations")
    void testMultipleClearOperations() {
        // Add some data
        cache.put("key1", 1);
        cache.put("key2", 2);
        
        // Clear multiple times
        cache.clear();
        cache.clear(); // Should not cause any issues
        
        // Verify state
        assertEquals(0, cache.size());
        assertTrue(cache.isEmpty());
    }

    @Test
    @DisplayName("Should handle operations after clear")
    void testOperationsAfterClear() {
        // Add data and clear
        cache.put("key1", 1);
        cache.clear();
        
        // Operations should work normally after clear
        cache.put("newKey", 100);
        assertTrue(cache.containsKey("newKey"));
        assertEquals(1, cache.size());
        assertFalse(cache.isEmpty());
    }

    @Test
    @DisplayName("Should handle removing same key multiple times")
    void testMultipleRemoveSameKey() {
        // Add and remove key
        cache.put("key", 42);
        Integer firstRemove = cache.remove("key");
        Integer secondRemove = cache.remove("key"); // Should return null
        
        assertEquals(42, firstRemove);
        assertNull(secondRemove);
        assertEquals(0, cache.size());
    }

    @Test
    @DisplayName("Should handle large number of operations")
    void testLargeNumberOfOperations() {
        // Add many entries
        for (int i = 0; i < 1000; i++) {
            cache.put("key" + i, i);
        }
        
        assertEquals(1000, cache.size());
        
        // Remove half
        for (int i = 0; i < 500; i++) {
            cache.remove("key" + i);
        }
        
        assertEquals(500, cache.size());
        
        // Verify remaining entries
        for (int i = 500; i < 1000; i++) {
            assertTrue(cache.containsKey("key" + i));
            assertEquals(i, cache.get("key" + i).orElse(-1));
        }
    }

    @Test
    @DisplayName("Should handle concurrent-like operations")
    void testConcurrentLikeOperations() {
        String key = "concurrentKey";
        
        // Rapid put/remove operations
        cache.put(key, 1);
        cache.remove(key);
        cache.put(key, 2);
        cache.put(key, 3); // Update
        
        assertTrue(cache.containsKey(key));
        assertEquals(3, cache.get(key).orElse(-1));
        assertEquals(1, cache.size());
    }

    @Test
    @DisplayName("Should handle edge case with empty string key")
    void testEmptyStringKey() {
        String emptyKey = "";
        
        // Empty string should be valid key
        cache.put(emptyKey, 42);
        assertTrue(cache.containsKey(emptyKey));
        assertEquals(42, cache.get(emptyKey).orElse(-1));
    }

    @Test
    @DisplayName("Should handle special characters in keys")
    void testSpecialCharacterKeys() {
        String specialKey = "key@#$%^&*()";
        
        cache.put(specialKey, 100);
        assertTrue(cache.containsKey(specialKey));
        assertEquals(100, cache.get(specialKey).orElse(-1));
    }

    @Test
    @DisplayName("Should maintain consistency after mixed operations")
    void testConsistencyAfterMixedOperations() {
        // Perform various operations
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        
        cache.remove("b");
        cache.put("d", 4);
        cache.put("a", 10); // Update
        
        // Verify final state
        assertEquals(3, cache.size());
        assertEquals(10, cache.get("a").orElse(-1));
        assertFalse(cache.containsKey("b"));
        assertEquals(3, cache.get("c").orElse(-1));
        assertEquals(4, cache.get("d").orElse(-1));
    }
}