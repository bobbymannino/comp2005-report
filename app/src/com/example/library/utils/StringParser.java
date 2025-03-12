package com.example.library.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StringParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parse(String str, Class<T> klass) throws StringParseError {
        try {
            return mapper.readValue(str, klass);
        } catch (Exception e) {
            throw new StringParseError("Unable to parse string: " + str);
        }
    }
}
