// Student Main page: Transcript

package application;

import java.io.BufferedReader;
import java.io.IOException;
// import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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
    // private final String LOGIN_STUDENT_ID = SecondaryController.studentNumber;
    private final String LOGIN_STUDENT_ID = "22343261"; //!Get rid of this

    // ^Get access to data
    private String studentCSVID; // gpt
    private String courseCSVID; // got

    private String studentLine;
    private String courseLine;
    private String moduleLine;

    private String[] studentParts;
    private String[] courseParts;
    private String[] moduleParts;

    private ArrayList<String[]> allModuleInfo = new ArrayList<>();
    private final int MODULE_LINE_COUNT = 25;

    // ~~ The Transcript ~~ //
    // ^Transcript Header
    private String studentID;
    private String prefix;
    private String forename;
    private String surname;
    private String courseName;
    private String courseCode; // got
    private String courseRoute;

    // ^Formatted Transcript
    // ^semesterHeader_n_QCA()
    private ArrayList<Double> semesterQCA;
    private double totalQCA; // if/for loop
    // ^semesterDetails()
    private ArrayList<String> semesterYears; // if loop
    private ArrayList<Integer> totalYears; // if loop
    private int year; // cannot get
    private int totalSemesters; // got
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

    public QuaternaryController() {
        getStudentInfoCSV();
        getCourseInfoCSV();
        getModuleDataArrayLists();
        // totalSemesters = Integer.parseInt(courseParts[3]);//TODO loop through courses
        // to check which part[3]!!!!!
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
    public boolean getStudentInfoCSV() { // Checks if student id exists and is the same as secondaryController
        while (true) {
            try {
                System.out.println("\nStudent:");
                while ((studentLine = studentReader.readLine()) != null) { // makes sure student.csv is not empty
                    System.out.println("Student Line search: " + studentLine);// ^ check iterations
                    studentParts = studentLine.split(",");
                    if (studentParts.length >= 2 && studentParts[0].equals(LOGIN_STUDENT_ID)) {
                        studentCSVID = studentParts[0];
                        prefix = studentParts[2];
                        forename = studentParts[3];
                        surname = studentParts[4];
                        courseCode = studentParts[5];
                        System.out.println("\nStudent Prefix: " + prefix); // ^ check after
                        System.out.println("Student Forename: " + forename); // ^ check after
                        System.out.println("Student Surname: " + surname); // ^ check after
                        System.out.println("Course Code: " + courseCode); // ^ check after
                        System.out.println("\nStudent ID final: " + studentCSVID); // ^ check after
                        System.out.println("Student Line final: " + studentLine); // ^ check after
                        System.out.println("studentParts final: " + Arrays.toString(studentParts));// ^ check
                                                                                                   // studentParts
                        return false;
                    }
                }
            } catch (IOException e) {
                System.out.println("Problem with studentReader/ " + STUDENT_FILE + ":\nIOException: " + e + "\n");
                e.printStackTrace();
                return false;
            }
            System.out.println("Escaped try-catch loop in getStudentInfoCSV");
            return false;
        }
    }

    @FXML
    public boolean getCourseInfoCSV() { // Checks if course id exists and is the same as secondaryController
        while (true) {
            try {
                System.out.println("\nCourse:");
                while ((courseLine = courseReader.readLine()) != null) {
                    System.out.println("Course Line search: " + courseLine);// ^ check iterations
                    courseParts = courseLine.split(",");
                    if (courseParts.length >= 2 && courseParts[1].equals(LOGIN_STUDENT_ID)) {
                        courseCSVID = courseParts[1];
                        totalSemesters = Integer.parseInt(courseParts[2]);
                        System.out.println("\nCourse ID: " + courseCSVID); // ^ check after
                        System.out.println("Total Semesters: " + totalSemesters); // ^ check after
                        System.out.println("Course Line final: " + courseLine); // ^ check after
                        System.out.println("CourseParts final: " + Arrays.toString(courseParts));// ^check CourseParts
                        return false;
                    }
                }
            } catch (IOException e) {
                System.out.println("Problem with courseReader/ " + COURSES_FILE + ":\nIOException: " + e + "\n");
                e.printStackTrace();
                return false;
            }
            System.out.println("Escaped try-catch loop in getCourseInfoCSV");
            return false;
        }
    }


    //TODO CURRENTLY module info
    @FXML
    public void getModuleDataArrayLists() {
        //getting info into main array
        try {
            System.out.println("\nModules: ");
            String line;
            while ((line = moduleReader.readLine()) != null) {
                if (line.contains(courseCode)) {// Found the courseCode
                    for (int i = 0; i < MODULE_LINE_COUNT; i++) {
                        // Gets all lines in "course"
                        allModuleInfo.add(line.split(","));
                        System.out.println("Module Line: " + line);
                        line = moduleReader.readLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with moduleReader/ " + MODULES_FILE + ":\nIOException: " + e + "\n");
            e.printStackTrace();
        }
        //inputting all info into independant arrays/ variables
        for (String[] row : allModuleInfo) {
            // Iterate through the array
            System.out.println("Array: " + Arrays.toString(row));
            for (int i = 0; i < MODULE_LINE_COUNT; i++) {
                for (String data : row) {
                    if (data.matches("\\d{4}/\\{2}")) {
                        System.out.println("Should be year 000/00: "+data);
                        semesterYears.add(data);                      
                    }
                }
                if (i == 0) {
                    courseName = row[2];
                }
            }
        }
    }

    public void getModuleCode(int currentSemester){

    }
    // @FXML
    // public boolean getSemsterYears() { // Checks if course id exists and is the same as secondaryController
    //                                    // finish semesterYearss
    //     while (true) {
    //         try {
    //             while ((moduleLine = moduleReader.readLine()) != null) {
    //                 System.out.println("Module Line search: " + moduleLine);// ^ check iterations
    //                 moduleParts = moduleLine.split(",");
    //                 if (moduleParts.length >= 2 && moduleParts[1].equals(courseCode)) {
    //                     System.out.println("CourseParts final: " + Arrays.toString(moduleParts) + "\n");// ^ check
    //                                                                                                     // courseParts
    //                     System.out.println("Course ID final: " + courseCSVID); // ^ check after
    //                     System.out.println("Course Line final: " + moduleLine); // ^ check after
    //                     return false;
    //                 }
    //             }
    //         } catch (IOException e) {
    //             System.out.println("Problem with studentReader/ " + COURSES_FILE + ":\nIOException: " + e + "\n");
    //             e.printStackTrace();
    //             return false;
    //         }
    //         System.out.println("Hello Stanley, you have managed to escape to the back rooms in getCourseInfoCSV");
    //         return false;
    //     }
    // }

    @FXML
    public void getModuleData(int currentSemester) {
        try {
            while ((moduleLine = moduleReader.readLine()) != null) {
                // TODO Get module info into arrayList then get the current semesters data

                // Determine the academic year
                if (currentSemester == 1 || currentSemester == 2) {
                    year = 1;
                } else if (currentSemester == 3 || currentSemester == 4) {
                    year = 2;
                } else if (currentSemester == 5 || currentSemester == 6) {
                    year = 3;
                } else if (currentSemester == 7 || currentSemester == 8) {
                    year = 4;
                } else if (currentSemester == 9 || currentSemester == 10) {
                    year = 5;
                }

                for (int i = 0; i < year; i++) {
                    totalYears.add(i);
                }

                // TODO determining QCA based on year once QCA is done
                // Get QCA and divide it by the number of semesters except the first one
                // if (year == 1) {
                // semesterQCA = -1.0;
                // } else if (year == 2) {
                // semesterQCA = -2.0;
                // } else if (year == 3) {
                // semesterQCA = -3.0;
                // } else if (year == 4) {
                // semesterQCA = -4.0;
                // } else if (year == 5) {
                // semesterQCA = -5.0;
                // } else {
                // semesterQCA = -10.0;
                // }

                totalQCA = -1; // TODO do maths

                // TODO Get semester year String
                // if (year == 1) {
                // semesterYears.add();
                // } else if (year == 2) {
                // semesterYears = moduleParts[35];
                // } else if (year == 3) {
                // semesterYears = moduleParts[68];
                // } else if (year == 4) {
                // semesterYears = moduleParts[101];
                // } else if (year == 5) {
                // semesterYears = moduleParts[134];
                // } else {
                // semesterYears = "0000/00";
                // }

            }
        } catch (IOException e) {
            System.out.println("Problem with moduleReader/ " + MODULES_FILE + ":\nIOException: " + e + "\n");
            e.printStackTrace();
        }
    }

    @FXML
    public String theTranscript() {
        if ((studentCSVID != null && courseCSVID != null) && (studentCSVID != courseCSVID)
                && LOGIN_STUDENT_ID != null) {
            if (studentID != null && prefix != null && forename != null && surname != null && courseName != null
                    && courseCode != null && courseRoute != null) {
                // TODO EVERYTHING HERE, AGHHHHHHHHHHHH

                // Build Transcript
                fullTranscript.append(
                        transcriptHeader(studentID, prefix, forename, surname, courseName, courseCode, courseRoute));
                fullTranscript.append(getFormattedTranscript(semesterQCA, totalQCA, semesterYears, totalYears,
                        totalSemesters, moduleIds, moduleNames, registrationTypes, gradeLetters, credits));

                return fullTranscript.toString(); // ^Final goal
            } else {
                return "Vital information missing";
            }
        } else {
            // ?ERROR ERROR ERROR
            System.out.println(
                    "(studentCSVID != null && courseCSVID != null) && (studentCSVID != courseCSVID) && LOGIN_STUDENT_ID != null");
            return "Student ID not registered";

        }
    }

    // ~~ Formatting ~~ //
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
     * @param semesterYears
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
    public String getFormattedTranscript(ArrayList<Double> semesterQCA, double totalQCA, ArrayList<String> semesterYears,
            ArrayList<Integer> totalYears,
            int totalSemesters, ArrayList<String> moduleIds, ArrayList<String> moduleNames,
            ArrayList<Character> registrationTypes, ArrayList<String> gradeLetters, ArrayList<Integer> credits) {
        StringBuilder transcriptBuilder = new StringBuilder();
        transcriptBuilder.append(theLine()).append("\r\n");

        for (int j = 2; j < totalSemesters; j++) {
            transcriptBuilder.append(semesterDetails(semesterYears, totalYears, totalSemesters)).append("\r\n");
        }

        transcriptBuilder.append(blankLines()).append("\r\n");

        for (int index = 0; index < totalSemesters; index++) { // semesterQCA = ArrayList<Double
            if (semesterQCA.size() > 0) {
                transcriptBuilder.append(semesterHeader_n_QCA(semesterQCA, totalQCA)).append("\r\n");
            }
        }

        transcriptBuilder.append(blankLines());

        for (int i = 0; i < moduleIds.size(); i++) {
            if (moduleIds.size() > 0) {
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
     * @param semesterYears
     * @param year
     * @param totalSemesters
     * @return
     */
    @FXML
    private static String semesterDetails(ArrayList<String> semesterYears, ArrayList<Integer> totalYears,
            int totalSemesters) {
        String yearString = "Year " + totalYears;
        String totalSemestersString = "Sem " + totalSemesters;
        String formatString = "|%-30s %-" + Math.max(18, String.valueOf(totalYears).length()) + "s %-"
                + Math.max(6, String.valueOf(totalSemesters).length()) + "s %-34s |%-5s %s %-5s|";
        return String.format(formatString, semesterYears, yearString, totalSemestersString, "", "", "Session To-Date",
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
    public static String semesterHeader_n_QCA(ArrayList<Double> semesterQCA, double totalQCA) {
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