package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {
    private static final ObjectMapper mapper = new ObjectMapper();

    /// Parse a string into a class
    public static <T> T parse(String str, Class<T> klass) throws ParseError {
        try {
            return mapper.readValue(str, klass);
        } catch (Exception e) {
            throw new ParseError("Unable to parse string: " + str + " to " + klass.getName());
        }
    }
}

