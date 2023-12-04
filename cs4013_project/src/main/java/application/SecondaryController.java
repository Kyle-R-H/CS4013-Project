// Student Login Page


package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * SecondaryController
 */
public class SecondaryController {

    @FXML
    private TextField studentNumberField;

    @FXML
    private PasswordField passwordField;

    /**
     * Handles student login validation.
     */
    @FXML
    private void handleLoginButtonAction() {
        String enteredStudentNumber = studentNumberField.getText();
        String enteredPassword = passwordField.getText();

        // Reads data from CSV and validate credentials
        if (validateCredentials(enteredStudentNumber, enteredPassword)) {
            System.out.println("Login successful!");
            // Add code to switch to the primary view or perform other actions upon successful login
            switchtoQuarternary();
        } else {
            System.out.println("Login failed. Invalid credentials.");
            // Add code to display an error message or perform other actions upon failed login
        }
    }

    /**
     * studentNumber for QuarternaryController for validation.
     */
    public static String studentNumber;
    /**
     * Ensures that the student number correlates to its respective password when logging in.
     * @param studentNumber The user's student number used to log into the system.
     * @param password The student's password.
     * @return true if credentials are valid, otherwise false.
     */
    private boolean validateCredentials(String studentNumber, String password) {
        // Reads data from CSV and validate credentials
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Student.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(studentNumber) && parts[1].equals(password)) {
                    SecondaryController.studentNumber = studentNumber;
                    return true; // Credentials are valid
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }

        return false; // Credentials are not valid
        
    }

    /**
     * Switches view to the primary view.
     * @throws IOException if an I/O error occurs while switching to the primary view.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    /**
     * Switches view to the quarternary view.
     * @throws IOException if an I/O error occurs while switching to the quarternary view.
     */
    @FXML
    private void switchtoQuarternary() {
        try {
            App.setRoot("quarternary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}