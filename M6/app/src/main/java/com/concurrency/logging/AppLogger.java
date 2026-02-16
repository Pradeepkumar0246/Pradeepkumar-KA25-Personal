package com.concurrency.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for creating loggers
 */
public class AppLogger {

    /**
     * Creates a logger for the specified class
     * @param clazz the class to create logger for
     * @return configured logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}