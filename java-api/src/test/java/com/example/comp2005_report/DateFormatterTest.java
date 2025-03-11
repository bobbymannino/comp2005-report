package com.example.comp2005_report;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateFormatterTest {
    @Test
    void unparseableDates() {
        assertNull(DateFormatter.parseDate("2019-04-20"));
        assertNull(DateFormatter.parseDate("2019-04-20T25:25:25"));
        assertNull(DateFormatter.parseDate("2019-04-2025"));
        assertNull(DateFormatter.parseDate("2019-04-2010:10:10"));
        assertNull(DateFormatter.parseDate("20000-04-20T20:20:20"));
        assertNull(DateFormatter.parseDate("100-04-20T20:20:20"));
    }

    @Test
    void formattedDates() {
        assertNotNull(DateFormatter.parseDate("2019-04-20T10:10:10"));
        assertNotNull(DateFormatter.parseDate("2000-03-20T16:10:10"));
        assertNotNull(DateFormatter.parseDate("2045-04-20T11:10:10"));
        assertNotNull(DateFormatter.parseDate("1900-05-20T15:15:00"));
    }

    @Test
    void parsedDate() {
        String dateString = "1979-12-22T15:00:00";

        Date date = DateFormatter.parseDate(dateString);

        assertEquals(12, date.getMonth());
        assertEquals(1979, date.getYear());
        assertEquals(22, date.getDay());
        assertEquals(15, date.getHours());
    }
}