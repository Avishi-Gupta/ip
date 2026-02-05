package koala.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import koala.InvalidTaskException;

public class Event extends Task {

    protected LocalDate from;
    protected LocalDate to;

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final List<DateTimeFormatter> INPUT_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy")
    );  

    public Event(String description, String from, String to) throws InvalidTaskException {
        super(description);
        this.from = parseDate(from);
        this.to = parseDate(to);
    }

    private LocalDate parseDate(String dateStr) throws InvalidTaskException {
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                return LocalDate.parse(dateStr, format);
            } catch (Exception e) {
                // Continue to the next format
            }
        }
        throw new InvalidTaskException("Invalid date format");
    }

    @Override
    public String toStoreString() {
        return "E | " + (this.getIsComplete() ? "1" : "0") + " | " + this.getDescription() + " | " + this.from.format(DISPLAY_FORMAT) + " | " + this.to.format(DISPLAY_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(DISPLAY_FORMAT) + " to: " + this.to.format(DISPLAY_FORMAT) + ")";
    }
}