package com.example.library.utils;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class StringParseErrorTest {
    @Test
    void testStringParseError() {
        assertThrows(StringParseError.class, () -> {
            throw new StringParseError("Failed to parse string");
        });
    }
}