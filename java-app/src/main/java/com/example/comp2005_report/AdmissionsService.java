package com.example.comp2005_report;

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
        String patients = APIHelper.get("/admissions");

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

        int[] most = new int[12];

        for (AdmissionClass admission : admissions) {
            int month =
                DateFormatter.parseDate(
                    admission.admissionDate
                ).getMonthValue() -
                1;
            most[month]++;
        }

        int maxIndex = -1;
        int max = 0;

        String[] months = new String[] {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",
        };

        for (int i = 0; i < most.length; i++) {
            if (most[i] > max) {
                maxIndex = i;
                max = most[i];
            }
        }

        node.put("mostMonth", months[maxIndex]);

        return node;
    }
}
