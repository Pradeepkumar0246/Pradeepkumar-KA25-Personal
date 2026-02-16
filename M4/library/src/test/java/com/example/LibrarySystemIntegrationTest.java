package com.example;

import com.example.model.Book;
import com.example.model.Loan;
import com.example.model.Member;
import com.example.service.Librarian;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

/**
 * Integration test class for the complete library management system.
 * Tests end-to-end workflows and system integration.
 */
class LibrarySystemIntegrationTest {

    private Librarian librarian;
    private Book cleanCode;
    private Book effectiveJava;
    private Book designPatterns;
    private Member john;
    private Member jane;
    private Member bob;

    @BeforeEach
    void setUp() {
        librarian = new Librarian();
        
        // Create books
        cleanCode = new Book(1, "Clean Code");
        effectiveJava = new Book(2, "Effective Java");
        designPatterns = new Book(3, "Design Patterns");
        
        // Create members
        john = new Member(101, "John Doe");
        jane = new Member(102, "Jane Smith");
        bob = new Member(103, "Bob Johnson");
    }

    @Test
    @DisplayName("Should handle complete library workflow")
    void shouldHandleCompleteLibraryWorkflow() {
        // Initial state - no loans
        assertEquals(0, librarian.getAllLoans().size());
        assertEquals(0, librarian.getActiveLoansCount());
        
        // John borrows Clean Code
        Optional<Loan> loan1 = librarian.lendBook(cleanCode, john);
        assertTrue(loan1.isPresent());
        assertEquals(1, librarian.getActiveLoansCount());
        assertFalse(cleanCode.isAvailable());
        
        // Jane borrows Effective Java
        Optional<Loan> loan2 = librarian.lendBook(effectiveJava, jane);
        assertTrue(loan2.isPresent());
        assertEquals(2, librarian.getActiveLoansCount());
        
        // Bob tries to borrow Clean Code (should fail)
        Optional<Loan> loan3 = librarian.lendBook(cleanCode, bob);
        assertFalse(loan3.isPresent());
        assertEquals(2, librarian.getActiveLoansCount());
        
        // Bob borrows Design Patterns instead
        Optional<Loan> loan4 = librarian.lendBook(designPatterns, bob);
        assertTrue(loan4.isPresent());
        assertEquals(3, librarian.getActiveLoansCount());
        
        // John returns Clean Code
        assertTrue(librarian.returnBook(cleanCode));
        assertEquals(2, librarian.getActiveLoansCount());
        assertTrue(cleanCode.isAvailable());
        
        // Bob can now borrow Clean Code
        Optional<Loan> loan5 = librarian.lendBook(cleanCode, bob);
        assertTrue(loan5.isPresent());
        assertEquals(3, librarian.getActiveLoansCount());
        
        // Check final state
        List<Loan> allLoans = librarian.getAllLoans();
        assertEquals(4, allLoans.size()); // 4 total loans created
        assertEquals(3, librarian.getActiveLoansCount()); // 3 currently active
    }

    @Test
    @DisplayName("Should handle multiple returns and re-lending")
    void shouldHandleMultipleReturnsAndReLending() {
        // Lend all books
        librarian.lendBook(cleanCode, john);
        librarian.lendBook(effectiveJava, jane);
        librarian.lendBook(designPatterns, bob);
        assertEquals(3, librarian.getActiveLoansCount());
        
        // Return all books
        assertTrue(librarian.returnBook(cleanCode));
        assertTrue(librarian.returnBook(effectiveJava));
        assertTrue(librarian.returnBook(designPatterns));
        assertEquals(0, librarian.getActiveLoansCount());
        
        // All books should be available again
        assertTrue(cleanCode.isAvailable());
        assertTrue(effectiveJava.isAvailable());
        assertTrue(designPatterns.isAvailable());
        
        // Re-lend to different members
        librarian.lendBook(cleanCode, jane);
        librarian.lendBook(effectiveJava, bob);
        librarian.lendBook(designPatterns, john);
        assertEquals(3, librarian.getActiveLoansCount());
        
        // Total loans should be 6 (3 original + 3 new)
        assertEquals(6, librarian.getAllLoans().size());
    }

    @Test
    @DisplayName("Should handle edge cases in system workflow")
    void shouldHandleEdgeCasesInSystemWorkflow() {
        // Try to return book that was never borrowed
        assertFalse(librarian.returnBook(cleanCode));
        
        // Lend book and try to return different book
        librarian.lendBook(cleanCode, john);
        assertFalse(librarian.returnBook(effectiveJava));
        
        // Return correct book
        assertTrue(librarian.returnBook(cleanCode));
        
        // Try to return same book again
        assertFalse(librarian.returnBook(cleanCode));
        
        // Verify system state is consistent
        assertEquals(1, librarian.getAllLoans().size());
        assertEquals(0, librarian.getActiveLoansCount());
        assertTrue(cleanCode.isAvailable());
    }

    @Test
    @DisplayName("Should maintain data integrity throughout operations")
    void shouldMaintainDataIntegrityThroughoutOperations() {
        // Perform various operations
        librarian.lendBook(cleanCode, john);
        librarian.lendBook(effectiveJava, jane);
        
        List<Loan> loansAfterLending = librarian.getAllLoans();
        assertEquals(2, loansAfterLending.size());
        
        // Verify loan details
        Loan johnLoan = loansAfterLending.stream()
            .filter(loan -> loan.getMember().equals(john))
            .findFirst()
            .orElse(null);
        assertNotNull(johnLoan);
        assertEquals(cleanCode, johnLoan.getBook());
        assertFalse(johnLoan.isReturned());
        
        // Return one book
        librarian.returnBook(cleanCode);
        
        // Verify loan is marked as returned but still in list
        List<Loan> loansAfterReturn = librarian.getAllLoans();
        assertEquals(2, loansAfterReturn.size());
        
        Loan returnedLoan = loansAfterReturn.stream()
            .filter(loan -> loan.getBook().equals(cleanCode))
            .findFirst()
            .orElse(null);
        assertNotNull(returnedLoan);
        assertTrue(returnedLoan.isReturned());
        assertNotNull(returnedLoan.getReturnDate());
    }

    @Test
    @DisplayName("Should handle concurrent-like operations correctly")
    void shouldHandleConcurrentLikeOperationsCorrectly() {
        // Simulate rapid operations
        for (int i = 0; i < 10; i++) {
            librarian.lendBook(cleanCode, john);
            librarian.returnBook(cleanCode);
        }
        
        // Should have 10 loan records
        assertEquals(10, librarian.getAllLoans().size());
        assertEquals(0, librarian.getActiveLoansCount());
        assertTrue(cleanCode.isAvailable());
        
        // All loans should be returned
        List<Loan> allLoans = librarian.getAllLoans();
        assertTrue(allLoans.stream().allMatch(Loan::isReturned));
    }

    @Test
    @DisplayName("Should validate business rules consistently")
    void shouldValidateBusinessRulesConsistently() {
        // Rule: Can't lend unavailable book
        librarian.lendBook(cleanCode, john);
        Optional<Loan> failedLoan = librarian.lendBook(cleanCode, jane);
        assertFalse(failedLoan.isPresent());
        
        // Rule: Can't return book without active loan
        assertFalse(librarian.returnBook(effectiveJava));
        
        // Rule: Can return book with active loan
        assertTrue(librarian.returnBook(cleanCode));
        
        // Rule: Can't return already returned book
        assertFalse(librarian.returnBook(cleanCode));
        
        // Rule: Can lend returned book again
        Optional<Loan> newLoan = librarian.lendBook(cleanCode, jane);
        assertTrue(newLoan.isPresent());
    }
}