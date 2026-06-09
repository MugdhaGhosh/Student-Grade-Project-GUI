import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ReportCardScreen extends JFrame {

    public ReportCardScreen(User student, String targetSemester, JFrame parentWindow, ArrayList<User> allUsers) {
        setTitle("Academic Report Card");
        setSize(1000, 650); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 15));
        root.setBackground(new Color(245, 246, 248));
        JPanel topBanner = new JPanel(new BorderLayout());
        topBanner.setBackground(new Color(21, 101, 192));
        topBanner.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));
        JLabel titleLbl = new JLabel("Official Performance Review");
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLbl.setForeground(Color.WHITE);
        topBanner.add(titleLbl, BorderLayout.WEST);
        root.add(topBanner, BorderLayout.NORTH);

        JPanel mainCard = new JPanel(new BorderLayout(0, 15));
        mainCard.setBackground(Color.WHITE);
        mainCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 25, 10, 25),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            )
        ));

        Semester semData = student.getOrCreateSemester(targetSemester);
        int totalCredits = 0;
        int totalMarksEarned = 0; 
        int totalPassedUnits = 0;
        int totalFailedUnits = 0;
        double dynamicWeightedGPAPoints = 0;

        for (int i = 0; i < semData.subjectNames.size(); i++) {
            int score = semData.subjectMarks.get(i);
            int units = semData.creditHours.get(i);
            
            totalCredits += units;
            totalMarksEarned += score; 
            dynamicWeightedGPAPoints += (calculateGradePoints(score) * units);
            
            if (score >= 40) {
                totalPassedUnits++;
            } else {
                totalFailedUnits++;
            }
        }

        int totalCoursesCount = semData.subjectNames.size();
        double averageMark = totalCoursesCount == 0 ? 0 : (double) totalMarksEarned / totalCoursesCount;
        String averagePassFailStatus = (averageMark >= 40.0) ? "PASSING AVERAGE" : "FAILING AVERAGE";
        double totalPercentage = totalCoursesCount == 0 ? 0 : ((double) totalMarksEarned / (totalCoursesCount * 100)) * 100;
        double finalCalculatedCGPA = totalCredits == 0 ? 0 : dynamicWeightedGPAPoints / totalCredits;
        
        String standingClass = getAcademicClassStanding(finalCalculatedCGPA);
        String finalPassFailStatus = (totalFailedUnits == 0) ? "ALL PASSED" : "FAILED UNITS EXIST";
        JPanel statsGrid = new JPanel(new GridLayout(4, 2, 0, 8)); 
        statsGrid.setBackground(Color.WHITE);
        statsGrid.add(new JLabel("Student: " + student.firstName + " " + student.lastName + " (@" + student.username + ")"));
        statsGrid.add(new JLabel("Target Semester: " + targetSemester.replaceAll("[\\D]", "")));
        statsGrid.add(new JLabel("Total Earned Credits: " + totalCredits + " Hours"));
        statsGrid.add(new JLabel("Calculated Total Marks: " + totalMarksEarned + " Points")); 
        statsGrid.add(new JLabel(String.format("Calculated Average Mark: %.2f / 100.0", averageMark)));
        statsGrid.add(new JLabel("Overall Average Pass/Fail: " + averagePassFailStatus));
        statsGrid.add(new JLabel(String.format("Total Cumulative Percentage: %.2f%%", totalPercentage)));
        statsGrid.add(new JLabel("Passed Units: " + totalPassedUnits + "  |  Failed Units: " + totalFailedUnits + "  (" + finalPassFailStatus + ")"));
        
        statsGrid.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240,240,240)),
            BorderFactory.createEmptyBorder(0, 0, 15, 0)
        ));
        mainCard.add(statsGrid, BorderLayout.NORTH);
        String[] columns = {"Course Title", "Credits", "Obtained Marks", "Subject Percentage", "Grade Point", "Letter", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable resultsTable = new JTable(model);
        resultsTable.setRowHeight(30);
        resultsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        resultsTable.getTableHeader().setBackground(new Color(250, 251, 252));

        for (int i = 0; i < semData.subjectNames.size(); i++) {
            int marks = semData.subjectMarks.get(i);
            double subjectPercentage = (marks / 100.0) * 100.0;
            String subjectStatus = (marks >= 40) ? "PASS" : "FAIL";

            model.addRow(new Object[]{
                semData.subjectNames.get(i),
                semData.creditHours.get(i),
                marks,
                String.format("%.1f%%", subjectPercentage),
                String.format("%.2f", calculateGradePoints(marks)),
                calculateLetterGrade(marks),
                subjectStatus
            });
        }

        JScrollPane tableScroll = new JScrollPane(resultsTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(230, 235, 240)));
        mainCard.add(tableScroll, BorderLayout.CENTER);
        JPanel summaryFooter = new JPanel(new BorderLayout());
        summaryFooter.setBackground(Color.WHITE);
        summaryFooter.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JPanel badgeContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        badgeContainer.setOpaque(false);

        JLabel gpaLabel = new JLabel(String.format("CGPA: %.2f", finalCalculatedCGPA));
        gpaLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        gpaLabel.setForeground(new Color(21, 101, 192));

        JLabel statusBadge = new JLabel(standingClass);
        statusBadge.setFont(new Font("SansSerif", Font.BOLD, 13));
        statusBadge.setForeground(new Color(46, 125, 50));
        statusBadge.setBackground(new Color(232, 245, 233));
        statusBadge.setOpaque(true);
        statusBadge.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

        badgeContainer.add(gpaLabel);
        badgeContainer.add(statusBadge);
        summaryFooter.add(badgeContainer, BorderLayout.CENTER);
        JButton backBtn = new JButton("← Back to Entry Dashboard");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        backBtn.setBackground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> {
            dispose();
            parentWindow.setVisible(true);
        });
        
        JPanel backBtnWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backBtnWrapper.setOpaque(false);
        backBtnWrapper.add(backBtn);
        summaryFooter.add(backBtnWrapper, BorderLayout.SOUTH);

        mainCard.add(summaryFooter, BorderLayout.SOUTH);
        root.add(mainCard, BorderLayout.CENTER);

        setContentPane(root);
    }

    private String calculateLetterGrade(int score) {
        if (score >= 80) return "A+";
        if (score >= 75) return "A";
        if (score >= 70) return "A-";
        if (score >= 65) return "B+";
        if (score >= 60) return "B";
        if (score >= 55) return "B-";
        if (score >= 50) return "C+";
        if (score >= 45) return "C";
        if (score >= 40) return "D";
        if (score < 40) return "F";
        return "Invalid Marks";
    }

    private double calculateGradePoints(int score) {
        if (score >= 80) return 4.00;
        if (score >= 75) return 3.75;
        if (score >= 70) return 3.50;
        if (score >= 65) return 3.25;
        if (score >= 60) return 3.00;
        if (score >= 55) return 2.75;
        if (score >= 50) return 2.50;
        if (score >= 45) return 2.25;
        if (score >= 40) return 2.00;
        if (score < 40) return 0.00;
        return 0.00; 
    }

    private String getAcademicClassStanding(double cgpa) {
        if (cgpa >= 3.75) return "First Class";
        if (cgpa >= 3.00) return "Second Class Upper";
        if (cgpa >= 2.50) return "Second Class Lower";
        if (cgpa >= 2.00) return "Third Class";
        return "Fail / Academic Probation";
    }
}