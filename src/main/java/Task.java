public class Task {
    private final String descript;
    private boolean isComplete;

    public Task (String descript) throws KoalaException {
        if (descript == null || descript.trim().isEmpty()) {
            throw new KoalaException("Task description cannot be empty.");
        }
        this.descript = descript;
        this.isComplete = false;
    }

    public void markAsComplete() {
        this.isComplete = true;
    }

    public void markAsIncomplete() {
        this.isComplete = false;
    }

    public String getDescript() {
        return descript;
    }

    public void delete() {
    }

    @Override
    public String toString() {
        return (isComplete ? "[X] " : "[ ] ") + descript;  
    }
}