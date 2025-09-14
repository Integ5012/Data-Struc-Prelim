package prelim;

import javax.swing.*;
import java.awt.*;
// Utility class for UI components
public final class UIUtils {
    public static final Color PRIMARY = new Color(66, 133, 244);
    public static final Color ACCENT = new Color(25, 103, 210);
    public static final Color BACKGROUND = new Color(245, 248, 255);
    public static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 20);
    public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);

    private UIUtils() {}

    public static JButton createPrimaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(DEFAULT_FONT);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static void styleButton(JButton b) {
        b.setFocusPainted(false);
        b.setBackground(Color.WHITE);
        b.setFont(DEFAULT_FONT);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void applyDialogDefaults(JDialog d) {
        d.getContentPane().setBackground(Color.WHITE);
    }

    public static ImageIcon loadIcon(String path) {
        try {
            return new ImageIcon(path);
        } catch (Exception ex) {
            return null;
        }
    }
}
