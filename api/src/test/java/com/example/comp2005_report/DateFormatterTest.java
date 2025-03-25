package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class DateFormatterTest {
    @Test
    void unparseableDates() {
        assertNull(DateFormatter.parseDate("2019-04-20"));
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
        assertNotNull(DateFormatter.parseDate("1900-05-20T00:00:00"));
    }

    @Test
    void parsedDate() {
        String dateString = "1979-12-22T15:00:00";

        Calendar cal = DateFormatter.parseDate(dateString);

        assertEquals(11, cal.get(Calendar.MONTH));
        assertEquals(1979, cal.get(Calendar.YEAR));
        assertEquals(22, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(15, cal.get(Calendar.HOUR_OF_DAY));
    }

    @Test
    void changeDate() {
        String dateString = "1979-12-22T15:00:00";

        Calendar cal = DateFormatter.parseDate(dateString);

        assertEquals(11, cal.get(Calendar.MONTH));
        assertEquals(1979, cal.get(Calendar.YEAR));
        assertEquals(22, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(15, cal.get(Calendar.HOUR_OF_DAY));

        cal.set(Calendar.DAY_OF_MONTH, 26);
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, 2);

        String dateString2 = "2011-03-26T15:00:00";

        Calendar cal2 = DateFormatter.parseDate(dateString2);

        cal.equals(cal2);
    }
}