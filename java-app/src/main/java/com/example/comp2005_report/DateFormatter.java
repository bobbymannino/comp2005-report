package com.example.comp2005_report;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    /// @dateString should be formatted like so YYYY-MM-DDThh:mm:ss
    public static Date parseDate(String dateString) {
        if (dateString == null) return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {}

        return null;
    }
}
