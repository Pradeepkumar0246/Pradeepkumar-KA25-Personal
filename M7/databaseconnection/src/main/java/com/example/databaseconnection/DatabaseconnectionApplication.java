package com.example.databaseconnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatabaseconnectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseconnectionApplication.class, args);
	}
	/*
     =========================================================
      TEST URLS (Open in Browser / Postman)
     =========================================================

     Create Employee (needs department_id)
     http://localhost:8080/employee/create/1

     Get All Employees
     http://localhost:8080/employees

     Assign Employee to Project
     http://localhost:8080/employee/1/project/1

     JPQL - Employees with salary greater than
     http://localhost:8080/employees/salary/50000

     JPQL - Employees by Department name
     http://localhost:8080/employees/department/IT

     JPQL - Employees above average salary
     http://localhost:8080/employees/above-average

     JDBC - Employee names (JdbcTemplate)
     http://localhost:8080/employees/jdbc

     =========================================================
     DATABASE CHECK (Run in MySQL)
     =========================================================

     select * from department;
     select * from employee;
     select * from project;
     select * from employee_project;

     =========================================================
     */

}
