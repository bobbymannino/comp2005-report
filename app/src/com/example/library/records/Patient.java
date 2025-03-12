package com.example.library.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Patient {
    public Integer id;
    public String lastName;
    public String firstName;
    public String nhsNumber;

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
        this.firstName = firstName;
        this.lastName = lastName;
        this.nhsNumber = nhsNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
