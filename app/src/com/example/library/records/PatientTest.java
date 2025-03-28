package com.example.library.records;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class PatientTest {
    @Test
    void testNoNamePatient() {
        // Arrange
        Patient patient = new Patient(1, null, "  ", "22121979");

        // Act
        // Don't need to do anything as it's handled in init

        // Assert
        assertEquals(1, patient.id);
        assertEquals(patient.firstName, "UNKNOWN");
        assertEquals(patient.lastName, "UNKNOWN");
        assertEquals(patient.getFullName(), "UNKNOWN UNKNOWN");
    }

    @Test
    void testNormalPatient() {
        Patient patient = new Patient(1, "Joe", "Bob", "22121979");

        assertEquals(1, patient.id);
        assertEquals(patient.firstName, "Bob");
        assertEquals(patient.lastName, "Joe");
        assertEquals(patient.getFullName(), "Bob Joe");
        assertEquals(patient.nhsNumber, "22121979");
    }
}