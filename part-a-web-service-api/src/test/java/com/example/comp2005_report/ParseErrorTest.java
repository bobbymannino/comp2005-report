package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class ParseErrorTest {
    @Test
    void parseError() {
        ParseError error = new ParseError("Parse Failed");

        assertEquals("Parse Failed", error.getMessage());
    }
}