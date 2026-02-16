package com.example.service;

import com.example.model.Book;
import com.example.model.Loan;
import com.example.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class that manages library operations including lending and returning books.
 * Maintains a record of all loan transactions.
 * 
 * @author Library System
 * @version 1.0
 */
public class Librarian {

    private static final Logger logger = LoggerFactory.getLogger(Librarian.class);
    
    private final List<Loan> loans = new ArrayList<>();

    /**
     * Attempts to lend a book to a member.
     * The book must be available for the loan to be successful.
     * 
     * @param book the book to be lent
     * @param member the member requesting the book
     * @return Optional containing the loan if successful, empty otherwise
     * @throws IllegalArgumentException if book or member is null
     */
    public Optional<Loan> lendBook(Book book, Member member) {
        if (book == null) {
            logger.error("Attempted to lend null book");
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (member == null) {
            logger.error("Attempted to lend book to null member");
            throw new IllegalArgumentException("Member cannot be null");
        }
        
        logger.debug("Attempting to lend book '{}' to member '{}'", 
                    book.getTitle(), member.getName());

        if (!book.isAvailable()) {
            logger.warn("Book lending failed - book not available: '{}'", book.getTitle());
            System.out.println("Book is not available: " + book.getTitle());
            return Optional.empty();
        }

        Loan loan = new Loan(book, member);
        book.markAsBorrowed();
        loans.add(loan);
        
        logger.info("Successfully lent book '{}' to member '{}'", 
                   book.getTitle(), member.getName());

        return Optional.of(loan);
    }

    /**
     * Attempts to return a book to the library.
     * Finds the active loan for the book and marks it as returned.
     * 
     * @param book the book being returned
     * @return true if the return was successful, false otherwise
     * @throws IllegalArgumentException if book is null
     */
    public boolean returnBook(Book book) {
        if (book == null) {
            logger.error("Attempted to return null book");
            throw new IllegalArgumentException("Book cannot be null");
        }
        
        logger.debug("Attempting to return book '{}'", book.getTitle());

        for (Loan loan : loans) {
            if (loan.getBook().equals(book) && !loan.isReturned()) {
                loan.markReturned();
                book.markAsReturned();
                
                logger.info("Successfully returned book '{}' by member '{}'", 
                           book.getTitle(), loan.getMember().getName());
                return true;
            }
        }

        logger.warn("Book return failed - no active loan found for: '{}'", book.getTitle());
        System.out.println("No active loan found for: " + book.getTitle());
        return false;
    }

    /**
     * Retrieves all loans (both active and returned) managed by this librarian.
     * 
     * @return a copy of the loans list to prevent external modification
     */
    public List<Loan> getAllLoans() {
        logger.debug("Retrieving all loans, total count: {}", loans.size());
        return new ArrayList<>(loans);
    }
    
    /**
     * Gets the count of active (unreturned) loans.
     * 
     * @return the number of active loans
     */
    public long getActiveLoansCount() {
        long count = loans.stream().filter(loan -> !loan.isReturned()).count();
        logger.debug("Active loans count: {}", count);
        return count;
    }
}
