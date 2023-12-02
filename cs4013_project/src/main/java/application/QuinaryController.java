// Admin Main Page


package application;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private ComboBox<String> semesterComboBox;

    @FXML
    private ComboBox<String> moduleComboBox;


    @FXML
    private TextField grade;

    @FXML
    private TextArea uneditableTextArea;



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
        loadDataFromCoursesCSV();

        // Populate ComboBoxes with unique values
        courseComboBox.setItems(uniqueCourses);
        studentComboBox.setItems(uniqueStudents);
        semesterComboBox.setItems(uniqueSemesters);

        // Use ChangeListener for semesterComboBox
        semesterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                updateModulesForSemester();
            }
        });
    }

    @FXML
    private void updateStudentsForCourse() {
        // Clear existing items in studentComboBox and semesterComboBox
        uniqueStudents.clear();
        uniqueSemesters.clear();

        // Get the selected course from the ComboBox
        String selectedCourse = courseComboBox.getValue();

        if (selectedCourse != null && !selectedCourse.isEmpty()) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get("src\\main\\resources\\application\\Courses.csv"))) {
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

    private void loadDataFromCoursesCSV() {
        Set<String> coursesSet = new HashSet<>();
    
        try {
            Path filePath = Paths.get("src\\main\\resources\\application\\Courses.csv");
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
    private void updateModulesForSemester() {
        System.out.println("Updating modules for semester...");

        // Get the selected course and semester from the ComboBoxes
        String selectedCourse = courseComboBox.getValue();
        String selectedSemester = semesterComboBox.getValue();

        // Save the current selection
        String currentSelection = moduleComboBox.getValue();

        // Clear existing items in moduleComboBox
        moduleComboBox.getItems().clear();

        if (selectedCourse != null && !selectedCourse.isEmpty() && selectedSemester != null && !selectedSemester.isEmpty()) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get("src\\main\\resources\\application\\Modules.csv"))) {
                String line;
                boolean isInSelectedCourse = false;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length >= 1 && parts[0].equals(selectedCourse)) {
                        // Found the selected course
                        isInSelectedCourse = true;
                    } else if (isInSelectedCourse && parts.length > 1 && parts[0].matches("\\d+")) {
                        // Found a line with module information for the selected course
                        int semesterNumber = Integer.parseInt(parts[0]);
                        if (semesterNumber == Integer.parseInt(selectedSemester)) {
                            // Matched the selected semester
                            // Check if there are modules for this semester
                            if (parts.length > 1) {
                                List<String> moduleList = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));

                                // Add the items to the ComboBox without clearing it
                                moduleComboBox.getItems().addAll(moduleList);

                                // Set the modules text in uneditableTextArea
                                StringBuilder modulesText = new StringBuilder();
                                for (String module : moduleList) {
                                    modulesText.append(module).append("\n");
                                }
                                uneditableTextArea.setText(modulesText.toString());

                                if (!moduleList.isEmpty()) {
                                    // Ensure the selected index is within bounds
                                    int selectedIndex = moduleComboBox.getItems().indexOf(currentSelection);
                                    if (selectedIndex >= moduleList.size() || selectedIndex < 0) {
                                        moduleComboBox.getSelectionModel().select(0);
                                    } else {
                                        moduleComboBox.getSelectionModel().select(currentSelection);
                                    }
                                } else {
                                    System.out.println("No modules found for the selected semester.");

                                    // Clear the selection and add "Select Module" option
                                    moduleComboBox.getSelectionModel().clearSelection();
                                    moduleComboBox.getItems().add("Select Module");
                                    moduleComboBox.getSelectionModel().select("Select Module");
                                }
                            } else {
                                System.out.println("No modules found for the selected semester.");

                                // Clear the selection and add "Select Module" option
                                moduleComboBox.getSelectionModel().clearSelection();
                                moduleComboBox.getItems().add("Select Module");
                                moduleComboBox.getSelectionModel().select("Select Module");
                            }

                            break; // Break out of the loop after adding modules for the selected semester
                        }
                    } else if (isInSelectedCourse && parts.length >= 1 && parts[0].isEmpty()) {
                        // Reached the end of module information for the selected course
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid selection: course or semester is null or empty.");
        }

        //System.out.println("Update completed.");
    }

    

    @FXML
    private void saveChanges() {
        // Implement the logic to save changes to the CSV file
        // Use the values from the ComboBoxes and TextFields to update the corresponding CSV fields
        // ... (your existing saveChanges() logic)
    }

}