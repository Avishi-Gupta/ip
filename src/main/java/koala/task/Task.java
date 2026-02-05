package koala.task;

import koala.InvalidTaskException;

public class Task {
    private final String descript;
    private boolean isComplete;

    public Task (String descript) throws InvalidTaskException {
        if (descript == null || descript.trim().isEmpty()) {
            throw new InvalidTaskException("Task description cannot be empty.");
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

    public String getDescription() {
        return descript;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void delete() {
    }

    public String toStoreString() {
        return "";
    }   

    @Override
    public String toString() {
        return (isComplete ? "[X] " : "[ ] ") + descript;  
    }
}