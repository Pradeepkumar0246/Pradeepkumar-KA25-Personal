package com.module2.studentgradecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.module2.studentgradecalculator.grade.StudentGradeCalculator;
import com.module2.studentgradecalculator.util.StringUtils;

@SpringBootApplication
public class StudentgradecalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentgradecalculatorApplication.class, args);
		int[] marks = {85, 90, 78, 88};
        StudentGradeCalculator calculator = new StudentGradeCalculator();
        calculator.calculateGrade(marks);
        String text = "Java Spring Boot";

        System.out.println("Reversed       : " + StringUtils.reverse(text));
        System.out.println("Word Count     : " + StringUtils.wordCount(text));

        int[] frequency = StringUtils.charFrequency("hello");
        System.out.println("Frequency of 'l': " + frequency['a']);
	}

}
