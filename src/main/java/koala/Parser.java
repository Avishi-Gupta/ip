package koala;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import koala.task.Deadline;
import koala.task.Event;
import koala.task.Task;
import koala.task.Todo;

/*
 * Parses and executes user commands for the Koala task management application.
 */
public class Parser {
    private final Storage storage;
    private final TaskList taskList;

    /*
     * Constructs a Parser.
     * @param storage The storage for saving and loading tasks.
     * @param taskList The list of tasks to manage.
     */
    public Parser(Storage storage, TaskList taskList) {
        assert storage != null : "Storage should not be null";
        assert taskList != null : "TaskList should not be null";

        this.storage = storage;
        this.taskList = taskList;
    }

    /**
     * Parses a user command and returns the appropriate response.
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

    /**
     * Handles a user command and returns the appropriate response.
     * @param input The user's input command.
     * @return The response to be displayed to the user.
     * @throws InvalidTaskException If the command is invalid.
     */
    private String handleCommand(String input) throws InvalidTaskException {
        String trimmedInput = input.trim();

        if (trimmedInput.startsWith("list")) {
            return listTasks();
        }

        if (trimmedInput.startsWith("mark")) {
            return markTask(trimmedInput, true);
        }

        if (trimmedInput.startsWith("unmark")) {
            return markTask(trimmedInput, false);
        }

        if (trimmedInput.startsWith("delete")) {
            return deleteTask(trimmedInput);
        }

        if (trimmedInput.startsWith("find")) {
            return findTasks(trimmedInput);
        }

        if (trimmedInput.startsWith("todo")) {
            return addTodoTask(trimmedInput);
        }

        if (trimmedInput.startsWith("deadline")) {
            return addDeadlineTask(trimmedInput);
        }

        if (trimmedInput.startsWith("event")) {
            return addEventTask(trimmedInput);
        }

        if (trimmedInput.startsWith("schedule")) {
            return getSchedule(trimmedInput);
        }

        if (trimmedInput.equals("help")) {
            return "Available commands:\n"
                    + "1. list - List all tasks\n"
                    + "2. todo <description> - Add a todo task\n"
                    + "3. deadline <description> /by <time> - Add a deadline task\n"
                    + "4. event <description> /from <start> /to <end> - Add an event task\n"
                    + "5. mark <task number> - Mark a task as complete\n"
                    + "6. unmark <task number> - Mark a task as incomplete\n"
                    + "7. delete <task number> - Delete a task\n"
                    + "8. find <keyword> - Find tasks containing the keyword\n"
                    + "9. schedule <date> - View tasks scheduled for a specific date\n"
                    + "10. bye - Exit the application";
        }

        throw new InvalidTaskException("I'm sorry, but I don't know what that means.\n Please enter a valid command.\n "
                                        + "Type 'help' for a list of available commands.");
    }

    public boolean isExitCommand(String input) {
        return input.equals("bye");
    }

    /**
     * Extracts task index safely.
     */
    private int extractIndex(String input) throws InvalidTaskException {
        String[] parts = input.split(" ");

        if (parts.length < 2) {
            throw new InvalidTaskException("I am already tired. Don't make my life more difficult!\n"
                                            + "Please provide a task number.");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;

            if (index < 0 || index >= taskList.getSize()) {
                throw new InvalidTaskException("Honey, do you not know how to count? It's an invalid task number.");
            }

            return index;
        } catch (NumberFormatException e) {
            throw new InvalidTaskException("Task number must be a valid integer.");
        }
    }

    /**
     * Adds a todo task.
     * @param input The user's input command.
     * @return The response to be displayed to the user.
     * @throws InvalidTaskException If the todo task inputformat is invalid.
     */
    private String addTodoTask(String input) throws InvalidTaskException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new InvalidTaskException("Are you planning to do invisible tasks?\nThe description of a todo cannot be empty!");
        }

        taskList.addTask(new Todo(description));
        return "Got it. I've added this task:\n  "
                + taskList.getTaskByIndex(taskList.getSize() - 1);
    }

    /**
     * Adds a deadline task.
     * @param input The user's input command.
     * @return The response to be displayed to the user.
     * @throws InvalidTaskException If the deadline task input format is invalid.
     */
    private String addDeadlineTask(String input) throws InvalidTaskException {
        String[] parts = input.split(" /by ");
        if (parts.length != 2) {
            throw new InvalidTaskException("Invalid deadline format\nOkay this one not your fault. I'm lazy and I only work with specific commands.\n"
                                           + "Use: deadline <desc> /by <time>");
        }

        String description = parts[0].substring(8).trim();
        String by = parts[1].trim();

        taskList.addTask(new Deadline(description, by));
        return "Got it. I've added this task:\n  "
                + taskList.getTaskByIndex(taskList.getSize() - 1);
    }

    /**
     * Adds an event task.
     * @param input The user's input command.
     * @return The response to be displayed to the user.
     * @throws InvalidTaskException If the event task input format is invalid.
     */
    private String addEventTask(String input) throws InvalidTaskException {
        String[] parts = input.split(" /from ");
        if (parts.length != 2) {
            throw new InvalidTaskException("Invalid event format\nOkay, not your fault. I'm lazy and I only work with specific commands.\n"
                                           + "Use: event <desc> /from <start> /to <end>");
        }

        String[] times = parts[1].split(" /to ");
        if (times.length != 2) {
            throw new InvalidTaskException("Invalid event format\nOkay, not your fault. I'm lazy and I only work with specific commands.\n"
                                           + "Use: event <desc> /from <start> /to <end>");
        }

        String description = parts[0].substring(5).trim();
        String from = times[0].trim();
        String to = times[1].trim();

        taskList.addTask(new Event(description, from, to));
        return "Got it. I've added this task:\n  "
                + taskList.getTaskByIndex(taskList.getSize() - 1);
    }
    
    /**
     * Lists all tasks in the task list.
     * @return The response to be displayed to the user.
     */
    private String listTasks() {
        if (taskList.getSize() == 0) {
            return "Your task list is empty. Seriously, don't be lazy like me try adding some tasks to your list.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\nNice! You have work to do! Meanwhile, I'm just gonna sleep and eat LOL\n");
        for (int i = 0; i < taskList.getSize(); i++) {
            sb.append(i + 1)
              .append(". ")
              .append(taskList.getTaskByIndex(i))
              .append("\n");
        }
        return sb.toString();
    }

    /**
     * Finds tasks that match a keyword.
     * @param input The user's input command.
     * @return The response to be displayed to the user.
     * @throws InvalidTaskException If the input is empty or only contains whitespace.
     */
    private String findTasks(String input) throws InvalidTaskException {
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new InvalidTaskException("I am already tired. Don't make my life more difficult!\n"
                                           + "Please provide a keyword to search for.");
        }

        ArrayList<Task> matches = taskList.findTasks(keyword);
        if (matches.isEmpty()) {
            return "No matching tasks found.";
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1)
              .append(". ")
              .append(matches.get(i))
              .append("\n");
        }
        return sb.toString();
    }

    /**
     * Marks or unmarks a task as complete.
     * @param input The user's input command.
     * @param mark True to mark as complete, false to mark as incomplete.
     * @return The response to be displayed to the user.
     * @throws InvalidTaskException If the task number is invalid.
     */
    private String markTask(String input, boolean mark) throws InvalidTaskException {
        int index = extractIndex(input);

        Task task = taskList.getTaskByIndex(index);

        if (mark) {
            task.markAsComplete();
            return "Good job on finishing this task! I aspire to be like you but I'd rather sleep.\n" + task;
        } else {
            task.markAsIncomplete();
            return "ahh! what happened? did you think you will do it and then backed out?\nI've marked this task as not done:\n" + task;
        }
    }

    /**
     * Deletes a task from the task list.
     * @param input The user's input command.
     * @return The response to be displayed to the user.
     * @throws InvalidTaskException If the task number is invalid.
     */
    private String deleteTask(String input) throws InvalidTaskException {
        int index = extractIndex(input);

        Task removed = taskList.getTaskByIndex(index);
        taskList.deleteTask(index);

        return "Nice! Less work. More sleep! I've removed this task:\n" + removed;
    }

    /**
     * Gets the schedule of tasks for a specific date.
     * @param input The user's input command.
     * @return The response to be displayed to the user.
     * @throws InvalidTaskException If the date format is invalid or no date is provided.
     */
    private String getSchedule(String input) throws InvalidTaskException {
        
        String[] parts = input.trim().split("\\s+");

        if (parts.length < 2) {
            throw new InvalidTaskException("I am already tired. Don't make my life more difficult!\n"
                                           + "Please provide a date to view the schedule for.");
        }

        LocalDate date;

        try {
            date = parseFlexibleDate(parts[1].trim());
        } catch (Exception e) {
            throw new InvalidTaskException("Invalid date format. Try 2026-02-18 or 18/02/2026. See I give you options but still you choose to be difficult :((");
        }
       
        boolean found = false;
       
        StringBuilder sb = new StringBuilder("Here are the tasks scheduled for " + date + ":\n");
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTaskByIndex(i);
            if (task.isScheduledFor(date)) {
                sb.append(i + 1)
                  .append(". ")
                  .append(task)
                  .append("\n");
                found = true;
            }
        }
        if (!found) {   
            return "No tasks scheduled for " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ".";
        }
        return sb.toString();
    }


    /**
     * Parses a date string into a LocalDate object using multiple formats.
     * @param input The date string to parse.
     * @return The parsed LocalDate object.
     * @throws InvalidTaskException If the date format is invalid.
     */
    private LocalDate parseFlexibleDate(String input) throws InvalidTaskException {

        DateTimeFormatter[] formats = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        };

        for (DateTimeFormatter formatter : formats) {
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new InvalidTaskException("Invalid date format. Try 2026-02-18 or 18/02/2026.");
    }
}
