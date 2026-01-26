public class Deadline extends Task {

    protected String due;

    public Deadline(String description, String due) throws KoalaException {
        super(description);
        this.due = due;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + due + ")";
    }
}
