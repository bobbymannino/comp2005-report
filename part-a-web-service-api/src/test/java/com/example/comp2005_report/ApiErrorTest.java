package com.example.comp2005_report;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class ApiErrorTest {
    @Test
    void apiError() {
        ApiError error = new ApiError("API Failed");

        assertEquals("API Failed", error.getMessage());
    }
}