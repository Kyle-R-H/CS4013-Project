import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class QCA {
    private static double totalPoints = 0.0;


    private static void processCSVFile(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].trim().matches("\\d")) {
                    double totalPoints = calculateTotalPoints(values);
                    // Overwrite the last entry in the row with the totalPoints
                    values[values.length - 1] = String.valueOf(totalPoints);
                }
                // Reconstruct the line with the updated or original values
                String updatedLine = String.join(",", values);
                lines.add(updatedLine);
            }
    
            // Write all lines (including those that didn't meet the condition) back to the CSV file
            Files.write(Paths.get("Courses.csv"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private static double calculateTotalPoints(String[] values) {
        double totalPoints = 0.0;
        int count = 0;
        for (String value : values) {
            // Check if the value matches your criteria
            switch (value.trim().toUpperCase()) {
                case "A1":
                    totalPoints += 4.00;
                    count++;
                    break;
                case "A2":
                    totalPoints += 3.60;
                    count++;
                    break;
                case "B1":
                    totalPoints += 3.20;
                    count++;
                    break;
                case "B2":
                    totalPoints += 3.00;
                    count++;
                    break;
                case "B3":
                    totalPoints += 2.80;
                    count++;
                    break;
                case "C1":
                    totalPoints += 2.60;
                    count++;
                    break;
                case "C2":
                    totalPoints += 2.40;
                    count++;
                    break;
                case "C3":
                    totalPoints += 2.00;
                    count++;
                    break;
                case "D1":
                    totalPoints += 1.60;
                    count++;
                    break;
                case "D2":
                    totalPoints += 1.20;
                    count++;
                    break;
                case "F":
                case "NG":
                    totalPoints += 0.00;
                    count++;
                    break;
            }
        }
        totalPoints = totalPoints/count;
        double roundedTotalPoints = roundToTwoDecimalPlaces(totalPoints);
        return roundedTotalPoints;
    }


    private static void calculateAndOverwriteAverage() {
        try (BufferedReader br = new BufferedReader(new FileReader("Courses.csv"))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].trim().matches("\\d")) {
                    // Overwrite the last entry in the row with the average
                    values[values.length - 1] = String.valueOf(calculateTotalPoints(values));
                }
                // Reconstruct the line with the updated values
                String updatedLine = String.join(",", values);
                lines.add(updatedLine);
            }
    
            // Write the updated lines back to the CSV file
            Files.write(Paths.get("Courses.csv"), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static double roundToTwoDecimalPlaces(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue = decimalFormat.format(value);
        return Double.parseDouble(formattedValue);
    }

    
    public static void main(String[] args){
        processCSVFile("Courses.csv");
        calculateAndOverwriteAverage();
    }
}