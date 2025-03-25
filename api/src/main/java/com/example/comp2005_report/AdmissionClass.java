package com.example.comp2005_report;

import org.springframework.lang.NonNull;

import java.util.Calendar;
import java.util.Date;

public class AdmissionClass {
    @NonNull
    public Integer id;
    @NonNull
    public String admissionDate;
    public String dischargeDate;
    @NonNull
    public Integer patientID;

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


    public Calendar getAdmissionDateParsed() {
        return DateFormatter.parseDate(this.admissionDate);
    }

    public Calendar getDischargeDateParsed() {
        return DateFormatter.parseDate(this.dischargeDate);
    }
}