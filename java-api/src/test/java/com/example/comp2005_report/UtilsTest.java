package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Date date1 = new Date();
        date1.setDate(22);
        date1.setMonth(11);
        date1.setYear(1979);
        date1.setHours(0);
        date1.setMinutes(0);
        date1.setSeconds(0);

        Date date2 = new Date();
        date2.setDate(24);
        date2.setMonth(11);
        date2.setYear(1979);
        date2.setHours(0);
        date2.setMinutes(0);
        date2.setSeconds(0);

        assertEquals(-2.0, Utils.differenceInDays(date1, date2));
    }

    @Test
    void bigDateDifference() {
        Date date1 = new Date();
        date1.setDate(22);
        date1.setMonth(11);
        date1.setYear(1979);
        date1.setHours(0);
        date1.setMinutes(0);
        date1.setSeconds(0);

        Date date2 = new Date();
        date2.setDate(23);
        date2.setMonth(11);
        date2.setYear(1978);
        date2.setHours(0);
        date2.setMinutes(0);
        date2.setSeconds(0);

        assertEquals(364.0, Utils.differenceInDays(date1, date2));
    }

    @Test
    void smallDateDifference() {
        Date date1 = new Date();
        date1.setDate(22);
        date1.setMonth(11);
        date1.setYear(1979);
        date1.setHours(0);
        date1.setMinutes(0);
        date1.setSeconds(0);

        Date date2 = new Date();
        date2.setDate(20);
        date2.setMonth(11);
        date2.setYear(1979);
        date2.setHours(0);
        date2.setMinutes(0);
        date2.setSeconds(0);

        assertEquals(2.0, Utils.differenceInDays(date1, date2));
    }
}