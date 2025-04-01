package com.example.comp2005_report;

import java.util.Calendar;
import java.util.List;

public class AdmissionUtils {

    public static boolean isPatientReadmittedWithin7Days(
            List<AdmissionClass> admissions
    ) {
        // if less than 2 admissions they cannot be readmitted
        // even if the admission date is 0-7 after the discharge date,
        // i will disallow if in the same admission
        if (admissions.size() < 2) return false;

        for (AdmissionClass admissionA : admissions) {
            for (AdmissionClass admissionB : admissions) {
                // if the admission records are the same, discard
                if (admissionA.id.equals(admissionB.id)) continue;

                Calendar dischargeDate = admissionB.getDischargeDateParsed();
                Calendar admissionDate = admissionA.getAdmissionDateParsed();

                // if not discharged, then it cannot be
                if (dischargeDate == null) continue;

                double diff = Utils.differenceInDays(admissionDate, dischargeDate);

                // difference must be more than discharge date but less (or =) then a week
                if (0.0 <= diff && diff <= 7.0) return true;
            }
        }

        return false;
    }

    public static AdmissionClass[] getAdmissions() throws ApiError, ParseError{
        String admissionsStr = APIHelper.get("/admissions");

        AdmissionClass[] admissions = Parser.parse(admissionsStr, AdmissionClass[].class);

        return admissions;
    }
}
