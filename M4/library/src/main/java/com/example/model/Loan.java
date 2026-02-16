package com.example.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Represents a loan transaction between a book and a member.
 * Tracks the loan date and return date of borrowed books.
 * 
 * @author Library System
 * @version 1.0
 */
public class Loan {

    private static final Logger logger = LoggerFactory.getLogger(Loan.class);
    
    private final Book book;
    private final Member member;
    private final LocalDate loanDate;
    private LocalDate returnDate;

    /**
     * Creates a new loan for the specified book and member.
     * The loan date is automatically set to the current date.
     * 
     * @param book the book being borrowed
     * @param member the member borrowing the book
     * @throws IllegalArgumentException if book or member is null
     */
    public Loan(Book book, Member member) {
        if (book == null) {
            logger.error("Attempted to create loan with null book");
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (member == null) {
            logger.error("Attempted to create loan with null member");
            throw new IllegalArgumentException("Member cannot be null");
        }
        
        this.book = book;
        this.member = member;
        this.loanDate = LocalDate.now();
        
        logger.info("Created new loan: Book='{}' to Member='{}' on {}", 
                   book.getTitle(), member.getName(), loanDate);
    }

    /**
     * Gets the book associated with this loan.
     * 
     * @return the borrowed book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Gets the member associated with this loan.
     * 
     * @return the borrowing member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Gets the date when this loan was created.
     * 
     * @return the loan date
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * Gets the date when this loan was returned.
     * 
     * @return the return date, or null if not yet returned
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Checks if this loan has been returned.
     * 
     * @return true if the book has been returned, false otherwise
     */
    public boolean isReturned() {
        return returnDate != null;
    }

    /**
     * Marks this loan as returned by setting the return date to today.
     * If the loan is already returned, logs a warning.
     */
    public void markReturned() {
        if (isReturned()) {
            logger.warn("Attempted to mark already returned loan as returned: {}", this);
            return;
        }
        
        this.returnDate = LocalDate.now();
        logger.info("Loan marked as returned: Book='{}' by Member='{}' on {}", 
                   book.getTitle(), member.getName(), returnDate);
    }

    @Override
    public String toString() {
        return "Loan{book=" + book.getTitle() +
                ", member=" + member.getName() +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate + "}";
    }
}
