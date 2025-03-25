package com.example.comp2005_report;

import org.springframework.lang.NonNull;

import java.util.Calendar;
import java.util.Date;

public class AllocationClass {
    @NonNull
    public Integer id;
    @NonNull
    public Integer admissionID;
    @NonNull
    public Integer employeeID;
    @NonNull
    public Calendar startDate;
    @NonNull
    public Calendar endDate;

    public AllocationClass(Integer id, Integer admissionID, Integer employeeID, String startDate, String endDate) {
        this.id = id;
        this.admissionID = admissionID;
        this.employeeID = employeeID;
        this.startDate = DateFormatter.parseDate(startDate);
        this.endDate = DateFormatter.parseDate(endDate);
    }
}
