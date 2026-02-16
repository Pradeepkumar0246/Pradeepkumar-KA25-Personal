package com.example.service;

import com.example.model.Book;
import com.example.model.Loan;
import com.example.model.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

/**
 * Comprehensive test class for Librarian service.
 * Tests both positive and negative scenarios.
 */
class LibrarianTest {

    private Librarian librarian;
    private Book book1;
    private Book book2;
    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        librarian = new Librarian();
        book1 = new Book(1, "Clean Code");
        book2 = new Book(2, "Effective Java");
        member1 = new Member(101, "John Doe");
        member2 = new Member(102, "Jane Smith");
    }

    // Positive test cases for lending books
    @Test
    @DisplayName("Should successfully lend available book")
    void shouldSuccessfullyLendAvailableBook() {
        Optional<Loan> result = librarian.lendBook(book1, member1);
        
        assertTrue(result.isPresent());
        assertEquals(book1, result.get().getBook());
        assertEquals(member1, result.get().getMember());
        assertFalse(book1.isAvailable());
        assertEquals(1, librarian.getAllLoans().size());
        assertEquals(1, librarian.getActiveLoansCount());
    }

    @Test
    @DisplayName("Should lend multiple books to same member")
    void shouldLendMultipleBooksToSameMember() {
        Optional<Loan> loan1 = librarian.lendBook(book1, member1);
        Optional<Loan> loan2 = librarian.lendBook(book2, member1);
        
        assertTrue(loan1.isPresent());
        assertTrue(loan2.isPresent());
        assertEquals(2, librarian.getAllLoans().size());
        assertEquals(2, librarian.getActiveLoansCount());
    }

    @Test
    @DisplayName("Should lend same book to different members after return")
    void shouldLendSameBookToDifferentMembersAfterReturn() {
        // First loan
        Optional<Loan> loan1 = librarian.lendBook(book1, member1);
        assertTrue(loan1.isPresent());
        
        // Return book
        assertTrue(librarian.returnBook(book1));
        
        // Second loan to different member
        Optional<Loan> loan2 = librarian.lendBook(book1, member2);
        assertTrue(loan2.isPresent());
        assertEquals(member2, loan2.get().getMember());
    }

    // Positive test cases for returning books
    @Test
    @DisplayName("Should successfully return borrowed book")
    void shouldSuccessfullyReturnBorrowedBook() {
        librarian.lendBook(book1, member1);
        assertFalse(book1.isAvailable());
        
        boolean result = librarian.returnBook(book1);
        
        assertTrue(result);
        assertTrue(book1.isAvailable());
        assertEquals(1, librarian.getActiveLoansCount());
    }

    @Test
    @DisplayName("Should handle multiple loans and returns correctly")
    void shouldHandleMultipleLoansAndReturnsCorrectly() {
        // Lend multiple books
        librarian.lendBook(book1, member1);
        librarian.lendBook(book2, member2);
        assertEquals(2, librarian.getActiveLoansCount());
        
        // Return one book
        librarian.returnBook(book1);
        assertEquals(1, librarian.getActiveLoansCount());
        
        // Return second book
        librarian.returnBook(book2);
        assertEquals(0, librarian.getActiveLoansCount());
    }

    // Positive test cases for loan management
    @Test
    @DisplayName("Should return empty list when no loans exist")
    void shouldReturnEmptyListWhenNoLoansExist() {
        List<Loan> loans = librarian.getAllLoans();
        
        assertTrue(loans.isEmpty());
        assertEquals(0, librarian.getActiveLoansCount());
    }

    @Test
    @DisplayName("Should return copy of loans list")
    void shouldReturnCopyOfLoansList() {
        librarian.lendBook(book1, member1);
        
        List<Loan> loans1 = librarian.getAllLoans();
        List<Loan> loans2 = librarian.getAllLoans();
        
        assertNotSame(loans1, loans2); // Different list instances
        assertEquals(loans1.size(), loans2.size()); // Same content
    }

    @Test
    @DisplayName("Should track active loans count correctly")
    void shouldTrackActiveLoansCountCorrectly() {
        assertEquals(0, librarian.getActiveLoansCount());
        
        librarian.lendBook(book1, member1);
        assertEquals(1, librarian.getActiveLoansCount());
        
        librarian.lendBook(book2, member2);
        assertEquals(2, librarian.getActiveLoansCount());
        
        librarian.returnBook(book1);
        assertEquals(1, librarian.getActiveLoansCount());
        
        librarian.returnBook(book2);
        assertEquals(0, librarian.getActiveLoansCount());
    }

    // Negative test cases for lending books
    @Test
    @DisplayName("Should not lend unavailable book")
    void shouldNotLendUnavailableBook() {
        // First loan
        librarian.lendBook(book1, member1);
        
        // Try to lend same book again
        Optional<Loan> result = librarian.lendBook(book1, member2);
        
        assertFalse(result.isPresent());
        assertEquals(1, librarian.getAllLoans().size());
        assertEquals(1, librarian.getActiveLoansCount());
    }

    @Test
    @DisplayName("Should throw exception when lending null book")
    void shouldThrowExceptionWhenLendingNullBook() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> librarian.lendBook(null, member1)
        );
        
        assertEquals("Book cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when lending to null member")
    void shouldThrowExceptionWhenLendingToNullMember() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> librarian.lendBook(book1, null)
        );
        
        assertEquals("Member cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when lending null book to null member")
    void shouldThrowExceptionWhenLendingNullBookToNullMember() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> librarian.lendBook(null, null)
        );
        
        assertEquals("Book cannot be null", exception.getMessage());
    }

    // Negative test cases for returning books
    @Test
    @DisplayName("Should not return book with no active loan")
    void shouldNotReturnBookWithNoActiveLoan() {
        boolean result = librarian.returnBook(book1);
        
        assertFalse(result);
        assertEquals(0, librarian.getAllLoans().size());
    }

    @Test
    @DisplayName("Should not return already returned book")
    void shouldNotReturnAlreadyReturnedBook() {
        // Lend and return book
        librarian.lendBook(book1, member1);
        librarian.returnBook(book1);
        
        // Try to return again
        boolean result = librarian.returnBook(book1);
        
        assertFalse(result);
        assertEquals(0, librarian.getActiveLoansCount());
    }

    @Test
    @DisplayName("Should throw exception when returning null book")
    void shouldThrowExceptionWhenReturningNullBook() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> librarian.returnBook(null)
        );
        
        assertEquals("Book cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle complex loan scenario")
    void shouldHandleComplexLoanScenario() {
        // Lend book1 to member1
        Optional<Loan> loan1 = librarian.lendBook(book1, member1);
        assertTrue(loan1.isPresent());
        
        // Try to lend book1 to member2 (should fail)
        Optional<Loan> loan2 = librarian.lendBook(book1, member2);
        assertFalse(loan2.isPresent());
        
        // Lend book2 to member2
        Optional<Loan> loan3 = librarian.lendBook(book2, member2);
        assertTrue(loan3.isPresent());
        
        // Return book1
        assertTrue(librarian.returnBook(book1));
        
        // Now lend book1 to member2
        Optional<Loan> loan4 = librarian.lendBook(book1, member2);
        assertTrue(loan4.isPresent());
        
        // Check final state
        assertEquals(3, librarian.getAllLoans().size());
        assertEquals(2, librarian.getActiveLoansCount());
    }
}