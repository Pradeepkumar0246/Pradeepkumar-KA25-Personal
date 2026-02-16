package com.module2.studentgradecalculator.util;

public class StringUtils {
     public static String reverse(String input) {

        if (input == null) {
            return null;
        }

        StringBuilder reversed = new StringBuilder();

        for (int i = input.length() - 1; i >= 0; i--) {
            reversed.append(input.charAt(i));
        }

        return reversed.toString();
    }

    // 2. Count words in a string
    public static int wordCount(String input) {

        if (input == null || input.trim().isEmpty()) {
            return 0;
        }

        int count = 0;
        boolean isWord = false;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isWhitespace(ch)) {
                isWord = false;
            } else if (!isWord) {
                count++;
                isWord = true;
            }
        }

        return count;
    }

    // 3. Character frequency
    public static int[] charFrequency(String input) {

        int[] frequency = new int[256]; // ASCII characters

        if (input == null) {
            return frequency;
        }

        for (int i = 0; i < input.length(); i++) {
            frequency[input.charAt(i)]++;
        }

        return frequency;
    }
}
