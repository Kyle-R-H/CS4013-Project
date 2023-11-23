// Student Main page: Transcript

package application;

import java.io.IOException;
import javafx.fxml.FXML;

public class QuaternaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}