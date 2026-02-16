package com.example.service;

import com.example.model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeAnalyticsService {

    public List<Employee> filterByDepartment(List<Employee> employees, String department) {
        return employees.stream()
                .filter(e -> e.department().equalsIgnoreCase(department))
                .toList();
    }

    public Map<String, List<Employee>> groupByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::department));
    }

    public Optional<Employee> getHighestPaidEmployee(List<Employee> employees) {
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::salary));
    }

    public List<String> getEmployeeNamesUsingStream(List<Employee> employees) {
        return employees.stream()
                .map(Employee::name)
                .toList();
    }

    public List<String> getEmployeeNamesUsingLoop(List<Employee> employees) {
        List<String> names = new ArrayList<>();
        for (Employee e : employees) {
            names.add(e.name());
        }
        return names;
    }

    public Set<String> getUniqueDepartments(List<Employee> employees) {
        return employees.stream()
                .map(Employee::department)
                .collect(Collectors.toSet());
    }

    public Queue<Employee> getProcessingQueue(List<Employee> employees) {
        return new LinkedList<>(employees);
    }
}
