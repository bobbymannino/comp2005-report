package com.example.library.records;

public class Patient {
    public Integer id;
    public String forename;
    public String surname;
    public String nhsNumber;

    public Patient(Integer id, String forename, String surname, String nhsNumber) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.nhsNumber = nhsNumber;
    }
}
