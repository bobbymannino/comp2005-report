import com.example.comp2005_report.records.Patient;
import com.example.comp2005_report.utils.ApiError;
import com.example.comp2005_report.utils.PatientService;
import com.example.comp2005_report.utils.StringParseError;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

/// Note: for these tests to work the UoP API must also be working
@Testable
class PatientServiceTest {
    @Test
    void testGetNonExistentPatient() {
        assertThrows(ApiError.class, () -> PatientService.getPatient(-1));
    }

    @Test
    void testGetNonExistentPatientOrNull() {
        assertNull(PatientService.getPatientOrNull(-1));
    }

    /// Note: for this to work it requires there to be a patient with ID `4`
    @Test
    void testGetPatientOrNull() {
        Patient patient = PatientService.getPatientOrNull(4);

        assertNotNull(patient);

        assertEquals(4, patient.id);
    }

    /// Note: for this to work it requires there to be a patient with ID `4`
    @Test
    void testGetPatient() throws ApiError, StringParseError {
        Patient patient = PatientService.getPatient(4);

        assertNotNull(patient);

        assertEquals(4, patient.id);
    }
}