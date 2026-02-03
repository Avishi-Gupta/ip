package koala;

public class UI {

    public static final String WELCOME_MESSAGE = """
                                                 Hello! I'm Koala
                                                 What can I do for you?
                                                 """;

    public static final String GOODBYE_MESSAGE = """
                                                 Bye. Hope to see you again soon!
                                                 """;

    public static final String DIVIDER = "____________________________________________________________";

    public static String formatMessage(String message) {
        return DIVIDER + "\n" + message + "\n" + DIVIDER;
    }

    public void showGoodbyeMessage() {
        System.out.println(formatMessage(GOODBYE_MESSAGE));
    }

    public void showWelcomeMessage() {
        System.out.println(formatMessage(WELCOME_MESSAGE));
    }

    public void showMessage(String message) {
        System.out.println(formatMessage(message));
    }
}