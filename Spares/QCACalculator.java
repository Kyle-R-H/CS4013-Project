// package cs4013_project;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QCACalculator {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of modules: ");
            int moduleCount = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            System.out.println("Enter grades (A1, A2, B1, B2, B3, C1, C2, C3, D1, D2, F, NG) for " + moduleCount + " modules:");
            double qca = 0;
            Map<Integer, String> deficientModules = new HashMap<>();

            for (int i = 1; i <= moduleCount; i++) {
                System.out.print("Enter grade for Module " + i + ": ");
                String grade = scanner.next().toUpperCase();

                double gradeValue = getGradeValue(grade);

                if (gradeValue == -1) {
                    System.out.println("Invalid grade entered. Please enter a valid grade.");
                    i--;
                } else {
                    qca += gradeValue;
                    if (grade.equals("F") || grade.equals("NG") || grade.equals("D1") || grade.equals("D2")) {
                        deficientModules.put(i, grade);
                    }
                }
            }

            qca = Math.round(qca * 100.0) / (moduleCount * 100.0);
            System.out.println("Your QCA is: " + qca);

            boolean passed = (qca >= 2.0);

            if (passed) {
                System.out.println("Congratulations! You passed.");
                if (!deficientModules.isEmpty()) {
                    System.out.println("You have deficient grades in the following modules for annual repeats:");
                    deficientModules.forEach((key, value) ->
                            System.out.println("Module " + key + ": " + value)
                    );
                }
            } else {
                System.out.println("QCA insufficient: \n» Take annual repeats (maximum of four modules excluding I grades). Annual repeats are capped  \r\n" + //
                        "» Repeat a full semester or take modules on a link-in basis  \r\n" +
                        "» Repeat full year");
            }
        }
    }

    public static double getGradeValue(String grade) {
        switch (grade) {
            case "A1":
                return 4.00;
            case "A2":
                return 3.60;
            case "B1":
                return 3.20;
            case "B2":
                return 3.00;
            case "B3":
                return 2.80;
            case "C1":
                return 2.60;
            case "C2":
                return 2.40;
            case "C3":
                return 2.00;
            case "D1":
                return 1.60;
            case "D2":
                return 1.20;
            case "F":
            case "NG":
                return 0.00;
            default:
                return -1; // Invalid grade
        }
    }
}