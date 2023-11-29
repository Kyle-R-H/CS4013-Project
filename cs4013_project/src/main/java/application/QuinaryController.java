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

        if (selectedCourse != null && !selectedCourse.isEmpty()) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/application/Courses.csv"))) {
                String line;                
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length >= 2 && parts[0].trim().equals(selectedCourse)) {
                        // Course code matches the selected course
                        String studentID = parts[1].trim();
                        String maxSemesters = "1"; // Default to 1 if not specified

                        // Check if the extracted student ID is an 8-digit number
                        if (studentID.matches("\\d+")) {
                            // Add the extracted student ID to the list (if not already added)
                            if (!uniqueStudents.contains(studentID)) {
                                uniqueStudents.add(studentID);
                            }

                            // Check if there's a number after the student ID for max semesters
                            if (parts.length >= 3 && parts[2].matches("\\d+")) {
                                maxSemesters = parts[2].trim();
                            }

                            // Populate the semesters ComboBox (if not already populated)
                            if (uniqueSemesters.isEmpty()) {
                                for (int i = 1; i <= Integer.parseInt(maxSemesters); i++) {
                                    uniqueSemesters.add(String.valueOf(i));
                                }
                            }
                        }
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
    
                if (parts.length >= 1 && parts[0].length() == 5) {
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