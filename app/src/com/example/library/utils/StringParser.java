package com.example.library.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringParser {
    private static final ObjectMapper mapper = new ObjectMapper();

    /// Parse a string into a class
    public static <T> T parse(String str, Class<T> klass) throws StringParseError {
        try {
            return mapper.readValue(str, klass);
        } catch (Exception e) {
            throw new StringParseError("Unable to parse string: " + str);
        }
    }

    /// Parse a string object and only return a value of one of the keys
    ///
    /// @example
    /// str = "{patients:[1,2,3]}
    /// key = "patients"
    /// klass = Integer[].class
    ///
    /// @param str The raw data you want to parse
    /// @param key The key you want to get from the object
    /// @param klass The value type of the key you want to get
    public static <T> T parse(String str, String key, Class<T> klass) throws StringParseError {
        try {
            JsonNode rootNode = mapper.readTree(str);

            return mapper.convertValue(rootNode.get("admissions"), klass);
        } catch (Exception e) {
            throw new StringParseError("Unable to parse string into object: " + str);
        }
    }
}
