package com.foo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringCalculatorTest {

    private final StringCalculator underTest = new StringCalculator();

    @Test
    void shouldReturnZeroForEmptyString() {

        // given
        String emptyString = "";

        // when
        int actual = underTest.add(emptyString);

        // then
        assertEquals(0, actual);
    }

    @Test
    void shouldReturnOneForStringOne() {

        // given
        String testString = "1";

        // when
        int actual = underTest.add(testString);

        // then
        assertEquals(1, actual);
    }

    @Test
    void shouldReturnThree() {

        // given
        String testString = "1,2";

        // when
        int actual = underTest.add(testString);

        // then
        assertEquals(3, actual);
    }

    @Test
    void shouldReturnSix() {

        // given
        String testString = "1,2,3";

        // when
        int actual = underTest.add(testString);

        // then
        assertEquals(6, actual);
    }

    @Test
    void shouldReturnSixUsingNewLineAsDelimiter() {

        // given
        String testString = "1\n2,3";

        // when
        int actual = underTest.add(testString);

        // then
        assertEquals(6, actual);
    }

    @Test
    void shouldReturnThreeUsingSemicolonAsDelimiter() {

        // given
        String testString = "//;\n1;2";

        // when
        int actual = underTest.add(testString);

        // then
        assertEquals(3, actual);
    }

    @Test
    void shouldThrowExceptionWhenNegativeNumber() {

        // given
        String testString = "-1,2";

        // when, then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> underTest.add(testString));

        assertEquals("Negatives not allowed: -1", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMultipleNegativeNumber() {

        // given
        String testString = "2,-4,3,-5";

        // when, then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> underTest.add(testString));

        assertEquals("Negatives not allowed: -4,-5", thrown.getMessage());
    }

    @Test
    void shouldIgnoreNumbersGreaterThanOneThousand() {

        // given
        String testString = "1001,2";

        // when
        int actual = underTest.add(testString);

        // then
        assertEquals(2, actual);
    }
}