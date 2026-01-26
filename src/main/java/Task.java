public class Task {
    private final String descript;
    private boolean isComplete;

    public Task (String descript) {
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

    @Override
    public String toString() {
        return (isComplete ? "[X] " : "[ ] ") + descript;  
    }
}