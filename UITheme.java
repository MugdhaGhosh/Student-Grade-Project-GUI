import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UITheme {
    public static final Color BLUE_600    = new Color(0x18, 0x5F, 0xA5);
    public static final Color BLUE_50     = new Color(0xE6, 0xF1, 0xFB);
    public static final Color GREEN_600   = new Color(0x0F, 0x6E, 0x56);
    public static final Color GREEN_50    = new Color(0xE1, 0xF5, 0xEE);
    public static final Color GREEN_800   = new Color(0x08, 0x50, 0x41);
    public static final Color RED_50      = new Color(0xFC, 0xEB, 0xEB);
    public static final Color RED_800     = new Color(0x79, 0x1F, 0x1F);
    public static final Color AMBER_50    = new Color(0xFA, 0xEE, 0xDA);
    public static final Color AMBER_800   = new Color(0x63, 0x38, 0x06);
    public static final Color BG_PAGE     = new Color(0xF4, 0xF5, 0xF7);
    public static final Color BG_CARD     = Color.WHITE;
    public static final Color BG_SURFACE  = new Color(0xF1, 0xF3, 0xF5);
    public static final Color BORDER      = new Color(0xD0, 0xD3, 0xD8);
    public static final Color TEXT_PRIMARY = new Color(0x1A, 0x1A, 0x2E);
    public static final Color TEXT_MUTED  = new Color(0x6B, 0x70, 0x80);
    public static final Color TEXT_HINT   = new Color(0xA0, 0xA5, 0xB5);
    public static final Color WHITE       = Color.WHITE;

    public static final Font F_TITLE  = new Font("SansSerif", Font.BOLD, 18);
    public static final Font F_H2     = new Font("SansSerif", Font.BOLD, 15);
    public static final Font F_H3     = new Font("SansSerif", Font.BOLD, 13);
    public static final Font F_LABEL  = new Font("SansSerif", Font.BOLD, 11);
    public static final Font F_BODY   = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font F_SMALL  = new Font("SansSerif", Font.PLAIN, 12);

    public static JPanel card(LayoutManager lm) {
        JPanel p = new JPanel(lm) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(BORDER);
                g2.setStroke(new BasicStroke(0.8f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        return p;
    }

    public static JTextField textField(String placeholder) {
        JTextField f = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(TEXT_HINT);
                    g2.setFont(F_BODY);
                    g2.drawString(placeholder, 8, getHeight()/2 + 5);
                    g2.dispose();
                }
            }
        };
        styleField(f);
        return f;
    }

    public static JPasswordField passField(String placeholder) {
        JPasswordField f = new JPasswordField();
        styleField(f);
        return f;
    }

    private static void styleField(JComponent f) {
        f.setFont(F_BODY);
        f.setForeground(TEXT_PRIMARY);
        f.setBackground(BG_CARD);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        f.setPreferredSize(new Dimension(0, 38));
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BLUE_600, 1, true),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
            }
            public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 1, true),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
            }
        });
    }

    public static JSpinner spinner(int val, int min, int max) {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(val, min, max, 1));
        sp.setFont(F_BODY);
        sp.setPreferredSize(new Dimension(80, 38));
        JComponent editor = sp.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setFont(F_BODY);
            ((JSpinner.DefaultEditor) editor).getTextField().setHorizontalAlignment(JTextField.CENTER);
        }
        return sp;
    }

    public static JButton primaryBtn(String text, Color bg) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.darker() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setForeground(WHITE);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(false);
        return b;
    }

    public static JButton ghostBtn(String text) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? BG_SURFACE : BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDER);
                g2.setStroke(new BasicStroke(0.8f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(F_BODY);
        b.setForeground(TEXT_PRIMARY);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(9, 20, 9, 20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JLabel badge(String text, Color bg, Color fg) {
        JLabel l = new JLabel(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(fg);
        l.setOpaque(false);
        l.setBorder(BorderFactory.createEmptyBorder(3, 9, 3, 9));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }

    public static JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text.toUpperCase());
        l.setFont(F_LABEL);
        l.setForeground(TEXT_MUTED);
        return l;
    }

    public static JPanel headerBar() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(BLUE_600);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        return p;
    }

    public static JLabel avatar(String initials, Color bg, Color fg, int size) {
        JLabel l = new JLabel(initials) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        l.setFont(new Font("SansSerif", Font.BOLD, size/3));
        l.setForeground(fg);
        l.setOpaque(false);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setPreferredSize(new Dimension(size, size));
        return l;
    }

    public static Component vGap(int h) { return Box.createRigidArea(new Dimension(0, h)); }
    public static Component hGap(int w) { return Box.createRigidArea(new Dimension(w, 0)); }
}