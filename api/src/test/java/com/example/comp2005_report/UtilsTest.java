package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class UtilsTest {
    @Test
    void difference() {
        Integer[] array1 = new Integer[] { 1, 2, 3, 4, 5 };
        Integer[] array2 = new Integer[] { 1, 2, 5 };

        Integer[] diff = Utils.difference(array1, array2);

        assertEquals(2, diff.length);
        assertEquals(3, diff[0]);
        assertEquals(4, diff[1]);
    }

    @Test
    void negativeDateDifference() {
        String date1Str = "1979-12-22T00:00:00";
        String date2Str = "1979-12-24T00:00:00";

        Calendar cal1 = DateFormatter.parseDate(date1Str);
        Calendar cal2 = DateFormatter.parseDate(date2Str);

        assertEquals(-2.0, Utils.differenceInDays(cal1, cal2));
    }

    @Test
    void bigDateDifference() {
        String date1Str = "1979-12-22T00:00:00";
        String date2Str = "1978-12-23T00:00:00";

        Calendar cal1 = DateFormatter.parseDate(date1Str);
        Calendar cal2 = DateFormatter.parseDate(date2Str);

        assertEquals(364.0, Utils.differenceInDays(cal1, cal2));
    }

    @Test
    void smallDateDifference() {
        String date1Str = "1979-12-22T00:00:00";
        String date2Str = "1979-12-20T00:00:00";

        Calendar cal1 = DateFormatter.parseDate(date1Str);
        Calendar cal2 = DateFormatter.parseDate(date2Str);

        assertEquals(2, Utils.differenceInDays(cal1, cal2));
    }
}