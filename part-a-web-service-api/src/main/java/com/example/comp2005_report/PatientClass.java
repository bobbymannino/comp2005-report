package com.example.comp2005_report;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

public class PatientClass {

    @NonNull
    @JsonProperty("id")
    public Integer id;

    @NonNull
    @JsonProperty("surname")
    public String surname;

    @NonNull
    @JsonProperty("forename")
    public String forename;

    @NonNull
    @JsonProperty("nhsNumber")
    public String nhsNumber;

    public PatientClass(
        @JsonProperty("id") Integer id,
        @JsonProperty("surname") String surname,
        @JsonProperty("forename") String forename,
        @JsonProperty("nhsNumber") String nhsNumber
    ) {
        this.id = id;
        this.surname = surname;
        this.forename = forename;
        this.nhsNumber = nhsNumber;
    }
}
