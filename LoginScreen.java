import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginScreen extends JFrame {
    private final ArrayList<User> users;
    private JTextField  userField;
    private JPasswordField passField;
    private JLabel msgLabel;

    public LoginScreen(ArrayList<User> users) {
        this.users = users;
        setTitle("Student Grade Calculator");
        setSize(440, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_PAGE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(UITheme.BG_PAGE);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(36, 0, 20, 0));

        JLabel icon = UITheme.avatar("\uD83C\uDF93", UITheme.BLUE_50, UITheme.BLUE_600, 64);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 28));
        icon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel title = new JLabel("Welcome back");
        title.setFont(UITheme.F_TITLE);
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Sign in to your account");
        subtitle.setFont(UITheme.F_BODY);
        subtitle.setForeground(UITheme.TEXT_MUTED);
        subtitle.setAlignmentX(CENTER_ALIGNMENT);

        topPanel.add(icon);
        topPanel.add(UITheme.vGap(12));
        topPanel.add(title);
        topPanel.add(UITheme.vGap(4));
        topPanel.add(subtitle);

        JPanel card = UITheme.card(null);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        card.add(UITheme.fieldLabel("Username"));
        card.add(UITheme.vGap(5));
        userField = UITheme.textField("e.g. john_doe");
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        card.add(userField);
        card.add(UITheme.vGap(14));

        card.add(UITheme.fieldLabel("Password"));
        card.add(UITheme.vGap(5));
        passField = UITheme.passField("");
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        card.add(passField);
        card.add(UITheme.vGap(8));

        msgLabel = new JLabel(" ");
        msgLabel.setFont(UITheme.F_SMALL);
        msgLabel.setForeground(UITheme.RED_800);
        msgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(msgLabel);
        card.add(UITheme.vGap(12));

        JButton loginBtn = UITheme.primaryBtn("Sign in", UITheme.BLUE_600);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(loginBtn);
        card.add(UITheme.vGap(12));

        card.add(makeDivider());
        card.add(UITheme.vGap(12));

        JButton regBtn = UITheme.ghostBtn("Create new account");
        regBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        regBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(regBtn);

        JPanel cardWrap = new JPanel(new BorderLayout());
        cardWrap.setBackground(UITheme.BG_PAGE);
        cardWrap.setBorder(BorderFactory.createEmptyBorder(0, 32, 32, 32));
        cardWrap.add(card, BorderLayout.CENTER);

        root.add(topPanel, BorderLayout.NORTH);
        root.add(cardWrap, BorderLayout.CENTER);
        setContentPane(root);

        ActionListener doLogin = e -> handleLogin();
        loginBtn.addActionListener(doLogin);
        passField.addActionListener(doLogin);

        regBtn.addActionListener(e -> {
            dispose();
            new RegisterScreen(users).setVisible(true);
        });
    }

    private void handleLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            showMsg("Please fill in all fields.", false);
            return;
        }
        for (User u : users) {
            if (u.username.equals(user) && u.password.equals(pass)) {
                dispose();
                new DashboardScreen(u, users).setVisible(true);
                return;
            }
        }
        showMsg("Invalid username or password.", false);
        passField.setText("");
    }

    private void showMsg(String text, boolean ok) {
        msgLabel.setText(text);
        msgLabel.setForeground(ok ? UITheme.GREEN_800 : UITheme.RED_800);
    }

    private JPanel makeDivider() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        JSeparator left  = new JSeparator(); left.setForeground(UITheme.BORDER);
        JSeparator right = new JSeparator(); right.setForeground(UITheme.BORDER);
        JLabel lbl = new JLabel("  or  ");
        lbl.setFont(UITheme.F_SMALL); lbl.setForeground(UITheme.TEXT_HINT);
        p.add(left, BorderLayout.WEST);
        p.add(lbl, BorderLayout.CENTER);
        p.add(right, BorderLayout.EAST);
        left.setPreferredSize(new Dimension(120, 1));
        right.setPreferredSize(new Dimension(120, 1));
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            
            // Seed sample database for effortless debugging
            ArrayList<User> testDb = new ArrayList<>();
            testDb.add(new User("admin", "admin", "Alex", "Smith"));
            
            new LoginScreen(testDb).setVisible(true);
        });
    }
}