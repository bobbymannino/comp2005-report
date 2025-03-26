package com.example.library.utils;

import com.example.library.records.Patient;

public class PatientService {
    public static Patient getPatient(Integer patientId) throws ApiError, StringParseError {
        String patientStr = ApiService.get(ApiBase.UNI, "/patients/" + patientId);

        Patient patient = StringParser.parse(patientStr, Patient.class);

        return patient;
    }
}
