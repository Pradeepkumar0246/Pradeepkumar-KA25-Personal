package com.example.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a book in the library system.
 * Each book has a unique ID, title, and availability status.
 * 
 * @author Library System
 * @version 1.0
 */
public class Book {

    private static final Logger logger = LoggerFactory.getLogger(Book.class);
    
    private final int id;
    private final String title;
    private boolean available;

    /**
     * Creates a new book with the specified ID and title.
     * The book is initially marked as available.
     * 
     * @param id the unique identifier for the book
     * @param title the title of the book
     * @throws IllegalArgumentException if id is negative or title is null/empty
     */
    public Book(int id, String title) {
        if (id < 0) {
            logger.error("Attempted to create book with negative ID: {}", id);
            throw new IllegalArgumentException("Book ID cannot be negative");
        }
        if (title == null || title.trim().isEmpty()) {
            logger.error("Attempted to create book with null or empty title");
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        
        this.id = id;
        this.title = title.trim();
        this.available = true;
        
        logger.debug("Created new book: ID={}, Title='{}'", id, title);
    }

    /**
     * Gets the unique identifier of this book.
     * 
     * @return the book ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the title of this book.
     * 
     * @return the book title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Checks if this book is currently available for borrowing.
     * 
     * @return true if the book is available, false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Marks this book as borrowed (not available).
     * This method should be called when a book is lent to a member.
     */
    public void markAsBorrowed() {
        if (!available) {
            logger.warn("Attempted to mark already borrowed book as borrowed: {}", this);
            return;
        }
        
        this.available = false;
        logger.info("Book marked as borrowed: ID={}, Title='{}'", id, title);
    }

    /**
     * Marks this book as returned (available).
     * This method should be called when a book is returned by a member.
     */
    public void markAsReturned() {
        if (available) {
            logger.warn("Attempted to mark already available book as returned: {}", this);
            return;
        }
        
        this.available = true;
        logger.info("Book marked as returned: ID={}, Title='{}'", id, title);
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', available=" + available + "}";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return id == book.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
