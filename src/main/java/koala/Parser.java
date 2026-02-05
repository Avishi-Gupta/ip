package koala;

import java.io.IOException;
import java.util.Scanner;

import koala.task.Deadline;
import koala.task.Event;
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

    /**
     * Runs the main command loop, processing user input until "bye" is entered.
     */
    public void run() throws InvalidTaskException, IOException {
        ui.showWelcomeMessage();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    String input = scanner.nextLine().trim();
                    ui.showUserCommand(input);
                    
                    if (input.equals("bye")) {
                        ui.showGoodbyeMessage();
                        break;
                    }

                    handleCommand(input);
                    storage.saveTasks(taskList.getTasks());
                } catch (InvalidTaskException e) {
                    ui.showError(e.getMessage());
                }
            }
        } catch (IOException e) {
            ui.showError("An error occurred while accessing the storage file.");
        }
    }

    private void handleCommand(String input) throws InvalidTaskException, IOException {
                    if (input.equals("list")) {
                        showList();
                        return;
                        }

                    if (input.startsWith("mark")) {
                        mark(input, true);
                        return;
                    }


                    if (input.startsWith("unmark")) {
                        mark(input, false);
                        return;
                    }

                    if (input.startsWith("delete")) {
                        delete(input);
                        return;
                    }

                    if (input.startsWith("deadline")) {
                        String[] parts = input.split(" /by ");
                        if (parts.length == 2 && !parts[0].substring(8).isEmpty() && !parts[1].isEmpty()) {
                            taskList.addTask(new Deadline(parts[0].substring(9), parts[1]));
                            ui.showMessage("Got it. I've added this task: " + taskList.getTasks().get(taskList.getTasks().size() - 1));
                            return;
                        } else {
                            throw new InvalidTaskException("Invalid deadline format.");
                        }
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
                        ui.showMessage("Got it. I've added this task: " + taskList.getTasks().get(taskList.getTasks().size() - 1));
                        return;
                    }

                    if (input.startsWith("todo")) {
                        taskList.addTask(new Todo(input.substring(5)));
                        ui.showMessage("Got it. I've added this task: " + taskList.getTasks().get(taskList.getTasks().size() - 1));
                        return;
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
}