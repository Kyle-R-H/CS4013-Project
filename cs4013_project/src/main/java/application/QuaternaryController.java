// Student Main page: Transcript

package application;

import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.InputStreamReader;

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
    private String transcriptWithCSVInfo() {
        // final String STUDENT_FILE = "Student.csv";
        // final String COURSE_FILE = "Courses.csv";
        // final String MODULES_FILE = "Modules.csv";

        try(BufferedReader studentReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Student.csv")));
            BufferedReader courseReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Courses.csv")));
            BufferedReader moduleReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Modules.csv")))) {

            String studentLine;
            while ((studentLine = studentReader.readLine()) != null) { //first while looping through studentLine
                String[] studentParts = studentLine.split(",");
                for (String row : studentParts) {
                    System.out.println("studentLine: "+ row);
                    if (studentLine.contains(SecondaryController.studentNumber)) {
                        studentLine = row;     
                        break;                   
                    }
                }
                if (studentLine.contains(SecondaryController.studentNumber)) {
                    String courseLine;
                    System.out.println("Student Line: " + studentLine);

                    while ((courseLine = courseReader.readLine()) != null) { //second while loop looping through courseLine
                        String[] courseParts = courseLine.split(",");
                        for (String row : courseParts) {
                            System.out.println("courseLine: " +row);
                            if (courseLine.contains(SecondaryController.studentNumber)) {
                                courseLine = row;     
                                break;                   
                            }
                        }
                        if (courseLine.contains(SecondaryController.studentNumber)) {
                            String moduleLine;
                            System.out.println("Course Line: " + courseLine);

                            while ((moduleLine = moduleReader.readLine()) != null) {
                                System.out.println("Student Line: " + studentLine);
                                System.out.println("Course Line: " + courseLine);
                                System.out.println("Module Line: " + moduleLine);

                                // Split lines into parts
                                String[] moduleParts = moduleLine.split(",");

                                System.out.println("Student Parts: " + Arrays.toString(studentParts));
                                System.out.println("Course Parts: " + Arrays.toString(courseParts));
                                System.out.println("Module Parts: " + Arrays.toString(moduleParts));

                                // Extract student IDs
                                String studentId = null;
                                if (studentParts.length >= 1 && studentParts[0].trim().equals(SecondaryController.studentNumber.trim())) {
                                    studentId = studentParts[0].trim();
                                }
                                String courseStudentId = null;
                                if (courseParts.length >= 2 && courseParts[1].trim().equals(SecondaryController.studentNumber.trim())) {
                                    courseStudentId = courseParts[1].trim();
                                }

                                // Check conditions for matching records
                                if (studentId != null && courseStudentId != null && studentId.equals(SecondaryController.studentNumber) && studentId.equals(courseStudentId) && SecondaryController.studentNumber != null) {
                                    // Extract information
                                    int year = 0;
                                    String courseName = moduleParts[0];
                                    String courseCode = moduleParts[1];
                                    String courseRoute = "n/a"; // ^get from module[2]

                                    String semesterYear;
                                    if (year == 1) {
                                        semesterYear = moduleParts[2];
                                    } else if (year == 2) {
                                        semesterYear = moduleParts[35];
                                    } else if (year == 3) {
                                        semesterYear = moduleParts[68];
                                    } else if (year == 4) {
                                        semesterYear = moduleParts[101];
                                    } else if (year == 5) {
                                        semesterYear = moduleParts[134];
                                    } else {
                                        semesterYear = "0000/00";
                                    }

                                    int totalSemesters = Integer.parseInt(studentParts[7]);

                                    // Determine the academic year
                                    if (totalSemesters == 1 || totalSemesters == 2) {
                                        year = 1;
                                    } else if (totalSemesters == 3 || totalSemesters == 4) {
                                        year = 2;
                                    } else if (totalSemesters == 5 || totalSemesters == 6) {
                                        year = 3;
                                    } else if (totalSemesters == 7 || totalSemesters == 8) {
                                        year = 4;
                                    } else if (totalSemesters == 9 || totalSemesters == 10) {
                                        year = 5;
                                    }

                                    // Initialize arrays for module information
                                    ArrayList<String> moduleIds = new ArrayList<>();
                                    ArrayList<String> moduleNames = new ArrayList<>();
                                    ArrayList<Character> registrationTypes = new ArrayList<>();
                                    ArrayList<String> gradeLetters = new ArrayList<>();
                                    ArrayList<Integer> credits = new ArrayList<>();

                                    // Build the transcript string
                                    StringBuilder fullTranscript = new StringBuilder();
                                    String theString = "Student Parts: " + Arrays.toString(studentParts) + "\n" +
                                            "Module Parts: " + Arrays.toString(moduleParts) + "\n" +
                                            "Course Parts: " + Arrays.toString(courseParts);
                                    fullTranscript.append(theString);
                                    fullTranscript.append(transcriptHeader(SecondaryController.studentNumber, studentParts[2], studentParts[3], studentParts[4], courseName, courseCode, courseRoute));

                                    for (int semesterCount = 1; semesterCount <= totalSemesters; semesterCount++) {
                                        // Check if the current line corresponds to the LM121 entry
                                        if (moduleParts.length > 0 && moduleParts[0].trim().equals(studentParts[5])) {
                                            // Assuming the second field (index 1) contains the value for the year 2023/24
                                            semesterYear = studentParts.length > 1 ? studentParts[1].trim() : "Code Wrong";
                                        }

                                        // Get QCA and divide it by the number of semesters except the first one
                                        Double semesterQCA = -1.0;
                                        if (year == 1) {
                                            semesterQCA = -1.0;
                                        } else if (year == 2) {
                                            semesterQCA = -2.0;
                                        } else if (year == 3) {
                                            semesterQCA = -3.0;
                                        } else if (year == 4) {
                                            semesterQCA = -4.0;
                                        } else if (year == 5) {
                                            semesterQCA = -5.0;
                                        } else {
                                            semesterQCA = -10.0;
                                        }

                                        // Get QCA average
                                        double totalQca = -1.0; // Average of all QCAs

                                        // Append the formatted transcript for the current semester
                                        fullTranscript.append(getFormattedTranscript(semesterQCA, totalQca, semesterYear, year, totalSemesters, moduleIds, moduleNames, registrationTypes, gradeLetters, credits));
                                    }

                                    // Convert StringBuilder to String
                                    return fullTranscript.toString();
                                } else {
                                    System.out.println("Conditions not satisfied");
                                    System.out.println("studentId: " + studentId);
                                    System.out.println("SecondaryController.studentNumber: " + SecondaryController.studentNumber);
                                    System.out.println("courseStudentId: " + courseStudentId);
                                    return "Something not equalling in CSVs";
                                }
                            }

                            // Debugging output
                            System.out.println("Student Line: " + studentLine);
                            System.out.println("Course Line: " + courseLine);
                            System.out.println("Module Line: " + moduleLine);
                            return "3rd while loop looped";
                        }

                        // Debugging output
                        System.out.println("Student Line: " + studentLine);
                        System.out.println("Course Line: " + courseLine);
                        return "2ND While loop";
                    }

                    // Debugging output
                    System.out.println("Student Line: " + studentLine);
                    return "1st while loop";
                }
            }
            //Debugging output
            return "Doesnt enter While loop: " + studentLine;

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
