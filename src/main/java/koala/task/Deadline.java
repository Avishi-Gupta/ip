package koala.task;
import koala.InvalidTaskException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Deadline extends Task {

    protected LocalDate due;

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final List<DateTimeFormatter> INPUT_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy")
    );

    public Deadline(String description, String due) throws InvalidTaskException {
        super(description);
        this.due = parseDate(due);
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
        return "D | " + (this.getIsComplete() ? "1" : "0") + " | " + this.getDescription() + " | " + this.due.format(DISPLAY_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.due.format(DISPLAY_FORMAT) + ")";
    }
}
