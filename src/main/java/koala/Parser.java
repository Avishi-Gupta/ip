package koala;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import koala.task.Deadline;
import koala.task.Event;
import koala.task.Task;
import koala.task.Todo;

public class Parser {
    
    public void run() throws KoalaException, IOException {
         try (Scanner scanner = new Scanner(System.in)) {
            ArrayList<Task> tasks;

            System.out.println("Hello! I'm Koala");
            System.out.println("What can I do for you?");

            Store storage = new Store("../data/koala.txt");

            try {
                tasks = storage.load();
            } catch (IOException e) {
                tasks = new ArrayList<>();
            }   

            while (true) {
                try {
                    String input = scanner.nextLine().trim();
                    
                    if (input.equals("bye")) {
                        System.out.println("Bye. Hope to see you again soon!");
                        break;
                    }

                    if (input.equals("list")) {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        continue;
                    }

                    if (input.startsWith("mark")) {
                        int index = Integer.parseInt(input.substring(5)) - 1;
                        if (index >= 0 && index < tasks.size()) {
                            tasks.get(index).markAsComplete();
                            System.out.println("Nice! I've marked this task as done: " + tasks.get(index));
                        }
                        continue;
                    }

                    if (input.startsWith("unmark")) {
                        int index = Integer.parseInt(input.substring(7)) - 1;
                        if (index >= 0 && index < tasks.size()) {
                            tasks.get(index).markAsIncomplete();
                            System.out.println("OK, I've marked this task as not done yet: " + tasks.get(index));
                        }
                        continue;
                    }

                    if (input.startsWith("delete")) {
                        int index = Integer.parseInt(input.substring(7)) - 1;
                        if (index >= 0 && index < tasks.size()) {
                            System.out.println("Noted. I've removed this task: " + tasks.get(index));
                            tasks.remove(index);
                        }
                        continue;
                            }

                    if (input.startsWith("deadline")) {
                        if (input.length() <= 9) {
                            throw new KoalaException("Invalid deadline description.");
                        }
        
                        String[] parts = input.split(" /by ");
                        if (parts.length == 2 && !parts[0].substring(8).isEmpty() && !parts[1].isEmpty()) {
                            tasks.add(new Deadline(parts[0].substring(9), parts[1]));
                            storage.save(tasks);
                            System.out.println("Got it. I've added this task: " + tasks.get(tasks.size() - 1));
                        } else {
                            throw new KoalaException("Invalid deadline format.");
                        }
                        continue;
                    }

                    if (input.startsWith("event")) {
                        if (input.length() <= 6) {
                            throw new KoalaException("Invalid event description.");
                        }
                        String[] parts = input.split(" /from ");
                        if (parts.length == 2 && !parts[0].substring(5).isEmpty()) {
                            String[] times = parts[1].split(" /to ");
                            if (times.length == 2 && !times[0].isEmpty() && !times[1].isEmpty()) {
                                tasks.add(new Event(parts[0].substring(6), times[0], times[1]));
                                storage.save(tasks);
                                System.out.println("Got it. I've added this task: " + tasks.get(tasks.size() - 1));
                            } else {
                                throw new KoalaException("Invalid event format.");
                            }
                        } else {
                            throw new KoalaException("Invalid event format.");
                        }
                        continue;
                    }

                    if (input.startsWith("todo")) {
                        if (input.length() <= 5) {
                            throw new KoalaException("Invalid todo description.");
                        }
                        tasks.add(new Todo(input.substring(5)));
                        storage.save(tasks);
                        System.out.println("Got it. I've added this task: " + tasks.get(tasks.size() - 1));
                        continue;
                    }
                    throw new KoalaException("I'm sorry, but I don't know what that means.");
                } catch (KoalaException e) {
                    System.out.println(e.getMessage());
               }
            }
         }
    }

}