package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

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

    @Test
    void badAdmissionClass() {
        AdmissionClass admission = new AdmissionClass(null, null, null, null);

        assertNull(admission.id);
        assertNull(admission.dischargeDate);
        assertNull(admission.getAdmissionDateParsed());
        assertNull(admission.getDischargeDateParsed());
    }

    @Test
    void admissionClassWithDateParser() {
        AdmissionClass admission = new AdmissionClass(1, "1979-12-22T15:00:00", null, 1);

        Calendar admissionDate = DateFormatter.parseDate(admission.admissionDate);

        assertEquals(admissionDate.get(Calendar.YEAR), 1979);
        assertEquals(admissionDate.get(Calendar.MONTH), 11);
        assertEquals(admissionDate.get(Calendar.DAY_OF_MONTH), 22);
    }

    @Test
    void admissionClassWithDateParser2() {
        AdmissionClass admission = new AdmissionClass(1, "1979-12-22T15:00:00", null, 1);

        Calendar admissionDate = DateFormatter.parseDate(admission.admissionDate);

        Calendar admissionDate2 = DateFormatter.parseDate("1980-12-22T15:00:00");

        assertTrue(admissionDate2.after(admissionDate));

        admissionDate2.set(Calendar.YEAR, admissionDate2.get(Calendar.YEAR) - 2);

        assertTrue(admissionDate2.before(admissionDate));

        admissionDate2.set(Calendar.YEAR, admissionDate2.get(Calendar.YEAR) + 1);

        assertTrue(admissionDate2.equals(admissionDate));
    }
}