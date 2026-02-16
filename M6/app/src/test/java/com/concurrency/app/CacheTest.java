package com.concurrency.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.concurrency.cache.ThreadSafeCache;

/**
 * Tests for ThreadSafeCache
 */
public class CacheTest {
    
    @Test
    void testCachePutAndGet() {
        ThreadSafeCache cache = new ThreadSafeCache();
        cache.put("key1", "value1");
        assertEquals("value1", cache.get("key1"));
    }
    
    @Test
    void testCacheWithNullKey() {
        ThreadSafeCache cache = new ThreadSafeCache();
        cache.put(null, "value1");
        assertNull(cache.get(null));
    }
    
    @Test
    void testCacheWithNullValue() {
        ThreadSafeCache cache = new ThreadSafeCache();
        cache.put("key1", null);
        assertNull(cache.get("key1"));
    }
}
