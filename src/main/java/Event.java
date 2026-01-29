public class Event extends Task {
    
    protected String from;
    protected String to;

    public Event(String description, String from, String to) throws KoalaException {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toStoreString() {
        return "E | " + (this.getIsComplete() ? "1" : "0") + " | " + this.getDescription() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}