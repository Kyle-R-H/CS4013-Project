// Admin Login Page


package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * TertiaryController
 */
public class TertiaryController {

    @FXML
    private TextField adminEmailField;

    @FXML
    private PasswordField adminPasswordField;

    /**
     * Handles admin login validation.
     */
    @FXML
    private void handleLoginButtonAction() {
        String enteredEmail = adminEmailField.getText();
        String enteredPassword = adminPasswordField.getText();

        // Validate admin credentials
        if (validateAdminCredentials(enteredEmail, enteredPassword)) {
            System.out.println("Admin Login Successful!");
            // Add code to switch to admin view or perform other actions upon successful admin login
            switchtoQuinary();
        } else {
            System.out.println("Admin Login failed. Invalid credentials.");
            // Add code to display an error message or perform other actions upon failed admin login
        }
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
     * Switches view to the quintary view.
     * @throws IOException if an I/O error occurs while switching to the quintary view.
     */
    @FXML
    private void switchtoQuinary() {
        try {
            App.setRoot("quinary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ensures that the email matches the password while logging into the system.
     * @param email The admin user's login email.
     * @param password The admin user's login password.
     * @return true if credentials are valid, otherwise false.
     */
    private boolean validateAdminCredentials(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Administrator.csv")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(email) && parts[1].equals(password)) {
                    return true; // Credentials are valid
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }

        return false; // Credentials are not valid
    }
}