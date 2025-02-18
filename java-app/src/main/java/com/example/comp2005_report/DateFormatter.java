package com.example.comp2005_report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {

    public static LocalDate parseDate(String dateString) {
        if (dateString == null) return null;

        // 2025-01-01T12:12:12

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd'T'HH:mm:ss"
        );

        try {
            LocalDate date = LocalDate.parse(dateString, formatter);

            return date;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
