package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * AccountLogin
 */
public class AccountLogin {
    // ~~ "Import" csv into arraylists ~~
    final String STUDENT_FILE = "C:\\Users\\russe\\Desktop\\4013 Projects Temp\\Student.csv";
    final String ADMIN_FILE = "C:\\Users\\russe\\Desktop\\4013 Projects Temp\\Administrator.csv";

    private String userLine;
    private ArrayList<String[]> allStudents = new ArrayList<>(); // ArrayList of all student logins
    private ArrayList<String[]> allAdmins = new ArrayList<>(); // ArrayList of all Admin logins
    
    public AccountLogin() throws IOException {
        try (BufferedReader studentInput = new BufferedReader(new FileReader(STUDENT_FILE));
        BufferedReader adminInput = new BufferedReader(new FileReader(ADMIN_FILE))) {
            // Student Array
            while ((userLine = studentInput.readLine()) != null) {
                final String loginLine[] = userLine.split(",");
                allStudents.add(loginLine);
            }
            
            // Admin Array
            while ((userLine = adminInput.readLine()) != null) {
                final String loginLine[] = userLine.split(",");
                allAdmins.add(loginLine);
            }
        }
    }
    
    // ~~ Allow Access? ~~
    boolean getAccess = false;               //!implement javafx
    public boolean checkStudentLogin(String id, String password) {
        for (String[] login : allStudents) {
            if (login[0].equals(id)) {
                if (login[1].equals(password)) {
                    return true; // Proceed
                } else {
                    System.out.println("Passwrong");
                    return false; // Password is incorrect
                }
            }
        }
        System.out.println("U no existy");
        return false; // ID not found
    }

    public boolean checkAdminLogin(String username, String password) {
        for (String[] login : allAdmins) {
            if (login[0].equals(username)) {
                if (login[1].equals(password)) {
                    return true; // Proceed
                } else {
                    System.out.println("Passwrong");
                    return false; // Password is incorrect
                }
            }
        }
        System.out.println("U no existy");
        return false; // Username not found
    }
}

