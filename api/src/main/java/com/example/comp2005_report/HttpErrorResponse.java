package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpErrorResponse {
    private Integer status;
    private String message;

    private HttpErrorResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode node = mapper.createObjectNode();

        node.put("status", status);
        node.put("message", message);

        return node;
    }

    public static ResponseEntity<ObjectNode> ApiErrorResponse() {
        HttpErrorResponse error = new HttpErrorResponse(503, "The university API is not working at the moment, please try again later.");

        return new ResponseEntity<>(error.toObjectNode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    public static ResponseEntity<ObjectNode> ParseErrorResponse() {
        HttpErrorResponse error = new HttpErrorResponse(500, "The data returned from the API is malformed, please try again later.");

        return new ResponseEntity<>(error.toObjectNode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
