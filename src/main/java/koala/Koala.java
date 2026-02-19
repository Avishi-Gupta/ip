package koala;

import java.io.IOException;

/**
 * The main class for the Koala task management application.
 */
public class Koala {
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;

    /**
     * Constructs a Koala application.
     */
    public Koala() {
        try {
            storage = new Storage("../data/koala.txt");
            taskList = new TaskList(storage.loadTasks());
            parser = new Parser(storage, taskList);

            assert storage != null : "Storage should be initialized";
            assert taskList != null : "TaskList should be initialized";
            assert parser != null : "Parser should be initialized";
        } catch (IOException | InvalidTaskException e) {
            throw new RuntimeException("Failed to start Koala");
        }
    }

    // GUI calls this
    public String getResponse(String input) {
        assert parser != null : "Parser should exist when getting response";
        assert input != null : "Input to getResponse should not be null";
        return parser.parseCommand(input); 
    }
}
