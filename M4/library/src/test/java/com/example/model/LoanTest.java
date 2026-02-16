package com.example.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Comprehensive test class for Loan model.
 * Tests both positive and negative scenarios.
 */
class LoanTest {

    private Book book;
    private Member member;
    private Loan loan;

    @BeforeEach
    void setUp() {
        book = new Book(1, "Test Book");
        member = new Member(101, "Test Member");
        loan = new Loan(book, member);
    }

    // Positive test cases
    @Test
    @DisplayName("Should create loan with valid parameters")
    void shouldCreateLoanWithValidParameters() {
        Book newBook = new Book(2, "Another Book");
        Member newMember = new Member(102, "Another Member");
        
        Loan newLoan = new Loan(newBook, newMember);
        
        assertEquals(newBook, newLoan.getBook());
        assertEquals(newMember, newLoan.getMember());
        assertEquals(LocalDate.now(), newLoan.getLoanDate());
        assertNull(newLoan.getReturnDate());
        assertFalse(newLoan.isReturned());
    }

    @Test
    @DisplayName("Should set loan date to current date")
    void shouldSetLoanDateToCurrentDate() {
        LocalDate today = LocalDate.now();
        
        assertEquals(today, loan.getLoanDate());
    }

    @Test
    @DisplayName("Should initially not be returned")
    void shouldInitiallyNotBeReturned() {
        assertFalse(loan.isReturned());
        assertNull(loan.getReturnDate());
    }

    @Test
    @DisplayName("Should mark loan as returned")
    void shouldMarkLoanAsReturned() {
        assertFalse(loan.isReturned());
        
        loan.markReturned();
        
        assertTrue(loan.isReturned());
        assertEquals(LocalDate.now(), loan.getReturnDate());
    }

    @Test
    @DisplayName("Should handle multiple return attempts gracefully")
    void shouldHandleMultipleReturnAttemptsGracefully() {
        loan.markReturned();
        LocalDate firstReturnDate = loan.getReturnDate();
        
        // Should not throw exception and should not change return date
        assertDoesNotThrow(() -> loan.markReturned());
        assertEquals(firstReturnDate, loan.getReturnDate());
        assertTrue(loan.isReturned());
    }

    @Test
    @DisplayName("Should return correct string representation for active loan")
    void shouldReturnCorrectStringRepresentationForActiveLoan() {
        String expected = "Loan{book=Test Book, member=Test Member, loanDate=" + 
                         LocalDate.now() + ", returnDate=null}";
        
        assertEquals(expected, loan.toString());
    }

    @Test
    @DisplayName("Should return correct string representation for returned loan")
    void shouldReturnCorrectStringRepresentationForReturnedLoan() {
        loan.markReturned();
        
        String expected = "Loan{book=Test Book, member=Test Member, loanDate=" + 
                         LocalDate.now() + ", returnDate=" + LocalDate.now() + "}";
        
        assertEquals(expected, loan.toString());
    }

    @Test
    @DisplayName("Should maintain immutable references to book and member")
    void shouldMaintainImmutableReferencesToBookAndMember() {
        Book originalBook = loan.getBook();
        Member originalMember = loan.getMember();
        
        // References should remain the same
        assertSame(originalBook, loan.getBook());
        assertSame(originalMember, loan.getMember());
    }

    @Test
    @DisplayName("Should handle loan with same book and member multiple times")
    void shouldHandleLoanWithSameBookAndMemberMultipleTimes() {
        Loan loan1 = new Loan(book, member);
        Loan loan2 = new Loan(book, member);
        
        // Should be different loan objects
        assertNotSame(loan1, loan2);
        
        // But should reference same book and member
        assertSame(loan1.getBook(), loan2.getBook());
        assertSame(loan1.getMember(), loan2.getMember());
    }

    // Negative test cases
    @Test
    @DisplayName("Should throw exception for null book")
    void shouldThrowExceptionForNullBook() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Loan(null, member)
        );
        
        assertEquals("Book cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for null member")
    void shouldThrowExceptionForNullMember() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Loan(book, null)
        );
        
        assertEquals("Member cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for both null book and member")
    void shouldThrowExceptionForBothNullBookAndMember() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Loan(null, null)
        );
        
        assertEquals("Book cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle loan creation with unavailable book")
    void shouldHandleLoanCreationWithUnavailableBook() {
        book.markAsBorrowed();
        
        // Loan creation should still work (business logic is in Librarian)
        assertDoesNotThrow(() -> new Loan(book, member));
        
        Loan newLoan = new Loan(book, member);
        assertEquals(book, newLoan.getBook());
        assertEquals(member, newLoan.getMember());
    }
}