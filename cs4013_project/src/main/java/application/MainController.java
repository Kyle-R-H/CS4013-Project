package application;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void switchToSecondary() throws IOException {
        application.setRoot("Admin");
    }
}
