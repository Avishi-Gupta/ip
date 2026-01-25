import java.util.Scanner;

public class Koala {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] msg = new String[100];
            int msgCount = 0;

            System.out.println("Hello! I'm Koala");
            System.out.println("What can I do for you?");
            
            while (true) {
                String input = scanner.nextLine();
                
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }

                if (input.equals("list")) {
                    for (int i = 0; i < msgCount; i++) {
                        System.out.println((i + 1) + ". " + msg[i]);
                    }
                    continue;
                }
                
                msg[msgCount] = input;
                msgCount++;
                System.out.println("added: " + input);
            }
        }
    }
}
