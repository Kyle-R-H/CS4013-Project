package application;

import java.io.IOException;
import javafx.fxml.FXML;

public class AdminController {

    @FXML
    private void switchToPrimary() throws IOException {
        application.setRoot("Main");
    }
}