package koala;

import java.util.ArrayList;

import koala.task.Task;

/**
 * Represents a list of tasks.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;

        assert tasks != null : "Tasks list should not be null";
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTaskByIndex(int index) {
        return tasks.get(index);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public int getSize() {
        return tasks.size();
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(task);
            }
        }
        return matches;
    }
}
