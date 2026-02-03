package koala;

import java.util.ArrayList;
import koala.task.Task;

public class TaskList {
    private ArrayList<Task> tasks;
    private final Store storage;

    public TaskList(String filePath) {
        tasks = new ArrayList<>();
        storage = new Store(filePath);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }   

    public void saveTasks() throws Exception {
        storage.save(tasks);
    }

    public void loadTasks() throws Exception {
        tasks = storage.load();
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }
}