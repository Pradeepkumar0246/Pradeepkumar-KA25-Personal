package com.concurrency.file;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for ParallelFileProcessor
 */
public class ParallelFileProcessorTest {

    @Test
    void testProcessFiles() throws Exception {
        ParallelFileProcessor processor = new ParallelFileProcessor();
        List<String> files = Arrays.asList("test1.txt", "test2.txt");
        
        // Should complete without throwing exception
        processor.processFiles(files);
    }
    
    @Test
    void testProcessEmptyFileList() throws Exception {
        ParallelFileProcessor processor = new ParallelFileProcessor();
        List<String> files = Arrays.asList();
        
        // Should handle empty list gracefully
        processor.processFiles(files);
    }
}