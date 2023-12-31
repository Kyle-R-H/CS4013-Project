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
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * QuinaryController
 */
public class QuinaryController {

    /**
     * Switches view to the primary view.
     * @throws IOException if an I/O error occurs while switching to the primary view.
     */
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
    int selectedModuleIndex = -1;
    private static double totalPoints = 0.0; // It is used.....



    /**
     *Initializes ArrayLists, loads data form CSV, populates ComboBox's and add a listener to semesterComboBox. 
     */
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

    /**
     * When a course is selected this method then populates the student and semester ComboBox from the Courses.csv
     */
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

    /**
     * Called in initialization to get data from Course.csv
     */
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

    /**
     * Called later to assist in populating the large uneditable textArea
     * @param selectedCourse The course code.
     * @param selectedSemester The current semester.
     * @return A string of...
     */
    private String findStudentValues(String selectedCourse, String selectedSemester) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src\\main\\resources\\application\\Courses.csv"))) {
            String line;
            boolean isInSelectedCourse = false;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
    
                if (isInSelectedCourse && parts.length > 1 && parts[0].matches("\\d+")) {
                    // Found a line with student information for the selected course
                    int semesterNumber = Integer.parseInt(parts[0]);
                    if (semesterNumber == Integer.parseInt(selectedSemester)) {
                        // Matched the selected semester
                        StringBuilder studentValues = new StringBuilder("");
    
                        // Append values for each semester
                        for (int i = 1; i < parts.length; i++) {
                            // Check if it's the last field (Module 9) and replace with "GPA = "
                            if (i == parts.length - 1) {
                                studentValues.append("GPA = ").append(parts[i].trim()).append("\n");
                            } else {
                                // Check if the field is not empty before appending
                                if (!parts[i].trim().isEmpty()) {
                                    studentValues.append("Module ").append(i).append(": ").append(parts[i].trim()).append("\n");
                                }
                            }
                        }
    
                        return studentValues.toString();
                    }
                } else if (parts.length >= 1 && parts[0].equals(selectedCourse)) {
                    // Found the selected course
                    isInSelectedCourse = true;
                } else if (isInSelectedCourse && parts.length >= 1 && parts[0].isEmpty()) {
                    // Reached the end of student information for the selected course
                    isInSelectedCourse = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Return an empty string if no matching information is found
        return "";
    }
    
    

    /**
     * Takes Value of Semester and populates the module ComboBox with Modules from Modules.csv also fills in large TextArea
     */
    @FXML
    private void updateModulesForSemester() {
 
        // Get the selected course and semester from the ComboBoxes
        String selectedCourse = courseComboBox.getValue();
        String selectedSemester = semesterComboBox.getValue();
        //String selectedModuleValue = moduleComboBox.getValue();


        // Clear existing items in moduleComboBox and uneditableTextArea
        moduleComboBox.getItems().clear();
        uneditableTextArea.clear();

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
                                modulesText.append("Studnet Modules for semester :").append(selectedSemester).append("\n");
                                for (String module : moduleList) {
                                    modulesText.append(module).append("\n");
                                }
                                uneditableTextArea.setText(modulesText.toString());

                                // Add a listener to the ComboBox selection property
                                moduleComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                                    if (newValue != null) {
                                        selectedModuleIndex = moduleComboBox.getItems().indexOf(newValue) + 1;
                                        //System.out.println("Selected Module Index: " + selectedModuleIndex);
                                    }
                                });

                                // Find and print the corresponding student values
                                String studentValues = findStudentValues(selectedCourse,selectedSemester);
                                uneditableTextArea.appendText("Respective student Grades for Semester " + selectedSemester + ":\n");
                                uneditableTextArea.appendText(studentValues);

                                
                            } else {
                                System.out.println("No modules found for the selected semester.");

                                // Clear the selection and add "Select Module" option
                                moduleComboBox.getSelectionModel().clearSelection();
                                moduleComboBox.getItems().add("Select Module");
                                moduleComboBox.getSelectionModel().select("Select Module");

                                // Set a message in uneditableTextArea
                                uneditableTextArea.setText("No modules found for the selected semester.");
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
    }
    
    /**
     * Saves the "Grade" textField over the corresponding value in Courses.csv
     */
    @FXML
    private void saveChanges() {
        // Get the selected course, student, module, and grade from the ComboBoxes and TextField
        String selectedCourse = courseComboBox.getValue();
        String selectedStudent = studentComboBox.getValue();
        String selectedModule = moduleComboBox.getValue();
        String enteredGrade = grade.getText();

        if (selectedCourse != null && !selectedCourse.isEmpty()
                && selectedStudent != null && !selectedStudent.isEmpty()
                && selectedModule != null && !selectedModule.isEmpty()
                && enteredGrade != null && !enteredGrade.isEmpty()) {

            // Update the Courses.csv file with the new grade
            updateCoursesCSV(selectedCourse, selectedStudent, selectedModule, enteredGrade);
        } else {
            System.out.println("Incomplete data. Please select course, semester, module, and enter grade.");
        }
    }

    /**
     *Builds the CSV file with the ammedment made by the user in the "Grade textField"
     * @param selectedCourse like the one above
     * @param selectedStudent
     * @param selectedModule
     * @param newGrade
     */
    private void updateCoursesCSV(String selectedCourse, String selectedStudent, String selectedModule, String newGrade) {
        try {
            Path filePath = Paths.get("src\\main\\resources\\application\\Courses.csv");

            String selectedSemester = semesterComboBox.getValue();
            int numb = 0;

            List<String> lines = Files.readAllLines(filePath);
            List<String> updatedLines = new ArrayList<>();
            
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                String[] parts = line.split(",");

                if (parts.length > 1 && parts.length < 10 && parts[0].equals(selectedCourse) && parts[1].equals(selectedStudent)){
                    numb +=1;
                }

                if (parts.length == 10 && parts[0].equals(selectedSemester) && numb == 1 ){
                    // Update the grade for the selected module
                    if (selectedModuleIndex != -1 && selectedModuleIndex < parts.length) {
                        parts[selectedModuleIndex] = newGrade;
                        updatedLines.add(String.join(",", parts));
                    }
                    numb -= 1;
                } 
                updatedLines.add(line);
                
            } 
            // Write the modified list back to the file
            writeLinesToFile(filePath, updatedLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Called in the above method to handle the updating/rewriting of the Courses.csv
     * @param filePath same ol story
     * @param lines
     */
    private void writeLinesToFile(Path filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//-------------------------------------------------------------Calc from here on------------------------------------------------------------------------------------------------

    /**
     * Processes the Course.csv file to be ready for QCA Calculation.
     * @param csvFile
     */
    private static void processCSVFile(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].trim().matches("\\d")) {
                    double totalPoints = calculateTotalPoints(values);
                    // Overwrite the last entry in the row with the totalPoints
                    values[values.length - 1] = String.valueOf(totalPoints);
                }
                // Reconstruct the line with the updated or original values
                String updatedLine = String.join(",", values);
                lines.add(updatedLine);
            }
    
            // Write all lines (including those that didn't meet the condition) back to the CSV file
            Files.write(Paths.get("src\\main\\resources\\application\\Courses.csv"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Calculates the QCA of every row deemed applicable when processed above.
     * @param values
     * @return
     */
    private static double calculateTotalPoints(String[] values) {
        double totalPoints = 0.0;
        int count = 0;
        for (String value : values) {
            // Check if the value matches your criteria
            switch (value.trim().toUpperCase()) {
                case "A1":
                    totalPoints += 4.00;
                    count++;
                    break;
                case "A2":
                    totalPoints += 3.60;
                    count++;
                    break;
                case "B1":
                    totalPoints += 3.20;
                    count++;
                    break;
                case "B2":
                    totalPoints += 3.00;
                    count++;
                    break;
                case "B3":
                    totalPoints += 2.80;
                    count++;
                    break;
                case "C1":
                    totalPoints += 2.60;
                    count++;
                    break;
                case "C2":
                    totalPoints += 2.40;
                    count++;
                    break;
                case "C3":
                    totalPoints += 2.00;
                    count++;
                    break;
                case "D1":
                    totalPoints += 1.60;
                    count++;
                    break;
                case "D2":
                    totalPoints += 1.20;
                    count++;
                    break;
                case "F":
                case "NG":
                    totalPoints += 0.00;
                    count++;
                    break;
            }
        }
        totalPoints = totalPoints/count;
        double roundedTotalPoints = roundToTwoDecimalPlaces(totalPoints);
        return roundedTotalPoints;
    }


    /**
     * Rewrites all QCA's in the Courses.csv file to the newly calculated ones. 
     */
    private static void calculateAndOverwriteAverage() {
        try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\application\\Courses.csv"))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].trim().matches("\\d")) {
                    // Overwrite the last entry in the row with the average
                    values[values.length - 1] = String.valueOf(calculateTotalPoints(values));
                }
                // Reconstruct the line with the updated values
                String updatedLine = String.join(",", values);
                lines.add(updatedLine);
            }
    
            // Write the updated lines back to the CSV file
            Files.write(Paths.get("src\\main\\resources\\application\\Courses.csv"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Rounds the QCA for better asthetic in displaying it.
     * @param value
     * @return
     */
    private static double roundToTwoDecimalPlaces(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue = decimalFormat.format(value);
        return Double.parseDouble(formattedValue);
    }

    /**
     * This method is linked to the "Calculate QCA" button it calls the methods required to update the QCA
     */
    @FXML
    private void updateQCA(){
        processCSVFile("src\\main\\resources\\application\\Courses.csv");
        calculateAndOverwriteAverage();
    }

}