package com.example;

import com.example.model.Book;
import com.example.model.Member;
import com.example.model.Loan;
import com.example.service.Librarian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Main class demonstrating the library management system.
 * Shows basic operations like lending and returning books.
 * 
 * @author Library System
 * @version 1.0
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting Library Management System");
        
        try {
            // Create books
            Book book1 = new Book(1, "Clean Code");
            Book book2 = new Book(2, "Effective Java");
            logger.info("Created {} books", 2);

            // Create members
            Member member1 = new Member(101, "Pradeep");
            Member member2 = new Member(102, "Rahul");
            logger.info("Created {} members", 2);

            // Librarian
            Librarian librarian = new Librarian();
            logger.info("Librarian service initialized");

            // Lend book
            Optional<Loan> loan1 = librarian.lendBook(book1, member1);
            loan1.ifPresent(System.out::println);

            // Try lending same book again
            librarian.lendBook(book1, member2);

            // Return book
            librarian.returnBook(book1);

            // Lend again after return
            Optional<Loan> loan2 = librarian.lendBook(book1, member2);
            loan2.ifPresent(System.out::println);

            // View all loans
            System.out.println("\nAll Loans:");
            librarian.getAllLoans().forEach(System.out::println);
            
            logger.info("Library operations completed successfully. Active loans: {}", 
                       librarian.getActiveLoansCount());
                       
        } catch (Exception e) {
            logger.error("Error occurred during library operations", e);
            System.err.println("An error occurred: " + e.getMessage());
        }
        
        logger.info("Library Management System shutdown");
    }
}
