package com.example.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A generic cache implementation using HashMap as the underlying storage.
 * This cache provides basic operations like put, get, remove, and size.
 * 
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 * 
 * @author Cache Team
 * @version 1.0
 * @since 1.0
 */
public class Cache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(Cache.class);
    
    /** The underlying storage for cache entries */
    private final Map<K, V> store = new HashMap<>();

    /**
     * Associates the specified value with the specified key in this cache.
     * If the cache previously contained a mapping for the key, the old value is replaced.
     * 
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws IllegalArgumentException if key is null
     */
    public void put(K key, V value) {
        if (key == null) {
            logger.error("Attempted to put null key into cache");
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        boolean isUpdate = store.containsKey(key);
        store.put(key, value);
        
        if (isUpdate) {
            logger.debug("Updated cache entry for key: {}", key);
        } else {
            logger.debug("Added new cache entry for key: {}", key);
        }
    }

    /**
     * Returns an Optional containing the value to which the specified key is mapped,
     * or an empty Optional if this cache contains no mapping for the key.
     * 
     * @param key the key whose associated value is to be returned
     * @return an Optional containing the value associated with the key, or empty if not found
     * @throws IllegalArgumentException if key is null
     */
    public Optional<V> get(K key) {
        if (key == null) {
            logger.error("Attempted to get value with null key from cache");
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        Optional<V> result = Optional.ofNullable(store.get(key));
        
        if (result.isPresent()) {
            logger.debug("Cache hit for key: {}", key);
        } else {
            logger.debug("Cache miss for key: {}", key);
        }
        
        return result;
    }

    /**
     * Returns true if this cache contains a mapping for the specified key.
     * 
     * @param key key whose presence in this cache is to be tested
     * @return true if this cache contains a mapping for the specified key
     * @throws IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            logger.error("Attempted to check containsKey with null key");
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        boolean contains = store.containsKey(key);
        logger.debug("ContainsKey check for key: {} returned: {}", key, contains);
        return contains;
    }

    /**
     * Removes the mapping for a key from this cache if it is present.
     * 
     * @param key key whose mapping is to be removed from the cache
     * @return the previous value associated with key, or null if there was no mapping
     * @throws IllegalArgumentException if key is null
     */
    public V remove(K key) {
        if (key == null) {
            logger.error("Attempted to remove entry with null key from cache");
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        V removedValue = store.remove(key);
        
        if (removedValue != null) {
            logger.debug("Removed cache entry for key: {}", key);
        } else {
            logger.debug("Attempted to remove non-existent key: {}", key);
        }
        
        return removedValue;
    }

    /**
     * Returns the number of key-value mappings in this cache.
     * 
     * @return the number of key-value mappings in this cache
     */
    public int size() {
        int currentSize = store.size();
        logger.debug("Cache size requested: {}", currentSize);
        return currentSize;
    }
    
    /**
     * Removes all mappings from this cache.
     */
    public void clear() {
        int previousSize = store.size();
        store.clear();
        logger.info("Cache cleared. Previous size: {}", previousSize);
    }
    
    /**
     * Returns true if this cache contains no key-value mappings.
     * 
     * @return true if this cache contains no key-value mappings
     */
    public boolean isEmpty() {
        boolean empty = store.isEmpty();
        logger.debug("Cache isEmpty check returned: {}", empty);
        return empty;
    }
}
