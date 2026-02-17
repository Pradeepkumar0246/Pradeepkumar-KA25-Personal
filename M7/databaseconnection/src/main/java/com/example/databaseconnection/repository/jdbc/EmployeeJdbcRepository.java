package com.example.databaseconnection.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JDBC Repository for direct database access using JdbcTemplate
 * Demonstrates raw SQL execution without JPA
 */
@Repository
public class EmployeeJdbcRepository {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all employee names using JDBC Template
     * @return List of employee names
     */
    public List<String> findEmployeeNames() {
        logger.info("Executing JDBC query to fetch employee names");
        
        List<String> names = jdbcTemplate.query(
                "SELECT employee_name FROM employee",
                (rs, rowNum) -> rs.getString("employee_name")
        );
        
        logger.info("Retrieved {} employee names using JDBC", names.size());
        return names;
    }
}
