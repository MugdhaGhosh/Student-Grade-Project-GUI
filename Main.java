import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            ArrayList<User> globalUserDatabase = new ArrayList<>();
            globalUserDatabase.add(new User("admin", "admin", "Alex", "Smith"));
            LoginScreen loginWindow = new LoginScreen(globalUserDatabase);
            loginWindow.setVisible(true);
        });
    }
}