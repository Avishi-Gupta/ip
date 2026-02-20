package koala;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Koala koala;

    private Image userImage;
    private Image koalaImage;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.jpg"));
        koalaImage = new Image(this.getClass().getResourceAsStream("/images/DaKoala.jpg"));
    }

    /** Injects the Koala instance */
    public void setKoala(Koala koala) {
        this.koala = koala;

        dialogContainer.getChildren().add(
            DialogBox.getKoalaDialog(
                "Hello! I'm Koala 🐨\nWhat can I do for you today?",
                koalaImage
            )
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Koala's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        
        try {
            response = koala.getResponse(input);
            if (response.equals("Bye. Hope to see you again soon!")) {
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> Platform.exit());
                delay.play();
            }
        } catch (Exception e) {
            response = e.getMessage();
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKoalaDialog(response, koalaImage)
        );
        userInput.clear();
    }
}

