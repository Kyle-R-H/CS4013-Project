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
    /*
     * DataField
     */

    // ~~ Files ~~ //
    // ^File Names
    private final String STUDENT_FILE = "Student.csv";
    private final String COURSES_FILE = "Courses.csv";
    private final String MODULES_FILE = "Modules.csv";
    // ^Read Files
    private BufferedReader studentReader = new BufferedReader(
            new InputStreamReader(getClass().getResourceAsStream(STUDENT_FILE)));
    private BufferedReader courseReader = new BufferedReader(
            new InputStreamReader(getClass().getResourceAsStream(COURSES_FILE)));
    private BufferedReader moduleReader = new BufferedReader(
            new InputStreamReader(getClass().getResourceAsStream(MODULES_FILE)));

    // ^Reference Student Id from SecondaryController.java
    private final String LOGIN_STUDENT_ID = SecondaryController.studentNumber;

    // ^Get access to data
    private String studentCSVID;
    private String courseCSVID;
    private String studentLine;
    private String courseLine;
    private String moduleLine;
    private String[] studentParts;
    private String[] courseParts;
    private String[] moduleParts;

    // ~~ The Transcript ~~ //
    // ^Transcript Header
    private String studentID;
    private String prefix;
    private String forename;
    private String surname;
    private String courseName;
    private String courseCode;
    private String courseRoute;

    // ^Formatted Transcript
    // ^semesterHeader_n_QCA()
    private double semesterQCA;
    private double totalQCA;
    // ^semesterDetails()
    private String semesterYear;
    private int year;
    private int totalSemesters;
    // ^moduleInfo()
    private ArrayList<String> moduleIds;
    private ArrayList<String> moduleNames;
    private ArrayList<Character> registrationTypes;
    private ArrayList<String> gradeLetters;
    private ArrayList<Integer> credits;
    // ^Printing Transcript
    StringBuilder fullTranscript = new StringBuilder();

    /*
     * Methods
     */
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Constructor
     */
    
    public QuaternaryController(){
        checkStudentCSVId();
        checkCoursesCSVId();
        // totalSemesters = Integer.parseInt(courseParts[3]);//TODO loop through courses to check which part[3]!!!!! 
    }
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private TextArea transcriptTextArea;

    @FXML
    private void initialize() { // javafx css
        transcriptTextArea.setStyle(
                "-fx-control-inner-background: transparent;" +
                        "-fx-font-weight: normal;" +
                        "-fx-text-fill: black; " +
                        "-fx-background-color: transparent; " +
                        "-fx-focus-color: transparent; " +
                        "-fx-faint-focus-color: transparent; " +
                        "-fx-border-color: transparent;" +
                        "-fx-font-family: Consolas;" +
                        "-fx-font-size: 12;");
        transcriptTextArea.getParent().requestFocus(); // Set focus to another node to apply the styles to TextArea
                                                       // immediately
        transcriptTextArea.setText(theTranscript());// Sets string into TextArea
    }

    // ~~ Splitting mess into smaller messes ~~
    @FXML
    public boolean checkStudentCSVId() { // Checks if student id exists and is the same as secondaryController
        while (true){
            try {
                while ((studentLine = studentReader.readLine()) != null) { // makes sure student.csv is not empty
                    System.out.println("Student Line search: " + studentLine);// ^ check iterations
                    studentParts = studentLine.split(",");
                    if (studentParts.length >= 2 && studentParts[0].equals(LOGIN_STUDENT_ID)) {
                        studentCSVID = studentParts[0];
                        System.out.println("Student ID final: " + studentCSVID); // ^ check after
                        System.out.println("Student Line final: " + studentLine); // ^ check after
                        System.out.println("studentParts final: " + Arrays.toString(studentParts) + "\n");// ^ check studentParts
                        return false;
                    }
                }
            } catch (IOException e) {
                System.out.println("Problem with studentReader/ " + STUDENT_FILE + ":\nIOException: " + e + "\n");
                e.printStackTrace();
                return false;
            }
            System.out.println("Hello Stanley, you have managed to escape to the back rooms in checkStudentCSVId");
            return false;
        }
    }

    @FXML
    public boolean checkCoursesCSVId() { //Checks if course id exists and is the same as secondaryController
        while (true){
            try {
                while ((courseLine = courseReader.readLine()) != null) {
                    System.out.println("Course Line search: " + courseLine);// ^ check iterations
                    courseParts = courseLine.split(",");
                    if (courseParts.length >= 2 && courseParts[1].equals(LOGIN_STUDENT_ID)) {
                        courseCSVID = courseParts[1];
                        System.out.println("Course ID final: " + courseCSVID); // ^ check after
                        System.out.println("Course Line final: " + courseLine); // ^ check after
                        System.out.println("CourseParts final: " + Arrays.toString(courseParts) + "\n");// ^ check courseParts
                        return false;
                    }
                }
            } catch (IOException e) {
                System.out.println("Problem with studentReader/ " + COURSES_FILE + ":\nIOException: " + e + "\n");
                e.printStackTrace();
                return false;
            }
            System.out.println("Hello Stanley, you have managed to escape to the back rooms in checkCoursesCSVId");
            return false;
        }
    }

    // @FXML
    // public void getModuleData(int currentSemester) {
    //     try {
    //         while ((moduleLine = moduleReader.readLine()) != null) {
    //             //TODO Get module info into arrayList then get the current semesters data

    //             // Determine the academic year
    //             if (currentSemester == 1 || currentSemester == 2) {
    //                 year = 1;
    //             } else if (currentSemester == 3 || currentSemester == 4) {
    //                 year = 2;
    //             } else if (currentSemester == 5 || currentSemester == 6) {
    //                 year = 3;
    //             } else if (currentSemester == 7 || currentSemester == 8) {
    //                 year = 4;
    //             } else if (currentSemester == 9 || currentSemester == 10) {
    //                 year = 5;
    //             }


    //             //TODO determining QCA based on year once QCA is done
    //             // Get QCA and divide it by the number of semesters except the first one
    //             if (year == 1) {
    //                 semesterQCA = -1.0;
    //             } else if (year == 2) {
    //                 semesterQCA = -2.0;
    //             } else if (year == 3) {
    //                 semesterQCA = -3.0;
    //             } else if (year == 4) {
    //                 semesterQCA = -4.0;
    //             } else if (year == 5) {
    //                 semesterQCA = -5.0;
    //             } else {
    //                 semesterQCA = -10.0;
    //             }

    //             totalQCA = -1; //TODO do maths

    //             //TODO Get semester year String
    //             // String semesterYear;
    //             // if (year == 1) {
    //             // semesterYear = moduleParts[2];
    //             // } else if (year == 2) {
    //             // semesterYear = moduleParts[35];
    //             // } else if (year == 3) {
    //             // semesterYear = moduleParts[68];
    //             // } else if (year == 4) {
    //             // semesterYear = moduleParts[101];
    //             // } else if (year == 5) {
    //             // semesterYear = moduleParts[134];
    //             // } else {
    //             // semesterYear = "0000/00";
    //             // }



    //             //TODO this is code, what does it do? ask kyle 2 days ago when he was sane because the current one also doesnt know
    //             // for (int semesterCount = 1; semesterCount <= totalSemesters; semesterCount++) {
    //             //     // Check if the current line corresponds to the LM121 entry
    //             //     if (moduleParts.length > 0 && moduleParts[0].trim().equals(studentParts[5])) {
    //             //         // Assuming the second field (index 1) contains the value for the year
    //             //         // 2023/24
    //             //         semesterYear = studentParts.length > 1 ? studentParts[1].trim()
    //             //                 : "Code Wrong";
    //             //     }



    //         }
    //     } catch (IOException e) {
    //         System.out.println("Problem with moduleReader/ " + MODULES_FILE + ":\nIOException: " + e + "\n");
    //         e.printStackTrace();
    //     }
    // }

    @FXML
    public String theTranscript() {
        if ((studentCSVID != null && courseCSVID != null) && (studentCSVID != courseCSVID) && LOGIN_STUDENT_ID != null) {
            //TODO EVERYTHING HERE, AGHHHHHHHHHHHH

            
            //Build Transcript
            fullTranscript.append(transcriptHeader(studentID, prefix, forename, surname, courseName, courseCode, courseRoute));
            for (int currentSemester = 0; currentSemester < totalSemesters; currentSemester++) { //loops 
                // getModuleData(currentSemester); //gets data of current semester in for loop
                fullTranscript.append(getFormattedTranscript(semesterQCA, totalQCA, semesterYear, year, totalSemesters, moduleIds, moduleNames, registrationTypes, gradeLetters, credits));
            }

            return fullTranscript.toString(); // ^Final goal
        } else {
            //?ERROR ERROR ERROR
            System.out.println("\nConditions not satisfied");
            System.out.println("LOGIN_STUDENT_ID: " + LOGIN_STUDENT_ID);

            System.out.println("studentCSVID: " + studentCSVID);
            System.out.println("studentLine: " + studentLine);
            System.out.println("studentParts: " + Arrays.toString(studentParts));

            System.out.println("courseCSVID: " + courseCSVID);
            System.out.println("courseLine: " + courseLine);
            System.out.println("courseParts: " + Arrays.toString(courseParts));

            // System.out.println("moduleLine: " + moduleLine);
            // System.out.println("moduleParts: " + Arrays.toString(moduleParts));
            return "Internal Error: Try Again";

        }
    }

    /**
     * transcriptHeader
     * 
     * @param studentID
     * @param prefix
     * @param forename
     * @param surname
     * @param courseName
     * @param courseCode
     * @param courseRoute
     * @return a the header of the transcript
     */
    @FXML
    public String transcriptHeader(String studentID, String prefix, String forename, String surname, String courseName,
            String courseCode, String courseRoute) {
        return theLine() + "\n" +
                String.format("|%48s %s %48s|", "", "University of Limerick", "") + "\r\n" +
                String.format("|%119s %s|", "", "") + "\r\n" +
                String.format("|%s %-40s %s %-30s %-" + Math.max(8, String.valueOf(studentID).length()) + "s %-9s|",
                        LocalDate.now(), "", "Student Transcript", "", studentID, "")
                + "\r\n" +
                String.format("|%119s %s|", "", "") + "\r\n" +
                theLine() + "\r\n" +
                String.format(
                        "|%-12s %-" + Math.max(6, String.valueOf(prefix).length()) + "s %-"
                                + Math.max(20, String.valueOf(forename).length()) + "s %-"
                                + Math.max(20, String.valueOf(surname).length()) + "s %58s|",
                        "Name", prefix, forename, surname, "")
                + "\r\n" +
                String.format(
                        "|%-12s %-" + Math.max(107, String.valueOf(courseName).length()) + "s|", "Course", courseName)
                + "\r\n" +
                String.format("|%-12s %-" + Math.max(107, String.valueOf(courseCode).length()) + "s|", "Course Code",
                        courseCode)
                + "\r\n" +
                String.format("|%-12s %-" + Math.max(107, String.valueOf(courseRoute).length()) + "s|", "Route",
                        courseRoute);
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
    @FXML
    public String getFormattedTranscript(double semesterQCA, double totalQCA, String semesterYear, int year,
            int totalSemesters, ArrayList<String> moduleIds, ArrayList<String> moduleNames,
            ArrayList<Character> registrationTypes, ArrayList<String> gradeLetters, ArrayList<Integer> credits) {
        StringBuilder transcriptBuilder = new StringBuilder();
        transcriptBuilder.append(theLine()).append("\r\n")
                .append(semesterDetails(semesterYear, year, totalSemesters)).append("\r\n")
                .append(blankLines()).append("\r\n")
                .append(semesterHeader_n_QCA(semesterQCA, totalQCA)).append("\r\n")
                .append(blankLines());
        for (int i = 0; i < moduleIds.size(); i++) {
            if (moduleIds.size() <= 0) {

            } else {
                transcriptBuilder.append(moduleInfo(moduleIds.get(i), moduleNames.get(i), registrationTypes.get(i),
                        gradeLetters.get(i), credits.get(i))).append("\r\n");
            }
        }
        transcriptBuilder.append(blankLines()).append("\r\n").append(theLine());
        return transcriptBuilder.toString();
    }

    /**
     * 
     * @return
     */
    @FXML
    private String theLine() {
        return "+--------------------------------------------------------------------------------------------+---------------------------+";
    }

    /**
     * 
     * @param semesterYear
     * @param year
     * @param totalSemesters
     * @return
     */
    @FXML
    private static String semesterDetails(String semesterYear, int year, int totalSemesters) {
        String yearString = "Year " + year;
        String totalSemestersString = "Sem " + totalSemesters;
        String formatString = "|%-30s %-" + Math.max(18, String.valueOf(year).length()) + "s %-"
                + Math.max(6, String.valueOf(totalSemesters).length()) + "s %-34s |%-5s %s %-5s|";
        return String.format(formatString, semesterYear, yearString, totalSemestersString, "", "", "Session To-Date",
                "");
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
    @FXML
    private static String moduleInfo(String moduleId, String moduleName, char registrationType, String gradeLetter,
            int credits) {
        String formaString = "|%s%-" + Math.max(13, String.valueOf(moduleId).length()) + "s%-"
                + Math.max(50, String.valueOf(moduleName).length()) + "s %s %-8s %-"
                + Math.max(9, String.valueOf(gradeLetter).length()) + "s %-"
                + Math.max(6, String.valueOf(credits).length()) + "s |%27s|";
        return String.format(formaString, "", moduleId, moduleName, registrationType, "", gradeLetter, credits, "");
    }

    /**
     * 
     * @param semesterQCA
     * @param totalQCA
     * @return
     */
    @FXML
    public static String semesterHeader_n_QCA(double semesterQCA, double totalQCA) {
        String formatString = "|%-12s %-50s %-9s %-8s %-9s|%-5s%10.2f%9.2f   |";
        return String.format(formatString, "Module", "Title", "Regn", "Grade", "Credits", "QCA", semesterQCA, totalQCA);
    }

    /**
     * 
     * @return
     */
    @FXML
    private static String blankLines() {
        return String.format("|%-91s | %-25s |", "", "");
    }
}