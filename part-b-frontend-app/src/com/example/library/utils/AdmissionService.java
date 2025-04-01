package com.example.library.utils;

public class AdmissionService {
    public static Integer[] getInadmittedPatients() throws ApiError, StringParseError {
        String patientIdsStr = ApiService.get(ApiBase.LOCAL, "/admissions/never");

        Integer[] patientIds = StringParser.parse(patientIdsStr, "admissions", Integer[].class);

        return patientIds;
    }
}
