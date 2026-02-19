package koala;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Koala using FXML.
 */
public class Main extends Application {

    private Koala koala = new Koala();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            scene.getStylesheets().addAll(
                getClass().getResource("/css/main.css").toExternalForm(),
                getClass().getResource("/css/dialog-box.css").toExternalForm()
            );

            fxmlLoader.<MainWindow>getController().setKoala(koala);  // inject the Koala instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
