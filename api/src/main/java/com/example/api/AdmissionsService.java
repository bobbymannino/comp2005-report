package com.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdmissionsService {

    private final ObjectMapper objectMapper;

    public AdmissionsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // identify month with the most admissions

    @GetMapping("/admissions/most")
    public ObjectNode index() {
        String patients = ApiService.get("/admissions");

        System.out.println(patients);

        AdmissionClass[] admissions;

        try {
            admissions = objectMapper.readValue(
                patients,
                AdmissionClass[].class
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ObjectNode node = objectMapper.createObjectNode();

        ArrayNode admissionsNode = node.putArray("admissions");

        for (AdmissionClass admission : admissions) {
            ObjectNode admissionNode = admissionsNode.addObject();

            admissionNode.put("id", admission.id);
            admissionNode.put("patientID", admission.patientID);
            admissionNode.put("admissionDate", admission.admissionDate);
            admissionNode.put("dischargeDate", admission.dischargeDate);
        }

        return node;
    }
}
