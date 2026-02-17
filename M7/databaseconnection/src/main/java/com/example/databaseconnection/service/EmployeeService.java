package com.example.databaseconnection.service;

import com.example.databaseconnection.entity.*;
import com.example.databaseconnection.exception.ResourceNotFoundException;
import com.example.databaseconnection.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for Employee business logic
 * Handles all employee-related operations
 */
@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeProjectRepository employeeProjectRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           ProjectRepository projectRepository,
                           EmployeeProjectRepository employeeProjectRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.employeeProjectRepository = employeeProjectRepository;
    }

    // ---------- BASIC ----------
    /**
     * Retrieves all employees from database
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        logger.info("Retrieving all employees from database");
        List<Employee> employees = employeeRepository.findAll();
        logger.info("Found {} employees", employees.size());
        return employees;
    }

    // ---------- TRANSACTION ----------
    /**
     * Creates a new employee and assigns to department
     * @param deptId Department ID to assign employee
     * @throws ResourceNotFoundException if department not found
     */
    @Transactional
    public void createEmployee(Integer deptId) {
        logger.info("Creating employee for department ID: {}", deptId);
        
        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> {
                    logger.error("Department not found with ID: {}", deptId);
                    return new ResourceNotFoundException("Department not found with ID: " + deptId);
                });

        Employee emp = new Employee();
        emp.setName("John");
        emp.setEmail("john" + System.currentTimeMillis() + "@mail.com");
        emp.setSalary(new BigDecimal("60000"));
        emp.setDepartment(dept);

        employeeRepository.save(emp);
        logger.info("Employee created successfully with email: {}", emp.getEmail());
    }

    // ---------- MANY TO MANY ----------
    /**
     * Assigns an employee to a project (Many-to-Many relationship)
     * @param empId Employee ID
     * @param projectId Project ID
     * @throws ResourceNotFoundException if employee or project not found
     */
    @Transactional
    public void assignEmployeeToProject(Integer empId, Integer projectId) {
        logger.info("Assigning employee {} to project {}", empId, projectId);

        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> {
                    logger.error("Employee not found with ID: {}", empId);
                    return new ResourceNotFoundException("Employee not found with ID: " + empId);
                });

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    logger.error("Project not found with ID: {}", projectId);
                    return new ResourceNotFoundException("Project not found with ID: " + projectId);
                });

        EmployeeProjectId id = new EmployeeProjectId();
        id.setEmployeeId(empId);
        id.setProjectId(projectId);

        EmployeeProject ep = new EmployeeProject();
        ep.setId(id);
        ep.setEmployee(emp);
        ep.setProject(project);
        ep.setAssignedDate(LocalDate.now());

        employeeProjectRepository.save(ep);
        logger.info("Employee {} assigned to project {} successfully", empId, projectId);
    }

    // ---------- JPQL ----------
    /**
     * Finds employees with salary greater than specified amount using JPQL
     * @param salary Minimum salary amount
     * @return List of employees with high salary
     */
    public List<Employee> employeesWithHighSalary(BigDecimal salary) {
        logger.info("Fetching employees with salary greater than {}", salary);
        List<Employee> employees = employeeRepository.findBySalaryGreaterThan(salary);
        logger.info("Found {} employees with salary greater than {}", employees.size(), salary);
        return employees;
    }

    /**
     * Finds employees by department name using JPQL
     * @param deptName Department name
     * @return List of employees in the department
     */
    public List<Employee> employeesByDepartment(String deptName) {
        logger.info("Fetching employees for department: {}", deptName);
        List<Employee> employees = employeeRepository.findByDepartmentName(deptName);
        logger.info("Found {} employees in department {}", employees.size(), deptName);
        return employees;
    }

    /**
     * Finds employees with salary above average using JPQL
     * @return List of employees with above average salary
     */
    public List<Employee> employeesAboveAverageSalary() {
        logger.info("Fetching employees with above average salary");
        List<Employee> employees = employeeRepository.findEmployeesAboveAverageSalary();
        logger.info("Found {} employees with above average salary", employees.size());
        return employees;
    }
}
