package koala;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import koala.task.Deadline;
import koala.task.Event;
import koala.task.Task;
import koala.task.Todo;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {

    private final Path filePath;

    public Storage(String path) {
        this.filePath = Paths.get(path);
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from the file.
     * @throws IOException If an error occurs while reading the file.
     * @throws InvalidTaskException If a task in the file is invalid.
     */
    public ArrayList<Task> loadTasks() throws IOException, InvalidTaskException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
            return tasks; // empty list
        }

        try (Scanner sc = new Scanner(filePath)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task task = parseTask(line);
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves the list of tasks to the storage file.
     *
     * @param tasks The list of tasks to save.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveTasks(List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Task task : tasks) {
            lines.add(task.toStoreString());
        }

        Files.write(filePath, lines);
    }

    private Task parseTask(String line) throws InvalidTaskException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc = parts[2];

        Task task;
        switch (type) {
            case "T" ->
                task = new Todo(desc);
            case "D" ->
                task = new Deadline(desc, parts[3]);
            case "E" ->
                task = new Event(desc, parts[3], parts[4]);
            default ->
                throw new IllegalArgumentException("Unknown task type");
        }

        if (isDone) {
            task.markAsComplete();
        }
        return task;
    }
}
