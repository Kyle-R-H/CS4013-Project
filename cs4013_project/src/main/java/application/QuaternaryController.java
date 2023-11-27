// Student Main page: Transcript

package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class QuaternaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private TextArea transcriptTextArea;
    

    // private void QuaternaryController(){
    //     transcriptTextArea.setStyle(-fx);
    // }
}