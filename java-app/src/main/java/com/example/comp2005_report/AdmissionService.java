package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class AdmissionService {

    private final ObjectMapper objectMapper;

    public AdmissionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/admissions/never")
    public ArrayNode getNeverBeenAdmitted() {
        String admissionsStr = APIHelper.get("/admissions");
        AdmissionClass[] admissions;
        try {
            admissions = objectMapper.readValue(admissionsStr, AdmissionClass[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String patientsStr = APIHelper.get("/patients");
        PatientClass[] patients;
        try {
            patients = objectMapper.readValue(patientsStr, PatientClass[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // I laugh at this long variable name, but I think it's best to have clear names
        Integer[] allPatients = Arrays.stream(patients).map(p -> p.id).toArray(Integer[]::new);

        // Turn admissions into array of unique patient IDs, use distinct to reduce loop later on
        Integer[] patientsThatHaveBeenAdmitted = Arrays.stream(admissions).map(a -> a.patientID).distinct().toArray(Integer[]::new);

        Integer[] patientsThatHaveNotBeenAdmitted = Utils.difference(allPatients, patientsThatHaveBeenAdmitted);

        ArrayNode arr =  objectMapper.createArrayNode();
        // Add patient ID to array
        for (Integer id : patientsThatHaveNotBeenAdmitted) {
            arr.add(id);
        }

        // Return a list of patient IDs that have not been admitted
        return arr;
    }

    // identify month with the most admissions
    @GetMapping("/admissions/most")
    public ObjectNode index() {
        String admissionsStr = APIHelper.get("/admissions");

        System.out.println("admissionsStr: " + admissionsStr);

        AdmissionClass[] admissions;

        try {
            admissions = new ObjectMapper().readValue(
                    admissionsStr,
                    AdmissionClass[].class
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ObjectNode node = objectMapper.createObjectNode();

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

        node.put("busiestMonth", months[maxIndex]);
        node.put("admissions", max);

        return node;
    }
}
