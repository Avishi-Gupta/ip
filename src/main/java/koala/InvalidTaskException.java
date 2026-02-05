package koala;

/**
 * Exception thrown when a task description is invalid.
 */
public class InvalidTaskException extends Exception {

    public InvalidTaskException(String message) {
        super(message);
    }
}
