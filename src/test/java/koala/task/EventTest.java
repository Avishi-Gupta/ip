package koala.task;

import koala.InvalidTaskException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test class for the Event task type in the Koala application.
 * Used ChatGPT to generate test cases based on the Event class's functionality and expected behavior.
 */
class EventTest {

    @Test
    void constructor_validDateOnly_formatsCorrectly() throws InvalidTaskException {
        Event event = new Event("Conference", "2026-03-10", "2026-03-12");

        String output = event.toString();

        assertTrue(output.contains("Mar 10 2026"));
        assertTrue(output.contains("Mar 12 2026"));
    }

    @Test
    void constructor_validDateTime_formatsCorrectly() throws InvalidTaskException {
        Event event = new Event("Meeting", "2026-03-10 14:00", "2026-03-10 16:00");

        String output = event.toString();

        assertTrue(output.contains("14:00"));
        assertTrue(output.contains("16:00"));
    }

    @Test
    void constructor_invalidDate_throwsException() {
        assertThrows(InvalidTaskException.class, () ->
                new Event("Invalid", "notadate", "alsonotadate")
        );
    }

    @Test
    void isScheduledFor_dateWithinRange_returnsTrue() throws InvalidTaskException {
        Event event = new Event("Trip", "2026-03-10", "2026-03-12");

        assertTrue(event.isScheduledFor(LocalDate.of(2026, 3, 11)));
    }

    @Test
    void isScheduledFor_dateOutsideRange_returnsFalse() throws InvalidTaskException {
        Event event = new Event("Trip", "2026-03-10", "2026-03-12");

        assertFalse(event.isScheduledFor(LocalDate.of(2026, 3, 15)));
    }

    @Test
    void formatForStorage_correctFormat() throws InvalidTaskException {
        Event event = new Event("Workshop", "2026-04-01", "2026-04-02");

        String storageFormat = event.formatForStorage();

        assertTrue(storageFormat.startsWith("E | 0 | Workshop"));
    }
}