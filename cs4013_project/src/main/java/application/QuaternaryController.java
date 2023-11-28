// Student Main page: Transcript

package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class QuaternaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private TextArea transcriptTextArea;
    
    @FXML
    private void initialize(){
        transcriptTextArea.setStyle(
                "-fx-control-inner-background: transparent;" +
                "-fx-font-weight: normal;" +
                "-fx-text-fill: black; " +
                "-fx-background-color: transparent; " +
                "-fx-focus-color: transparent; " +
                "-fx-faint-focus-color: transparent; " +
                "-fx-border-color: transparent;" +
                "-fx-font-family: Consolas;" +
                "-fx-font-size: 12;"
        );
        transcriptTextArea.getParent().requestFocus(); // Set focus to another node to apply the styles immediately
        transcriptTextArea.setText(transcriptWithCSVInfo());
    }

    @FXML
    private String transcriptWithCSVInfo(){
        final String STUDENT_FILE = "src\\main\\resources\\application\\Student.csv";
        final String COURSE_FILE = "src\\main\\resources\\application\\Courses.csv";
        final String MODULES_FILE = "src\\main\\resources\\application\\Modules.csv";

        try(BufferedReader studentReader = new BufferedReader(new FileReader(STUDENT_FILE));
            BufferedReader courseReader = new BufferedReader(new FileReader(COURSE_FILE));
            BufferedReader moduleReader = new BufferedReader(new FileReader(MODULES_FILE));) {

            String studentLine = studentReader.readLine();
            String courseLine = courseReader.readLine();
            String moduleLine = moduleReader.readLine();

            while (studentLine != null && courseLine != null && moduleLine != null) { //while both files arent empty
                String[] studentParts = studentLine.split(","); 
                String[] courseParts = courseLine.split(",");
                String[] moduleParts = moduleLine.split(",");
                if (studentParts[0].equals(SecondaryController.studentNumber) && studentParts[0].equals(courseParts[1]) && SecondaryController.studentNumber != null) {
                    //get info
                    int year = 0;
                    Double semesterQCA = -1.0; //! Get from Calculator into array
                    if (year == 1) {
                        semesterQCA = -1.0;
                    } else if(year == 2){
                        semesterQCA = -2.0;
                    } else if (year == 3){
                        semesterQCA = -3.0;
                    } else if(year == 4){
                        semesterQCA = -4.0;
                    } else if (year == 5){
                        semesterQCA = -5.0;
                    } else {
                        semesterQCA = -10.0;
                    }
                    
                    double totalQca = -1.0; //Average of all QCAs
                    //!Get QCA and divide it by number of semesters except the first one


                    String semesterYear;
                    if (year == 1) {
                        semesterYear = moduleParts[2];
                    } else if(year == 2){
                        semesterYear = moduleParts[35];
                    } else if (year == 3){
                        semesterYear = moduleParts[68];
                    } else if(year == 4){
                        semesterYear = moduleParts[101];
                    } else if (year == 5){
                        semesterYear = moduleParts[134];
                    } else {
                        semesterYear ="0000/00";
                    }
                    String forename = studentParts[3];
                    String surname = studentParts[4];
                    String courseName = moduleParts[0];
                    String courseCode = moduleParts[1];
                    String courseRoute = studentParts[6];
                    int totalSemesters = Integer.parseInt(studentParts[7]);



                    if (totalSemesters == 1 || totalSemesters == 2) {
                        year = 1;
                    } else if (totalSemesters == 3 || totalSemesters == 4){
                        year = 2;
                    } else if (totalSemesters == 5 || totalSemesters == 6){
                        year = 3;
                    } else if (totalSemesters == 7 || totalSemesters == 8){
                        year = 4;
                    } else if (totalSemesters == 9 || totalSemesters == 10){
                        year = 5;
                    }
                    ArrayList<String> moduleIds = new ArrayList<>();

                    ArrayList<String> moduleNames = new ArrayList<>();
                    ArrayList<Character> registrationTypes = new ArrayList<>();
                    ArrayList<String> gradeLetters = new ArrayList<>();
                    ArrayList<Integer> credits = new ArrayList<>();
                    
                    
                    String theString = "Student Parts: " + Arrays.toString(studentParts) + "\n" +
                                        "Course Parts: " + Arrays.toString(courseParts);
                    String transcript = transcriptHeader(SecondaryController.studentNumber, "N/A", forename, surname, courseName, courseCode, courseRoute) + "\n" +
                                        
                                        getFormattedTranscript(semesterQCA, totalQca, semesterYear, year, totalSemesters, moduleIds, moduleNames, registrationTypes, gradeLetters, credits);
                    
                    
                    
                    return theString + "\n" + transcript;
                } else{
                    return "Something not equalling in CSV or courseNumber is NULL";
                }
            }
            return "while loop looped";
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
            return "Caught something while fishing";
        }
    }

    /**
     * transcriptHeader
     * @param studentID
     * @param prefix
     * @param forename
     * @param surname
     * @param course
     * @param courseCode
     * @param courseRoute
     * @return a the header of the transcript
     */
    public String transcriptHeader(String studentID, String prefix, String forename, String surname, String courseName, String courseCode, String courseRoute){
        return  theLine()+ "\n" +
                String.format("|%48s %s %48s|", "","University of Limerick", "") +"\r\n" +
                String.format("|%119s %s|", "", "")+ "\r\n" +
                String.format("|%s %-40s %s %-30s %-"+ Math.max(8, String.valueOf(studentID).length()) +"s %-9s|", LocalDate.now(), "", "Student Transcript", "", studentID, "")   + "\r\n" +
                String.format("|%119s %s|", "", "")+ "\r\n" +
                theLine() +"\r\n" +
                String.format("|%-12s %-"+ Math.max(6, String.valueOf(prefix).length()) +"s %-"+ Math.max(20, String.valueOf(forename).length()) +"s %-" + Math.max(20, String.valueOf(surname).length()) + "s %58s|" ,"Name", prefix, forename, surname, "") + "\r\n" +
                String.format("|%-12s %-" + Math.max(107, String.valueOf(courseName).length()) +"s|", "Course", courseName)+ "\r\n" +
                String.format("|%-12s %-" + Math.max(107, String.valueOf(courseCode).length()) +"s|", "Course Code", courseCode)+ "\r\n" +
                String.format("|%-12s %-"+ Math.max(107, String.valueOf(courseRoute).length()) +"s|","Route", courseRoute);
    }

    /**
     * 
     * @param semesterQCA
     * @param totalQCA
     * @param semesterYear
     * @param year
     * @param totalSemesters
     * @param moduleIds
     * @param moduleNames
     * @param registrationTypes
     * @param gradeLetters
     * @param credits
     * @return
     */
    public String getFormattedTranscript(double semesterQCA, double totalQCA, String semesterYear, int year, int totalSemesters, ArrayList<String> moduleIds, ArrayList<String> moduleNames, ArrayList<Character> registrationTypes, ArrayList<String> gradeLetters, ArrayList<Integer> credits) {
        StringBuilder transcriptBuilder = new StringBuilder();
        transcriptBuilder.append(theLine()).append("\r\n")
                        .append(semesterDetails(semesterYear, year, totalSemesters)).append("\r\n")
                        .append(blankLines()).append("\r\n")
                        .append(semesterHeader_n_QCA(semesterQCA, totalQCA)).append("\r\n")
                        .append(blankLines());
        for (int i = 0; i < moduleIds.size(); i++) {
            if (moduleIds.size() <= 0) {
                
            }else{
                transcriptBuilder.append(moduleInfo(moduleIds.get(i), moduleNames.get(i), registrationTypes.get(i), gradeLetters.get(i), credits.get(i))).append("\r\n");
            }
        }
        transcriptBuilder.append(blankLines()).append("\r\n").append(theLine());
        return transcriptBuilder.toString();
    }
    
    /**
     * 
     * @return
     */
    private String theLine(){
        return "+--------------------------------------------------------------------------------------------+---------------------------+";
    }

    /**
     * 
     * @param semesterYear
     * @param year
     * @param totalSemesters
     * @return
     */
    private static String semesterDetails(String semesterYear, int year, int totalSemesters) {
        String yearString = "Year " + year;
        String totalSemestersString = "Sem " + totalSemesters;
        String formatString = "|%-30s %-" + Math.max(18, String.valueOf(year).length()) + "s %-" + Math.max(6, String.valueOf(totalSemesters).length()) + "s %-34s |%-5s %s %-5s|";
        return String.format(formatString, semesterYear, yearString ,totalSemestersString,"", "", "Session To-Date", "");
    }
    
    /**
     * 
     * @param moduleId
     * @param moduleName
     * @param registrationType
     * @param gradeLetter
     * @param credits
     * @return
     */
    private static String moduleInfo(String moduleId, String moduleName, char registrationType, String gradeLetter, int credits){
        String formaString = "|%s%-"+ Math.max(13, String.valueOf(moduleId).length()) +"s%-"+ Math.max(50, String.valueOf(moduleName).length()) +"s %s %-8s %-"+ Math.max(9, String.valueOf(gradeLetter).length()) +"s %-"+ Math.max(6, String.valueOf(credits).length()) +"s |%27s|";
        return String.format(formaString,  "", moduleId, moduleName, registrationType, "", gradeLetter, credits, "");
    }
    
    /**
     * 
     * @return
     */
    private static String blankLines() {
        return String.format("|%-91s | %-25s |", "","");
    }

    /**
     * 
     * @param semesterQCA
     * @param totalQCA
     * @return
     */
    public static String semesterHeader_n_QCA(double semesterQCA, double totalQCA) {
        String formatString = "|%-12s %-50s %-9s %-8s %-9s|%-5s%10.2f%9.2f   |";
        return String.format(formatString, "Module", "Title", "Regn", "Grade", "Credits", "QCA", semesterQCA, totalQCA);
    }

}

/*  
    ~ TODO ~
 *  Make course data change for each semester - for loop getFormattedTranscript
 *  Get data from csvs for transcript
 *  Edit/Add onto a csv for transcript data
 */