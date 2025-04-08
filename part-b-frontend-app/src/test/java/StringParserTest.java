import com.example.comp2005_report.records.Patient;
import com.example.comp2005_report.utils.StringParseError;
import com.example.comp2005_report.utils.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testable
class StringParserTest {
    @Test
    void parseEmptyString() {
        String raw = "";

        assertThrows(StringParseError.class, () -> StringParser.parse(raw, Patient.class));
    }

    @Test
    void parseBlankString() {
        String raw = "     ";

        assertThrows(StringParseError.class, () -> StringParser.parse(raw, Patient.class));
    }

    @Test
    void parseNullString() {
        String raw = null;

        assertThrows(StringParseError.class, () -> StringParser.parse(raw, Patient.class));
    }

    @Test
    void parseBadPatient() {
        String raw = "{id:1,nhsNumber:\"1\"}";

        assertThrows(StringParseError.class, () -> StringParser.parse(raw, Patient.class));
    }

    @Test
    void testParsePatientStringWithoutNames() throws StringParseError {
        String raw = "{\"id\":1,\"nhsNumber\":\"1\"}";

        Patient patient = StringParser.parse(raw, Patient.class);

        assertEquals(1, patient.id);
        assertEquals("UNKNOWN", patient.firstName);
        assertEquals("UNKNOWN", patient.lastName);
        assertEquals("UNKNOWN UNKNOWN", patient.getFullName());
        assertEquals("1", patient.nhsNumber);
    }

    @Test
    void testParsePatientStringWithFirstName() throws StringParseError {
        String raw = "{\"id\":1,\"nhsNumber\":\"1\",\"firstName\":\"bob\"}";

        Patient patient = StringParser.parse(raw, Patient.class);

        assertEquals(1, patient.id);
        assertEquals("bob", patient.firstName);
        assertEquals("UNKNOWN", patient.lastName);
        assertEquals("bob UNKNOWN", patient.getFullName());
        assertEquals("1", patient.nhsNumber);
    }

    @Test
    void testParsePatientStringWithBothNames() throws StringParseError {
        String raw = "{\"id\":1,\"nhsNumber\":\"1\",\"firstName\":\"bob\",\"lastName\":\"bob\"}";

        Patient patient = StringParser.parse(raw, Patient.class);

        assertEquals(1, patient.id);
        assertEquals("bob", patient.firstName);
        assertEquals("bob", patient.lastName);
        assertEquals("bob bob", patient.getFullName());
        assertEquals("1", patient.nhsNumber);
    }
}