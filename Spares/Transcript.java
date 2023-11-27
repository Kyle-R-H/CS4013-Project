import java.time.LocalDate;
import java.util.ArrayList;

public class Transcript {
//prefix = if null == NA
    public void transcriptHeader(int id, String prefix, String forename, String surname, String course, String courseCode, String route){
        System.out.println( 
            theLine()+ "\n" +
            String.format("|%48s %s %48s|", "","University of Limerick", "") +"\r\n" +
            String.format("|%119s %s|", "", "")+ "\r\n" +
            String.format("|%s %-40s %s %-30s %-"+ Math.max(8, String.valueOf(id).length()) +"s %-9s|", LocalDate.now(), "", "Student Transcript", "", id, "")   + "\r\n" +
            String.format("|%119s %s|", "", "")+ "\r\n" +
            theLine() +"\r\n" +
            String.format("|%-12s %-"+ Math.max(6, String.valueOf(prefix).length()) +"s %-"+ Math.max(20, String.valueOf(forename).length()) +"s %-" + Math.max(20, String.valueOf(surname).length()) + "s %58s|" ,"Name", prefix, forename, surname, "") + "\r\n" +
            String.format("|%-12s %-" + Math.max(107, String.valueOf(course).length()) +"s|", "Course", course)+ "\r\n" +
            String.format("|%-12s %-" + Math.max(107, String.valueOf(courseCode).length()) +"s|", "Course Code", courseCode)+ "\r\n" +
            String.format("|%-12s %-"+ Math.max(107, String.valueOf(route).length()) +"s|","Route", route)

        );
    }
    public void printFormattedTranscript(double semesterQCA, double totalQCA, String courseYearString, int year, int totalSemesters, ArrayList<String> moduleIds, ArrayList<String> moduleNames, ArrayList<Character> registrationTypes, ArrayList<String> gradeLetters, ArrayList<Integer> credits) {
        System.out.println(
            theLine() + "\r\n" +
            semesterDetails(courseYearString, year, totalSemesters) + "\r\n" +
            blankLines() + "\r\n" +
            semesterHeader_n_QCA(semesterQCA, totalQCA) + "\r\n" +
            blankLines());
        for (int i = 0; i < moduleIds.size(); i++) {
            System.out.print(moduleInfo(moduleIds.get(i), moduleNames.get(i), registrationTypes.get(i), gradeLetters.get(i), credits.get(i)) + "\r\n");
        }
        System.out.println(blankLines() + "\r\n" + theLine());
    }
    
    private String theLine(){
        return "+--------------------------------------------------------------------------------------------+---------------------------+";
    }

    private static String semesterDetails(String courseYearString, int year, int totalSemesters) {
        String yearString = "Year " + year;
        String totalSemestersString = "Sem " + totalSemesters;
        String formatString = "|%-30s %-" + Math.max(18, String.valueOf(year).length()) + "s %-" + Math.max(6, String.valueOf(totalSemesters).length()) + "s %-34s |%-5s %s %-5s|";
        return String.format(formatString, courseYearString, yearString ,totalSemestersString,"", "", "Session To-Date", "");
    }
    
    private static String moduleInfo(String moduleId, String moduleName, char registrationType, String gradeLetter, int credits){
        String formaString = "|%s%-"+ Math.max(13, String.valueOf(moduleId).length()) +"s%-"+ Math.max(50, String.valueOf(moduleName).length()) +"s %s %-8s %-"+ Math.max(9, String.valueOf(gradeLetter).length()) +"s %-"+ Math.max(6, String.valueOf(credits).length()) +"s |%27s|";
        return String.format(formaString,  "", moduleId, moduleName, registrationType, "", gradeLetter, credits, "");
    }
    
    private static String blankLines() {
        return String.format("|%-91s | %-25s |", "","");
    }

    public static String semesterHeader_n_QCA(double semesterQCA, double totalQCA) {
        String formatString = "|%-12s %-50s %-9s %-8s %-9s|%-5s%10.2f%9.2f   |";
        return String.format(formatString, "Module", "Title", "Regn", "Grade", "Credits", "QCA", semesterQCA, totalQCA);
    }



        public static void main(String[] args) {
            //^Inputs
                    //!QCA calculator time!
        double semesterQCA = 0.0; 
        double totalQCA = 0.0;
        
        int totalSemesters = 8;
        int year = -1;
        String courseYearString = "2023/24";
        int studentID = 22343261;

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

        moduleIds.add("CS4012");
        moduleNames.add("REPRESENTATION AND MODELLING");
        registrationTypes.add('N');
        gradeLetters.add("B1");
        credits.add(6);

        moduleIds.add("CS4141");
        moduleNames.add("INTRODUCTION TO PROGRAMMING");
        registrationTypes.add('N');
        gradeLetters.add("A2");
        credits.add(6);

        moduleIds.add("CS4221");
        moduleNames.add("FOUNDATIONS OF COMPUTER SCIENCE 1");
        registrationTypes.add('N');
        gradeLetters.add("A2");
        credits.add(6);

        moduleIds.add("ET4011");
        moduleNames.add("FUNDAMENTALS OF COMPUTER ORGANISATION");
        registrationTypes.add('N');
        gradeLetters.add("A1");
        credits.add(6);

        moduleIds.add("MS4111");
        moduleNames.add("DISCRETE MATHEMATICS 1");
        registrationTypes.add('N');
        gradeLetters.add("C1");
        credits.add(6);

        Transcript transcript = new Transcript();
        transcript.transcriptHeader(studentID, "Mr", "Ky3987weuhle", "Helwiwfuhiflstrom", "Computer Science","LM121", "CS");
        transcript.printFormattedTranscript(semesterQCA, totalQCA, courseYearString, year, totalSemesters, moduleIds, moduleNames, registrationTypes, gradeLetters, credits);
        transcript.printFormattedTranscript(semesterQCA, totalQCA, courseYearString, year, totalSemesters, moduleIds, moduleNames, registrationTypes, gradeLetters, credits);
    }
}
