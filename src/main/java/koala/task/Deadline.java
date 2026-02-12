package koala.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalTime;

import koala.InvalidTaskException;

/**
 * Represents a deadline task with a description and a due date.
 */
public class Deadline extends Task {

    protected LocalDateTime due;

    private static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    
    private static final DateTimeFormatter DISPLAY_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    private static final List<DateTimeFormatter> INPUT_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")
    );

    /**
     * Constructs a Deadline task.
     *
     * @param description The description of the deadline task.
     * @param due The due date as a string.
     * @throws InvalidTaskException If the date format is invalid.
     */
    public Deadline(String description, String due) throws InvalidTaskException {
        super(description);
        this.due = parseDate(due);
    }

    /*
        * Parses a date string into a LocalDateTime object using multiple formats.
        *
        * @param dateStr The date string to parse.
        * @return The parsed LocalDateTime object.
        * @throws InvalidTaskException If the date format is invalid.
        */
    private LocalDateTime parseDate(String dateStr) throws InvalidTaskException {
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(dateStr, format);
            } catch (Exception e) {
                // try next
            }
            try {
                return LocalDate.parse(dateStr, format).atStartOfDay();
            } catch (Exception e) {
                // try next
            }
        }
        throw new InvalidTaskException("Invalid date format");
    }

    /* 
        * Formats a LocalDateTime object for display.
        *
        * @param dateTime The LocalDateTime object to format.
        * @return The formatted date string.
        */
    private String formatForDisplay(LocalDateTime dateTime) { 
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.format(DISPLAY_DATE_FORMAT); 
        } 
        return dateTime.format(DISPLAY_DATE_TIME_FORMAT); 
    }

    @Override
    public String formatForStorage() {
        return "D | " + (this.getIsComplete() ? "1" : "0") + " | " + this.getDescription() + " | " + this.formatForDisplay(this.due);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.formatForDisplay(this.due) + ")";
    }
}
