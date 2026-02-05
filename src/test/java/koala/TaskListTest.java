package koala;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import koala.task.Task;
import koala.task.Todo;

public class TaskListTest {

    @Test
    public void addAndGetTask_returnsSameTask() throws InvalidTaskException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList list = new TaskList(tasks);
        Todo t = new Todo("sleep");
        list.addTask(t);

        assertEquals(t, list.getTaskByIndex(0));
    }

    @Test
    public void deleteTask_reducesSize() throws InvalidTaskException {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList list = new TaskList(tasks);
        list.addTask(new Todo("sleep"));
        list.addTask(new Todo("eat"));

        list.deleteTask(0);

        assertEquals(1, list.getSize());
        assertEquals("eat", list.getTaskByIndex(0).getDescription());
    }
}
