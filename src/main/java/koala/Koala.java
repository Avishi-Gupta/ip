package koala;

import java.io.IOException;

/**
 * The main class for the Koala task management application.
 */
public class Koala {
    private final UI ui;
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;

    /**
     * Constructs a Koala application.
     */
    public Koala() {
        try {
            ui = new UI();
            storage = new Storage("../data/koala.txt");
            taskList = new TaskList(storage.loadTasks());
            parser = new Parser(ui, storage, taskList);
        } catch (IOException | InvalidTaskException e) {
            throw new RuntimeException("Failed to start Koala");
        }
    }

    // GUI calls this
    public String getResponse(String input) {
        return parser.parseCommand(input); 
    }
}
