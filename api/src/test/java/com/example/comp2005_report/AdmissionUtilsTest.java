package com.example.comp2005_report;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class AdmissionUtilsTest {

    @Test
    void testEdgeCaseWithin7Days() {
        // arrange
        AdmissionClass admission1 = new AdmissionClass(
            1,
            "1979-12-22T15:00:00",
            "1979-12-22T15:00:00",
            1
        );
        AdmissionClass admission2 = new AdmissionClass(
            2,
            "1979-12-29T15:00:00",
            null,
            1
        );

        List<AdmissionClass> admissions = new ArrayList<>();
        admissions.add(admission1);
        admissions.add(admission2);

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertTrue(isReadmittedWithin7Days);
    }

    @Test
    void testOneAdmission() {
        // arrange
        AdmissionClass admission1 = new AdmissionClass(
            1,
            "1979-12-22T15:00:00",
            "1979-12-22T15:00:00",
            1
        );

        List<AdmissionClass> admissions = new ArrayList<>();
        admissions.add(admission1);

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertFalse(isReadmittedWithin7Days);
    }

    @Test
    void testNoAdmissions() {
        // arrange
        List<AdmissionClass> admissions = new ArrayList<>();

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertFalse(isReadmittedWithin7Days);
    }

    @Test
    void test25AdmissionsNoDischarges() {
        // arrange
        List<AdmissionClass> admissions = new ArrayList<>();

        for (Integer i = 0; i < 25; i++) {
            AdmissionClass admission = new AdmissionClass(
                i + 1,
                "1979-12-22T15:00:00",
                null,
                1
            );
            admissions.add(admission);
        }

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertFalse(isReadmittedWithin7Days);
    }

    @Test
    void test5AdmissionsWith1Discharge() {
        // arrange
        List<AdmissionClass> admissions = new ArrayList<>();

        for (Integer i = 0; i < 5; i++) {
            AdmissionClass admission = new AdmissionClass(
                i + 1,
                "1979-12-22T15:00:00",
                null,
                1
            );
            if (i == 2) admission.dischargeDate = "1979-12-22T15:00:00";
            admissions.add(admission);
        }

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertTrue(isReadmittedWithin7Days);
    }

    @Test
    void testInBy1Second() {
        // arrange
        List<AdmissionClass> admissions = new ArrayList<>();

        AdmissionClass admissionA = new AdmissionClass(
            1,
            "1979-12-20T15:00:00",
            "1979-12-22T15:00:00",
            1
        );
        admissions.add(admissionA);

        AdmissionClass admissionB = new AdmissionClass(
            2,
            "1979-12-29T14:59:59",
            null,
            1
        );
        admissions.add(admissionB);

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertTrue(isReadmittedWithin7Days);
    }

    @Test
    void testOutBy1Second() {
        // arrange
        List<AdmissionClass> admissions = new ArrayList<>();

        AdmissionClass admissionA = new AdmissionClass(
            1,
            "1979-12-20T15:00:00",
            "1979-12-22T15:00:00",
            1
        );
        admissions.add(admissionA);

        AdmissionClass admissionB = new AdmissionClass(
            2,
            "1979-12-29T15:00:01",
            null,
            1
        );
        admissions.add(admissionB);

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertFalse(isReadmittedWithin7Days);
    }

    @Test
    void testOutBy1Hour() {
        // arrange
        List<AdmissionClass> admissions = new ArrayList<>();

        AdmissionClass admissionA = new AdmissionClass(
            1,
            "1979-12-20T15:00:00",
            "1979-12-22T15:00:00",
            1
        );
        admissions.add(admissionA);

        AdmissionClass admissionB = new AdmissionClass(
            2,
            "1979-12-29T16:00:00",
            null,
            1
        );
        admissions.add(admissionB);

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertFalse(isReadmittedWithin7Days);
    }

    @Test
    void testInBy1Hour() {
        // arrange
        List<AdmissionClass> admissions = new ArrayList<>();

        AdmissionClass admissionA = new AdmissionClass(
            1,
            "1979-12-20T15:00:00",
            "1979-12-22T15:00:00",
            1
        );
        admissions.add(admissionA);

        AdmissionClass admissionB = new AdmissionClass(
            2,
            "1979-12-29T14:00:00",
            null,
            1
        );
        admissions.add(admissionB);

        // act
        boolean isReadmittedWithin7Days =
            AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertTrue(isReadmittedWithin7Days);
    }
}
