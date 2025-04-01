package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdmissionService {

    private final ObjectMapper objectMapper;

    public AdmissionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /// These are patients that have been re-admitted within 7 days of being discharged
    @Operation(
        summary = "Re-admitted Patients < 7 days",
        description = "Patients that were discharged and admitted within 7 days of being discharged"
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
    @GetMapping("/admissions/re")
    public ResponseEntity<ObjectNode> getReadmittedAdmissions() {
        String admissionsStr;

        try {
            // Get all admissions (as string)
            admissionsStr = APIHelper.get("/admissions");
        } catch (ApiError e) {
            return HttpErrorResponse.ApiErrorResponse();
        }

        AdmissionClass[] admissions;
        try {
            // Turn string into data i can work with
            admissions = objectMapper.readValue(
                admissionsStr,
                AdmissionClass[].class
            );
        } catch (Exception e) {
            return HttpErrorResponse.ParseErrorResponse();
        }

        // An admission class looks like this:
        // { id: int; admissionDate: string; dischargeDate?: string; patientID: int; }

        // 1. split admissions into an object where the key is the patientID
        // and the value is an array of admissions
        HashMap<Integer, List<AdmissionClass>> patients = new HashMap<>();
        for (AdmissionClass admission : admissions) {
            List<AdmissionClass> patientAdmissions = patients.getOrDefault(
                admission.patientID,
                new ArrayList<>()
            );
            patientAdmissions.add(admission);
            patients.put(admission.patientID, patientAdmissions);
        }

        ArrayNode patientIdsWhoHaveBeenReadmitted =
            objectMapper.createArrayNode();

        // 2. go through each patient's admissions and check if the diff is < 7 days
        patients.forEach((patientId, patientAdmissions) -> {
            boolean isPatientReadmitted =
                AdmissionUtils.isPatientReadmittedWithin7Days(
                    patientAdmissions
                );

            if (isPatientReadmitted) patientIdsWhoHaveBeenReadmitted.add(
                patientId
            );
        });

        ObjectNode res = objectMapper.createObjectNode();

        res.set("admissions", patientIdsWhoHaveBeenReadmitted);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /// These are patients who have never been admitted
    @Operation(
        summary = "Never admitted patients",
        description = "Patients that have never been admitted"
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
    @GetMapping("/admissions/never")
    public ResponseEntity<ObjectNode> getNeverBeenAdmitted() {
        String admissionsStr;

        try {
            admissionsStr = APIHelper.get("/admissions");
        } catch (ApiError e) {
            return HttpErrorResponse.ApiErrorResponse();
        }

        AdmissionClass[] admissions;
        try {
            admissions = objectMapper.readValue(
                admissionsStr,
                AdmissionClass[].class
            );
        } catch (Exception e) {
            return HttpErrorResponse.ParseErrorResponse();
        }

        String patientsStr;

        try {
            patientsStr = APIHelper.get("/patients");
        } catch (ApiError e) {
            return HttpErrorResponse.ApiErrorResponse();
        }

        PatientClass[] patients;
        try {
            patients = objectMapper.readValue(
                patientsStr,
                PatientClass[].class
            );
        } catch (Exception e) {
            return HttpErrorResponse.ParseErrorResponse();
        }

        // I laugh at this long variable name, but I think it's best to have clear names
        Integer[] allPatients = Arrays.stream(patients)
            .map(p -> p.id)
            .toArray(Integer[]::new);

        // Turn admissions into array of unique patient IDs, use distinct to reduce loop later on
        Integer[] patientsThatHaveBeenAdmitted = Arrays.stream(admissions)
            .map(a -> a.patientID)
            .distinct()
            .toArray(Integer[]::new);

        Integer[] patientsThatHaveNotBeenAdmitted = Utils.difference(
            allPatients,
            patientsThatHaveBeenAdmitted
        );

        ArrayNode arr = objectMapper.createArrayNode();
        // Add patient ID to array
        for (Integer id : patientsThatHaveNotBeenAdmitted) {
            arr.add(id);
        }

        ObjectNode res = objectMapper.createObjectNode();

        res.set("admissions", arr);

        // Return a list of patient IDs that have not been admitted
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /// The month with the most admissions
    @Operation(
        summary = "The month with the most admissions",
        description = "The month with the most admissions"
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
    @GetMapping("/admissions/most")
    public ResponseEntity<ObjectNode> busiestMonthForAdmissions() {
        AdmissionClass[] admissions;

        try {
           admissions = AdmissionUtils.getAdmissions();
        } catch (ApiError e) {
            return HttpErrorResponse.ApiErrorResponse();
        } catch (ParseError e) {
            return HttpErrorResponse.ParseErrorResponse();
        }

        ObjectNode out = objectMapper.createObjectNode();

        if (admissions.length == 0) {
            out.set("admissions", null);
            out.set("busiestMonth", null);
            out.put(
                "message",
                "There were no admissions so no month was the busiest"
            );

            return new ResponseEntity<>(out, HttpStatus.OK);
        }

        int[] most = new int[12];

        for (AdmissionClass admission : admissions) {
            Calendar cal = DateFormatter.parseDate(admission.admissionDate);
            if (cal == null) continue;

            int month = cal.get(Calendar.MONTH);

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

        out.put("busiestMonth", months[maxIndex]);
        out.put("admissions", max);

        return new ResponseEntity<>(out, HttpStatus.OK);
    }
}
