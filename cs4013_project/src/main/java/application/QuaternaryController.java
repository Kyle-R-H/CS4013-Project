// Student Main page: Transcript

package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

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
    

    @FXML
    private void initialize(){
        //get info from csv's

        transcriptTextArea.setText("WHY");
    }

    @FXML
    private String csvInfo(){
        try (BufferedReader studentReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("src\\main\\resources\\application\\Student.csv")));
        BufferedReader courseReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("src\\main\\resources\\application\\Courses.csv")))) {
            String studentLine = studentReader.readLine();
            String courseLine = courseReader.readLine();
            while (studentLine != null && courseLine != null) {
                String[] studentParts = studentLine.split(",");
                String[] courseParts = courseLine.split(",");
                if (studentParts.length == 2 && studentParts[0].equals(SecondaryController.studentNumber) && studentParts[0].equals(courseParts[1])) {
                    String theString = "Student Parts: " + Arrays.toString(studentParts) + "\n" +
                    "Course Parts: " + Arrays.toString(courseParts);
                    return theString;
                    //!insert the whole transcript into this one line
                }
            }

        
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }
        return "Big oopsie";
    }
}