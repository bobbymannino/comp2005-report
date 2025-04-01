package com.example.comp2005_report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {

    /// @param dateString should be formatted like so YYYY-MM-DDThh:mm:ss
    public static Calendar parseDate(String dateString) {
        if (dateString == null) return null;

        // Regex test string to see if correct
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";
        if (!dateString.matches(dateRegex)) return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            Date formatted = sdf.parse(dateString);

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(formatted);

            return calendar;
        } catch (Exception e) {
            return null;
        }
    }
}
