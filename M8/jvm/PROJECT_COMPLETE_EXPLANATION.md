# JVM PERFORMANCE PLAYGROUND - COMPLETE PROJECT EXPLANATION

## 📋 PROJECT OVERVIEW
This is a **JVM Performance Testing and Monitoring Project** that demonstrates how the Java Virtual Machine (JVM) behaves under different stress conditions. It simulates three critical scenarios: Memory Stress, Memory Leaks, and CPU Stress, while monitoring JVM metrics in real-time.

**Purpose**: Educational tool to understand JVM memory management, garbage collection, thread behavior, and performance characteristics.

---

## 🏗️ PROJECT STRUCTURE

```
M8/jvm/
├── pom.xml                          # Maven build configuration
└── src/main/java/com/example/
    ├── Main.java                    # Entry point - orchestrates all scenarios
    ├── Scenario.java                # Interface defining scenario contract
    ├── JvmInfoPrinter.java          # Prints JVM runtime information
    ├── MemoryStress.java            # Simulates high memory allocation
    ├── MemoryLeak.java              # Simulates memory leak pattern
    └── CpuStress.java               # Simulates high CPU usage
```

---

## 📦 1. POM.XML - Maven Configuration

### What is Maven?
Maven is a build automation and dependency management tool for Java projects.

### Line-by-Line Explanation:

```xml
<?xml version="1.0" encoding="UTF-8"?>
```
- XML declaration specifying version 1.0 and UTF-8 encoding

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
```
- Root element defining Maven project with XML namespaces for validation

```xml
    <modelVersion>4.0.0</modelVersion>
```
- Specifies POM model version (always 4.0.0 for Maven 2+)

```xml
    <groupId>com.example</groupId>
```
- Unique identifier for the organization/group (like a package name)

```xml
    <artifactId>jvm</artifactId>
```
- Name of the project/artifact being built

```xml
    <version>1.0-SNAPSHOT</version>
```
- Project version (SNAPSHOT means it's under development)

```xml
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
```
- Configures Java version 17 for both source code and compiled bytecode

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.4.1</version>
```
- Configures the JAR packaging plugin (version 3.4.1)

```xml
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.example.Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
```
- Sets Main class in JAR manifest so it can be executed with `java -jar`

**Result**: Creates executable JAR file `jvm-1.0-SNAPSHOT.jar` with Main as entry point

---

## 🎯 2. SCENARIO.JAVA - Interface Contract

### Purpose:
Defines a common contract that all stress test scenarios must follow.

### Line-by-Line Explanation:

```java
package com.example;
```
- Declares this class belongs to com.example package (organizes code)

```java
public interface Scenario {
```
- Defines a public interface named Scenario (contract for all test scenarios)

```java
    void run(long durationMs) throws Exception;
```
- Abstract method signature that all implementing classes must provide
- **Parameters**: `durationMs` - how long the scenario should run in milliseconds
- **throws Exception**: Allows implementations to throw any exception
- **Returns**: void (no return value)

```java
}
```
- End of interface

**Why Interface?**
- Ensures all scenarios (MemoryStress, MemoryLeak, CpuStress) have consistent run() method
- Enables polymorphism - can treat all scenarios uniformly
- Makes code extensible - easy to add new scenarios

---

## 📊 3. JVMINFOPRINTER.JAVA - JVM Metrics Display

### Purpose:
Displays real-time JVM information including memory, threads, and runtime details.

### Line-by-Line Explanation:

```java
package com.example;
```
- Package declaration

```java
import java.lang.management.*;
```
- Imports all classes from java.lang.management package (JVM monitoring APIs)

```java
public class JvmInfoPrinter {
```
- Public class declaration

```java
    public static void print() {
```
- Static method (can be called without creating object: JvmInfoPrinter.print())
- Public access - can be called from anywhere

```java
        Runtime runtime = Runtime.getRuntime();
```
- Gets singleton Runtime instance representing the JVM runtime environment
- Provides access to: available processors, memory, etc.

```java
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
```
- Gets MXBean for memory management
- Provides heap and non-heap memory usage information

```java
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
```
- Gets MXBean for thread management
- Provides thread count, peak threads, etc.

```java
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
```
- Gets MXBean for runtime information
- Provides JVM name, version, uptime, etc.

```java
        System.out.println("===== JVM INFO =====");
```
- Prints header to console

```java
        System.out.println("JVM Name    : " + runtimeBean.getVmName());
```
- Prints JVM name (e.g., "Java HotSpot(TM) 64-Bit Server VM")

```java
        System.out.println("JVM Version : " + runtimeBean.getVmVersion());
```
- Prints JVM version (e.g., "17.0.1+12")

```java
        System.out.println("Uptime (ms) : " + runtimeBean.getUptime());
```
- Prints how long JVM has been running in milliseconds

```java
        System.out.println("\n===== HEAP =====");
```
- Prints heap memory section header (\n = newline)

```java
        System.out.println("Used     : " + memoryBean.getHeapMemoryUsage().getUsed() / 1024 / 1024 + " MB");
```
- Gets heap memory currently used
- Converts bytes → KB (÷1024) → MB (÷1024)
- **Heap**: Memory where Java objects are stored

```java
        System.out.println("Max      : " + memoryBean.getHeapMemoryUsage().getMax() / 1024 / 1024 + " MB");
```
- Prints maximum heap memory available (set by -Xmx JVM flag)

```java
        System.out.println("\n===== NON-HEAP =====");
```
- Non-heap section header

```java
        System.out.println("Used     : " + memoryBean.getNonHeapMemoryUsage().getUsed() / 1024 / 1024 + " MB");
```
- **Non-Heap**: Memory for JVM internals (method area, code cache, etc.)

```java
        System.out.println("\n===== THREADS =====");
```
- Thread information section

```java
        System.out.println("Live Threads : " + threadBean.getThreadCount());
```
- Current number of live threads in JVM

```java
        System.out.println("Peak Threads : " + threadBean.getPeakThreadCount());
```
- Maximum number of threads since JVM started

```java
        System.out.println("\nProcessors : " + runtime.availableProcessors());
```
- Number of CPU cores available to JVM

```java
    }
}
```
- End of method and class

**Key Concepts**:
- **Heap**: Where objects live (garbage collected)
- **Non-Heap**: JVM metadata, compiled code
- **MXBean**: Management Extension Bean - JVM monitoring interface

---

## 💾 4. MEMORYSTRESS.JAVA - Memory Allocation Test

### Purpose:
Simulates high memory allocation to stress test garbage collection and heap management.

### Line-by-Line Explanation:

```java
package com.example;
```
- Package declaration

```java
import java.util.ArrayList;
import java.util.List;
```
- Imports ArrayList (resizable array) and List interface

```java
public class MemoryStress implements Scenario {
```
- Class implements Scenario interface (must provide run method)

```java
    @Override
```
- Annotation indicating this method overrides interface method

```java
    public void run(long durationMs) throws InterruptedException {
```
- Implements run method from Scenario interface
- **Parameter**: durationMs - how long to run the test
- **throws InterruptedException**: Required because Thread.sleep() can be interrupted

```java
        long end = System.currentTimeMillis() + durationMs;
```
- Calculates end time: current time + duration
- System.currentTimeMillis() returns current time in milliseconds since epoch (Jan 1, 1970)

```java
        List<byte[]> list = new ArrayList<>();
```
- Creates empty ArrayList to hold byte arrays
- **Purpose**: Keep references so objects aren't garbage collected immediately

```java
        while (System.currentTimeMillis() < end) {
```
- Loop continues until current time reaches end time

```java
            list.add(new byte[5 * 1024 * 1024]); // 5 MB
```
- Creates new byte array of 5 MB (5 × 1024 KB × 1024 bytes)
- Adds to list (keeps reference, preventing garbage collection)
- **Effect**: Heap memory grows by 5 MB each iteration

```java
            System.out.println("[MemoryStress] Allocated chunks: " + list.size());
```
- Prints how many 5MB chunks have been allocated

```java
            Thread.sleep(500);
```
- Pauses execution for 500 milliseconds (0.5 seconds)
- Gives time to observe memory growth

```java
        }
    }
}
```
- End of loop, method, and class

**What Happens**:
1. Every 0.5 seconds, allocates 5 MB of memory
2. Keeps references in list (objects stay alive)
3. After method ends, list goes out of scope
4. Garbage collector can reclaim all allocated memory
5. **Tests**: GC efficiency, heap expansion, memory pressure handling

---

## 🚨 5. MEMORYLEAK.JAVA - Memory Leak Simulation

### Purpose:
Simulates a memory leak by storing objects in a static field that never gets cleared.

### Line-by-Line Explanation:

```java
package com.example;
```
- Package declaration

```java
import java.util.ArrayList;
import java.util.List;
```
- Import collection classes

```java
public class MemoryLeak implements Scenario {
```
- Implements Scenario interface

```java
    private static final List<byte[]> LEAK = new ArrayList<>();
```
- **CRITICAL LINE**: Static field that persists for entire JVM lifetime
- **static**: Belongs to class, not instance (never garbage collected)
- **final**: Reference cannot be changed (list itself can grow)
- **LEAK**: Holds references preventing garbage collection
- **This is the memory leak**: Objects added here are never removed

```java
    @Override
    public void run(long durationMs) throws InterruptedException {
```
- Implements run method

```java
        long end = System.currentTimeMillis() + durationMs;
```
- Calculate end time

```java
        while (System.currentTimeMillis() < end) {
```
- Loop until duration expires

```java
            LEAK.add(new byte[3 * 1024 * 1024]); // 3 MB
```
- Allocates 3 MB byte array
- Adds to static LEAK list
- **Memory Leak**: These objects are never removed, even after run() ends

```java
            System.out.println("[MemoryLeak] Leaked objects: " + LEAK.size());
```
- Prints count of leaked objects

```java
            Thread.sleep(1000);
```
- Wait 1 second between allocations

```java
        }
    }
}
```
- End of method and class

**Key Difference from MemoryStress**:
- **MemoryStress**: Objects can be garbage collected after run() ends
- **MemoryLeak**: Objects stay in memory forever (static field)

**Real-World Analogy**:
- Like leaving lights on in rooms you'll never visit again
- Memory is allocated but never freed
- Eventually leads to OutOfMemoryError

**Common Causes of Memory Leaks**:
- Static collections that grow indefinitely
- Listeners not unregistered
- Unclosed resources (files, connections)
- ThreadLocal variables not cleaned

---

## ⚡ 6. CPUSTRESS.JAVA - CPU Intensive Test

### Purpose:
Simulates high CPU usage by performing continuous mathematical calculations.

### Line-by-Line Explanation:

```java
package com.example;
```
- Package declaration

```java
public class CpuStress implements Scenario {
```
- Implements Scenario interface

```java
    @Override
    public void run(long durationMs) {
```
- Implements run method
- **Note**: Doesn't throw InterruptedException (no Thread.sleep)

```java
        long end = System.currentTimeMillis() + durationMs;
```
- Calculate end time

```java
        while (System.currentTimeMillis() < end) {
```
- Tight loop - runs as fast as possible (no sleep)

```java
            Math.sqrt(System.nanoTime());
```
- Performs square root calculation on current nanosecond time
- **System.nanoTime()**: Returns current time in nanoseconds (high precision)
- **Math.sqrt()**: Computationally expensive operation
- **Result is discarded**: Only purpose is to consume CPU cycles

```java
        }
```
- Loop continues until time expires

```java
        System.out.println("[CpuStress] Finished");
```
- Prints completion message

```java
    }
}
```
- End of method and class

**What Happens**:
1. Loop runs millions of times per second
2. Each iteration performs floating-point calculation
3. No sleep/wait - maxes out one CPU core
4. **Tests**: CPU scheduling, thread performance, thermal behavior

**Why This Stresses CPU**:
- Tight loop with no blocking operations
- Continuous mathematical calculations
- No I/O waits or sleeps
- Single thread can saturate one CPU core at 100%

---

## 🚀 7. MAIN.JAVA - Application Entry Point

### Purpose:
Orchestrates the execution of all scenarios in sequence with timing and monitoring.

### Line-by-Line Explanation:

```java
package com.example;
```
- Package declaration

```java
public class Main {
```
- Main class - entry point of application

```java
    public static void main(String[] args) throws Exception {
```
- **main method**: JVM calls this when program starts
- **static**: Can be called without creating Main object
- **void**: Doesn't return a value
- **String[] args**: Command-line arguments (not used here)
- **throws Exception**: Propagates any exceptions to JVM (will terminate program)

```java
        long duration = 15_000; // 15 seconds per scenario
```
- Sets duration for each scenario to 15,000 milliseconds (15 seconds)
- **Underscore**: Java allows underscores in numeric literals for readability

```java
        System.out.println("===== JVM PERFORMANCE PLAYGROUND =====");
```
- Prints program header

```java
        JvmInfoPrinter.print();
```
- Calls static method to display initial JVM state
- Shows baseline metrics before stress tests

```java
        Thread.sleep(3000);
```
- Pauses for 3 seconds (3000 ms)
- Gives time to read JVM info before starting tests

```java
        new MemoryStress().run(duration);
```
- **new MemoryStress()**: Creates new MemoryStress object
- **.run(duration)**: Immediately calls run method with 15-second duration
- **Effect**: Allocates 5 MB every 0.5 seconds for 15 seconds (~150 MB total)

```java
        Thread.sleep(3000);
```
- 3-second pause between scenarios
- Allows GC to clean up MemoryStress allocations

```java
        new MemoryLeak().run(duration);
```
- Creates and runs MemoryLeak scenario
- **Effect**: Leaks 3 MB every second for 15 seconds (~45 MB leaked permanently)

```java
        Thread.sleep(3000);
```
- 3-second pause

```java
        new CpuStress().run(duration);
```
- Creates and runs CpuStress scenario
- **Effect**: Maxes out one CPU core for 15 seconds

```java
        System.out.println("===== ALL SCENARIOS COMPLETED =====");
```
- Prints completion message

```java
    }
}
```
- End of main method and class
- JVM exits after this

**Execution Timeline**:
```
0s:  Program starts
0s:  Print JVM info
3s:  Start MemoryStress (runs until 18s)
18s: Pause
21s: Start MemoryLeak (runs until 36s)
36s: Pause
39s: Start CpuStress (runs until 54s)
54s: Program ends
```

---

## 🔄 EXECUTION FLOW DIAGRAM

```
JVM Starts
    ↓
main() method called
    ↓
duration = 15000 ms
    ↓
Print "JVM PERFORMANCE PLAYGROUND"
    ↓
JvmInfoPrinter.print()
    ├─ Get Runtime, MemoryMXBean, ThreadMXBean, RuntimeMXBean
    ├─ Print JVM Name, Version, Uptime
    ├─ Print Heap Used/Max
    ├─ Print Non-Heap Used
    ├─ Print Thread Count
    └─ Print Processor Count
    ↓
Sleep 3 seconds
    ↓
new MemoryStress().run(15000)
    ├─ Create ArrayList
    ├─ Loop for 15 seconds:
    │   ├─ Allocate 5 MB byte array
    │   ├─ Add to list
    │   ├─ Print chunk count
    │   └─ Sleep 0.5 seconds
    └─ Exit (list becomes eligible for GC)
    ↓
Sleep 3 seconds (GC may run here)
    ↓
new MemoryLeak().run(15000)
    ├─ Loop for 15 seconds:
    │   ├─ Allocate 3 MB byte array
    │   ├─ Add to static LEAK list
    │   ├─ Print leaked object count
    │   └─ Sleep 1 second
    └─ Exit (LEAK list persists - memory leak!)
    ↓
Sleep 3 seconds
    ↓
new CpuStress().run(15000)
    ├─ Tight loop for 15 seconds:
    │   └─ Calculate Math.sqrt(System.nanoTime())
    └─ Print "Finished"
    ↓
Print "ALL SCENARIOS COMPLETED"
    ↓
main() returns
    ↓
JVM Exits
```

---

## 🧠 KEY CONCEPTS EXPLAINED

### 1. **Heap vs Non-Heap Memory**
- **Heap**: Where objects are allocated (managed by GC)
  - Young Generation: New objects
  - Old Generation: Long-lived objects
- **Non-Heap**: JVM internals
  - Metaspace: Class metadata
  - Code Cache: Compiled native code

### 2. **Garbage Collection (GC)**
- Automatic memory management
- Reclaims memory from unreachable objects
- Triggered when heap is full or by JVM heuristics
- **MemoryStress tests GC**: Creates many short-lived objects
- **MemoryLeak defeats GC**: Static references prevent collection

### 3. **Static vs Instance Fields**
- **Instance**: Belongs to object (GC'd when object is unreachable)
- **Static**: Belongs to class (lives until JVM shutdown)
- **Memory Leak**: Static field LEAK holds references forever

### 4. **Thread.sleep() vs Tight Loop**
- **Thread.sleep()**: Yields CPU, allows other threads to run
- **Tight Loop**: Consumes CPU continuously (CpuStress)

### 5. **MXBeans (Management Extensions)**
- JVM monitoring interfaces
- Provide runtime metrics without overhead
- Used by profilers and monitoring tools

---

## 🎓 LEARNING OBJECTIVES

### What This Project Teaches:

1. **JVM Architecture**
   - Memory structure (heap, non-heap)
   - Runtime information access
   - Thread management

2. **Memory Management**
   - Object allocation patterns
   - Garbage collection behavior
   - Memory leak identification

3. **Performance Testing**
   - Stress testing methodologies
   - Resource monitoring
   - Bottleneck identification

4. **Java Best Practices**
   - Interface-based design
   - Static vs instance members
   - Resource lifecycle management

5. **Common Pitfalls**
   - Memory leaks from static collections
   - CPU-intensive operations
   - Uncontrolled memory growth

---

## 🛠️ HOW TO RUN

### Compile and Package:
```bash
cd J:\Java-Program\Java-Training-modules\M8\jvm
mvn clean package
```

### Run the JAR:
```bash
java -jar target/jvm-1.0-SNAPSHOT.jar
```

### Run with JVM Monitoring Flags:
```bash
# Enable GC logging
java -Xlog:gc* -jar target/jvm-1.0-SNAPSHOT.jar

# Set heap size
java -Xms256m -Xmx512m -jar target/jvm-1.0-SNAPSHOT.jar

# Enable JMX monitoring
java -Dcom.sun.management.jmxremote -jar target/jvm-1.0-SNAPSHOT.jar
```

### Monitor with Tools:
- **VisualVM**: Visual monitoring and profiling
- **JConsole**: JMX-based monitoring
- **Java Mission Control**: Advanced profiling

---

## 📈 EXPECTED BEHAVIOR

### MemoryStress Phase:
- Heap usage increases rapidly
- GC runs frequently
- After phase ends, memory drops (GC reclaims)

### MemoryLeak Phase:
- Heap usage increases
- Memory is NOT reclaimed after phase
- Leaked memory persists until JVM shutdown

### CpuStress Phase:
- One CPU core reaches 100%
- No memory impact
- System may become less responsive

---

## 🐛 TROUBLESHOOTING

### OutOfMemoryError:
- Increase heap: `-Xmx1g`
- Reduce duration in Main.java

### Program Runs Too Long:
- Reduce `duration` variable (currently 15000 ms)

### CPU Overheating:
- Reduce CpuStress duration
- Add Thread.sleep() in CpuStress loop

---

## 🎯 REAL-WORLD APPLICATIONS

### This Project Simulates:

1. **Memory Stress**: 
   - Batch processing large datasets
   - Image/video processing
   - Caching systems

2. **Memory Leaks**:
   - Long-running servers with bugs
   - Event listeners not cleaned up
   - Session management issues

3. **CPU Stress**:
   - Cryptographic operations
   - Data compression
   - Scientific calculations

---

## 📚 FURTHER EXPLORATION

### Experiment Ideas:

1. **Add JVM Flags**:
   - `-XX:+PrintGCDetails`: See GC activity
   - `-XX:MaxHeapSize=256m`: Limit heap
   - `-XX:+HeapDumpOnOutOfMemoryError`: Dump on OOM

2. **Modify Scenarios**:
   - Change allocation sizes
   - Add multi-threading
   - Implement different leak patterns

3. **Add Monitoring**:
   - Log memory usage to file
   - Create graphs of metrics
   - Add JMX endpoints

4. **Fix the Leak**:
   - Clear LEAK list after run()
   - Use WeakReference
   - Implement cleanup method

---

## ✅ SUMMARY

This project is a **hands-on JVM performance laboratory** that demonstrates:

- ✅ How to monitor JVM metrics programmatically
- ✅ How memory allocation affects heap
- ✅ How memory leaks occur and persist
- ✅ How CPU-intensive operations behave
- ✅ How to structure performance tests
- ✅ How garbage collection works in practice

**Every line of code serves a specific educational purpose** - from the Maven configuration that builds the project, to the interface that enforces contracts, to the scenarios that stress different JVM subsystems.

By running and modifying this project, you gain practical understanding of JVM internals, performance characteristics, and common pitfalls in Java applications.

---

## 📞 QUICK REFERENCE

| File | Purpose | Key Method |
|------|---------|------------|
| Main.java | Entry point | main() |
| Scenario.java | Interface | run(long) |
| JvmInfoPrinter.java | Display metrics | print() |
| MemoryStress.java | Heap stress test | run(long) |
| MemoryLeak.java | Leak simulation | run(long) |
| CpuStress.java | CPU stress test | run(long) |
| pom.xml | Build config | N/A |

**Total Runtime**: ~54 seconds (15s × 3 scenarios + 9s pauses + startup)

**Memory Impact**: ~150 MB temporary + ~45 MB leaked

**CPU Impact**: 100% of one core for 15 seconds

---

*This documentation explains every aspect of the JVM Performance Playground project. Use it as a reference while exploring JVM behavior and performance characteristics.*
