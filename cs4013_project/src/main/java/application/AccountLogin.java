package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * AccountLogin
 */
public class AccountLogin {
    final String STUDENT_FILE = "cs4013_project\\src\\main\\resources\\application\\Student.csv";
    final String ADMIN_FILE = "cs4013_project\\src\\main\\resources\\application\\Administrator.csv";
    private String userLine;
    private String[][] allStudents = new String[0][]; // Array of all student logins
    private String[][] allAdmins = new String[0][]; // Array of all Admin logins

    public AccountLogin() throws IOException {
        try (BufferedReader studentInput = new BufferedReader(new FileReader(STUDENT_FILE));
                BufferedReader adminInput = new BufferedReader(new FileReader(ADMIN_FILE))) {
            // Student Array
            int csvRow = 0;
            while ((userLine = studentInput.readLine()) != null) {
                ++csvRow;

                final String[][] studentLoginsTemp = new String[csvRow][2]; //Size of Array

                final String loginLine[] = userLine.split(";"); // get logins as "ID,Password"
                studentLoginsTemp[csvRow - 1] = loginLine; // adds login to array

                System.arraycopy(allStudents, 0, studentLoginsTemp, 0, csvRow - 1); // copy previously read values to temp array
                this.allStudents = studentLoginsTemp; // set original array as temp array
            }

            // Admin Array
            while ((userLine = adminInput.readLine()) != null) {
                ++csvRow;

                final String[][] adminLoginsTemp = new String[csvRow][2];

                final String loginLine[] = userLine.split(";");
                adminLoginsTemp[csvRow - 1] = loginLine;

                System.arraycopy(allAdmins, 0, adminLoginsTemp, 0, csvRow - 1);
                this.allAdmins = adminLoginsTemp; 
            }
        }
    }


}


/*
 * [ ]Compare col1 to col2 for scanner input
 * [ ]JavaFX page to read in username and password and is they match
 * then proceed to next page else add text saying "wrong password try again"
 * ^ For both Student and Admin
 */

//Read csv file into 2D Array ref.: "https://stackoverflow.com/questions/33034833/converting-csv-file-into-2d-array"