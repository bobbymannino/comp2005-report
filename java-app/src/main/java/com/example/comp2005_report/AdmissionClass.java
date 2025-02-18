package com.example.comp2005_report;

import org.springframework.lang.NonNull;

public class AdmissionClass {
    @NonNull
    public int id;
    @NonNull
    public String admissionDate;
    public String dischargeDate;
    @NonNull
    public int patientID;

    public AdmissionClass(
            int id,
            String admissionDate,
            String dischargeDate,
            int patientID
    ) {
        this.id = id;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.patientID = patientID;
    }
}