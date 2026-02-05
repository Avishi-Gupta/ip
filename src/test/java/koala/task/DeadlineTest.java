package koala.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import koala.InvalidTaskException;

public class DeadlineTest {

    @Test
    public void constructor_validDate_formatsParsedCorrectly() throws InvalidTaskException {
        Deadline d1 = new Deadline("submit report", "2026-02-10");
        Deadline d2 = new Deadline("submit report", "10/02/2026");

        assertEquals("submit report", d1.getDescription());
        assertEquals("submit report", d2.getDescription());
    }

    @Test
    public void constructor_invalidDate_throwsException() {
        assertThrows(
                InvalidTaskException.class,
                () -> new Deadline("submit report", "tomorrow")
        );
    }
}
