package com.example.repository;

import com.example.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "Pradeep", "IT", 60000));
        employees.add(new Employee(2, "Rahul", "HR", 45000));
        employees.add(new Employee(3, "Anita", "IT", 75000));
        employees.add(new Employee(4, "Suresh", "Finance", 50000));
        employees.add(new Employee(5, "Meena", "HR", 55000));

        return employees;
    }
}
