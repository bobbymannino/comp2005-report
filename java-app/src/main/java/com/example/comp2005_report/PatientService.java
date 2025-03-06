package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class PatientService {

    private final ObjectMapper objectMapper;

    public PatientService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /// A list of patients who have had > 1 member of staff
    @GetMapping("/patients/multi-staff")
    public ArrayNode getReadmittedAdmissions() {
        // create a hashmap key: patientId, value: employeeId[]
        HashMap<Integer, List<Integer>> patients = new HashMap<>();

        // get all allocations
        String allocationsStr = APIHelper.get("/allocations");
        AllocationClass[] allocations;
        try {
            allocations = objectMapper.readValue(allocationsStr, AllocationClass[].class);
        } catch (Exception e) {
            throw new Error("Failed to parse AllocationClass[] in /patients/multi-staff");
        }

        // for each allocation:
        for (AllocationClass allocation : allocations) {
            // - get admission
            String admissionStr = APIHelper.get("/admissions/" + allocation.admissionID);
            AdmissionClass admission;
            try {
                admission = objectMapper.readValue(admissionStr, AdmissionClass.class);
            } catch (Exception e) {
                throw new Error("Failed to parse AdmissionClass in /patients/multi-staff");
            }

            // - add allocation.employeeId to hashmap using key admission.patientId
            List<Integer> patientEmployees = patients.getOrDefault(admission.patientID, new ArrayList<>());
            if (!patientEmployees.contains(allocation.employeeID)) {
                patientEmployees.add(allocation.employeeID);
            }
            patients.put(admission.patientID, patientEmployees);
        }

        List<Integer> patientsThatHaveHadManyStaff = new ArrayList<>();

        patients.forEach((patientId, employeeIDs) -> {
            List<Integer> uniqueList = new ArrayList<>();

            // ensure employeeIds are unique per value in the hashmap
            for (Integer employeeID : employeeIDs) {
                if (!uniqueList.contains(employeeID)) uniqueList.add(employeeID);
            }

            // return a list of patientIds who have a value.length > 1
            if (uniqueList.size() >= 2) patientsThatHaveHadManyStaff.add(patientId);
        });

        ArrayNode res = objectMapper.createArrayNode();

        for (Integer patientID : patientsThatHaveHadManyStaff) {
            res.add(patientID);
        }

        return res;
    }
}
