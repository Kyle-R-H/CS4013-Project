package application;

import java.io.IOException;
import javafx.fxml.FXML;

public class QuinaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}