package com.example.databaseconnection.controller;

import com.example.databaseconnection.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department/create")
    public String createDepartment() {
        departmentService.createDepartment();
        return "Department created";
    }
}
