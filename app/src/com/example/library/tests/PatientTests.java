package com.example.library.tests;

import com.example.library.records.Patient;
import com.example.library.utils.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class PatientTests {
    @Test
    public void asItShouldBe() {
        Patient patient = new Patient(1, "Ward", "Lucy", "221279");

        assert patient.id == 1;
        assert patient.firstName.equals("Lucy");
        assert patient.lastName.equals("Ward");
        assert patient.nhsNumber.equals("221279");
        assert patient.getFullName().equals("Lucy Ward");
    }
}
