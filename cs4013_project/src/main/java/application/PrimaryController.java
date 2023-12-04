// Home Page


package application;

import java.io.IOException;
import javafx.fxml.FXML;

/**
 * PrimaryController
 */
public class PrimaryController {

    /**
     * Switches view to secondary view.
     * @throws IOException if an I/O error occurs while switching to the secondary view.
     */
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    /**
     * Switches view to the tertiary view.
     * @throws IOException if an I/O error occurs while switching to the tertiary view.
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