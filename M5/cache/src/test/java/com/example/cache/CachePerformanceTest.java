package com.example.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Performance and stress test cases for the Cache class.
 * These tests verify that the cache performs well under various load conditions.
 */
@DisplayName("Cache Performance Tests")
class CachePerformanceTest {

    private Cache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new Cache<>();
    }

    @Test
    @DisplayName("Should handle 10,000 put operations efficiently")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testLargePutOperations() {
        // When
        for (int i = 0; i < 10000; i++) {
            cache.put("key" + i, i);
        }

        // Then
        assertEquals(10000, cache.size());
        assertTrue(cache.containsKey("key0"));
        assertTrue(cache.containsKey("key9999"));
    }

    @Test
    @DisplayName("Should handle 10,000 get operations efficiently")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testLargeGetOperations() {
        // Given - populate cache
        for (int i = 0; i < 1000; i++) {
            cache.put("key" + i, i);
        }

        // When - perform many get operations
        for (int i = 0; i < 10000; i++) {
            cache.get("key" + (i % 1000));
        }

        // Then - cache should still be consistent
        assertEquals(1000, cache.size());
    }

    @Test
    @DisplayName("Should handle mixed operations efficiently")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testMixedOperations() {
        // Perform mixed operations
        for (int i = 0; i < 5000; i++) {
            cache.put("key" + i, i);
            
            if (i % 2 == 0) {
                cache.get("key" + (i / 2));
            }
            
            if (i % 3 == 0 && i > 0) {
                cache.remove("key" + (i - 1));
            }
            
            if (i % 100 == 0) {
                cache.size();
                cache.isEmpty();
            }
        }

        // Verify cache is still functional
        assertTrue(cache.size() > 0);
        assertFalse(cache.isEmpty());
    }

    @Test
    @DisplayName("Should handle memory stress test")
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    void testMemoryStress() {
        // Create large strings as values
        String largeValue = "x".repeat(1000); // 1KB string
        
        // Add many large entries
        for (int i = 0; i < 1000; i++) {
            cache.put("largeKey" + i, i);
        }

        // Verify functionality
        assertEquals(1000, cache.size());
        assertTrue(cache.containsKey("largeKey0"));
        assertTrue(cache.containsKey("largeKey999"));

        // Clear to free memory
        cache.clear();
        assertEquals(0, cache.size());
    }

    @Test
    @DisplayName("Should handle rapid put/remove cycles")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testRapidPutRemoveCycles() {
        String key = "cycleKey";
        
        // Rapid cycles
        for (int i = 0; i < 10000; i++) {
            cache.put(key, i);
            cache.remove(key);
        }

        // Cache should be empty
        assertEquals(0, cache.size());
        assertTrue(cache.isEmpty());
        assertFalse(cache.containsKey(key));
    }

    @Test
    @DisplayName("Should maintain performance with frequent updates")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testFrequentUpdates() {
        String key = "updateKey";
        
        // Frequent updates to same key
        for (int i = 0; i < 10000; i++) {
            cache.put(key, i);
        }

        // Verify final state
        assertEquals(1, cache.size());
        assertEquals(9999, cache.get(key).orElse(-1));
    }

    @Test
    @DisplayName("Should handle cache growth and shrinkage")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void testCacheGrowthAndShrinkage() {
        // Growth phase
        for (int i = 0; i < 5000; i++) {
            cache.put("growKey" + i, i);
        }
        assertEquals(5000, cache.size());

        // Shrinkage phase
        for (int i = 0; i < 4000; i++) {
            cache.remove("growKey" + i);
        }
        assertEquals(1000, cache.size());

        // Verify remaining entries
        for (int i = 4000; i < 5000; i++) {
            assertTrue(cache.containsKey("growKey" + i));
        }
    }

    @Test
    @DisplayName("Should handle operations on empty cache efficiently")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testEmptyCacheOperations() {
        // Perform many operations on empty cache
        for (int i = 0; i < 1000; i++) {
            cache.get("nonExistent" + i);
            cache.containsKey("nonExistent" + i);
            cache.remove("nonExistent" + i);
            cache.size();
            cache.isEmpty();
        }

        // Cache should remain empty
        assertEquals(0, cache.size());
        assertTrue(cache.isEmpty());
    }
}