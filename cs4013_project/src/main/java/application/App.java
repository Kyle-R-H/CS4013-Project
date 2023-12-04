// Launch file


package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Creates javafx stage and scenes.
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("QCA - Calculator");
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows views to be switched.
     * @param fxml The variable for the desired fxml file.
     * @throws IOException if an I/O error occurs while switching to the desired view.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads specified fxml file.
     * @param fxml The variable for the desired fxml file.
     * @return the fxml file to load.
     * @throws IOException if I/O error occurs while switching to homepage.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Main method.
     * @param args main method String[].
     */
    public static void main(String[] args) {
        launch();
    }

}