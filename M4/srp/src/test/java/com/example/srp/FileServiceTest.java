package com.example.srp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Comprehensive test class for FileService.
 * Tests both positive and negative scenarios.
 */
class FileServiceTest {

    private FileService fileService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    // Positive test cases for save()
    @Test
    @DisplayName("Should save valid message successfully")
    void shouldSaveValidMessageSuccessfully() {
        String message = "Test message";
        
        assertDoesNotThrow(() -> fileService.save(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + message));
    }

    @Test
    @DisplayName("Should save message with special characters")
    void shouldSaveMessageWithSpecialCharacters() {
        String message = "Test message with special chars: !@#$%^&*()";
        
        assertDoesNotThrow(() -> fileService.save(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + message));
    }

    @Test
    @DisplayName("Should save long message successfully")
    void shouldSaveLongMessageSuccessfully() {
        String message = "This is a very long message that contains multiple words and should be saved successfully without any issues";
        
        assertDoesNotThrow(() -> fileService.save(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + message));
    }

    @Test
    @DisplayName("Should save message with numbers")
    void shouldSaveMessageWithNumbers() {
        String message = "Result: 123456789";
        
        assertDoesNotThrow(() -> fileService.save(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + message));
    }

    @Test
    @DisplayName("Should trim and save message with leading/trailing spaces")
    void shouldTrimAndSaveMessageWithLeadingTrailingSpaces() {
        String message = "  Valid message with spaces  ";
        
        assertDoesNotThrow(() -> fileService.save(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + message));
    }

    // Negative test cases for save()
    @Test
    @DisplayName("Should throw exception for null message")
    void shouldThrowExceptionForNullMessage() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.save(null)
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty message")
    void shouldThrowExceptionForEmptyMessage() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.save("")
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only message")
    void shouldThrowExceptionForWhitespaceOnlyMessage() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.save("   ")
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for tab-only message")
    void shouldThrowExceptionForTabOnlyMessage() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.save("\t\t\t")
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for newline-only message")
    void shouldThrowExceptionForNewlineOnlyMessage() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.save("\n\n")
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    // Positive test cases for saveWithTimestamp()
    @Test
    @DisplayName("Should save message with timestamp successfully")
    void shouldSaveMessageWithTimestampSuccessfully() {
        String message = "Test message";
        
        assertDoesNotThrow(() -> fileService.saveWithTimestamp(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result:"));
        assertTrue(output.contains(message));
        // Check that timestamp format is present (contains date-time pattern)
        assertTrue(output.matches(".*\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*"));
    }

    @Test
    @DisplayName("Should save timestamped message with special characters")
    void shouldSaveTimestampedMessageWithSpecialCharacters() {
        String message = "Message with special chars: !@#$%";
        
        assertDoesNotThrow(() -> fileService.saveWithTimestamp(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result:"));
        assertTrue(output.contains(message));
    }

    // Negative test cases for saveWithTimestamp()
    @Test
    @DisplayName("Should throw exception for null message in saveWithTimestamp")
    void shouldThrowExceptionForNullMessageInSaveWithTimestamp() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.saveWithTimestamp(null)
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty message in saveWithTimestamp")
    void shouldThrowExceptionForEmptyMessageInSaveWithTimestamp() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.saveWithTimestamp("")
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only message in saveWithTimestamp")
    void shouldThrowExceptionForWhitespaceOnlyMessageInSaveWithTimestamp() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.saveWithTimestamp("   ")
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    // Positive test cases for append()
    @Test
    @DisplayName("Should append valid message successfully")
    void shouldAppendValidMessageSuccessfully() {
        String message = "Appended message";
        
        assertDoesNotThrow(() -> fileService.append(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Appending result: " + message));
    }

    @Test
    @DisplayName("Should append message with numbers")
    void shouldAppendMessageWithNumbers() {
        String message = "Additional data: 987654321";
        
        assertDoesNotThrow(() -> fileService.append(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Appending result: " + message));
    }

    @Test
    @DisplayName("Should append multiple messages")
    void shouldAppendMultipleMessages() {
        String message1 = "First append";
        String message2 = "Second append";
        
        assertDoesNotThrow(() -> {
            fileService.append(message1);
            fileService.append(message2);
        });
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Appending result: " + message1));
        assertTrue(output.contains("Appending result: " + message2));
    }

    // Negative test cases for append()
    @Test
    @DisplayName("Should throw exception for null message in append")
    void shouldThrowExceptionForNullMessageInAppend() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.append(null)
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for empty message in append")
    void shouldThrowExceptionForEmptyMessageInAppend() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.append("")
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for whitespace-only message in append")
    void shouldThrowExceptionForWhitespaceOnlyMessageInAppend() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> fileService.append("   ")
        );
        
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    // Edge cases and integration tests
    @Test
    @DisplayName("Should handle mixed operations correctly")
    void shouldHandleMixedOperationsCorrectly() {
        String saveMessage = "Saved message";
        String appendMessage = "Appended message";
        String timestampMessage = "Timestamped message";
        
        assertDoesNotThrow(() -> {
            fileService.save(saveMessage);
            fileService.append(appendMessage);
            fileService.saveWithTimestamp(timestampMessage);
        });
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + saveMessage));
        assertTrue(output.contains("Appending result: " + appendMessage));
        assertTrue(output.contains("Saving result:"));
        assertTrue(output.contains(timestampMessage));
    }

    @Test
    @DisplayName("Should handle single character messages")
    void shouldHandleSingleCharacterMessages() {
        String message = "A";
        
        assertDoesNotThrow(() -> {
            fileService.save(message);
            fileService.append(message);
            fileService.saveWithTimestamp(message);
        });
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + message));
        assertTrue(output.contains("Appending result: " + message));
    }

    @Test
    @DisplayName("Should handle unicode characters")
    void shouldHandleUnicodeCharacters() {
        String message = "Unicode test: 你好 🌟 café";
        
        assertDoesNotThrow(() -> fileService.save(message));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Saving result: " + message));
    }
}