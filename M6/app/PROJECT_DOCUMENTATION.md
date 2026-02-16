# Complete Project Documentation - Java Concurrency Application

## 📋 Table of Contents
1. [Project Overview](#project-overview)
2. [Project Structure](#project-structure)
3. [Dependencies & Configuration](#dependencies--configuration)
4. [Detailed Code Explanation](#detailed-code-explanation)
5. [Execution Flow](#execution-flow)
6. [Testing](#testing)
7. [Key Concepts](#key-concepts)

---

## 🎯 Project Overview

**Project Name:** Java Concurrency Learning Application  
**Purpose:** Demonstrates various Java concurrency concepts including multithreading, asynchronous programming, thread-safe operations, and parallel processing.

**Main Features:**
- ✅ Parallel file processing using ExecutorService
- ✅ Thread-safe caching with locks
- ✅ Asynchronous order processing with CompletableFuture
- ✅ Runnable tasks for notifications
- ✅ Proper logging and exception handling

---

## 📁 Project Structure

```
M6/app/
├── pom.xml                                    # Maven configuration
├── src/
│   ├── main/java/com/concurrency/
│   │   ├── Main.java                          # Application entry point
│   │   ├── async/
│   │   │   └── AsyncOrderService.java         # Async order processing
│   │   ├── cache/
│   │   │   └── ThreadSafeCache.java           # Thread-safe cache
│   │   ├── enums/
│   │   │   └── FileStatus.java                # File processing status enum
│   │   ├── exception/
│   │   │   └── FileProcessingException.java   # Custom exception
│   │   ├── file/
│   │   │   ├── FileTask.java                  # Callable file task
│   │   │   └── ParallelFileProcessor.java     # Parallel file processor
│   │   ├── logging/
│   │   │   └── AppLogger.java                 # Logger utility
│   │   ├── notification/
│   │   │   └── NotificationTask.java          # Runnable notification
│   │   └── util/
│   │       └── ThreadUtil.java                # Thread utilities
│   └── test/java/com/concurrency/
│       ├── app/CacheTest.java                 # Cache tests
│       ├── async/AsyncOrderServiceTest.java   # Async tests
│       └── file/ParallelFileProcessorTest.java # File processor tests
```

---

## 🔧 Dependencies & Configuration

### pom.xml - Maven Configuration

```xml
<groupId>com.concurrency</groupId>
<artifactId>app</artifactId>
<version>1.0-SNAPSHOT</version>
```

**Java Version:** 17

**Dependencies:**
1. **slf4j-simple (2.0.9)** - Logging framework for application logs
2. **junit-jupiter (5.10.1)** - Testing framework (test scope only)

---

## 📖 Detailed Code Explanation

### 1️⃣ Main.java - Application Entry Point

**Location:** `src/main/java/com/concurrency/Main.java`

**Line-by-Line Explanation:**

```java
package com.concurrency;
```
- Declares the package name for organizing classes

```java
import java.util.Arrays;
import java.util.List;
import com.concurrency.async.AsyncOrderService;
import com.concurrency.cache.ThreadSafeCache;
import com.concurrency.file.ParallelFileProcessor;
import com.concurrency.notification.NotificationTask;
import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;
```
- Imports all necessary classes and interfaces
- Arrays and List for file collections
- Custom classes for different concurrency features
- SLF4J Logger for logging

```java
public class Main {
    private static final Logger logger = AppLogger.getLogger(Main.class);
```
- Creates the Main class
- Initializes a static logger instance for this class using AppLogger utility

```java
    public static void main(String[] args) {
        try {
            logger.info("Starting concurrency application");
```
- Entry point of the application
- Logs the start of the application
- Wrapped in try-catch for exception handling

```java
            // 1. Parallel File Processing
            List<String> files = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
            ParallelFileProcessor processor = new ParallelFileProcessor();
            processor.processFiles(files);
```
- **STEP 1:** Creates a list of 3 file names
- Creates a ParallelFileProcessor instance
- Processes all files in parallel using thread pool
- Each file takes ~1 second, but all run simultaneously

```java
            // 2. Thread-safe Cache
            ThreadSafeCache cache = new ThreadSafeCache();
            cache.put("user1", "Pradeep");
            cache.put("user2", "Kumar");
            logger.info("Cache value: {}", cache.get("user1"));
```
- **STEP 2:** Creates a thread-safe cache instance
- Stores two key-value pairs in the cache
- Retrieves and logs the value for "user1"
- Uses locks to ensure thread safety

```java
            // 3. Async Order Processing
            AsyncOrderService orderService = new AsyncOrderService();
            orderService.processOrder();
```
- **STEP 3:** Creates an async order service
- Processes an order asynchronously
- Payment and inventory services run in parallel
- Uses CompletableFuture for async operations

```java
            // 4. Runnable Task
            Thread notificationThread = new Thread(new NotificationTask("Order completed"));
            notificationThread.start();
            notificationThread.join();
```
- **STEP 4:** Creates a new thread with NotificationTask
- Starts the thread (runs in background)
- join() waits for the thread to complete before continuing
- Demonstrates basic thread creation and management

```java
            logger.info("Application completed successfully");
            
        } catch (Exception e) {
            logger.error("Application failed: {}", e.getMessage(), e);
        }
    }
}
```
- Logs successful completion
- Catches any exceptions and logs error details
- Ensures application doesn't crash unexpectedly

---

### 2️⃣ AsyncOrderService.java - Asynchronous Order Processing

**Location:** `src/main/java/com/concurrency/async/AsyncOrderService.java`

**Purpose:** Demonstrates CompletableFuture for async operations

**Line-by-Line Explanation:**

```java
package com.concurrency.async;

import java.util.concurrent.CompletableFuture;
import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;
```
- Package declaration and imports
- CompletableFuture is the key class for async programming

```java
public class AsyncOrderService {
    private static final Logger logger = AppLogger.getLogger(AsyncOrderService.class);
```
- Class declaration with logger instance

```java
    public void processOrder() {
        logger.info("Starting order processing");
```
- Main method to process orders
- Logs the start of processing

```java
        CompletableFuture<String> paymentService =
                CompletableFuture.supplyAsync(() -> {
                    sleep();
                    logger.info("Payment completed");
                    return "PAYMENT_OK";
                });
```
- **Creates async payment task**
- `supplyAsync()` runs the lambda in a separate thread from ForkJoinPool
- Lambda expression: `() -> { ... }` is the task to execute
- Sleeps for 1 second (simulates payment processing)
- Returns "PAYMENT_OK" when done
- Runs independently without blocking main thread

```java
        CompletableFuture<String> inventoryService =
                CompletableFuture.supplyAsync(() -> {
                    sleep();
                    logger.info("Inventory updated");
                    return "INVENTORY_OK";
                });
```
- **Creates async inventory task**
- Similar to payment service
- Runs in parallel with payment service
- Both services execute simultaneously (not sequentially)

```java
        CompletableFuture<Void> finalResult =
                paymentService.thenCombine(inventoryService,
                        (payment, inventory) -> payment + " & " + inventory)
```
- **Combines both async results**
- `thenCombine()` waits for BOTH futures to complete
- Takes results from both: payment and inventory
- Combines them into a single string: "PAYMENT_OK & INVENTORY_OK"

```java
                        .thenAccept(result ->
                                logger.info("Order processed successfully: {}", result)
                        );
```
- **Processes the combined result**
- `thenAccept()` consumes the result (doesn't return anything)
- Logs the final combined result
- Returns CompletableFuture<Void> (no return value)

```java
        finalResult.join();
    }
```
- **Waits for completion**
- `join()` blocks the current thread until async operations complete
- Without this, main thread would continue before async tasks finish
- Similar to Future.get() but throws unchecked exceptions

```java
    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted during processing");
            Thread.currentThread().interrupt();
        }
    }
}
```
- **Helper method to simulate delay**
- Sleeps for 1000 milliseconds (1 second)
- Catches InterruptedException (required for Thread.sleep)
- Re-interrupts the thread to preserve interrupt status
- Best practice for handling interruptions

**Execution Timeline:**
- Time 0s: Both payment and inventory start simultaneously
- Time 1s: Both complete (parallel execution)
- Total time: ~1 second (not 2 seconds!)

---

### 3️⃣ ThreadSafeCache.java - Thread-Safe Caching

**Location:** `src/main/java/com/concurrency/cache/ThreadSafeCache.java`

**Purpose:** Demonstrates thread-safe operations using ConcurrentHashMap and ReentrantLock

**Line-by-Line Explanation:**

```java
package com.concurrency.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;
```
- Imports ConcurrentHashMap: thread-safe map implementation
- Imports ReentrantLock: explicit locking mechanism

```java
public class ThreadSafeCache {
    private static final Logger logger = AppLogger.getLogger(ThreadSafeCache.class);
```
- Class declaration with logger

```java
    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
```
- **cache:** ConcurrentHashMap allows concurrent reads/writes without external synchronization
- **lock:** ReentrantLock provides explicit locking for additional safety
- Both are marked `final` so they can't be reassigned

```java
    public void put(String key, String value) {
        if (key == null || value == null) {
            logger.warn("Null key or value provided to cache");
            return;
        }
```
- **put method:** Stores key-value pairs
- Validates inputs (null check)
- Returns early if null to prevent NullPointerException

```java
        lock.lock();
        try {
            logger.info("Putting into cache: {} = {}", key, value);
            cache.put(key, value);
        } finally {
            lock.unlock();
        }
    }
```
- **Acquires lock** before modifying cache
- `lock.lock()` blocks if another thread holds the lock
- try-finally ensures lock is ALWAYS released
- Even if exception occurs, finally block executes
- Prevents deadlocks from unreleased locks

```java
    public String get(String key) {
        if (key == null) {
            logger.warn("Null key provided to cache get");
            return null;
        }
        
        logger.info("Getting from cache: {}", key);
        return cache.get(key);
    }
}
```
- **get method:** Retrieves values from cache
- Validates key is not null
- No lock needed for reads (ConcurrentHashMap handles it)
- Returns null if key doesn't exist

**Why Thread-Safe?**
- ConcurrentHashMap: Multiple threads can read simultaneously
- ReentrantLock: Only one thread can write at a time
- Prevents data corruption in multi-threaded environment

---

### 4️⃣ ParallelFileProcessor.java - Parallel File Processing

**Location:** `src/main/java/com/concurrency/file/ParallelFileProcessor.java`

**Purpose:** Processes multiple files in parallel using ExecutorService

**Line-by-Line Explanation:**

```java
package com.concurrency.file;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import com.concurrency.enums.FileStatus;
import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;
```
- Imports ExecutorService framework classes
- Future and Callable for async task execution

```java
public class ParallelFileProcessor {
    private static final Logger logger = AppLogger.getLogger(ParallelFileProcessor.class);
    private static final int THREAD_POOL_SIZE = 3;
```
- Defines thread pool size as 3
- Means maximum 3 files can process simultaneously

```java
    public void processFiles(List<String> files) throws Exception {
        if (files == null || files.isEmpty()) {
            logger.warn("No files provided for processing");
            return;
        }
```
- Main method to process file list
- Validates input (null/empty check)
- Returns early if no files to process

```java
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<FileStatus>> results = new ArrayList<>();
```
- **Creates thread pool** with 3 worker threads
- These threads are reused for all tasks
- **results list:** Stores Future objects for each task
- Future represents a pending result

```java
        try {
            // Submit all tasks
            for (String file : files) {
                FileTask task = new FileTask(file);
                results.add(executor.submit(task));
            }
```
- **Submits tasks to executor**
- Creates FileTask for each file
- `executor.submit()` adds task to queue
- Returns Future immediately (doesn't wait)
- Tasks start executing in thread pool

```java
            // Collect results
            for (Future<FileStatus> future : results) {
                FileStatus status = future.get();
                logger.info("File processed with status: {}", status);
            }
```
- **Waits for all tasks to complete**
- `future.get()` blocks until that task finishes
- Retrieves the FileStatus result
- Logs each file's processing status

```java
        } finally {
            executor.shutdown();
```
- **Initiates graceful shutdown**
- No new tasks accepted
- Existing tasks continue to completion
- Always in finally block to ensure cleanup

```java
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
```
- **Waits up to 60 seconds** for tasks to finish
- If timeout occurs, forces shutdown with `shutdownNow()`
- Interrupts running tasks

```java
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
```
- Handles interruption during shutdown
- Forces immediate shutdown
- Preserves interrupt status

**Execution Flow:**
1. Create thread pool (3 threads)
2. Submit 3 file tasks (all start immediately)
3. Wait for all to complete
4. Collect results
5. Shutdown executor

---

### 5️⃣ FileTask.java - Callable File Task

**Location:** `src/main/java/com/concurrency/file/FileTask.java`

**Purpose:** Represents a single file processing task that returns a result

**Line-by-Line Explanation:**

```java
package com.concurrency.file;

import java.util.concurrent.Callable;
import com.concurrency.enums.FileStatus;
import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;
```
- Imports Callable interface (returns result, unlike Runnable)

```java
public class FileTask implements Callable<FileStatus> {
    private static final Logger logger = AppLogger.getLogger(FileTask.class);
    private final String fileName;
```
- **Implements Callable<FileStatus>**
- Generic type FileStatus is the return type
- Stores fileName as instance variable

```java
    public FileTask(String fileName) {
        this.fileName = fileName;
    }
```
- Constructor to initialize fileName
- Each task processes one file

```java
    @Override
    public FileStatus call() throws Exception {
        if (fileName == null || fileName.trim().isEmpty()) {
            logger.error("Invalid file name provided");
            return FileStatus.FAILED;
        }
```
- **call() method:** Required by Callable interface
- Validates fileName
- Returns FAILED status for invalid names

```java
        logger.info("Processing file: {}", fileName);

        try {
            // Simulate file processing time
            Thread.sleep(1000);
            logger.info("Completed file: {}", fileName);
            return FileStatus.COMPLETED;
```
- Logs start of processing
- Sleeps 1 second (simulates actual file processing)
- Returns COMPLETED status on success

```java
        } catch (InterruptedException e) {
            logger.error("File processing interrupted: {}", fileName);
            Thread.currentThread().interrupt();
            return FileStatus.FAILED;
        }
    }
}
```
- Catches interruption
- Preserves interrupt status
- Returns FAILED status

**Callable vs Runnable:**
- Callable: Can return a value and throw checked exceptions
- Runnable: No return value, can't throw checked exceptions

---

### 6️⃣ NotificationTask.java - Runnable Task

**Location:** `src/main/java/com/concurrency/notification/NotificationTask.java`

**Purpose:** Demonstrates Runnable for fire-and-forget operations

**Line-by-Line Explanation:**

```java
package com.concurrency.notification;

import com.concurrency.logging.AppLogger;
import org.slf4j.Logger;
```
- Simple imports for logging

```java
public class NotificationTask implements Runnable {
    private static final Logger logger = AppLogger.getLogger(NotificationTask.class);
    private final String message;
```
- **Implements Runnable** (no return value)
- Stores notification message

```java
    public NotificationTask(String message) {
        this.message = message;
    }
```
- Constructor to set message

```java
    @Override
    public void run() {
        logger.info("Sending notification: {}", message);
        try {
            Thread.sleep(500);
            logger.info("Notification sent: {}", message);
        } catch (InterruptedException e) {
            logger.error("Notification interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
```
- **run() method:** Required by Runnable
- Logs notification sending
- Sleeps 500ms (simulates sending)
- Handles interruption properly
- No return value (void)

**Use Case:** When you don't need a result, just want to execute a task

---

### 7️⃣ FileStatus.java - Enum

**Location:** `src/main/java/com/concurrency/enums/FileStatus.java`

```java
public enum FileStatus {
    PENDING,      // Waiting to be processed
    PROCESSING,   // Currently being processed
    COMPLETED,    // Successfully completed
    FAILED        // Processing failed
}
```
- Enum defines fixed set of constants
- Type-safe way to represent file processing states
- Used as return type in FileTask

---

### 8️⃣ FileProcessingException.java - Custom Exception

**Location:** `src/main/java/com/concurrency/exception/FileProcessingException.java`

```java
public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super(message);
    }
    
    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
```
- Custom exception for file processing errors
- Extends RuntimeException (unchecked exception)
- Two constructors: with/without cause
- Not currently used but available for error handling

---

### 9️⃣ AppLogger.java - Logger Utility

**Location:** `src/main/java/com/concurrency/logging/AppLogger.java`

```java
public class AppLogger {
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
```
- Utility class for creating loggers
- Static method returns logger for any class
- Uses SLF4J LoggerFactory
- Centralizes logger creation

---

### 🔟 ThreadUtil.java - Thread Utility

**Location:** `src/main/java/com/concurrency/util/ThreadUtil.java`

```java
public class ThreadUtil {
    private static final Logger logger = AppLogger.getLogger(ThreadUtil.class);
    
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Thread sleep interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
```
- Utility for thread operations
- Wraps Thread.sleep with proper exception handling
- Reusable across application
- Not currently used but available

---

## 🔄 Execution Flow

### Complete Application Flow (Step-by-Step):

```
1. Application Starts
   └─> Main.main() executes
   └─> Logger initialized

2. Parallel File Processing
   └─> Creates list: ["file1.txt", "file2.txt", "file3.txt"]
   └─> Creates ExecutorService with 3 threads
   └─> Submits 3 FileTask instances
   └─> All 3 files process simultaneously (1 second each)
   └─> Collects results: [COMPLETED, COMPLETED, COMPLETED]
   └─> Shuts down executor
   └─> Total time: ~1 second (parallel)

3. Thread-Safe Cache
   └─> Creates ThreadSafeCache instance
   └─> Puts "user1" -> "Pradeep" (with lock)
   └─> Puts "user2" -> "Kumar" (with lock)
   └─> Gets "user1" -> Returns "Pradeep"
   └─> Logs the value

4. Async Order Processing
   └─> Creates AsyncOrderService instance
   └─> Starts payment service (async, 1 second)
   └─> Starts inventory service (async, 1 second)
   └─> Both run in parallel
   └─> Combines results: "PAYMENT_OK & INVENTORY_OK"
   └─> Logs combined result
   └─> Total time: ~1 second (parallel)

5. Notification Task
   └─> Creates NotificationTask with message
   └─> Creates new Thread
   └─> Starts thread (runs in background)
   └─> Waits for completion with join()
   └─> Notification sent after 500ms

6. Application Completes
   └─> Logs success message
   └─> Program exits
```

**Total Execution Time:** ~3-4 seconds (due to parallel execution)

---

## 🧪 Testing

### Test Files Overview:

#### 1. CacheTest.java
```java
@Test
void testCachePutAndGet() {
    // Tests basic put and get operations
    // Verifies value is stored and retrieved correctly
}

@Test
void testCacheWithNullKey() {
    // Tests null key handling
    // Verifies null returns null
}

@Test
void testCacheWithNullValue() {
    // Tests null value handling
    // Verifies null value not stored
}
```

#### 2. AsyncOrderServiceTest.java
```java
@Test
@Timeout(value = 5, unit = TimeUnit.SECONDS)
void testProcessOrder() {
    // Tests order processing completes within 5 seconds
    // Verifies no exceptions thrown
}
```

#### 3. ParallelFileProcessorTest.java
```java
@Test
void testProcessFiles() {
    // Tests processing multiple files
    // Verifies successful completion
}

@Test
void testProcessEmptyFileList() {
    // Tests handling of empty file list
    // Verifies graceful handling
}
```

**Running Tests:**
```bash
mvn test
```

---

## 🎓 Key Concepts Demonstrated

### 1. **Multithreading**
- Creating and managing threads
- Thread lifecycle (start, run, join)
- Thread interruption handling

### 2. **ExecutorService**
- Thread pool management
- Task submission and execution
- Graceful shutdown

### 3. **Callable vs Runnable**
- Callable: Returns result, throws exceptions
- Runnable: No return, fire-and-forget

### 4. **CompletableFuture**
- Asynchronous programming
- Non-blocking operations
- Combining multiple async tasks

### 5. **Thread Safety**
- ConcurrentHashMap for concurrent access
- ReentrantLock for explicit locking
- Proper lock management (try-finally)

### 6. **Best Practices**
- Proper exception handling
- Resource cleanup (finally blocks)
- Interrupt status preservation
- Comprehensive logging

### 7. **Design Patterns**
- Separation of concerns
- Utility classes
- Custom exceptions
- Enum for constants

---

## 🚀 How to Run

### Prerequisites:
- Java 17 or higher
- Maven 3.6+

### Commands:
```bash
# Compile the project
mvn compile

# Run tests
mvn test

# Run the application
mvn exec:java -Dexec.mainClass="com.concurrency.Main"

# Package as JAR
mvn package
```

---

## 📊 Performance Analysis

### Sequential vs Parallel Execution:

**If Sequential (one after another):**
- File processing: 3 files × 1 second = 3 seconds
- Order processing: 2 services × 1 second = 2 seconds
- Total: ~5 seconds

**With Parallel Execution (current implementation):**
- File processing: 3 files in parallel = 1 second
- Order processing: 2 services in parallel = 1 second
- Total: ~3 seconds

**Performance Gain:** ~40% faster!

---

## 🎯 Learning Outcomes

After understanding this project, you will know:

1. ✅ How to create and manage threads in Java
2. ✅ How to use ExecutorService for thread pools
3. ✅ How to implement asynchronous operations with CompletableFuture
4. ✅ How to ensure thread safety with locks and concurrent collections
5. ✅ How to handle exceptions in concurrent code
6. ✅ How to properly shutdown thread pools
7. ✅ How to write unit tests for concurrent code
8. ✅ Best practices for logging in multi-threaded applications

---

## 📝 Summary

This project is a **comprehensive demonstration of Java concurrency** covering:
- **Thread management** (creation, lifecycle, interruption)
- **Parallel processing** (ExecutorService, thread pools)
- **Asynchronous programming** (CompletableFuture)
- **Thread safety** (locks, concurrent collections)
- **Best practices** (exception handling, resource cleanup, logging)

Each component is designed to teach a specific concurrency concept while working together as a cohesive application.

---

**Created for:** Java Concurrency Learning  
**Module:** M6  
**Last Updated:** 2024
