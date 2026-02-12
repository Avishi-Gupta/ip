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

            String response = handleCommandAndReturn(input);
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
    private String handleCommandAndReturn(String input) throws InvalidTaskException {
        if (input.equals("list")) {
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < taskList.getSize(); i++) {
                sb.append(i + 1).append(". ")
                .append(taskList.getTaskByIndex(i)).append("\n");
            }
            return sb.toString();
        }

        if (input.startsWith("mark")) {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            taskList.getTaskByIndex(index).markAsComplete();
            return "Nice! I've marked this task as done:\n  "
                    + taskList.getTaskByIndex(index);
        }

        if (input.startsWith("unmark")) {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            taskList.getTaskByIndex(index).markAsIncomplete();
            return "Ok! I've marked this task as not done:\n  "
                    + taskList.getTaskByIndex(index);
        }

        if (input.startsWith("delete")) {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            Task removed = taskList.getTaskByIndex(index);
            taskList.deleteTask(index);
            return "Noted. I've removed this task:\n  " + removed;
        }

        if (input.startsWith("todo")) {
            taskList.addTask(new Todo(input.substring(5)));
            return "Got it. I've added this task:\n  "
                    + taskList.getTaskByIndex(taskList.getSize() - 1);
        }

        if (input.startsWith("deadline")) {
            String[] parts = input.split(" /by ");
            if (parts.length != 2) {
                throw new InvalidTaskException("Invalid deadline format.");
            }
            taskList.addTask(new Deadline(parts[0].substring(9), parts[1]));
            return "Got it. I've added this task:\n  "
                    + taskList.getTaskByIndex(taskList.getSize() - 1);
        }

        if (input.startsWith("event")) {
            String[] parts = input.split(" /from ");
            if (parts.length != 2) {
                throw new InvalidTaskException("Invalid event format.");
            }
            String[] times = parts[1].split(" /to ");
            if (times.length != 2) {
                throw new InvalidTaskException("Invalid event format.");
            }
            taskList.addTask(new Event(parts[0].substring(6), times[0], times[1]));
            return "Got it. I've added this task:\n  "
                    + taskList.getTaskByIndex(taskList.getSize() - 1);
        }

        throw new InvalidTaskException("I'm sorry, but I don't know what that means.");
    }

    private void showList() {
        ui.showMessage("Here are the tasks in your list:");
        for (int i = 0; i < taskList.getSize(); i++) {
            System.out.println((i + 1) + ". " + taskList.getTaskByIndex(i));
        }
    }

    private void mark(String input, boolean done) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        if (index < 0 || index >= taskList.getSize() || String.valueOf(index).isEmpty()) {
            ui.showError("Invalid task number.");
            return;
        }
        if (done) {
            taskList.getTaskByIndex(index).markAsComplete();
            ui.showMessage("Nice! I've marked this task as done:\n  " + taskList.getTaskByIndex(index));
        } else {
            taskList.getTaskByIndex(index).markAsIncomplete();
            ui.showMessage("Ok! I've marked this task as not done:\n  " + taskList.getTaskByIndex(index));
        }
    }

    private void delete(String input) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        if (index < 0 || index >= taskList.getSize() || String.valueOf(index).isEmpty()) {
            ui.showError("Invalid task number.");
            return;
        }
        ui.showMessage("Noted. I've removed this task:\n  " + taskList.getTaskByIndex(index));
        taskList.deleteTask(index);
    }

    private void find(String input) throws InvalidTaskException {
        if (input.length() <= 5) {
            throw new InvalidTaskException("Please provide a keyword to search for.");
        }

        String keyword = input.substring(5).trim();
        ArrayList<Task> matches = taskList.findTasks(keyword);

        ui.showMessage("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i));
        }
    }
}
