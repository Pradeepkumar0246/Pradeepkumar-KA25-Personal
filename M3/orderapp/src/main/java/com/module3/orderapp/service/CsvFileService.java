package com.module3.orderapp.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.module3.orderapp.model.OrderRecord;

/**
 * Service class for reading and parsing CSV files containing order data.
 * Handles file reading, parsing, and error management for malformed data.
 */
@Service
public class CsvFileService {
    
    // Logger for tracking CSV file operations
    private static final Logger logger = LoggerFactory.getLogger(CsvFileService.class);

    /**
     * Reads orders from a CSV file and returns both valid orders and malformed entries.
     * 
     * @param path The path to the CSV file in the resources directory
     * @return Map containing "orders" (List<OrderRecord>) and "malformed" (List<String>)
     */
    public Map<String, List<?>> readOrders(String path) {
        logger.info("Reading orders from CSV file: {}", path);
        
        List<OrderRecord> orders = new ArrayList<>();
        List<String> malformed = new ArrayList<>();

        // Load the CSV file from resources
        InputStream is = getClass().getResourceAsStream(path);

        if (is == null) {
            logger.error("CSV file not found: {}", path);
            malformed.add("CSV file not found: " + path);
            return createResult(orders, malformed);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            boolean header = true;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                // Skip header row
                if (header) {
                    logger.debug("Skipping header line: {}", line);
                    header = false;
                    continue;
                }

                try {
                    // Parse CSV line into OrderRecord
                    OrderRecord order = parseCsvLine(line);
                    orders.add(order);
                    logger.debug("Successfully parsed order ID: {}", order.getOrderId());
                    
                } catch (Exception e) {
                    logger.warn("Malformed line {} in CSV: {}", lineNumber, line);
                    malformed.add("MALFORMED LINE: " + line);
                }
            }
            
            logger.info("CSV processing completed. Valid orders: {}, Malformed entries: {}", 
                       orders.size(), malformed.size());

        } catch (Exception e) {
            logger.error("IO error while reading CSV file: {}", e.getMessage());
            malformed.add("IO error while reading CSV");
        }

        return createResult(orders, malformed);
    }
    
    /**
     * Parses a single CSV line into an OrderRecord.
     * 
     * @param line The CSV line to parse
     * @return OrderRecord created from the CSV data
     * @throws Exception if the line cannot be parsed
     */
    private OrderRecord parseCsvLine(String line) throws Exception {
        String[] fields = line.split(",");
        
        if (fields.length != 4) {
            throw new IllegalArgumentException("Expected 4 fields, got " + fields.length);
        }
        
        return new OrderRecord(
                Integer.parseInt(fields[0].trim()),  // orderId
                fields[1].trim(),                    // product
                Integer.parseInt(fields[2].trim()),  // quantity
                Double.parseDouble(fields[3].trim()) // price
        );
    }
    
    /**
     * Creates the result map containing orders and malformed entries.
     * 
     * @param orders List of valid OrderRecord objects
     * @param malformed List of malformed line descriptions
     * @return Map with "orders" and "malformed" keys
     */
    private Map<String, List<?>> createResult(List<OrderRecord> orders, List<String> malformed) {
        Map<String, List<?>> result = new HashMap<>();
        result.put("orders", orders);
        result.put("malformed", malformed);
        return result;
    }
}
