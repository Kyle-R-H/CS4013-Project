// Home Page


package application;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    /**
     * Switches view to secondary view.
     * @throws IOException if I/O error occurs while switching to homepage.
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    /**
     * Switches view to tertiary view.
     * @throws IOException if I/O error occurs while switching to homepage.
     */
    @FXML
    private void switchToTertiary() throws IOException {
        App.setRoot("tertiary");
    }


    // remove //
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