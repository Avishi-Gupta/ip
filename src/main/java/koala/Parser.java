package koala;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import koala.task.Deadline;
import koala.task.Event;
import koala.task.Task;
import koala.task.Todo;

/**
 * Parses and executes user commands for the Koala task management application.
 */
public class Parser {
    private final UI ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Constructs a Parser.
     *
     * @param ui The user interface for the application.
     * @param storage The storage for saving and loading tasks.
     * @param taskList The list of tasks to manage.
     */
    public Parser(UI ui, Storage storage, TaskList taskList) {
        this.ui = ui;
        this.storage = storage;
        this.taskList = taskList;
    }

    /*
     * Parses a user command and returns the appropriate response.
     *
     * @param input The user's input command.
     * @return The response to be displayed to the user.
     */
    public String parseCommand(String input) {
        try {
            if (input.equals("bye")) {
                return "Bye. Hope to see you again soon!";
            }

            String response = handleCommand(input);
            storage.saveTasks(taskList.getTasks());
            return response;

        } catch (InvalidTaskException e) {
            return e.getMessage();
        } catch (IOException e) {
            return "An error occurred while saving tasks.";
        }
    }

    /*
        * Handles a user command and returns the appropriate response.
        * @param input The user's input command.
        * @return The response to be displayed to the user.
        * @throws InvalidTaskException If the command is invalid.
     */
    private String handleCommand(String input) throws InvalidTaskException {
        if (input.equals("list")) {
            return listTasks();
        }

        if (input.startsWith("mark")) {
            return markTask(input, true);
        }

        if (input.startsWith("unmark")) {
            return markTask(input, false);
        }

        if (input.startsWith("delete")) {
            return deleteTask(input);
        }

        if (input.startsWith("todo")) {
            return addTodo(input);
        }

        if (input.startsWith("deadline")) {
            return addDeadline(input);
        }

        if (input.startsWith("event")) {
            return addEvent(input);
        }

        throw new InvalidTaskException("I'm sorry, but I don't know what that means.");
    }

    /**
     * Extracts task index safely.
     */
    private int extractIndex(String input) throws InvalidTaskException {
        String[] parts = input.split(" ");

        if (parts.length < 2) {
            throw new InvalidTaskException("Please provide a task number.");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;

            if (index < 0 || index >= taskList.getSize()) {
                throw new InvalidTaskException("Invalid task number.");
            }

            return index;
        } catch (NumberFormatException e) {
            throw new InvalidTaskException("Task number must be a valid integer.");
        }
    }

    private String addTodo(String input) throws InvalidTaskException {
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new InvalidTaskException("The description of a todo cannot be empty.");
        }

        taskList.addTask(new Todo(description));
        return "Got it. I've added this task:\n  "
                + taskList.getTaskByIndex(taskList.getSize() - 1);
    }

    private String addDeadline(String input) throws InvalidTaskException {
        String[] parts = input.split(" /by ");
        if (parts.length != 2) {
            throw new InvalidTaskException("Invalid deadline format. Use: deadline <desc> /by <time>");
        }

        String description = parts[0].substring(9).trim();
        String by = parts[1].trim();

        taskList.addTask(new Deadline(description, by));
        return "Got it. I've added this task:\n  "
                + taskList.getTaskByIndex(taskList.getSize() - 1);
    }

    private String addEvent(String input) throws InvalidTaskException {
        String[] parts = input.split(" /from ");
        if (parts.length != 2) {
            throw new InvalidTaskException("Invalid event format.");
        }

        String[] times = parts[1].split(" /to ");
        if (times.length != 2) {
            throw new InvalidTaskException("Invalid event format.");
        }

        String description = parts[0].substring(6).trim();
        String from = times[0].trim();
        String to = times[1].trim();

        taskList.addTask(new Event(description, from, to));
        return "Got it. I've added this task:\n  "
                + taskList.getTaskByIndex(taskList.getSize() - 1);
    }
    
    private String listTasks() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.getSize(); i++) {
            sb.append(i + 1)
              .append(". ")
              .append(taskList.getTaskByIndex(i))
              .append("\n");
        }
        return sb.toString();
    }

    private String markTask(String input, boolean mark) throws InvalidTaskException {
        int index = extractIndex(input);

        Task task = taskList.getTaskByIndex(index);

        if (mark) {
            task.markAsComplete();
            return "Nice! I've marked this task as done:\n  " + task;
        } else {
            task.markAsIncomplete();
            return "Ok! I've marked this task as not done:\n  " + task;
        }
    }

    private String deleteTask(String input) throws InvalidTaskException {
        int index = extractIndex(input);

        Task removed = taskList.getTaskByIndex(index);
        taskList.deleteTask(index);

        return "Noted. I've removed this task:\n  " + removed;
    }
    
    
}
