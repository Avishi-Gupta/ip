package koala.task;

import koala.InvalidTaskException;
/**
 * Represents a todo task with a description.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task.
     *
     * @param description The description of the todo task.
     * @throws InvalidTaskException If the description is invalid.
     */
    
    public Todo(String description) throws InvalidTaskException {
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