// Home Page


package application;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void switchToTertiary() throws IOException {
        App.setRoot("tertiary");
    }

    // Remove when doen for testing ---------------------------------------------------------------
    @FXML
    private void switchToQuarternary() throws IOException {
        App.setRoot("quarternary");
    }

    @FXML
    private void switchToQuinary() throws IOException {
        App.setRoot("quinary");
    }
    // --------------------------------------------------------------------------------------------
}