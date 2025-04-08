import com.example.comp2005_report.utils.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testable
class ApiErrorTest {

    @Test
    public void testConstructorWithMessage() {
        // Arrange
        String errorMessage = "Network connection failed";

        // Act
        ApiError error = new ApiError(errorMessage);

        // Assert
        assertEquals(errorMessage, error.getMessage());
    }

    @Test
    public void testInheritanceFromException() {
        // Arrange & Act
        ApiError error = new ApiError("Test error");

        // Assert
        assertTrue(error instanceof Exception);
    }

    @Test
    public void testExceptionHandling() {
        // Arrange
        String errorMessage = "Invalid input";
        boolean exceptionCaught = false;

        try {
            // Act
            throw new ApiError(errorMessage);
        } catch (Exception e) {
            // Assert
            exceptionCaught = true;
            assertEquals(errorMessage, e.getMessage());
            assertTrue(e instanceof ApiError);
        }

        assertTrue(exceptionCaught);
    }
}
