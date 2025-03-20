package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

        List<Integer> patientIdsWhoHaveBeenReadmitted = new ArrayList<>();

        // 2. go through each patient's admissions and check if the diff is < 7 days
        patients.forEach((patientId, patientAdmissions) -> {
            // If there is only one admission on a patient, they have not been readmitted
            // sidenote, it should be called "break" or "continue" not "return", annoys me
            if (patientAdmissions.size() == 1) return;

            //            I sometimes like to leave my prints in but just commented
            //            I may come back to it later on, and it saves me writing it again
            //            System.out.println("Patient #" + patientId + " has " + patientAdmissions.size() + " admissions");

            for (AdmissionClass a : patientAdmissions) {
                // If the patient is already in the list then who cares about the
                // rest of the admissions for them
                if (
                    patientIdsWhoHaveBeenReadmitted.contains(a.patientID)
                ) break;

                for (AdmissionClass b : patientAdmissions) {
                    // If the patient is already in the list then who cares about the
                    // rest of the admissions for them
                    if (
                        patientIdsWhoHaveBeenReadmitted.contains(b.patientID)
                    ) break;

                    // if the admission records are the same, discard
                    if (a.id.equals(b.id)) continue;

                    // if a.admission < b.discharge + 7 then add patient to list
                    Date dischargeDate = b.getDischargeDateParsed();
                    if (dischargeDate == null) continue;
                    Date admissionDate = a.getAdmissionDateParsed();

                    double diff = Utils.differenceInDays(
                        admissionDate,
                        dischargeDate
                    );

                    // difference must be more than discharge date but less (or =) then a week
                    if (
                        0.0 <= diff && diff <= 7.0
                    ) patientIdsWhoHaveBeenReadmitted.add(a.patientID);
                }
            }
        });

        ArrayNode arr = objectMapper.createArrayNode();

        for (Integer i : patientIdsWhoHaveBeenReadmitted) {
            arr.add(i);
        }

        ObjectNode res = objectMapper.createObjectNode();

        res.set("admissions", arr);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /// These are patients who have never been admitted
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

    // The month with the most admissions
    @GetMapping("/admissions/most")
    public ResponseEntity<ObjectNode> index() {
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

        ObjectNode node = objectMapper.createObjectNode();

        int[] most = new int[12];

        for (AdmissionClass admission : admissions) {
            int month = DateFormatter.parseDate(
                admission.admissionDate
            ).getMonth();
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

        return new ResponseEntity<>(node, HttpStatus.OK);
    }
}
