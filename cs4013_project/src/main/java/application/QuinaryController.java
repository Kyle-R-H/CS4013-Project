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
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        // Initialize observable lists for unique courses, students, and semesters
        uniqueCourses = FXCollections.observableArrayList();
        uniqueStudents = FXCollections.observableArrayList();
        uniqueSemesters = FXCollections.observableArrayList();

        // Load data from Courses.csv
        loadDataFromGradesCSV();

        // Populate ComboBoxes with unique values
        courseComboBox.setItems(uniqueCourses);
        studentComboBox.setItems(uniqueStudents);
        semesterComboBox.setItems(uniqueSemesters);

        // Add listener to courseComboBox to update studentComboBox based on selected course
        courseComboBox.valueProperty().addListener((observable, oldValue, newValue) -> updateStudentsForCourse(newValue));
    }

    private void updateStudentsForCourse(String selectedCourse) {
        // Clear existing items in studentComboBox
        uniqueStudents.clear();

        // Load students based on the selected course
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Courses.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].trim().equals(selectedCourse)) {
                    uniqueStudents.add(parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update semesterComboBox based on the selected course
        updateSemestersForCourse(selectedCourse);
    }

    private void updateSemestersForCourse(String selectedCourse) {
        // Clear existing items in semesterComboBox
        uniqueSemesters.clear();

        // Load semesters based on the maximum number of semesters specified in the CSV file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Courses.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].trim().equals(selectedCourse)) {
                    int maxSemesters = Integer.parseInt(parts[2].trim());
                    // Add semesters from 1 to the maximum
                    uniqueSemesters.addAll(IntStream.rangeClosed(1, maxSemesters).boxed().map(Object::toString).collect(Collectors.toList()));
                    break; // Stop processing once we find the matching course
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromGradesCSV() {
        Set<String> coursesSet = new HashSet<>();
        Set<String> studentsSet = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Courses.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    coursesSet.add(parts[0].trim());
                    studentsSet.add(parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        uniqueCourses.addAll(coursesSet);
        uniqueStudents.addAll(studentsSet);
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
