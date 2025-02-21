package com.example.comp2005_report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {

    /// @dateString should be formatted like so YYYY-MM-DDThh:mm:ss
    public static LocalDate parseDate(String dateString) {
        if (dateString == null) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd'T'HH:mm:ss"
        );

        try {
            LocalDate date = LocalDate.parse(dateString, formatter);

            return date;
        } catch (DateTimeParseException e) {
//            e.printStackTrace();
        }

        return null;
    }
}
