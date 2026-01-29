public class Todo extends Task {

    public Todo(String description) throws KoalaException {
        super(description);
    }

    @Override
    public String toStoreString() {
        return "T | " + (this.getIsComplete() ? "1" : "0") + " | " + this.getDescription();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}