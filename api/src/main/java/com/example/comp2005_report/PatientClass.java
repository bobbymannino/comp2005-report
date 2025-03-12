package com.example.comp2005_report;

import org.springframework.lang.NonNull;

public class PatientClass {
    @NonNull
    public Integer id;
    @NonNull
    public String surname;
    @NonNull
    public String forename;
    @NonNull
    public String nhsNumber;

    public PatientClass(Integer id, String surname, String forename, String nhsNumber) {
        this.id = id;
        this.surname = surname;
        this.forename = forename;
        this.nhsNumber = nhsNumber;
    }
}