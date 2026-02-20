package koala.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalTime;

import koala.InvalidTaskException;

/**
 * Represents an event task with a description, start date, and end date.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

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

    /*
     * Constructs an Event task.
     * @param description The description of the event task.
     * @param from The start date as a string.
     * @param to The end date as a string.
     * @throws InvalidTaskException If the date format is invalid.
     */
    public Event(String description, String from, String to) throws InvalidTaskException {
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);

        assert description != null : "Task description should not be null";
        assert from != null : "Start date should not be null";
        assert to != null : "End date should not be null";
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
                // Continue to the next format
            }

            try {
                return LocalDate.parse(dateStr, format).atStartOfDay();
            } catch (Exception e) {
                // Continue to the next format
            }
        }
        throw new InvalidTaskException("Invalid date format. Try formats like 2026-02-18, 18/02/2026 or Mar 18 2026.\n"
                                        + "If you need to add time, then try 2026-02-18 12:00, 18/02/2026 23:59 or Mar 18 2026 13:00.\n"
                                        + "See I give you options but still you choose to be difficult :((");
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

    /* 
    * Checks if the event is scheduled for a specific date.
    * @param date The date to check against.
    * @return True if the event occurs on the specified date, false otherwise.
     */
    @Override
    public boolean isScheduledFor(LocalDate date) {
        return (from.toLocalDate().equals(date) || from.toLocalDate().isBefore(date)) &&
               (to.toLocalDate().equals(date) || to.toLocalDate().isAfter(date));
    }

    @Override
    public String formatForStorage() {
        return "E | " + (this.getIsComplete() ? "1" : "0") + " | " + this.getDescription() + " | " + this.formatForDisplay(this.from) + " | " + this.formatForDisplay(this.to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.formatForDisplay(this.from) + " to: " + this.formatForDisplay(this.to) + ")";
    }
}
