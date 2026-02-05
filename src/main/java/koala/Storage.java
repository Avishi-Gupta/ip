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


public class Storage {
    private final Path filePath;

    public Storage(String path) {
        this.filePath = Paths.get(path);
    }


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
        case "T" -> task = new Todo(desc);
        case "D" -> task = new Deadline(desc, parts[3]);
        case "E" -> task = new Event(desc, parts[3], parts[4]);
        default -> throw new IllegalArgumentException("Unknown task type");
        }

        if (isDone) {
            task.markAsComplete();
        }
        return task;
    }
}