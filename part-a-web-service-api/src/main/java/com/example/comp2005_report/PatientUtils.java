package com.example.comp2005_report;

public class PatientUtils {
    public static PatientClass getPatient (Integer id) throws ParseError, ApiError {
        String patientStr = APIHelper.get("/patients/" + id);

        PatientClass patient = Parser.parse(patientStr, PatientClass.class);

        return patient;
    }
}
