import java.util.ArrayList;

public class GradeCalculatorEngine {

    public String getGrade(int marks) {
        if (marks >= 80) return "A+";
        if (marks >= 75) return "A";
        if (marks >= 70) return "A-";
        if (marks >= 65) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 55) return "B-";
        if (marks >= 50) return "C+";
        if (marks >= 45) return "C";
        if (marks >= 40) return "D";
        if (marks < 40 ) return "F";
            return "Invalid Marks";
    }

    public double getGradePoint(int marks) {
        if (marks >= 80) return 4.00;
        if (marks >= 75) return 3.75;
        if (marks >= 70) return 3.50;
        if (marks >= 65) return 3.25;
        if (marks >= 60) return 3.00;
        if (marks >= 55) return 2.75;
        if (marks >= 50) return 2.50;
        if (marks >= 45) return 2.25;
        if (marks >= 40) return 2.00;
        if (marks < 40) return 0.00;
        return -1; 
    }

    public String getDivision(double percentage) {
        if (percentage >= 70) return "First Class";
        if (percentage >= 60) return "Upper Second Class";
        if (percentage >= 50) return "Lower Second Class";
        if (percentage >= 40) return "Third Class";
        return "Fail";
    }

    public double semesterGPA(ArrayList<Integer> marksList, ArrayList<Integer> creditsList) {
        double totalGP = 0;
        int totalCredits = 0;
        for (int i = 0; i < marksList.size(); i++) {
            totalGP      += getGradePoint(marksList.get(i)) * creditsList.get(i);
            totalCredits += creditsList.get(i);
        }
        return totalCredits == 0 ? 0 : totalGP / totalCredits;
    }
}