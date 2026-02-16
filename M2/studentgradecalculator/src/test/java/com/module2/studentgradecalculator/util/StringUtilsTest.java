package com.module2.studentgradecalculator.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {
    @Test
    void testReverse() {
        String result = StringUtils.reverse("hello");
        assertEquals("olleh", result);
    }
    @Test
    void testWordCount() {
        int count = StringUtils.wordCount("Java Spring Boot");
        assertEquals(3, count);
    }

    @Test
    void testCharFrequency() {
        int[] freq = StringUtils.charFrequency("hello");

        assertEquals(2, freq['l']);
        assertEquals(1, freq['h']);
        assertEquals(1, freq['e']);
        assertEquals(1, freq['o']);
    }
}
