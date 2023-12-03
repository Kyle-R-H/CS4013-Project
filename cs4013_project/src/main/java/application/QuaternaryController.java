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
    private final String LOGIN_STUDENT_ID = "22343261"; // !Get rid of this

    // ^Get access to data
    private String studentCSVID; // gpt
    private String courseCSVID; // got

    private String studentLine;
    private String courseLine;
    private String moduleLine;

    private String[] studentParts;
    private String[] courseParts;
    private String[] moduleParts;

    private ArrayList<String[]> allModuleCodes = new ArrayList<>();
    private ArrayList<String[]> allModuleNames = new ArrayList<>();
    private ArrayList<String[]> allModuleCredits = new ArrayList<>();
    private ArrayList<String[]> allModuleGrades = new ArrayList<>();

    private ArrayList<String> moduleId;
    private ArrayList<String> moduleName;
    private ArrayList<String> gradeLetter;
    private ArrayList<String> moduleCredit;

    // ~~ The Transcript ~~ //
    // ^Transcript Header
    private String studentID; // got
    private String prefix; // got
    private String forename; // got
    private String surname; // got
    private String courseName; // got
    private String courseCode; // got
    private String courseRoute; // got

    // ^Formatted Transcript
    // ^semesterHeader_n_QCA()
    private int currentSemester; //got
    private ArrayList<Double> semesterQCA; // TODO get from course.csv
    private double totalQCA; // if/for loop
    // ^semesterDetails()
    private ArrayList<String> semesterYears; // if loop
    private ArrayList<Integer> totalYears; // if loop
    private int year; // cannot get
    private int totalSemesters; // got
    private ArrayList<Integer> numOfModules; // got

    // ^moduleInfo()
    // TODO all arraylists this
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
        // initialize ArrayLists
        semesterQCA = new ArrayList<>();
        semesterYears = new ArrayList<>();
        totalYears = new ArrayList<>();
        moduleIds = new ArrayList<>();
        moduleNames = new ArrayList<>();
        gradeLetters = new ArrayList<>();
        credits = new ArrayList<>();
        numOfModules = new ArrayList<>();
        moduleId = new ArrayList<>();
        moduleName = new ArrayList<>();
        gradeLetter = new ArrayList<>();
        moduleCredit = new ArrayList<>();

        // get info
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

                        if (!courseParts[2].isEmpty()) {
                            totalSemesters = Integer.parseInt(courseParts[2]);
                        } else {
                            totalSemesters = 10; // default value
                        }

                        System.out.println("semesterQCA: " + semesterQCA);
                        System.out.println("\nCourse ID: " + courseCSVID); // ^ check after
                        System.out.println("Total Semesters: " + totalSemesters); // ^ check after
                        System.out.println("Course Line final: " + courseLine); // ^ check after
                        System.out.println("CourseParts final: " + Arrays.toString(courseParts));// ^check CourseParts

                        return false;
                    } else if (courseParts.length >= 8 && Integer.parseInt(courseParts[0]) >= 1
                        && Integer.parseInt(courseParts[0]) <= 8) {
                        allModuleGrades.add(courseParts);
                        semesterQCA.add(Double.parseDouble(courseParts[9]));
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

    // TODO CURRENTLY module info
    public void getModuleData() {
        // Store info into main array
        try {
            System.out.println("\nModules: ");
            while ((moduleLine = moduleReader.readLine()) != null) {
                if (moduleLine.contains(courseCode)) {
                    // Extract information from the first line
                    courseParts = moduleLine.split(",");
                    courseRoute = courseParts[1];
                    courseName = courseParts[2];
                    semesterYears.add(courseParts[3]);
                    semesterYears.add(courseParts[3]);
                    semesterYears.add(courseParts[4]);
                    semesterYears.add(courseParts[4]);
                    semesterYears.add(courseParts[5]);
                    semesterYears.add(courseParts[5]);
                    semesterYears.add(courseParts[6]);
                    semesterYears.add(courseParts[6]);
                    if (totalSemesters == 10) {
                        semesterYears.add(courseParts[7]);
                        semesterYears.add(courseParts[7]);
                    }

                    System.out.println("Course Route: " + courseRoute.toString());
                    System.out.println("Course Name: " + courseName.toString());
                    System.out.println("Course Years: " + semesterYears.toString());

                    // Read the next 8 lines (including the current one)
                    for (int i = 0; i < 8; i++) {
                        moduleLine = moduleReader.readLine();
                        moduleParts = moduleLine.split(",");
                        allModuleCodes.add(moduleParts);
                    }

                    // Read the line starting with 11
                    for (int i = 0; i < 8; i++) {
                        moduleLine = moduleReader.readLine();
                        moduleParts = moduleLine.split(",");
                        allModuleNames.add(moduleParts);
                    }

                    // Read the line starting with 21
                    for (int i = 0; i < 8; i++) {
                        moduleLine = moduleReader.readLine();
                        moduleParts = moduleLine.split(",");
                        allModuleCredits.add(moduleParts);
                    }
                    // Print or store the module credits
                    System.out.println("\nAll Module Codes:");
                    for (String[] code : allModuleCodes) {
                        System.out.println("Codes: " + Arrays.toString(code));
                    }
                    System.out.println("\nCourse Names:");
                    for (String[] names : allModuleNames) {
                        System.out.println("Names: " + Arrays.toString(names));
                    }
                    System.out.println("\nModule Credits:");
                    for (String[] credit : allModuleCredits) {
                        System.out.println("Credits: " + Arrays.toString(credit));
                    }
                    System.out.println("\nModule Grades:");
                    for (String[] grade : allModuleGrades) {
                        System.out.println("Grades: " + Arrays.toString(grade));
                    }
                    for (String[] num : allModuleCredits) {
                        numOfModules.add(num.length-1);
                    }
                    System.out.println("Number of modules:");
                    for (Integer value : numOfModules) {
                        System.out.print(value + "\n");
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with moduleReader/ " + MODULES_FILE + ":\nIOException: " + e + "\n");
            e.printStackTrace();
        }
        // ^Year
        //checks how many years there are and sets the total years
        if (totalSemesters == 7 || totalSemesters == 8) { 
            year = 4;
        } else if (totalSemesters == 9 || totalSemesters == 10) {
            year = 5;
        }
        for (int i = 0; i < year; i++) {
            totalYears.add(1 + i);
            totalYears.add(1 + i);
        }
    }

    @FXML
    public String theTranscript() {
        if ((studentCSVID != null && courseCSVID != null) && (studentCSVID != courseCSVID)
                && LOGIN_STUDENT_ID != null) {
            studentID = LOGIN_STUDENT_ID;
            if (studentID != null && forename != null && surname != null && courseName != null && courseCode != null
                    && courseRoute != null && semesterQCA != null && semesterYears != null && totalYears != null
                    && moduleIds != null && moduleNames != null && gradeLetters != null && credits != null) {
                // TODO make it so each semester can be printed with correct info

                // Build Transcript
                fullTranscript.append(
                        transcriptHeader(studentID, prefix, forename, surname, courseName, courseCode, courseRoute));
                fullTranscript.append(getFormattedTranscript(semesterQCA, totalQCA, semesterYears, totalYears,
                        totalSemesters, moduleIds, moduleNames, gradeLetters, credits));

                return fullTranscript.toString(); // ^Final goal
            } else {
                System.out.println("StudentId: " + (studentID != null));
                System.out.println("\nForename: " + (forename != null));
                System.out.println("\nSurname: " + (surname != null));
                System.out.println("\nCourseName: " + (courseName != null));
                System.out.println("\nCourseCode: " + (courseCode != null));
                System.out.println("\nRoute: " + (courseRoute != null));
                System.out.println("\nSemesterQCA: " + (semesterQCA != null));
                System.out.println("\nsemesterYears: " + (semesterYears != null));
                System.out.println("\ntotalYears: " + (totalYears != null));
                System.out.println("\nModuleIds: " + (moduleIds != null));
                System.out.println("\nModulesNames: " + (moduleNames != null));
                System.out.println("\nGradeLetters: " + (gradeLetters != null));
                System.out.println("\nCredits: " + (credits != null));
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
    public String getFormattedTranscript(ArrayList<Double> semesterQCA, double totalQCA,
            ArrayList<String> semesterYears, ArrayList<Integer> totalYears, int totalSemesters,
            ArrayList<String> moduleIds, ArrayList<String> moduleNames,
            ArrayList<String> gradeLetters, ArrayList<Integer> credits) {
        StringBuilder transcriptBuilder = new StringBuilder();
        transcriptBuilder.append("\n").append(theLine()).append("\r\n");
    
            if (currentSemester == 1 || currentSemester == 2) {
                totalQCA = (semesterQCA.get(0) + semesterQCA.get(1)) / 2;
            } else if (currentSemester == 3 || currentSemester == 4) {
                totalQCA = (semesterQCA.get(2) + semesterQCA.get(3)) / 2;
            } else if (currentSemester == 5 || currentSemester == 6) {
                totalQCA = (semesterQCA.get(2) + semesterQCA.get(3) + semesterQCA.get(4) + semesterQCA.get(5)) / 4;
            } else if (currentSemester == 7 || currentSemester == 8) {
                totalQCA = (semesterQCA.get(2) + semesterQCA.get(3) + semesterQCA.get(4) + semesterQCA.get(5)
                        + semesterQCA.get(6) + semesterQCA.get(7)) / 6;
            } else if (currentSemester == 9 || currentSemester == 10) {
                totalQCA = (semesterQCA.get(2) + semesterQCA.get(3) + semesterQCA.get(4) + semesterQCA.get(5)
                        + semesterQCA.get(6) + semesterQCA.get(7) + semesterQCA.get(8) + semesterQCA.get(9)) / 8;
            } else {
                totalQCA = -1;
            }

            currentSemester = 0;
        for (int rowCount = 0; rowCount < totalSemesters; rowCount++) {
            System.out.println("Loop Iteration: " + (rowCount + 1));
            currentSemester = rowCount + 1; // start semesters from semester 1 //TODO

            transcriptBuilder.append(semesterDetails(semesterYears.get(rowCount), totalYears.get(rowCount), currentSemester))
                    .append("\r\n");
    
            // Print header once per semester
            transcriptBuilder.append(blankLines()).append("\r\n");
            transcriptBuilder.append(semesterHeader_n_QCA(semesterQCA.get(rowCount), totalQCA)).append("\r\n");
    

                moduleId.clear();
                moduleName.clear();
                gradeLetter.clear();
                moduleCredit.clear();
                for (int a = 1; a < numOfModules.get(rowCount); a++) {
                    moduleIds.add(allModuleCodes.get(rowCount)[a]);
                    System.out.println("a: " + a + ", moduleId: " + moduleIds.get(moduleIds.size() - 1));
                }
                for (int b = 1; b < numOfModules.get(rowCount); b++) {
                    moduleName.add(allModuleNames.get(rowCount)[b]);
                    System.out.println("b: " + b + ", moduleName: " + moduleName.get(moduleName.size() - 1));
                }
                for (int c = 1; c < numOfModules.get(rowCount); c++) {
                    gradeLetter.add(allModuleGrades.get(rowCount)[c]);
                    System.out.println("c: " + c + ", gradeLetter: " + gradeLetter.get(gradeLetter.size() - 1));
                }
                for (int d = 1; d < numOfModules.get(rowCount); d++) {
                    moduleCredit.add(allModuleCredits.get(rowCount)[d]);
                    System.out.println("d: " + d + ", moduleCredit: " + moduleCredit.get(moduleCredit.size() - 1));
                }
                for (int index = 0; index < numOfModules.get(rowCount); index++) {
                    transcriptBuilder.append(moduleInfo(moduleIds.get(moduleIds.size() - 1), 
                                                        moduleName.get(moduleName.size() - 1), 
                                                        gradeLetter.get(gradeLetter.size() - 1), 
                                                        moduleCredit.get(moduleCredit.size() - 1))).append("\r\n");
                }
                





            //TODO if i have time, make certain fields be N/A if there's no data
            // transcriptBuilder.append(moduleInfo("N/A", "N/A", "N/A", "N/A")).append("\r\n");
            transcriptBuilder.append(theLine()).append("\r\n");
        }
    
        return transcriptBuilder.toString();
    }

    /*@FXML //TODO add back in the !null stuff
public String getFormattedTranscript(ArrayList<Double> semesterQCA, double totalQCA,
        ArrayList<String> semesterYears, ArrayList<Integer> totalYears, int totalSemesters,
        ArrayList<String> moduleIds, ArrayList<String> moduleNames,
        ArrayList<String> gradeLetters, ArrayList<Integer> credits) {
    StringBuilder transcriptBuilder = new StringBuilder();
    transcriptBuilder.append("\n").append(theLine()).append("\r\n");

    for (int j = 0; j < totalSemesters; j++) {
        System.out.println("Loop Iteration: " + j);
        // Check if semesterQCA has elements
        if (!semesterQCA.isEmpty() && !totalYears.isEmpty() && j < totalYears.size()) {
            System.out.println("Semester: " + semesterYears.get(j) + " - Year: " + totalYears.get(j));

            int currentSemester = j + 1; // Semesters start from 1

            transcriptBuilder.append(semesterDetails(semesterYears.get(j), totalYears.get(j), currentSemester))
                    .append("\r\n");

            // Check if semesterQCA has elements
            if (!semesterQCA.isEmpty() && j < semesterQCA.size()) {
                // Print header once per semester
                transcriptBuilder.append(blankLines()).append("\r\n");
                transcriptBuilder.append(semesterHeader_n_QCA(semesterQCA.get(j), totalQCA)).append("\r\n");
            }

            // Print modules
            for (int i = 0; i < moduleIds.size(); i++) {
                // Check if moduleIds, moduleNames, gradeLetters, and credits have elements
                if (!moduleIds.isEmpty() && !moduleNames.isEmpty() && !gradeLetters.isEmpty() && !credits.isEmpty()
                        && i < moduleIds.size() && i < moduleNames.size() && i < gradeLetters.size() && i < credits.size()) {
                    transcriptBuilder.append(moduleInfo(moduleIds.get(i), moduleNames.get(i),
                            gradeLetters.get(i), credits.get(i))).append("\r\n");
                } else {
                    transcriptBuilder.append(moduleInfo("plzfix", "Nothin", "N/A", -1)).append("\r\n");
                }
            }
        }
    }

    transcriptBuilder.append(blankLines()).append("\r\n").append(theLine());
    return transcriptBuilder.toString();
}
*/

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
    private static String semesterDetails(String semesterYears, int totalYears,
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
            String credits) {
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
    public static String semesterHeader_n_QCA(double semesterQCA, double totalQCA) {
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

// TODO javadoc, documentation