package prelim;

import javax.swing.*;
import java.awt.*;

public class Register {
    public static void showRegisterGUI(JFrame parentFrame, GmailSimulator simulator) {
        JDialog dialog = new JDialog(parentFrame, "Register Account", true);
        dialog.setSize(480, 360);
        dialog.setLocationRelativeTo(parentFrame);
        UIUtils.applyDialogDefaults(dialog);

    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    ImageIcon logoIcon = new ImageIcon("src/prelim/Logo/gmail_logo_64x64.png");
    JLabel logoLabel = new JLabel(logoIcon);
    logoLabel.setHorizontalAlignment(JLabel.CENTER);
    logoLabel.setVerticalAlignment(JLabel.CENTER);
    logoLabel.setOpaque(false);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    panel.add(logoLabel, gbc);
    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setFont(UIUtils.DEFAULT_FONT);
    JTextField nameField = new JTextField(15);
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(UIUtils.DEFAULT_FONT);
    JTextField emailField = new JTextField(15);
    JLabel passLabel = new JLabel("Password:");
    passLabel.setFont(UIUtils.DEFAULT_FONT);
    JPasswordField passField = new JPasswordField(15);
    JButton registerButton = UIUtils.createPrimaryButton("Register");
    JLabel messageLabel = new JLabel("");
    messageLabel.setFont(UIUtils.DEFAULT_FONT);
    messageLabel.setHorizontalAlignment(JLabel.CENTER);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(nameLabel, gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(emailLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    panel.add(emailField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(passLabel, gbc);
    gbc.gridx = 1;
    gbc.gridy = 3;
    panel.add(passField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(registerButton, gbc);

    gbc.gridy = 5;
    panel.add(messageLabel, gbc);

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