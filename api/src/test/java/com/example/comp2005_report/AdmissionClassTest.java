package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class AdmissionClassTest {
    @Test
    void createAdmissionClass() {
        AdmissionClass admission = new AdmissionClass(1, "1979-12-22T15:00:00", null, 12);

        assertEquals(1, admission.id);
        assertEquals(12, admission.patientID);
        assertEquals("1979-12-22T15:00:00", admission.admissionDate);
        assertNull(admission.dischargeDate);
        assertEquals(11, admission.getAdmissionDateParsed().get(Calendar.MONTH));
        assertEquals(1979, admission.getAdmissionDateParsed().get(Calendar.YEAR));
        assertNull(admission.getDischargeDateParsed());
    }
}