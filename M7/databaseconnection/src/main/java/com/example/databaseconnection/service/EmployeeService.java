package com.example.databaseconnection.service;

import com.example.databaseconnection.entity.*;
import com.example.databaseconnection.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

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
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ---------- TRANSACTION ----------
    @Transactional
    public void createEmployee(Integer deptId) {
        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Employee emp = new Employee();
        emp.setName("John");
        emp.setEmail("john" + System.currentTimeMillis() + "@mail.com");
        emp.setSalary(new BigDecimal("60000"));
        emp.setDepartment(dept);

        employeeRepository.save(emp);
    }

    // ---------- MANY TO MANY ----------
    @Transactional
    public void assignEmployeeToProject(Integer empId, Integer projectId) {

        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        EmployeeProjectId id = new EmployeeProjectId();
        id.setEmployeeId(empId);
        id.setProjectId(projectId);

        EmployeeProject ep = new EmployeeProject();
        ep.setId(id);
        ep.setEmployee(emp);
        ep.setProject(project);
        ep.setAssignedDate(LocalDate.now());

        employeeProjectRepository.save(ep);
    }

    // ---------- JPQL ----------
    public List<Employee> employeesWithHighSalary(BigDecimal salary) {
        return employeeRepository.findBySalaryGreaterThan(salary);
    }

    public List<Employee> employeesByDepartment(String deptName) {
        return employeeRepository.findByDepartmentName(deptName);
    }

    public List<Employee> employeesAboveAverageSalary() {
        return employeeRepository.findEmployeesAboveAverageSalary();
    }
}
