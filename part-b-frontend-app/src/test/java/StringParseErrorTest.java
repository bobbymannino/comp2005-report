import com.example.comp2005_report.utils.StringParseError;
import com.example.comp2005_report.utils.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Testable
class StringParseErrorTest {

    @Test
    void testStringParseError2Args() {
        assertThrows(StringParseError.class, () -> {
            StringParser.parse("tom", Integer.class);
        });
    }

    @Test
    void testStringParseError3Args() {
        assertThrows(StringParseError.class, () -> {
            StringParser.parse("tom", "tom", Integer.class);
        });
    }
}
