package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class AdmissionUtilsTest {
    @Test
    void testEdgeCaseWithin7Days() {
        // arrange
        AdmissionClass admission1 = new AdmissionClass(1, "1979-12-22T15:00:00", "1979-12-22T15:00:00", 1);
        AdmissionClass admission2 = new AdmissionClass(2, "1979-12-29T15:00:00", null, 1);

        List<AdmissionClass> admissions = new ArrayList<>();
        admissions.add(admission1);
        admissions.add(admission2);

        // act
        boolean isReadmittedWithin7Days = AdmissionUtils.isPatientReadmittedWithin7Days(admissions);

        // assert
        assertTrue(isReadmittedWithin7Days);
    }
}