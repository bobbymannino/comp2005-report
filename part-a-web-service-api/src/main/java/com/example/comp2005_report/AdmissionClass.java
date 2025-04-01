package com.example.comp2005_report;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Calendar;
import java.util.Date;
import org.springframework.lang.NonNull;

public class AdmissionClass {

    @NonNull
    @JsonProperty("id")
    public Integer id;

    @NonNull
    @JsonProperty("admissionDate")
    public String admissionDate;

    @JsonProperty("dischargeDate")
    public String dischargeDate;

    @NonNull
    @JsonProperty("patientID")
    public Integer patientID;

    public AdmissionClass(
        @JsonProperty("id") Integer id,
        @JsonProperty("admissionDate") String admissionDate,
        @JsonProperty("dischargeDate") String dischargeDate,
        @JsonProperty("patientID") Integer patientID
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
