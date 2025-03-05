package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

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
}