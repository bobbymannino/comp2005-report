package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class PatientClassTest {
    @Test
    void testCreatePatient() {
        PatientClass patient = new PatientClass(1, "Joe", "Bob", "1");

        assertEquals(1, patient.id);
        assertEquals("Bob", patient.forename);
        assertEquals("Joe", patient.surname);
        assertEquals("1", patient.nhsNumber);
    }
}