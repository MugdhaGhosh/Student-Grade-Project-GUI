import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RegisterScreen extends JFrame {

    private final ArrayList<User> users;
    private JTextField firstField, lastField, userField;
    private JPasswordField passField, confirmField;
    private JLabel msgLabel;
    private JPanel strengthBar;

    public RegisterScreen(ArrayList<User> users) {
        this.users = users;
        setTitle("Create Account");
        setSize(460, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_PAGE);
        JPanel topPanel = new JPanel();
        topPanel.setBackground(UITheme.BG_PAGE);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(28, 0, 16, 0));

        JLabel icon = UITheme.avatar("+", UITheme.GREEN_50, UITheme.GREEN_600, 56);
        icon.setFont(new Font("SansSerif", Font.BOLD, 22));
        icon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel title = new JLabel("Create account");
        title.setFont(UITheme.F_TITLE);
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Fill in your details to register");
        subtitle.setFont(UITheme.F_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);

        topPanel.add(icon);
        topPanel.add(UITheme.vGap(10));
        topPanel.add(title);
        topPanel.add(UITheme.vGap(4));
        topPanel.add(subtitle);
        JPanel card = UITheme.card(null);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(24, 30, 24, 30));
        JPanel nameRow = new JPanel(new GridLayout(1, 2, 10, 0));
        nameRow.setOpaque(false);
        nameRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JPanel firstWrap = new JPanel(new BorderLayout(0, 4));
        firstWrap.setOpaque(false);
        firstWrap.add(UITheme.fieldLabel("First name"), BorderLayout.NORTH);
        firstField = UITheme.textField("First name");
        firstWrap.add(firstField, BorderLayout.CENTER);

        JPanel lastWrap = new JPanel(new BorderLayout(0, 4));
        lastWrap.setOpaque(false);
        lastWrap.add(UITheme.fieldLabel("Last name"), BorderLayout.NORTH);
        lastField = UITheme.textField("Last name");
        lastWrap.add(lastField, BorderLayout.CENTER);

        nameRow.add(firstWrap);
        nameRow.add(lastWrap);
        card.add(nameRow);
        card.add(UITheme.vGap(12));
        card.add(UITheme.fieldLabel("Username"));
        card.add(UITheme.vGap(5));
        userField = UITheme.textField("Choose a username");
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        card.add(userField);
        card.add(UITheme.vGap(12));
        card.add(UITheme.fieldLabel("Password"));
        card.add(UITheme.vGap(5));
        passField = UITheme.passField("");
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        card.add(passField);
        card.add(UITheme.vGap(5));
        strengthBar = new JPanel(new GridLayout(1, 4, 4, 0));
        strengthBar.setOpaque(false);
        strengthBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
        for (int i = 0; i < 4; i++) {
            JPanel seg = new JPanel();
            seg.setBackground(UITheme.BORDER);
            seg.setPreferredSize(new Dimension(0, 4));
            strengthBar.add(seg);
        }
        card.add(strengthBar);
        card.add(UITheme.vGap(12));
        card.add(UITheme.fieldLabel("Confirm password"));
        card.add(UITheme.vGap(5));
        confirmField = UITheme.passField("");
        confirmField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        card.add(confirmField);
        card.add(UITheme.vGap(8));
        msgLabel = new JLabel(" ");
        msgLabel.setFont(UITheme.F_SMALL);
        msgLabel.setForeground(UITheme.RED_800);
        card.add(msgLabel);
        card.add(UITheme.vGap(12));
        JButton regBtn = UITheme.primaryBtn("Create account", UITheme.GREEN_600);
        regBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        regBtn.setAlignmentX(CENTER_ALIGNMENT);
        card.add(regBtn);

        card.add(UITheme.vGap(12));
        JPanel linkRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        linkRow.setOpaque(false);
        linkRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        JLabel linkLbl = new JLabel("Already have an account?");
        linkLbl.setFont(UITheme.F_SMALL);
        linkLbl.setForeground(UITheme.TEXT_MUTED);
        JButton loginLink = new JButton("Sign in");
        loginLink.setFont(new Font("SansSerif", Font.BOLD, 12));
        loginLink.setForeground(UITheme.BLUE_600);
        loginLink.setBorderPainted(false);
        loginLink.setContentAreaFilled(false);
        loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLink.setFocusPainted(false);
        linkRow.add(linkLbl);
        linkRow.add(loginLink);
        card.add(linkRow);

        JPanel cardWrap = new JPanel(new BorderLayout());
        cardWrap.setBackground(UITheme.BG_PAGE);
        cardWrap.setBorder(BorderFactory.createEmptyBorder(0, 28, 28, 28));
        cardWrap.add(card, BorderLayout.CENTER);

        root.add(topPanel, BorderLayout.NORTH);
        root.add(cardWrap, BorderLayout.CENTER);
        setContentPane(root);
        passField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void update() { 
                updateStrength(new String(passField.getPassword())); 
            }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { update(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { update(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });
        regBtn.addActionListener(e -> handleRegister());
        loginLink.addActionListener(e -> {
            dispose();
            new LoginScreen(users).setVisible(true);
        });
    }

    private void handleRegister() {
        String first   = firstField.getText().trim();
        String last    = lastField.getText().trim();
        String user    = userField.getText().trim();
        String pass    = new String(passField.getPassword());
        String confirm = new String(confirmField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            showMsg("Username and password are required.", false); return;
        }
        if (pass.length() < 4) {
            showMsg("Password must be at least 4 characters.", false); return;
        }
        if (!pass.equals(confirm)) {
            showMsg("Passwords do not match.", false); return;
        }
        for (User u : users) {
            if (u.username.equalsIgnoreCase(user)) {
                showMsg("Username already taken. Try another.", false); return;
            }
        }
        
        users.add(new User(user, pass, first, last));

        showMsg("Account created! Signing you in…", true);

        Timer t = new Timer(1200, e -> {
            dispose();
            new DashboardScreen(users.get(users.size()-1), users).setVisible(true);
        });
        t.setRepeats(false);
        t.start();
    }

    private void showMsg(String text, boolean ok) {
        msgLabel.setText(text);
        msgLabel.setForeground(ok ? UITheme.GREEN_800 : UITheme.RED_800);
    }

    private void updateStrength(String pass) {
        int score = 0;
        if (pass.length() >= 4) score++;
        if (pass.length() >= 8) score++;
        if (pass.matches(".*[A-Z].*") || pass.matches(".*\\d.*")) score++;
        if (pass.matches(".*[^a-zA-Z0-9].*")) score++;

        Color[] colors = {
            new Color(0xE2, 0x4B, 0x4A),  
            new Color(0xBA, 0x75, 0x17), 
            UITheme.GREEN_600,            
            UITheme.GREEN_600             
        };
        Component[] segs = strengthBar.getComponents();
        for (int i = 0; i < segs.length; i++) {
            ((JPanel) segs[i]).setBackground(i < score ? colors[score - 1] : UITheme.BORDER);
        }
        strengthBar.repaint();
    }
}