package com.example.comp2005_report.utils;

import com.example.comp2005_report.records.Patient;

public class PatientService {
    public static Patient getPatient(Integer patientId) throws ApiError, StringParseError {
        String patientStr = ApiService.get(ApiBase.UNI, "/patients/" + patientId);

        Patient patient = StringParser.parse(patientStr, Patient.class);

        return patient;
    }

    /// This will take a patient ID and try to get the patient from the API, and
    /// then it will try to parse the patient into its class, if anything fails
    /// instead of throwing an error you will get null.
    ///
    /// @return A `Patient` or `null`
    public static Patient getPatientOrNull(Integer patientId) {
        try {
            return getPatient(patientId);
        } catch (ApiError | StringParseError e) {
            return null;
        }
    }
}
