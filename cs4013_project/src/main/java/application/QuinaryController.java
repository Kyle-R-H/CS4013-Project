// Admin Main Page


package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuinaryController {

    @FXML
    private HBox quinaryHBox;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private ComboBox<String> studentComboBox;

    @FXML
    private ComboBox<String> semesterComboBox;

    @FXML
    private TextField zero1TextField;

    @FXML
    private TextField zero2TextField;

    @FXML
    private TextField zero3TextField;

    @FXML
    private TextField zero4TextField;

    @FXML
    private TextField zero5TextField;

    private ObservableList<String> uniqueCourses;
    private ObservableList<String> uniqueStudents;
    private ObservableList<String> uniqueSemesters;


    @FXML
    private void initialize() {
        // Initialize observable lists
        uniqueCourses = FXCollections.observableArrayList();
        uniqueStudents = FXCollections.observableArrayList();
        uniqueSemesters = FXCollections.observableArrayList();

        // Load data from Courses.csv
        loadDataFromGradesCSV();

        // Populate ComboBoxes with unique values
        courseComboBox.setItems(uniqueCourses);
        studentComboBox.setItems(uniqueStudents);
        semesterComboBox.setItems(uniqueSemesters);
    }

    @FXML
    private void updateStudentsForCourse() {
        // Clear existing items in studentComboBox and semesterComboBox
        uniqueStudents.clear();
        uniqueSemesters.clear();

        // Get the selected course from the ComboBox
        String selectedCourse = courseComboBox.getValue();

        System.out.println("Selected Course: " + selectedCourse); // Remove this line ----------------------------------------------------------------


        if (selectedCourse != null && !selectedCourse.isEmpty()) {
            // Load students based on the selected course
            try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/application/Courses.csv"))) {
                String line;
                boolean courseFound = false;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String courseCode = parts[0].trim();
                        String studentID = parts[1].trim();
                        
                        // Check if the course code matches the selected course
                        if (courseCode.equals(selectedCourse)) {
                            System.out.println("Student ID: " + studentID); // Add this line

                            // Check if the extracted student ID is an 8-digit number
                            if (studentID.matches("\\d+")) {
                                // Add the extracted student ID to the list
                                uniqueStudents.add(studentID);
                            }
                        }
                    }

                    if (courseFound && !line.isEmpty()) {
                        // Use a regular expression to match the course code and capture the student ID
                        String regex = "^" + selectedCourse + "(\\S+)";
                        Matcher matcher = Pattern.compile(regex).matcher(line);
                        while (matcher.find()) {
                            String studentID = matcher.group(1).trim();

                            System.out.println("Student ID: " + studentID); // Add this line

                            // Check if the extracted student ID is an 8-digit number
                            if (studentID.matches("\\d+")) {
                                // Add the extracted student ID to the list
                                uniqueStudents.add(studentID);
                            }
                        }

                    }

                    if (line.equals(selectedCourse)) {
                        courseFound = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void loadDataFromGradesCSV() {
        Set<String> coursesSet = new HashSet<>();
    
        try {
            Path filePath = Paths.get("src/main/resources/application/Courses.csv");
            List<String> lines = Files.readAllLines(filePath);
    
            for (String line : lines) {
                line = line.trim();
                String[] parts = line.split(",");
    
                if (parts.length == 2 && parts[0].length() == 5 && parts[1].matches("\\d+")) {
                    // Add the course code to the set
                    coursesSet.add(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        uniqueCourses.addAll(coursesSet);
    }

    @FXML
    private void saveChanges() {
        // Implement the logic to save changes to the CSV file
        // Use the values from the ComboBoxes and TextFields to update the corresponding CSV fields
        // ... (your existing saveChanges() logic)
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}