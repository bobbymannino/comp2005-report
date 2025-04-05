package com.example.comp2005_report;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class HttpErrorResponseTest {
    @Test
    void apiErrorResponse() {
        ResponseEntity<ObjectNode> res = HttpErrorResponse.ApiErrorResponse();

        assertEquals(503, res.getStatusCode().value());
    }

    @Test
    void parseErrorResponse() {
        ResponseEntity<ObjectNode> res = HttpErrorResponse.ParseErrorResponse();

        assertEquals(500, res.getStatusCode().value());
    }
}