package com.example.library.tests;

import com.example.library.records.Patient;
import com.example.library.utils.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class PatientTests {
    @Test
    public void asItShouldBe() {
        Patient patient = new Patient(1, "Lucy", "Ward", "221279");

        assert patient.id == 1;
        assert patient.forename.equals("Lucy");
        assert patient.surname.equals("Ward");
        assert patient.nhsNumber.equals("221279");
    }
}
