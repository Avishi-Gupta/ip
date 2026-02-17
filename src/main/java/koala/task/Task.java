package koala.task;

import java.time.LocalDate;

import koala.InvalidTaskException;

/**
 * Represents a generic task with a description and completion status.
 */

public class Task {

    private final String description;
    private boolean isComplete;

    /**
     * Constructs a Task.
     *
     * @param description The description of the task.
     * @throws InvalidTaskException If the description is invalid.
     */
    public Task(String description) throws InvalidTaskException {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidTaskException("Task description cannot be empty.");
        }
        this.description = description;
        this.isComplete = false;
    }

    public void markAsComplete() {
        this.isComplete = true;
    }

    public void markAsIncomplete() {
        this.isComplete = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void delete() {
    }

    public String formatForStorage() {
        return "";
    }

    public boolean isScheduledFor(LocalDate date) {
    return false;
    }

    @Override
    public String toString() {
        return (isComplete ? "[X] " : "[ ] ") + description;
    }
}
