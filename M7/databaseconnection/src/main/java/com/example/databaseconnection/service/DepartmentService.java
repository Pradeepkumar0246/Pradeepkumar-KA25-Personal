package com.example.databaseconnection.service;

import com.example.databaseconnection.entity.Department;
import com.example.databaseconnection.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service layer for Department business logic
 * Handles all department-related operations
 */
@Service
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Creates a new department with default values
     */
    public void createDepartment() {
        logger.info("Creating new department with name: IT");
        
        Department dept = new Department();
        dept.setName("IT");
        dept.setBudget(new BigDecimal("500000"));

        departmentRepository.save(dept);
        logger.info("Department created successfully with ID: {}", dept.getId());
    }
}
