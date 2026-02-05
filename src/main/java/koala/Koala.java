package koala;

import java.io.IOException;

/**
 * The main class for the Koala task management application.
 */

public class Koala {
    public static void main(String[] args) throws IOException, InvalidTaskException {
        UI ui = new UI();
        Storage storage = new Storage("../data/koala.txt");

        try {
            TaskList taskList = new TaskList(storage.loadTasks());
            Parser parser = new Parser(ui, storage, taskList);
            parser.run();
        } catch (IOException | InvalidTaskException e) {
            ui.showMessage("Failed to start Koala.");
        }
   }
}
