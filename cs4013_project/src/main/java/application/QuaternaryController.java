// Student Main page: Transcript

package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
// import java.util.ArrayList;
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
        transcriptTextArea.setStyle(
                "-fx-control-inner-background: transparent;" +
                "-fx-font-weight: normal;" +
                "-fx-text-fill: black; " +
                "-fx-background-color: transparent; " +
                "-fx-focus-color: transparent; " +
                "-fx-faint-focus-color: transparent; " +
                "-fx-border-color: transparent;"
        );
        transcriptTextArea.getParent().requestFocus(); // Set focus to another node to apply the styles immediately
        //get info from csv's

        transcriptTextArea.setText(csvInfo());
    }

    @FXML
    private String csvInfo(){
        final String STUDENT_FILE = "\\application\\Student.csv";
        final String COURSE_FILE = "\\application\\Courses.csv";

        try(BufferedReader studentReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(STUDENT_FILE)));
            BufferedReader  courseReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(COURSE_FILE)))) {

            String studentLine = studentReader.readLine();
            String courseLine = courseReader.readLine();

            while (studentLine != null && courseLine != null) { //while both files arent empty
                String[] studentParts = studentLine.split(","); 
                String[] courseParts = courseLine.split(",");
                if (studentParts[0].equals(SecondaryController.studentNumber) && studentParts[0].equals(courseParts[1]) && SecondaryController.studentNumber != null) {
                    String theString = "Student Parts: " + Arrays.toString(studentParts) + "\n" +
                    "Course Parts: " + Arrays.toString(courseParts);
                    return theString;
                    //!insert the whole transcript info into this one line
                } else{
                    return "Something not equalling in CSV or courseNumber is NULL";
                }
            }
            return "while loop looped";
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
            return "Caught something while fishing";
        }
    }
}