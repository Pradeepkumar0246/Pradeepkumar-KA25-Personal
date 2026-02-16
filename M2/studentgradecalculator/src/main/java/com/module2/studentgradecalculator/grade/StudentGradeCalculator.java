package com.module2.studentgradecalculator.grade;

public class StudentGradeCalculator {
    public void calculateGrade(int[] marks) {

        // 1. Validate input
        if (marks == null || marks.length == 0) {
            System.out.println("No marks provided.");
            return;
        }

        int total = 0;

        for (int mark : marks) {
            if (mark < 0 || mark > 100) {
                System.out.println("Invalid mark found: " + mark);
                return;
            }
            total += mark;
        }

        // 2. Calculate average
        double average = (double) total / marks.length;

        // 3. Determine grade
        char grade;

        if (average >= 90) {
            grade = 'A';
        } else if (average >= 75) {
            grade = 'B';
        } else if (average >= 60) {
            grade = 'C';
        } else if (average >= 50) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        // 4. Print formatted report
        System.out.println("----- Student Grade Report -----");
        System.out.println("Total Subjects : " + marks.length);
        System.out.println("Average Marks  : " + average);
        System.out.println("Grade          : " + grade);
        System.out.println("--------------------------------");
    }
}
