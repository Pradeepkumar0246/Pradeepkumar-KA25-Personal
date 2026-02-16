package com.concurrency.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;

/**
 * Thread-safe cache implementation demonstrating:
 * - ConcurrentHashMap
 * - ReentrantLock
 * - Thread-safe operations
 */
public class ThreadSafeCache {

    private static final Logger logger = AppLogger.getLogger(ThreadSafeCache.class);

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Stores a key-value pair in the cache
     * @param key the cache key
     * @param value the value to store
     */
    public void put(String key, String value) {
        if (key == null || value == null) {
            logger.warn("Null key or value provided to cache");
            return;
        }
        
        lock.lock();
        try {
            logger.info("Putting into cache: {} = {}", key, value);
            cache.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves a value from the cache
     * @param key the cache key
     * @return the cached value or null if not found
     */
    public String get(String key) {
        if (key == null) {
            logger.warn("Null key provided to cache get");
            return null;
        }
        
        logger.info("Getting from cache: {}", key);
        return cache.get(key);
    }
}
