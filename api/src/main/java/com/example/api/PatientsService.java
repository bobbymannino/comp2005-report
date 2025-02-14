package com.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.juli.logging.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class PatientsService {
    private final ObjectMapper objectMapper;

    public PatientsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    // list of patients who have never been admitted
    // list of patients who have been re admitted within 7 days of being discharged
    // list of patients who have had more then 1 member of staff
}
