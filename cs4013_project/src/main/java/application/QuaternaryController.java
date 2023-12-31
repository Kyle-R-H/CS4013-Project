//Student Main Page

package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * QuaternaryController
 */
public class QuaternaryController {
    // ~~ Files ~~ //
    // File Names
    private final String STUDENT_FILE = "Student.csv";
    private final String COURSES_FILE = "Courses.csv";
    private final String MODULES_FILE = "Modules.csv";
    // Read Files
    private BufferedReader studentReader = new BufferedReader(
            new InputStreamReader(getClass().getResourceAsStream(STUDENT_FILE)));
    private BufferedReader courseReader = new BufferedReader(
            new InputStreamReader(getClass().getResourceAsStream(COURSES_FILE)));
    private BufferedReader moduleReader = new BufferedReader(
            new InputStreamReader(getClass().getResourceAsStream(MODULES_FILE)));

    // Reference Student Id from SecondaryController.java
    private final String LOGIN_STUDENT_ID = SecondaryController.studentNumber;

    // Get access to data
    private String studentCSVID;
    private String courseCSVID; 

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

    // ~~ The Transcript ~~ //
    // Transcript Header
    private String studentID; 
    private String prefix; 
    private String forename; 
    private String surname; 
    private String courseName; 
    private String courseCode; 
    private String courseRoute; 

    // Formatted Transcript
    // semesterHeader_n_QCA()
    private int currentSemester; 
    private ArrayList<Double> semesterQCA;
    private double totalQCA;
    // semesterDetails()
    private ArrayList<String> semesterYears;
    private ArrayList<Integer> totalYears; 
    private int year; 
    private int totalSemesters; 

    // moduleInfo()
    private ArrayList<String> moduleIds;
    private ArrayList<String> moduleNames;
    private ArrayList<String> gradeLetters;
    private ArrayList<Integer> moduleCredits;
    StringBuilder fullTranscript = new StringBuilder();

    // ~~ Methods ~~ //
    /**
     * Initializes ArrayLists and retrieves information.
     */
    public QuaternaryController() {
        // initialize ArrayLists
        semesterQCA = new ArrayList<>();
        semesterYears = new ArrayList<>();
        totalYears = new ArrayList<>();
        moduleIds = new ArrayList<>();
        moduleNames = new ArrayList<>();
        gradeLetters = new ArrayList<>();
        moduleCredits = new ArrayList<>();

        // get info
        getStudentInfoCSV();
        getCourseInfoCSV();
        getModuleData();
        if (studentCSVID == courseCSVID) {
            studentID = LOGIN_STUDENT_ID;
        }
    }

    /**
     * Switches view to homepage.
     * @throws IOException if I/O error occurs while switching to homepage.
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private TextArea transcriptTextArea;

    /**
     * Initializes the TextArea's CSS styling and sets the transcript text.
     */
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
        transcriptTextArea.getParent().requestFocus(); // Set focus to another node to apply the styles to TextArea immediately
        transcriptTextArea.setText(theTranscript());// Sets string into TextArea
    }

    /**
     * Puts information from the 'Student.csv' file into relevant variables.
     * @return false once completed running.
     */
    public boolean getStudentInfoCSV() { // Checks if student id exists and is the same as secondaryController
        while (true) {
            try {
                System.out.println("\nStudent:");
                while ((studentLine = studentReader.readLine()) != null) { // makes sure student.csv is not empty
                    System.out.println("Student Line search: " + studentLine);//  check iterations
                    studentParts = studentLine.split(",");
                    if (studentParts.length >= 2 && studentParts[0].equals(LOGIN_STUDENT_ID)) { //makes sure 2 indexes of id and password are present and if the student id is present
                        studentCSVID = studentParts[0];
                        prefix = studentParts[2];
                        forename = studentParts[3];
                        surname = studentParts[4];
                        courseCode = studentParts[5];
                        System.out.println("\nStudent Prefix: " + prefix); 
                        System.out.println("Student Forename: " + forename); 
                        System.out.println("Student Surname: " + surname); 
                        System.out.println("Course Code: " + courseCode); 
                        System.out.println("\nStudent ID final: " + studentCSVID); 
                        System.out.println("Student Line final: " + studentLine); 
                        System.out.println("studentParts final: " + Arrays.toString(studentParts));
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

    /**
     * Puts information from the 'Courses.csv' file into relevant variables.
     * @return false once completed running
     */
    public boolean getCourseInfoCSV() { // Checks if course id exists and is the same as secondaryController
        while (true) {
            try {
                System.out.println("\nCourse:");
                while ((courseLine = courseReader.readLine()) != null) {
                    System.out.println("Course Line search: " + courseLine);//  check iterations
                    courseParts = courseLine.split(",");
                    if (courseParts.length >= 2 && courseParts[1].equals(LOGIN_STUDENT_ID)) {
                        courseCSVID = courseParts[1];

                        if (!courseParts[2].isEmpty()) {
                            totalSemesters = Integer.parseInt(courseParts[2]);
                        } else {
                            totalSemesters = 8; // default value
                        }

                        courseLine = courseReader.readLine();
                        courseParts = courseLine.split(",");
                        while (courseParts.length >= 8 && Integer.parseInt(courseParts[0]) >= 1
                                && Integer.parseInt(courseParts[0]) <= 8) {
                            allModuleGrades.add(courseParts);
                            semesterQCA.add(Double.parseDouble(courseParts[9]));
                            courseLine = courseReader.readLine();
                            courseParts = courseLine.split(",");
                        }
                        System.out.println("semesterQCA: " + semesterQCA);
                        System.out.println("\nCourse ID: " + courseCSVID); 
                        System.out.println("Total Semesters: " + totalSemesters); 
                        System.out.println("Course Line final: " + courseLine); 
                        System.out.println("CourseParts final: " + Arrays.toString(courseParts));// check CourseParts

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

    /**
     * Puts information from the 'Modules.csv' file into relevant variables.
     */
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
                    if (totalSemesters >= 8) {
                        semesterYears.add(courseParts[6]);
                        semesterYears.add(courseParts[6]);
                    }
                    if (totalSemesters == 10) {
                        semesterYears.add(courseParts[7]);
                        semesterYears.add(courseParts[7]);
                    }

                    System.out.println("Course Route: " + courseRoute.toString());
                    System.out.println("Course Name: " + courseName.toString());
                    System.out.println("Course Years: " + semesterYears.toString());

                    // Read the next 8 lines 
                    System.out.println("\nallModulesCodes: ");
                    for (int i = 0; i < 8; i++) {
                        moduleLine = moduleReader.readLine();
                        moduleParts = moduleLine.split(",");
                        System.out.println(Arrays.toString(moduleParts));
                        allModuleCodes.add(moduleParts);
                    }

                    // Read the line starting with 11
                    System.out.println("\nallModuleNames");
                    for (int i = 0; i < 8; i++) {
                        moduleLine = moduleReader.readLine();
                        moduleParts = moduleLine.split(",");
                        System.out.println(Arrays.toString(moduleParts));
                        allModuleNames.add(moduleParts);
                    }

                    // Read the line starting with 21
                    System.out.println("\nallModulesCredits: ");
                    for (int i = 0; i < 8; i++) {
                        moduleLine = moduleReader.readLine();
                        moduleParts = moduleLine.split(",");
                        System.out.println(Arrays.toString(moduleParts));
                        allModuleCredits.add(moduleParts);
                    }
                    //for testing/debugging
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
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with moduleReader/ " + MODULES_FILE + ":\nIOException: " + e + "\n");
            e.printStackTrace();
        }
        // checks how many years there are and sets the total years
        if (totalSemesters == 5 || totalSemesters == 6) {
            year = 3;
        } else if (totalSemesters == 7 || totalSemesters == 8) {
            year = 4;
        } else if (totalSemesters == 9 || totalSemesters == 10) {
            year = 5;
        }
        for (int i = 0; i < year; i++) {
            totalYears.add(1 + i);
            totalYears.add(1 + i);
        }
    }

    /**
     * Calls methods to create the transcript.
     * @return a formatted string containing the transcript.
     */
    @FXML
    public String theTranscript() {
        if ((studentCSVID != null && courseCSVID != null) && (studentCSVID != courseCSVID)
                && LOGIN_STUDENT_ID != null) {
            studentID = LOGIN_STUDENT_ID;
            if (studentID != null && forename != null && surname != null && courseName != null && courseCode != null
                    && courseRoute != null && semesterQCA != null && semesterYears != null && totalYears != null
                    && moduleIds != null && moduleNames != null && gradeLetters != null && moduleCredits != null) {

                // Build Transcript
                fullTranscript.append(
                        transcriptHeader(studentID, prefix, forename, surname, courseName, courseCode, courseRoute));
                fullTranscript.append(getFormattedTranscript(semesterQCA, totalQCA, semesterYears, totalYears,
                        totalSemesters, moduleIds, moduleNames, gradeLetters, moduleCredits));

                return fullTranscript.toString(); // Final goal
            } else {
                //testing/ debugging if something goes wrong
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
                System.out.println("\nmoduleCredits: " + (moduleCredits != null));
                return "Vital information missing";
            }
        } else {
            System.out.println(
                    "(studentCSVID != null && courseCSVID != null) && (studentCSVID != courseCSVID) && LOGIN_STUDENT_ID != null");
            return "Student ID not registered";

        }
    }

    // ~~ Formatting ~~ //
    /**
     * Generates the transcript header.
     *
     * @param studentID   The student ID.
     * @param prefix      The student's prefix.
     * @param forename    The student's forename.
     * @param surname     The student's surname.
     * @param courseName  The name of the course.
     * @param courseCode  The code of the course.
     * @param courseRoute The route of the course.
     * @return The header of the transcript as a formatted string.
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
     * Generates the formatted string of each semester and its respective modules.
     * @param semesterQCA The semester's QCA.
     * @param totalQCA The average QCA.
     * @param semesterYears The string of the semester year as yyyy/yy.
     * @param totalYears The course year of the semester.
     * @param totalSemesters The accumulative number of semesters in the course.
     * @param moduleIds An arraylist of modles codes.
     * @param moduleNames An arraylist of module names.
     * @param gradeLetters An arraylist of result strings.
     * @param moduleCredits An arraylist of credits.
     * @return The full transcript string.
     */
    @FXML
    public String getFormattedTranscript(ArrayList<Double> semesterQCA, double totalQCA,
            ArrayList<String> semesterYears, ArrayList<Integer> totalYears, int totalSemesters,
            ArrayList<String> moduleIds, ArrayList<String> moduleNames,
            ArrayList<String> gradeLetters, ArrayList<Integer> moduleCredits) {
        StringBuilder transcriptBuilder = new StringBuilder();
        transcriptBuilder.append("\n").append(theLine()).append("\r\n");

        for (int rowCount = 0; rowCount < totalSemesters; rowCount++) {
            System.out.println("\nLoop Iteration: " + (rowCount + 1));
            currentSemester = rowCount + 1; // start semesters from semester 1 

            //Gets average of QCA
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
                totalQCA = -2.0;
            }

            transcriptBuilder
                    .append(semesterDetails(semesterYears.get(rowCount), totalYears.get(rowCount), currentSemester))
                    .append("\r\n");

            // Print header once per semester
            transcriptBuilder.append(blankLines()).append("\r\n");
            transcriptBuilder.append(semesterHeader_n_QCA(semesterQCA.get(rowCount), totalQCA)).append("\r\n");

            moduleIds.clear();
            moduleNames.clear();
            gradeLetters.clear();
            moduleCredits.clear();
            boolean first = true;
            for (String moduleCode : allModuleCodes.get(rowCount)) {
                if (first) {
                    first = false;
                    continue; // Skips the first iteration
                }
                if (moduleCode != null && !moduleCode.isEmpty()) { //adds ids while the array is not empty
                    moduleIds.add(moduleCode);
                } else {
                    break;
                }
            }
            System.out.println("moduleId: " + Arrays.toString(moduleIds.toArray()));

            first = true;
            for (String moduleName : allModuleNames.get(rowCount)) {
                if (first) {
                    first = false;
                    continue;
                }
                if (moduleName != null && !moduleName.isEmpty()) {
                    moduleNames.add(moduleName);
                } else {
                    break;
                }
            }
            System.out.println("moduleNames: " + Arrays.toString(moduleNames.toArray()));

            first = true;
            for (String grade : allModuleGrades.get(rowCount)) {
                if (first) {
                    first = false;
                    continue;
                }
                if (grade != null && !grade.isEmpty()) {
                    gradeLetters.add(grade);
                } else {
                    break;
                }
            }
            System.out.println("gradeLetters: " + gradeLetters);

            first = true;
            for (String credit : allModuleCredits.get(rowCount)) {
                if (first) {
                    first = false;
                    continue;
                }
                if (credit != null && !credit.isEmpty()) {
                    moduleCredits.add(Integer.parseInt(credit));
                } else {
                    break;
                }
            }
            System.out.println("moduleCredits: " + moduleCredits);

            transcriptBuilder.append(moduleInfo(moduleIds, //in the moduleInfo it loops for the amount of modules there are, displaying the module info 
                    moduleNames,
                    gradeLetters,
                    moduleCredits)).append("\r\n");

            transcriptBuilder.append(theLine()).append("\r\n");
        }

        System.out.println(transcriptBuilder.toString());
        return transcriptBuilder.toString();
    }

    /**
     * The line for seperation for the transcript.
     * @return a string of "-"s for transcript.
     */
    @FXML
    private String theLine() {
        return "+--------------------------------------------------------------------------------------------+---------------------------+";
    }

    /**
     * Formatting each semester's header 
     * @param semesterYears The string of the semester year as yyyy/yy.
     * @param year The course year of the semester.
     * @param totalSemesters The accumulative number of semesters in the course.
     * @return a formatted string for each semester.
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
     * Formats strings containing the information of all modules of a semester.
     * @param moduleIds An arraylist of modles codes.
     * @param moduleNames An arraylist of module names.
     * @param gradeLetters An arraylist of result strings.
     * @param moduleCredits An arraylist of credits.
     * @return formatted string for a semesters information about the modules.
     */
    @FXML
    private static String moduleInfo(ArrayList<String> moduleId, ArrayList<String> moduleName,
            ArrayList<String> gradeLetters,
            ArrayList<Integer> moduleCredits) {
        StringBuilder moduleBuilder = new StringBuilder();
        System.out.println("\nModuleId size: "+ moduleId.size());

        for (int i = 0; i < moduleId.size(); i++) {
            String formattedString = String.format("|%s%-" + Math.max(13, moduleId.get(i).length()) + "s%-"
                    + Math.max(50, moduleName.get(i).length()) + "s %-10s %-"
                    + Math.max(9, gradeLetters.get(i).length()) + "s %-"
                    + Math.max(6, String.valueOf(moduleCredits.get(i)).length()) + "s |%27s|",
                    "", moduleId.get(i), moduleName.get(i), "", gradeLetters.get(i), moduleCredits.get(i), "");
            moduleBuilder.append(formattedString).append("\n");
        }
        moduleBuilder.append(blankLines());

        return moduleBuilder.toString();
    }

    /**
     * Formats a string for QCA informatin and headers foe module information.
     * @param semesterQCA The semester's QCA.
     * @param totalQCA The average QCA.
     * @return a string with headings and QCA information for a semester.
     */
    @FXML
    public static String semesterHeader_n_QCA(double semesterQCA, double totalQCA) {
        String formatString = "|%-12s %-60s %-8s %-9s|%-5s%10.2f%9.2f   |";
        return String.format(formatString, "Module", "Title", "Grade", "Credits", "QCA", semesterQCA, totalQCA);
    }

    /**
     * Formats a blank line to seperate information in each semester.
     * @return a formatted blank line.
     */
    @FXML
    private static String blankLines() {
        return String.format("|%-91s | %-25s |", "", "");
    }
}