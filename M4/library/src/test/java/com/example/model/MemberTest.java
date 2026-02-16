package com.example.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for Member model.
 * Tests both positive and negative scenarios.
 */
class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(101, "John Doe");
    }

    // Positive test cases
    @Test
    @DisplayName("Should create member with valid parameters")
    void shouldCreateMemberWithValidParameters() {
        Member newMember = new Member(200, "Jane Smith");
        
        assertEquals(200, newMember.getId());
        assertEquals("Jane Smith", newMember.getName());
    }

    @Test
    @DisplayName("Should trim whitespace from name")
    void shouldTrimWhitespaceFromName() {
        Member newMember = new Member(1, "  Alice Johnson  ");
        
        assertEquals("Alice Johnson", newMember.getName());
    }

    @Test
    @DisplayName("Should handle single character name")
    void shouldHandleSingleCharacterName() {
        Member newMember = new Member(1, "A");
        
        assertEquals("A", newMember.getName());
    }

    @Test
    @DisplayName("Should implement equals correctly")
    void shouldImplementEqualsCorrectly() {
        Member member1 = new Member(1, "Name1");
        Member member2 = new Member(1, "Name2");
        Member member3 = new Member(2, "Name1");
        
        assertEquals(member1, member2); // Same ID
        assertNotEquals(member1, member3); // Different ID
        assertEquals(member1, member1); // Same object
        assertNotEquals(member1, null); // Null comparison
        assertNotEquals(member1, "string"); // Different type
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void shouldImplementHashCodeCorrectly() {
        Member member1 = new Member(1, "Name1");
        Member member2 = new Member(1, "Name2");
        
        assertEquals(member1.hashCode(), member2.hashCode());
    }

    @Test
    @DisplayName("Should return correct string representation")
    void shouldReturnCorrectStringRepresentation() {
        String expected = "Member{id=101, name='John Doe'}";
        
        assertEquals(expected, member.toString());
    }

    @Test
    @DisplayName("Should handle zero ID correctly")
    void shouldHandleZeroIdCorrectly() {
        assertDoesNotThrow(() -> new Member(0, "Valid Name"));
        
        Member zeroIdMember = new Member(0, "Valid Name");
        assertEquals(0, zeroIdMember.getId());
    }

    @Test
    @DisplayName("Should handle large ID values")
    void shouldHandleLargeIdValues() {
        int largeId = Integer.MAX_VALUE;
        Member largeMember = new Member(largeId, "Large ID Member");
        
        assertEquals(largeId, largeMember.getId());
    }

    // Negative test cases
    @Test
    @DisplayName("Should throw exception for negative ID")
    void shouldThrowExceptionForNegativeId() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Member(-1, "Valid Name")
        );
        
        assertEquals("Member ID cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for null name")
    void shouldThrowExceptionForNullName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Member(1, null)
        );
        
        assertEquals("Member name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty name")
    void shouldThrowExceptionForEmptyName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Member(1, "")
        );
        
        assertEquals("Member name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only name")
    void shouldThrowExceptionForWhitespaceOnlyName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Member(1, "   ")
        );
        
        assertEquals("Member name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for very negative ID")
    void shouldThrowExceptionForVeryNegativeId() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Member(Integer.MIN_VALUE, "Valid Name")
        );
        
        assertEquals("Member ID cannot be negative", exception.getMessage());
    }
}