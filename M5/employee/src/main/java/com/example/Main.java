package com.example;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeAnalyticsService;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        EmployeeRepository repository = new EmployeeRepository();
        EmployeeAnalyticsService service = new EmployeeAnalyticsService();

        List<Employee> employees = repository.getEmployees();

        System.out.println("All Employees:");
        employees.forEach(System.out::println);

        // Filtering
        System.out.println("\nEmployees in IT Department:");
        service.filterByDepartment(employees, "IT")
                .forEach(System.out::println);

        // Grouping
        System.out.println("\nEmployees Grouped By Department:");
        Map<String, List<Employee>> grouped = service.groupByDepartment(employees);
        grouped.forEach((dept, empList) -> {
            System.out.println(dept + " -> " + empList);
        });

        //Top Salary using Optional
        System.out.println("\nHighest Paid Employee:");
        service.getHighestPaidEmployee(employees)
                .ifPresent(System.out::println);

        //Stream vs Loop
        System.out.println("\nEmployee Names Using Stream:");
        System.out.println(service.getEmployeeNamesUsingStream(employees));

        System.out.println("\nEmployee Names Using Loop:");
        System.out.println(service.getEmployeeNamesUsingLoop(employees));

        //Set Example
        System.out.println("\nUnique Departments:");
        Set<String> departments = service.getUniqueDepartments(employees);
        System.out.println(departments);

        // Queue Example
        System.out.println("\nProcessing Queue:");
        Queue<Employee> queue = service.getProcessingQueue(employees);
        while (!queue.isEmpty()) {
            System.out.println("Processing: " + queue.poll().name());
        }
    }
}
