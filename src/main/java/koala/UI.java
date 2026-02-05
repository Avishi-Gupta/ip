package koala;

/**
 * User interface for the Koala application.
 */
public class UI {

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    public static final String WELCOME_MESSAGE = """
                                                 Hello! I'm Koala
                                                 What can I do for you?
                                                 """;

    public static final String GOODBYE_MESSAGE = """
                                                 Bye. Hope to see you again soon!
                                                 """;

    public static final String DIVIDER = "____________________________________________________________";

    private void divider() {
        System.out.println(DIVIDER);
    }

    public void showGoodbyeMessage() {
        divider();
        System.out.println(RED + GOODBYE_MESSAGE + RESET);
        divider();
    }

    public void showWelcomeMessage() {
        divider();
        System.out.println(GREEN + WELCOME_MESSAGE + RESET);
        divider();
    }

    public void showMessage(String message) {
        divider();
        System.out.println(GREEN + message + RESET);
        divider();
    }

    public void showUserCommand(String command) {
        divider();
        System.out.println(BLUE + "> " + command + RESET);
    }

    public void showError(String message) {
        divider();
        System.out.println(RED + message + RESET);
        divider();
    }
}
