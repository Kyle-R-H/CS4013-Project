// Student Main page: Transcript

package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private String studentID; //got
    private String prefix; //got
    private String forename; //got
    private String surname; //got
    private String courseName; //got
    private String courseCode; // got
    private String courseRoute; //got

    // ^Formatted Transcript
    // ^semesterHeader_n_QCA()
    private ArrayList<Double> semesterQCA; //TODO get from course.csv
    private double totalQCA; // if/for loop
    // ^semesterDetails()
    private ArrayList<String> semesterYears; // if loop
    private ArrayList<Integer> totalYears; // if loop
    private int year; // cannot get
    private int totalSemesters; // got
    // ^moduleInfo() 
    //TODO all arraylists this
    private ArrayList<String> moduleIds;
    private ArrayList<String> moduleNames;
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
        //initialize ArrayLists
        semesterQCA = new ArrayList<>();
        semesterYears = new ArrayList<>();
        totalYears = new ArrayList<>();
        moduleIds = new ArrayList<>();
        moduleNames = new ArrayList<>();
        gradeLetters = new ArrayList<>();
        credits = new ArrayList<>();

        //get info
        getStudentInfoCSV();
        getCourseInfoCSV();
        getModuleData();
        if (studentCSVID == courseCSVID) {
            studentID = LOGIN_STUDENT_ID;
        }
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
    public void getModuleData() {
        //getting info into main array
        try {
            System.out.println("\nModules: ");
            while ((moduleLine = moduleReader.readLine()) != null) {
                moduleParts = moduleLine.split(",");
                if (moduleLine.contains(courseCode)) {// Found the courseCode
                    for (int i = 0; i < MODULE_LINE_COUNT; i++) {
                        // Gets all moduleLines in "course"
                        allModuleInfo.add(moduleParts);
                        System.out.println("Module module: " + moduleLine);
                        moduleLine = moduleReader.readLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with moduleReader/ " + MODULES_FILE + ":\nIOException: " + e + "\n");
            e.printStackTrace();
        }
        //inputting all info into independant arrays/ variables
        for (int i = 0; i < MODULE_LINE_COUNT;) {
            for (int j = 0; j < allModuleInfo.size(); j++) {
                String[] row = allModuleInfo.get(j);
                System.out.println("Array: " + Arrays.toString(row));
                if (i == 0) {
                    courseRoute = row[1]; // ^route
                    courseName = row[2]; // ^courseName
                    if (totalSemesters <= 8) { // ^semesterYears
                        semesterYears.add(row[3]);
                        semesterYears.add(row[4]);
                        semesterYears.add(row[5]);
                        semesterYears.add(row[6]);
                    } else if (totalSemesters >= 10) {
                        semesterYears.add(row[3]);
                        semesterYears.add(row[4]);
                        semesterYears.add(row[5]);
                        semesterYears.add(row[6]);
                        semesterYears.add(row[7]);
                    }
                    i++;
                }
                if (i == 1) {
                    //TODO left off here
                }
            }
        }

        // ^Year
        if (totalSemesters == 7 || totalSemesters == 8) {
            year = 4;
        } else if (totalSemesters == 9 || totalSemesters == 10) {
            year = 5;
        }
        for (int i = 0; i < year; i++) {
            totalYears.add(i);
        }
    }

    public void getEachSemesterInfo() {
        try {
            while ((moduleLine = moduleReader.readLine()) != null) {
                // TODO determining QCA based on year
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

                    //TODO make it so each semester can be printed with correct info
                // Build Transcript
                fullTranscript.append(
                        transcriptHeader(studentID, prefix, forename, surname, courseName, courseCode, courseRoute));
                fullTranscript.append(getFormattedTranscript(semesterQCA, totalQCA, semesterYears, totalYears,
                        totalSemesters, moduleIds, moduleNames, gradeLetters, credits));

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
            ArrayList<String> gradeLetters, ArrayList<Integer> credits) {
        StringBuilder transcriptBuilder = new StringBuilder();
        transcriptBuilder.append(theLine()).append("\r\n");

        for (int j = 2; j < totalSemesters; j++) {
            transcriptBuilder.append(semesterDetails(semesterYears, totalYears, totalSemesters)).append("\r\n");
        }

        transcriptBuilder.append(blankLines()).append("\r\n");

        for (int index = 0; index < totalSemesters; index++) { // semesterQCA = ArrayList<Double //TODO maybe add onto this to make 1 arraylist with all info needed per section
            if (semesterQCA.size() > 0) {
                transcriptBuilder.append(semesterHeader_n_QCA(semesterQCA, totalQCA)).append("\r\n");
            }
        }

        transcriptBuilder.append(blankLines());

        for (int i = 0; i < moduleIds.size(); i++) {
            if (moduleIds.size() > 0) {
            } else {
                transcriptBuilder.append(moduleInfo(moduleIds.get(i), moduleNames.get(i),
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
    private static String moduleInfo(String moduleId, String moduleName, String gradeLetter,
            int credits) {
        String formaString = "|%s%-" + Math.max(13, String.valueOf(moduleId).length()) + "s%-"
                + Math.max(50, String.valueOf(moduleName).length()) + "s %-10s %-"
                + Math.max(9, String.valueOf(gradeLetter).length()) + "s %-"
                + Math.max(6, String.valueOf(credits).length()) + "s |%27s|";
        return String.format(formaString, "", moduleId, moduleName, "", gradeLetter, credits, "");
    }

    /**
     * 
     * @param semesterQCA
     * @param totalQCA
     * @return
     */
    @FXML
    public static String semesterHeader_n_QCA(ArrayList<Double> semesterQCA, double totalQCA) {
        String formatString = "|%-12s %-60s %-8s %-9s|%-5s%10.2f%9.2f   |";
        return String.format(formatString, "Module", "Title", "Grade", "Credits", "QCA", semesterQCA, totalQCA);
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

//TODO javadoc, documentation