import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * AccountLogin
 */
public class AccountLogin {
    final String STUDENT_FILE;
    private int csvRow = 0;
    private String userLine;
    private String[][] allUsers = new String[0][]; //initial 2D Array before getting info

    
    public AccountLogin() throws FileNotFoundException, IOException {
    FileInputStream studentFileInput = new FileInputStream(STUDENT_FILE);
    DataInputStream studentInput = new DataInputStream(studentFileInput);

    
    }


}

//String = "column0, column1" == "Username,Password"

/*
 * [ ]Read-In CSV into 2D Array
 *    - 
 *    - 
 * [ ]Compare col1 to col2 for scanner input
 * [ ]JavaFX page to read in username and password and is they match
 *    then proceed to next page else add text saying "wrong password try again"
    ^ For both Student and Admin
*/