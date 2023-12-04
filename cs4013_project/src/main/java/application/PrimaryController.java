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
}