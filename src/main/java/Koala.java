import java.util.Scanner;

public class Koala {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Task[] tasks = new Task[100];
            int msgCount = 0;

            System.out.println("Hello! I'm Koala");
            System.out.println("What can I do for you?");

            while (true) {
                String input = scanner.nextLine().trim();
                
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }

                if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < msgCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                    continue;
                }

                if (input.startsWith("mark ")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index >= 0 && index < msgCount) {
                        tasks[index].markAsComplete();
                        System.out.println("Nice! I've marked this task as done: " + tasks[index]);
                    }
                    continue;
                }

                if (input.startsWith("unmark ")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index >= 0 && index < msgCount) {
                        tasks[index].markAsIncomplete();
                        System.out.println("OK, I've marked this task as not done yet: " + tasks[index]);
                    }
                    continue;
                }

                if (input.startsWith("deadline ")) {
                    if (input.length() <= 9) {
                        System.out.println("Invalid deadline description.");
                        continue;
                    }
                    String[] parts = input.split(" /by ");
                    if (parts.length == 2) {
                        tasks[msgCount] = new Deadline(parts[0].substring(9), parts[1]);
                        msgCount++;
                        System.out.println("Got it. I've added this task: " + tasks[msgCount - 1]);
                    } else {
                        System.out.println("Invalid deadline format.");
                    }
                    continue;
                }

                if (input.startsWith("event ")) {
                    if (input.length() <= 6) {
                        System.out.println("Invalid event description.");
                        continue;
                    }
                    String[] parts = input.split(" /from ");
                    if (parts.length == 2) {
                        String[] times = parts[1].split(" /to ");
                        if (times.length == 2) {
                            tasks[msgCount] = new Event(parts[0].substring(6), times[0], times[1]);
                            msgCount++;
                            System.out.println("Got it. I've added this task: " + tasks[msgCount - 1]);
                        } else {
                            System.out.println("Invalid event format.");
                        }
                    }
                    continue;
                }

                if (input.startsWith("todo ")) {
                    if (input.length() <= 5) {
                        System.out.println("Invalid todo description.");
                        continue;
                    }
                    tasks[msgCount] = new Todo(input.substring(5));
                    msgCount++;
                    System.out.println("Got it. I've added this task: " + tasks[msgCount - 1]);
                    continue;
                }

                System.out.println("I'm sorry, but I don't know what that means.");
            }
        }
    }
}
