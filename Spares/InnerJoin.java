import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class InnerJoin {
    final String STUDENT_FILE = "C:\\Users\\russe\\Desktop\\4013 Projects Temp\\Student.csv";
    final String GRADES_FILE = "C:\\Users\\russe\\Desktop\\4013 Projects Temp\\Grades.csv";
    
    private void getStudentInfo(){
        // Reads data from CSV
        try (BufferedReader studentReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(STUDENT_FILE)));
            BufferedReader gradeReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(GRADES_FILE)))) {
            String studentLine;
            String gradesLine;
            while ((studentLine = studentReader.readLine()) != null && (gradesLine = gradeReader.readLine()) != null) {
                String[] studentParts = studentLine.split(",");
                String[] gradeParts = studentLine.split(",");
                if ((studentParts.length == 2 && gradeParts.length == 8) && studentParts[0].equals(gradeParts[1])) { //! Check is 8 is the right number ~
                    //! Figure out what to do when QCA is implemented ~
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }
    }

}
