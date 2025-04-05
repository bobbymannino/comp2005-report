package test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.records.Patient;
import com.example.library.utils.StringParseError;
import com.example.library.utils.StringParser;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

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
