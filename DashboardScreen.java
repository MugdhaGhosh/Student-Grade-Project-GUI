import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DashboardScreen extends JFrame {
   
    private final User currentUser;
    private final ArrayList<User> allUsers;

    private JTextField nameField;
    private JSpinner semesterSpinner;
    private JSpinner courseCountSpinner;
    private JPanel coursesContainer;
    private JScrollPane scrollPane;
    private ArrayList<JTextField> nameFieldsList = new ArrayList<>();
    private ArrayList<JSpinner> marksSpinnersList = new ArrayList<>();
    private ArrayList<JSpinner> creditsSpinnersList = new ArrayList<>();

    public DashboardScreen(User user, ArrayList<User> allUsers) {
        this.currentUser = user;
        this.allUsers = allUsers;

        setTitle("Student Grade Calculator - Dashboard");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 15));
        root.setBackground(Color.WHITE);
        JPanel headerBanner = new JPanel(new BorderLayout());
        headerBanner.setBackground(new Color(21, 101, 192));
        headerBanner.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        userPanel.setOpaque(false);
        JLabel avatar = UITheme.avatar(user.firstName.substring(0,1).toUpperCase() + user.lastName.substring(0,1).toUpperCase(), 
                                      new Color(255,255,255,60), Color.WHITE, 40);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textPanel.setOpaque(false);
        JLabel titleLbl = new JLabel("Student Result System");
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLbl.setForeground(Color.WHITE);
        JLabel subtitleLbl = new JLabel("Logged in as " + user.username);
        subtitleLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitleLbl.setForeground(new Color(200, 220, 245));
        textPanel.add(titleLbl);
        textPanel.add(subtitleLbl);
        
        userPanel.add(avatar);
        userPanel.add(textPanel);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(255, 255, 255, 30));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        logoutBtn.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,100), 1));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setOpaque(true);
        logoutBtn.setPreferredSize(new Dimension(80, 32));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginScreen(allUsers).setVisible(true);
        });

        headerBanner.add(userPanel, BorderLayout.WEST);
        headerBanner.add(logoutBtn, BorderLayout.EAST);
        root.add(headerBanner, BorderLayout.NORTH);
        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setBackground(Color.WHITE);
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JPanel innerCard = new JPanel(new BorderLayout(0, 15));
        innerCard.setBackground(Color.WHITE);
        innerCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        JPanel metaBar = new JPanel(new GridLayout(1, 3, 20, 0));
        metaBar.setOpaque(false);

        JPanel p1 = createFieldWrapper("STUDENT NAME");
        nameField = new JTextField(user.firstName + " " + user.lastName);
        nameField.setEditable(false);
        nameField.setBackground(new Color(248, 249, 250));
        p1.add(nameField, BorderLayout.CENTER);

        JPanel p2 = createFieldWrapper("SEMESTER");
        semesterSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        p2.add(semesterSpinner, BorderLayout.CENTER);

        JPanel p3 = createFieldWrapper("NO. OF COURSES");
        courseCountSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
        p3.add(courseCountSpinner, BorderLayout.CENTER);

        metaBar.add(p1);
        metaBar.add(p2);
        metaBar.add(p3);
        innerCard.add(metaBar, BorderLayout.NORTH);
        coursesContainer = new JPanel();
        coursesContainer.setLayout(new BoxLayout(coursesContainer, BoxLayout.Y_AXIS));
        coursesContainer.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(coursesContainer);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)), "Course Details"));
        scrollPane.setBackground(Color.WHITE);
        innerCard.add(scrollPane, BorderLayout.CENTER);
        cardWrapper.add(innerCard, BorderLayout.CENTER);
        root.add(cardWrapper, BorderLayout.CENTER);
        JButton generateBtn = new JButton("Generate Report Card");
        generateBtn.setBackground(new Color(21, 101, 192));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        generateBtn.setPreferredSize(new Dimension(0, 45));
        generateBtn.setFocusPainted(false);
        generateBtn.setBorderPainted(false);
        
        JPanel bottomWrap = new JPanel(new BorderLayout());
        bottomWrap.setBackground(Color.WHITE);
        bottomWrap.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
        bottomWrap.add(generateBtn, BorderLayout.CENTER);
        root.add(bottomWrap, BorderLayout.SOUTH);

        setContentPane(root);
        courseCountSpinner.addChangeListener(e -> rebuildCourseInputRows());
        generateBtn.addActionListener(e -> saveAndOpenReportCard());
        rebuildCourseInputRows();
    }

    private JPanel createFieldWrapper(String labelTitle) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 4));
        wrapper.setOpaque(false);
        JLabel lbl = new JLabel(labelTitle);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(new Color(120, 130, 140));
        wrapper.add(lbl, BorderLayout.NORTH);
        return wrapper;
    }

    private void rebuildCourseInputRows() {
        coursesContainer.removeAll();
        nameFieldsList.clear();
        marksSpinnersList.clear();
        creditsSpinnersList.clear();

        int desiredCount = (int) courseCountSpinner.getValue();

        for (int i = 1; i <= desiredCount; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
            rowPanel.setOpaque(false);
            rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

            JLabel rowNum = new JLabel(String.format("%02d. Name:", i));
            rowNum.setFont(new Font("SansSerif", Font.BOLD, 13));

            JTextField cName = new JTextField("Course " + i, 12);
            JLabel marksLbl = new JLabel("Marks (0-100):");
            JSpinner marksSpin = new JSpinner(new SpinnerNumberModel(75, 0, 100, 1));
            marksSpin.setPreferredSize(new Dimension(65, 28));

            JLabel creditsLbl = new JLabel("Credits:");
            JSpinner creditsSpin = new JSpinner(new SpinnerNumberModel(3, 1, 6, 1));
            creditsSpin.setPreferredSize(new Dimension(65, 28));

            rowPanel.add(rowNum);
            rowPanel.add(cName);
            rowPanel.add(marksLbl);
            rowPanel.add(marksSpin);
            rowPanel.add(creditsLbl);
            rowPanel.add(creditsSpin);

            coursesContainer.add(rowPanel);
            nameFieldsList.add(cName);
            marksSpinnersList.add(marksSpin);
            creditsSpinnersList.add(creditsSpin);
        }

        coursesContainer.revalidate();
        coursesContainer.repaint();
    }

    private void saveAndOpenReportCard() {
        String targetSemName = "Semester " + semesterSpinner.getValue();
        Semester semData = currentUser.getOrCreateSemester(targetSemName);
        semData.subjectNames.clear();
        semData.subjectMarks.clear();
        semData.creditHours.clear();

        for (int i = 0; i < nameFieldsList.size(); i++) {
            String subName = nameFieldsList.get(i).getText().trim();
            int score = (int) marksSpinnersList.get(i).getValue();
            int units = (int) creditsSpinnersList.get(i).getValue();

            if (subName.isEmpty()) subName = "Course " + (i + 1);

            semData.subjectNames.add(subName);
            semData.subjectMarks.add(score);
            semData.creditHours.add(units);
        }
        new ReportCardScreen(currentUser, targetSemName, this, allUsers).setVisible(true);
        this.setVisible(false);
    }
}