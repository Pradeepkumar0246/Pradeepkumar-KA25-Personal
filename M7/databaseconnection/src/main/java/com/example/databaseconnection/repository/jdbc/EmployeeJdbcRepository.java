package com.example.databaseconnection.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findEmployeeNames() {
        return jdbcTemplate.query(
                "SELECT employee_name FROM employee",
                (rs, rowNum) -> rs.getString("employee_name")
        );
    }
}
