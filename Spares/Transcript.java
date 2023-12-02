import java.time.LocalDate;
import java.util.ArrayList;

public class Transcript {
    // prefix = if null == NA
    /**
     * 
     * @param studentID
     * @param prefix
     * @param forename
     * @param surname
     * @param courseName
     * @param courseCode
     * @param courseRoute
     * @return
     */
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

    public String getFormattedTranscript(int forLoop, ArrayList<Double> semesterQCA, double totalQCA,
            ArrayList<String> semesterYears, ArrayList<Integer> totalYears, int totalSemesters,
            ArrayList<String> moduleIds, ArrayList<String> moduleNames,
            ArrayList<String> gradeLetters, ArrayList<Integer> credits) {
        StringBuilder transcriptBuilder = new StringBuilder();
        transcriptBuilder.append(theLine()).append("\r\n");
        if () {
            transcriptBuilder.append(semesterDetails(semesterYears.get(j), totalYears.get(j), totalSemesters))
                    .append("\r\n");
        }

        transcriptBuilder.append(blankLines()).append("\r\n");

        // semesterQCA = ArrayList<Double //TODO maybe add onto this to make
                                                   // 1 arraylist with all info needed per section
        if (semesterQCA.size() > 0) {
            transcriptBuilder.append(semesterHeader_n_QCA(semesterQCA.get(i), totalQCA)).append("\r\n");
        }


        transcriptBuilder.append(blankLines());

        for (int j = 0; j < allModuleInfo.size(); j++) { //TODO grades
            String[] moduleIDs = allModuleInfo.get(j);
            String[] moduleName = allModuleInfo.get(j+8);
            String[] moduleCredits = allModuleInfo.get(j+16);

            if (moduleIds.size() > 0) {
                transcriptBuilder.append(moduleInfo(moduleIds.get(moduleIDs[forLoop]), moduleNames.get(moduleName[forLoop]),
                        gradeLetters.get(i), credits.get(moduleCredits[forLoop]))).append("\r\n");
            }
        }


        transcriptBuilder.append(blankLines()).append("\r\n").append(theLine());
        return transcriptBuilder.toString();
    }

    private String theLine() {
        return "+--------------------------------------------------------------------------------------------+---------------------------+";
    }

    private static String semesterDetails(String semesterYears, int totalYears,
            int totalSemesters) {
        String yearString = "Year " + totalYears;
        String totalSemestersString = "Sem " + totalSemesters;
        String formatString = "|%-30s %-" + Math.max(18, String.valueOf(totalYears).length()) + "s %-"
                + Math.max(6, String.valueOf(totalSemesters).length()) + "s %-34s |%-5s %s %-5s|";
        return String.format(formatString, semesterYears, yearString, totalSemestersString, "", "", "Session To-Date",
                "");
    }

    private static String moduleInfo(String moduleId, String moduleName, String gradeLetter,
            int credits) {
        String formaString = "|%s%-" + Math.max(13, String.valueOf(moduleId).length()) + "s%-"
                + Math.max(50, String.valueOf(moduleName).length()) + "s %-10s %-"
                + Math.max(9, String.valueOf(gradeLetter).length()) + "s %-"
                + Math.max(6, String.valueOf(credits).length()) + "s |%27s|";
        return String.format(formaString, "", moduleId, moduleName, "", gradeLetter, credits, "");
    }

    private static String blankLines() {
        return String.format("|%-91s | %-25s |", "", "");
    }

    public static String semesterHeader_n_QCA(double semesterQCA, double totalQCA) {
        String formatString = "|%-12s %-60s %-8s %-9s|%-5s%10.2f%9.2f   |";
        return String.format(formatString, "Module", "Title", "Grade", "Credits", "QCA", semesterQCA, totalQCA);
    }

    public static void main(String[] args) {
        // ^Inputs
        int studentID = 22343261;
        String prefix = "mr";
        String forename = "Kyle";
        String surname = "Hellstrom";
        String courseName = "Computer systems";
        String courseCode = "LM121";
        String courseRoute = "CS";
        double semesterQCA = 0.0;
        double totalQCA = 0.0;

        int totalSemesters = 8;
        int year = -1;
        String courseYearString = "2023/24";

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

        ArrayList<String> moduleIds = new ArrayList<>();
        ArrayList<String> moduleNames = new ArrayList<>();
        ArrayList<String> gradeLetters = new ArrayList<>();
        ArrayList<Integer> credits = new ArrayList<>();



        //number of modules in semester

        Transcript transcript = new Transcript();
        transcript.transcriptHeader(studentID, prefix, forename, surname, courseName, courseCode,
                courseRoute);
        for (int forLoop = 1; forLoop < allModuleInfo.length; forLoop++) {
            transcript.getFormattedTranscript(forLoop, semesterQCA, totalQCA, courseYearString, year, totalSemesters, moduleIds,
                    moduleNames, gradeLetters, credits);
        }
    }
}
