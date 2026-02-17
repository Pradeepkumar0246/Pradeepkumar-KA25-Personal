package com.example.databaseconnection.repository;

import com.example.databaseconnection.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
