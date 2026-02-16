package com.example.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for Book model.
 * Tests both positive and negative scenarios.
 */
class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1, "Test Book");
    }

    // Positive test cases
    @Test
    @DisplayName("Should create book with valid parameters")
    void shouldCreateBookWithValidParameters() {
        Book newBook = new Book(100, "Clean Code");
        
        assertEquals(100, newBook.getId());
        assertEquals("Clean Code", newBook.getTitle());
        assertTrue(newBook.isAvailable());
    }

    @Test
    @DisplayName("Should trim whitespace from title")
    void shouldTrimWhitespaceFromTitle() {
        Book newBook = new Book(1, "  Effective Java  ");
        
        assertEquals("Effective Java", newBook.getTitle());
    }

    @Test
    @DisplayName("Should mark book as borrowed")
    void shouldMarkBookAsBorrowed() {
        assertTrue(book.isAvailable());
        
        book.markAsBorrowed();
        
        assertFalse(book.isAvailable());
    }

    @Test
    @DisplayName("Should mark book as returned")
    void shouldMarkBookAsReturned() {
        book.markAsBorrowed();
        assertFalse(book.isAvailable());
        
        book.markAsReturned();
        
        assertTrue(book.isAvailable());
    }

    @Test
    @DisplayName("Should handle multiple borrow attempts gracefully")
    void shouldHandleMultipleBorrowAttemptsGracefully() {
        book.markAsBorrowed();
        assertFalse(book.isAvailable());
        
        // Should not throw exception
        assertDoesNotThrow(() -> book.markAsBorrowed());
        assertFalse(book.isAvailable());
    }

    @Test
    @DisplayName("Should handle multiple return attempts gracefully")
    void shouldHandleMultipleReturnAttemptsGracefully() {
        assertTrue(book.isAvailable());
        
        // Should not throw exception
        assertDoesNotThrow(() -> book.markAsReturned());
        assertTrue(book.isAvailable());
    }

    @Test
    @DisplayName("Should implement equals correctly")
    void shouldImplementEqualsCorrectly() {
        Book book1 = new Book(1, "Title1");
        Book book2 = new Book(1, "Title2");
        Book book3 = new Book(2, "Title1");
        
        assertEquals(book1, book2); // Same ID
        assertNotEquals(book1, book3); // Different ID
        assertEquals(book1, book1); // Same object
        assertNotEquals(book1, null); // Null comparison
        assertNotEquals(book1, "string"); // Different type
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void shouldImplementHashCodeCorrectly() {
        Book book1 = new Book(1, "Title1");
        Book book2 = new Book(1, "Title2");
        
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    @DisplayName("Should return correct string representation")
    void shouldReturnCorrectStringRepresentation() {
        String expected = "Book{id=1, title='Test Book', available=true}";
        
        assertEquals(expected, book.toString());
    }

    // Negative test cases
    @Test
    @DisplayName("Should throw exception for negative ID")
    void shouldThrowExceptionForNegativeId() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Book(-1, "Valid Title")
        );
        
        assertEquals("Book ID cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for null title")
    void shouldThrowExceptionForNullTitle() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Book(1, null)
        );
        
        assertEquals("Book title cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty title")
    void shouldThrowExceptionForEmptyTitle() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Book(1, "")
        );
        
        assertEquals("Book title cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only title")
    void shouldThrowExceptionForWhitespaceOnlyTitle() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Book(1, "   ")
        );
        
        assertEquals("Book title cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle zero ID correctly")
    void shouldHandleZeroIdCorrectly() {
        assertDoesNotThrow(() -> new Book(0, "Valid Title"));
        
        Book zeroIdBook = new Book(0, "Valid Title");
        assertEquals(0, zeroIdBook.getId());
    }
}