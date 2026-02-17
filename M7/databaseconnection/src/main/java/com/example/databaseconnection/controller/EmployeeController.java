package com.example.databaseconnection.controller;

import com.example.databaseconnection.service.EmployeeService;
import com.example.databaseconnection.repository.jdbc.EmployeeJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * REST Controller for Employee operations
 * Handles all employee-related HTTP requests
 */
@RestController
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final EmployeeJdbcRepository employeeJdbcRepository;

    public EmployeeController(EmployeeService employeeService,
                              EmployeeJdbcRepository employeeJdbcRepository) {
        this.employeeService = employeeService;
        this.employeeJdbcRepository = employeeJdbcRepository;
    }

    // ---------- BASIC ----------
    /**
     * Retrieves all employees from the database
     * @return List of all employees
     */
    @GetMapping("/employees")
    public Object getEmployees() {
        logger.info("Fetching all employees");
        return employeeService.getAllEmployees();
    }

    /**
     * Creates a new employee in the specified department
     * @param deptId Department ID to assign the employee
     * @return Success message
     */
    @GetMapping("/employee/create/{deptId}")
    public String createEmployee(@PathVariable Integer deptId) {
        logger.info("Creating employee for department ID: {}", deptId);
        employeeService.createEmployee(deptId);
        logger.info("Employee created successfully");
        return "Employee created";
    }

    // ---------- MANY TO MANY ----------
    /**
     * Assigns an employee to a project (Many-to-Many relationship)
     * @param empId Employee ID
     * @param projectId Project ID
     * @return Success message
     */
    @GetMapping("/employee/{empId}/project/{projectId}")
    public String assignProject(@PathVariable Integer empId,
                                @PathVariable Integer projectId) {
        logger.info("Assigning employee {} to project {}", empId, projectId);
        employeeService.assignEmployeeToProject(empId, projectId);
        logger.info("Employee assigned to project successfully");
        return "Employee assigned to project";
    }

    // ---------- JPQL ----------
    /**
     * Finds employees with salary greater than specified amount using JPQL
     * @param amount Minimum salary amount
     * @return List of employees with salary greater than amount
     */
    @GetMapping("/employees/salary/{amount}")
    public Object salaryGreater(@PathVariable BigDecimal amount) {
        logger.info("Fetching employees with salary greater than {}", amount);
        return employeeService.employeesWithHighSalary(amount);
    }

    /**
     * Finds employees by department name using JPQL
     * @param name Department name
     * @return List of employees in the specified department
     */
    @GetMapping("/employees/department/{name}")
    public Object byDepartment(@PathVariable String name) {
        logger.info("Fetching employees for department: {}", name);
        return employeeService.employeesByDepartment(name);
    }

    /**
     * Finds employees with salary above average using JPQL
     * @return List of employees with above average salary
     */
    @GetMapping("/employees/above-average")
    public Object aboveAverage() {
        logger.info("Fetching employees with above average salary");
        return employeeService.employeesAboveAverageSalary();
    }

    // ---------- JDBC ----------
    /**
     * Retrieves employee names using JDBC Template
     * @return List of employee names
     */
    @GetMapping("/employees/jdbc")
    public Object jdbcEmployees() {
        logger.info("Fetching employee names using JDBC");
        return employeeJdbcRepository.findEmployeeNames();
    }
}
