package com.example.library.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Patient {
    public Integer id;
    public String lastName;
    public String firstName;
    public String nhsNumber;

    private final static String PATIENT_NAME_NO_NAME = "UNKNOWN";

    public Patient(
            @JsonProperty("id")
        Integer id,
        @JsonProperty("surname")
        String lastName,
            @JsonProperty("forename")
        String firstName,
            @JsonProperty("nhsNumber")
        String nhsNumber
    ) {
        this.id = id;
        this.nhsNumber = nhsNumber;

        if (firstName == null || firstName.isBlank())  this.firstName = PATIENT_NAME_NO_NAME;
        else this.firstName = firstName;

        if (lastName == null || lastName.isBlank())  this.lastName = PATIENT_NAME_NO_NAME;
        else this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
