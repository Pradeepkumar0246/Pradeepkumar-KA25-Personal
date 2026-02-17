package com.example.databaseconnection.repository;

import com.example.databaseconnection.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
