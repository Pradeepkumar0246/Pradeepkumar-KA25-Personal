package com.example.databaseconnection.repository;

import com.example.databaseconnection.entity.EmployeeProject;
import com.example.databaseconnection.entity.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProjectRepository
        extends JpaRepository<EmployeeProject, EmployeeProjectId> {
}
