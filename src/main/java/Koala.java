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

                tasks[msgCount] = new Task(input);
                msgCount++;
                System.out.println("added: " + input);
            }
        }
    }
}
