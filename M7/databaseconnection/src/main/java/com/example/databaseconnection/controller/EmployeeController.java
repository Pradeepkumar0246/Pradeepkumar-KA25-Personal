package com.example.databaseconnection.controller;

import com.example.databaseconnection.service.EmployeeService;
import com.example.databaseconnection.repository.jdbc.EmployeeJdbcRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeJdbcRepository employeeJdbcRepository;

    public EmployeeController(EmployeeService employeeService,
                              EmployeeJdbcRepository employeeJdbcRepository) {
        this.employeeService = employeeService;
        this.employeeJdbcRepository = employeeJdbcRepository;
    }

    // ---------- BASIC ----------
    @GetMapping("/employees")
    public Object getEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/create/{deptId}")
    public String createEmployee(@PathVariable Integer deptId) {
        employeeService.createEmployee(deptId);
        return "Employee created";
    }

    // ---------- MANY TO MANY ----------
    @GetMapping("/employee/{empId}/project/{projectId}")
    public String assignProject(@PathVariable Integer empId,
                                @PathVariable Integer projectId) {
        employeeService.assignEmployeeToProject(empId, projectId);
        return "Employee assigned to project";
    }

    // ---------- JPQL ----------
    @GetMapping("/employees/salary/{amount}")
    public Object salaryGreater(@PathVariable BigDecimal amount) {
        return employeeService.employeesWithHighSalary(amount);
    }

    @GetMapping("/employees/department/{name}")
    public Object byDepartment(@PathVariable String name) {
        return employeeService.employeesByDepartment(name);
    }

    @GetMapping("/employees/above-average")
    public Object aboveAverage() {
        return employeeService.employeesAboveAverageSalary();
    }

    // ---------- JDBC ----------
    @GetMapping("/employees/jdbc")
    public Object jdbcEmployees() {
        return employeeJdbcRepository.findEmployeeNames();
    }
}
