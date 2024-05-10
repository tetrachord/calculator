package com.foo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final int DELIMITER_DEFINITION_WIDTH = 4;
    private static final String DELIMITER_CHARACTER_CLASS = "[,\n]";
    private static final String MINUS_CHAR = "-";

    public int add(String numbers) {

        String sanitisedNumbers;

        // we need to handle the case of empty string, to avoid a java.lang.NumberFormatException
        // from Integer.parseInt()
        sanitisedNumbers = numbers.isEmpty() ? "0" : numbers;

        String[] tokens = getTokens(sanitisedNumbers);

        // covert each token to an Integer & then reduce the stream of Integers to a single int value by summing each
        // element of the stream.
        return Arrays.stream(tokens)
                .mapToInt(t -> Integer.parseInt(t))
                .filter(n -> (n < 1000))
                .reduce(0, (a, b) -> a + b);
    }

    private static String[] getTokens(String sanitisedNumbers) {
        
        // If the input string starts with "//" this denotes that an alternative delimiter
        // will be defined in the format "//[delimiter]\n[numbers]"
        String alternativeDelimiter = "";

        if (sanitisedNumbers.startsWith("//")) {
            alternativeDelimiter = String.valueOf(sanitisedNumbers.charAt(2));
        }

        String acceptableDelimiters = alternativeDelimiter.isEmpty() ? DELIMITER_CHARACTER_CLASS : alternativeDelimiter;

        // if we have an alternative delimiter we need to strip the delimiter definition string from the beginning of
        // the input string since those chars will cause Integer.parseInt() to fail.
        String numbersWithoutDelimiterDefinition = alternativeDelimiter.isEmpty() ? sanitisedNumbers : 
                sanitisedNumbers.substring(DELIMITER_DEFINITION_WIDTH);

        // now that we have defined our delimiter & removed any delimiter definition string, we can return
        // each of the number tokens from our input string
        String[] tokens = numbersWithoutDelimiterDefinition.split(acceptableDelimiters);

        handleNegativeNumbers(tokens);

        return tokens;
    }

    private static void handleNegativeNumbers(String[] tokens) {

        List<String> negativeNumbers = new ArrayList<>();

        for (String t : tokens) {
            if (t.startsWith(MINUS_CHAR)) {
                negativeNumbers.add(t);
            }
        }

        if ( !negativeNumbers.isEmpty() ) {
            String negativeNumbersAsString = negativeNumbers.stream().collect(Collectors.joining(","));
            throw new IllegalArgumentException(String.format("Negatives not allowed: %s", negativeNumbersAsString));
        }
    }
}