package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class PatientUtilsTest {
    @Test
    void getPatient404() {
        assertThrows(ApiError.class, () -> {
            PatientUtils.getPatient(-1);
        });
    }
}