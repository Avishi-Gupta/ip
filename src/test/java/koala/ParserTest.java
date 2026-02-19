package koala;

import koala.InvalidTaskException;
import koala.Storage;
import koala.TaskList;
import koala.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

class ParserTest {

    private Parser parser;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage("test_data.txt"); // dummy test file
        taskList = new TaskList(new ArrayList<>());
        parser = new Parser(storage, taskList);
    }

    @Test
    void parseCommand_bye_returnsGoodbyeMessage() {
        String response = parser.parseCommand("bye");
        assertEquals("Bye. Hope to see you again soon!", response);
    }

    @Test
    void parseCommand_addTodo_success() {
        String response = parser.parseCommand("todo read book");

        assertTrue(response.contains("I've added this task"));
        assertEquals(1, taskList.getSize());
    }

    @Test
    void parseCommand_addTodo_emptyDescription_returnsError() {
        String response = parser.parseCommand("todo ");

        assertEquals("The description of a todo cannot be empty.", response);
        assertEquals(0, taskList.getSize());
    }

    @Test
    void parseCommand_markTask_success() {
        try{
            taskList.addTask(new Todo("study"));
        } catch (InvalidTaskException e) {
            throw new RuntimeException("Failed to add task in test setup");
        }

        String response = parser.parseCommand("mark 1");

        assertTrue(response.contains("marked this task as done"));
    }

    @Test
    void parseCommand_mark_invalidIndex_returnsError() {
        String response = parser.parseCommand("mark 5");

        assertEquals("Invalid task number.", response);
    }

    @Test
    void parseCommand_delete_success() {
        try{
            taskList.addTask(new Todo("study"));
        } catch (InvalidTaskException e) {
            throw new RuntimeException("Failed to add task in test setup");
        }

        String response = parser.parseCommand("delete 1");

        assertTrue(response.contains("removed this task"));
        assertEquals(0, taskList.getSize());
    }

    @Test
    void parseCommand_find_success() {
        try{
            taskList.addTask(new Todo("read book"));
            taskList.addTask(new Todo("write code"));
        } catch (InvalidTaskException e) {
            throw new RuntimeException("Failed to add task in test setup");
        }

        String response = parser.parseCommand("find read");

        assertTrue(response.contains("read book"));
    }

    @Test
    void parseCommand_schedule_validDate_noTasks() {
        String response = parser.parseCommand("schedule 2026-02-18");

        assertTrue(response.contains("No tasks scheduled"));
    }

    @Test
    void parseCommand_schedule_invalidDate_returnsError() {
        String response = parser.parseCommand("schedule notadate");

        assertTrue(response.contains("Invalid date format"));
    }

    @Test
    void parseCommand_unknownCommand_returnsError() {
        String response = parser.parseCommand("unknowncommand");

        assertEquals("I'm sorry, but I don't know what that means.", response);
    }
}