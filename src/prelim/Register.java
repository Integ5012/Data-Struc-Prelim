package prelim;

import javax.swing.*;
import java.awt.*;

public class Register {
    public static void showRegisterGUI(JFrame parentFrame, GmailSimulator simulator) {
        JDialog dialog = new JDialog(parentFrame, "Register Account", true);
        dialog.setSize(480, 360);
        dialog.setLocationRelativeTo(parentFrame);
        UIUtils.applyDialogDefaults(dialog);

        // Create main panel with simple vertical layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add logo at the top
        ImageIcon logoIcon = new ImageIcon("src/prelim/Logo/gmail_logo_64x64.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        // Add some space after logo
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Name field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(UIUtils.DEFAULT_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);

        JTextField nameField = new JTextField(10);
        nameField.setMaximumSize(new Dimension(300, 30));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameField);

        // Add space between fields
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(UIUtils.DEFAULT_FONT);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(emailLabel);

        JTextField emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(300, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(emailField);

        // Add space between fields
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Password field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(UIUtils.DEFAULT_FONT);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField(20);
        passField.setMaximumSize(new Dimension(300, 30));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(passField);

        // Add space before button
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Register button
        JButton registerButton = UIUtils.createPrimaryButton("Register");
        registerButton.setMaximumSize(new Dimension(300, 30));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(registerButton);

        // Add space before message
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Message label
        JLabel messageLabel = new JLabel("");
        messageLabel.setFont(UIUtils.DEFAULT_FONT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        // Register button action
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim().toLowerCase();
                String password = new String(passField.getPassword());
                UserDatabase db = simulator.userDatabase;
                try {
                    db.addUser(name, email, password);
                    messageLabel.setText("Registration successful! You can now log in.");
                    dialog.dispose();
                    SwingUtilities.invokeLater(() -> new LogIn().createAndShowGUI(simulator));
                } catch (IllegalArgumentException ex) {
                    messageLabel.setText(ex.getMessage());
                }
            }
        });

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }
}