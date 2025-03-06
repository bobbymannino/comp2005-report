package com.example.comp2005_report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class AdmissionService {

    private final ObjectMapper objectMapper;

    public AdmissionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /// These are patients that have been re-admitted within 7 days of being discharged
    @GetMapping("/admissions/re")
    public ArrayNode getReadmittedAdmissions() {
        /// Get all admissions and turn it into an array of objects
        String admissionsStr = APIHelper.get("/admissions");
        AdmissionClass[] admissions;
        try {
            admissions = objectMapper.readValue(admissionsStr, AdmissionClass[].class);
        } catch (Exception e) {
            throw new Error("Failed to parse AdmissionClass[]");
        }

        // An admission class looks like this:
        // { id: int; admissionDate: string; dischargeDate?: string; patientID: int; }

        // 1. split admissions into an object where the key is the patientID
        // and the value is an array of admissions
        HashMap<Integer, List<AdmissionClass>> patients = new HashMap<>();
        for (AdmissionClass admission : admissions) {
            List<AdmissionClass> patientAdmissions = patients.getOrDefault(admission.patientID, new ArrayList<>());
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

            for (var i = 0; i < patientAdmissions.size(); i++) {
                AdmissionClass a = patientAdmissions.get(i);

                // If the patient is already in the list then who cares about the
                // rest of the admissions for them
                if (patientIdsWhoHaveBeenReadmitted.contains(a.patientID)) break;

                for (var x = 0; x < patientAdmissions.size(); x++) {
                    AdmissionClass b = patientAdmissions.get(x);

                    // That's enough of that thank you very much :)
                    if (patientIdsWhoHaveBeenReadmitted.contains(b.patientID)) break;

                    if (a.id.equals(b.id)) continue;

                    // if a.admission < b.discharge + 7 then add patient to list
                    Date dischargeDate = b.getDischargeDateParsed();
                    if (dischargeDate == null) continue;
                    Date admissionDate = a.getAdmissionDateParsed();

                    if (Utils.differenceInDays(admissionDate, dischargeDate) <= 7.0) patientIdsWhoHaveBeenReadmitted.add(a.patientID);
                }
            }
        });

        ArrayNode arr = objectMapper.createArrayNode();

        for (Integer i : patientIdsWhoHaveBeenReadmitted) {
            arr.add(i);
        }

        return arr;
    }

    /// These are patients who have never been admitted
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

    // The month with the most admissions
    @GetMapping("/admissions/most")
    public ObjectNode index() {
        String admissionsStr = APIHelper.get("/admissions");
        AdmissionClass[] admissions;
        try {
            admissions = objectMapper.readValue(admissionsStr, AdmissionClass[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ObjectNode node = objectMapper.createObjectNode();

        int[] most = new int[12];

        for (AdmissionClass admission : admissions) {
            int month =
                DateFormatter.parseDate(
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

        return node;
    }
}
