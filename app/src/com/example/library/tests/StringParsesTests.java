package com.example.library.tests;

import com.example.library.records.Patient;
import com.example.library.utils.StringParser;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class StringParsesTests {
    @Test
    public void missingFields() {
        String raw = "{'id': 1}";

        try {
            Patient patient = StringParser.parse(raw, Patient.class);
        } catch (Exception e){
            // I am expecting it to fail
            assert true;
        }
    }

    @Test
    public void asItShouldBe() {
        String raw = """
                 {
                    "id": 1,
                    "forename": "Bob",
                    "surname": "Watt",
                    "nhsNumber": "221279"
                 }
                 """;

        try {
            Patient patient = StringParser.parse(raw, Patient.class);

            assert patient.id == 1;
            assert patient.forename.equals("Bob");
            assert patient.surname.equals("Watt");
            assert patient.nhsNumber.equals("221279");
        } catch (Exception e){
            // If it reaches this fail, as it should pass
            System.out.println(e.getMessage());
            assert false;
        }
    }

    @Test
    public void ifNull() {
        try {
            Patient patient = StringParser.parse(null, Patient.class);
            assert false;
        } catch (Exception e){
            assert true;
        }
    }
}
