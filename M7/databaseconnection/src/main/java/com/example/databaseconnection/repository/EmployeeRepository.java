package com.example.databaseconnection.repository;

import com.example.databaseconnection.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findBySalaryGreaterThan(BigDecimal salary);

    @Query("""
        SELECT e FROM Employee e
        JOIN e.department d
        WHERE d.name = :deptName
    """)
    List<Employee> findByDepartmentName(String deptName);

    @Query("""
        SELECT e FROM Employee e
        WHERE e.salary >
        (SELECT AVG(e2.salary) FROM Employee e2)
    """)
    List<Employee> findEmployeesAboveAverageSalary();
}
