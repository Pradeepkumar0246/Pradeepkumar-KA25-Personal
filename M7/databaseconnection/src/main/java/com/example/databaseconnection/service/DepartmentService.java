package com.example.databaseconnection.service;

import com.example.databaseconnection.entity.Department;
import com.example.databaseconnection.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void createDepartment() {
        Department dept = new Department();
        dept.setName("IT");
        dept.setBudget(new BigDecimal("500000"));

        departmentRepository.save(dept);
    }
}
