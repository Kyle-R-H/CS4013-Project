package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    private TextField studentNumberField;

    @FXML
    private PasswordField passwordField;

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

    private boolean validateCredentials(String studentNumber, String password) {
        // Reads data from CSV and validate credentials
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Student.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(studentNumber) && parts[1].equals(password)) {
                    return true; // Credentials are valid
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }

        return false; // Credentials are not valid


        
    }
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    private void switchtoQuarternary() {
        try {
            App.setRoot("quaternary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}