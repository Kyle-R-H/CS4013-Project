// Student Main page: Transcript

package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
    

    // @FXML
    // private void initialize(){
    //     //get info from csv's


    //     transcriptTextArea.setText("To be Continued...");
    // }

    // @FXML
    // private void csvInfo(){
    //     final String STUDENT_FILE = "src\\main\\resources\\application\\Student.csv";
    //     final String COURSE_FILE = "src\\main\\resources\\application\\Courses.csv";
    //     try (BufferedReader studentReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(STUDENT_FILE)));
    //     BufferedReader courseReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(COURSE_FILE)))) {
    //         String studentLine = studentReader.readLine();
    //         String courseLine = courseReader.readLine();

        
        
        
        
        
        
        
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         // Handle IO exception
    //     }

    // }
}