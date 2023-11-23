// Admin Main Page


package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class QuinaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private HBox quinaryHBox;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private ComboBox<String> studentComboBox;

    @FXML
    private ComboBox<String> yearComboBox;

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

    @FXML
    private Button saveButton;

    private ObservableList<String> uniqueCourses;
    private ObservableList<String> uniqueStudents;
    private ObservableList<String> uniqueYears;
    private ObservableList<String> uniqueSemesters;

    @FXML
    private void initialize() {
        // Initialize observable lists for unique courses, students, years, and semesters
        uniqueCourses = FXCollections.observableArrayList();
        uniqueStudents = FXCollections.observableArrayList();
        uniqueYears = FXCollections.observableArrayList();
        uniqueSemesters = FXCollections.observableArrayList();

        // Load data from Grades.csv
        loadDataFromGradesCSV();

        // Populate ComboBoxes with unique values
        courseComboBox.setItems(uniqueCourses);
        studentComboBox.setItems(uniqueStudents);
        yearComboBox.setItems(uniqueYears);
        semesterComboBox.setItems(uniqueSemesters);
    }

    private void loadDataFromGradesCSV() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Grades.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    uniqueCourses.add(parts[0].trim());
                    uniqueStudents.add(parts[1].trim());
                    uniqueYears.add(parts[2].trim());
                    uniqueSemesters.add(parts[3].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveChanges() {
        // Implement the logic to save changes to the CSV file
        // Use the values from the ComboBoxes and TextFields to update the corresponding CSV fields
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Grades.csv")))) {
            StringBuilder newData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8
                        && parts[0].trim().equals(courseComboBox.getValue())
                        && parts[1].trim().equals(studentComboBox.getValue())
                        && parts[2].trim().equals(yearComboBox.getValue())
                        && parts[3].trim().equals(semesterComboBox.getValue())) {
                    // Update the zeros based on TextField values
                    parts[4] = zero1TextField.getText().trim();
                    parts[5] = zero2TextField.getText().trim();
                    parts[6] = zero3TextField.getText().trim();
                    parts[7] = zero4TextField.getText().trim();
                    // Add the modified line to the new data
                    newData.append(String.join(",", parts)).append("\n");
                } else {
                    // Add unchanged line to the new data
                    newData.append(line).append("\n");
                }
            }

            // Write the modified data back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Grades.csv"))) {
                writer.write(newData.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}