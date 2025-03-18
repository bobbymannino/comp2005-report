package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HttpErrorResponse {
    public Integer status;
    public String message;

    public HttpErrorResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ObjectNode toObjectNode() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode node = mapper.createObjectNode();

        node.put("status", status);
        node.put("message", message);

        return node;
    }
}
