public class Koala {
    public static void main(String[] args) {
        System.out.println("Hello from Koala!\nWhat can I do for you?\n");
        if (args.length > 0 && args[0].equals("bye")) {
            System.out.println("Hope to see you again soon!\n");
        } else {
            for (String arg : args) {
                System.out.println(arg);
            }
        }
    }
}

