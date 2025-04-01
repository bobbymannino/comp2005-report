package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientService {

    private final ObjectMapper objectMapper;

    public PatientService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /// A list of patients who have had > 1 member of staff
    @Operation(
        summary = "Patients who had 2+ members of staff",
        description = "Patients who have had 2 or more members of staff"
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "Ok - Patients returned"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Parse error - Failed to parse string into a class"
            ),
            @ApiResponse(
                responseCode = "503",
                description = "API error - Failed to reach the UoP API"
            ),
        }
    )
    @GetMapping("/patients/multi-staff")
    public ResponseEntity<ObjectNode> getReadmittedAdmissions() {
        // create a hashmap key: patientId, value: employeeId[]
        HashMap<Integer, List<Integer>> patients = new HashMap<>();

        // get all allocations
        String allocationsStr;

        try {
            allocationsStr = APIHelper.get("/allocations");
        } catch (ApiError e) {
            return HttpErrorResponse.ApiErrorResponse();
        }

        AllocationClass[] allocations;
        try {
            allocations = objectMapper.readValue(
                allocationsStr,
                AllocationClass[].class
            );
        } catch (Exception e) {
            return HttpErrorResponse.ParseErrorResponse();
        }

        // for each allocation:
        for (AllocationClass allocation : allocations) {
            String admissionStr;

            try {
                // - get admission
                admissionStr = APIHelper.get(
                    "/admissions/" + allocation.admissionID
                );
            } catch (ApiError e) {
                return HttpErrorResponse.ApiErrorResponse();
            }

            AdmissionClass admission;
            try {
                admission = objectMapper.readValue(
                    admissionStr,
                    AdmissionClass.class
                );
            } catch (Exception e) {
                return HttpErrorResponse.ParseErrorResponse();
            }

            // - add allocation.employeeId to hashmap using key admission.patientId
            List<Integer> patientEmployees = patients.getOrDefault(
                admission.patientID,
                new ArrayList<>()
            );
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
                if (!uniqueList.contains(employeeID)) uniqueList.add(
                    employeeID
                );
            }

            // return a list of patientIds who have a value.length > 1
            if (uniqueList.size() >= 2) patientsThatHaveHadManyStaff.add(
                patientId
            );
        });

        ArrayNode res = objectMapper.createArrayNode();

        for (Integer patientID : patientsThatHaveHadManyStaff) {
            res.add(patientID);
        }

        ObjectNode result = objectMapper.createObjectNode();

        result.set("patients", res);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(
        summary = "Patient details",
        description = "Retrieve a patients details"
    )
    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "Ok - Patient returned"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Parse error - Failed to parse string into a class"
            ),
            @ApiResponse(
                responseCode = "503",
                description = "API error - Failed to reach the UoP API"
            ),
        }
    )
    @GetMapping("/patients/{id}")
    public ResponseEntity<ObjectNode> getReadmittedAdmissions(
        @PathVariable("id") Integer id
    ) {
        PatientClass patient;

        try {
            patient = PatientUtils.getPatient(id);
        } catch (ParseError e) {
            return HttpErrorResponse.ParseErrorResponse();
        } catch (ApiError e) {
            return HttpErrorResponse.ApiErrorResponse();
        }


        ObjectNode out = objectMapper.createObjectNode();

        out.put("id", patient.id);
        out.put("firstName", patient.forename);
        out.put("lastName", patient.surname);
        out.put("nshNumber", patient.nhsNumber);

        return new ResponseEntity<>(out, HttpStatus.OK);
    }
}
