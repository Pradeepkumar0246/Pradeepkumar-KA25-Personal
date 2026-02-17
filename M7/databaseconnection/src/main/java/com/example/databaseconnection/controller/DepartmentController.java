package com.example.databaseconnection.controller;

import com.example.databaseconnection.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Department operations
 * Handles all department-related HTTP requests
 */
@RestController
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Creates a new department
     * @return Success message
     */
    @GetMapping("/department/create")
    public String createDepartment() {
        logger.info("Creating new department");
        departmentService.createDepartment();
        logger.info("Department created successfully");
        return "Department created";
    }
}
