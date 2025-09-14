package prelim;

import javax.swing.*;
import java.awt.*;

public class SendEmailDialog extends JDialog {
    private JTextField toField;
    private JTextField subjectField;
    private JTextArea bodyArea;
    private JLabel statusLabel;
    private User currentUser;
    private GmailSimulator simulator;

    public SendEmailDialog(JFrame parent, User user, GmailSimulator simulator) {
        super(parent, "Send Email", true);
        this.currentUser = user;
        this.simulator = simulator;
        setupUI();
    }

    private void setupUI() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        toField = new JTextField(20);
        subjectField = new JTextField(20);
        bodyArea = new JTextArea(5, 20);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
    JButton sendButton = UIUtils.createPrimaryButton("Send");
    statusLabel = new JLabel("");
    statusLabel.setFont(UIUtils.DEFAULT_FONT);
    bodyArea.setFont(UIUtils.DEFAULT_FONT);

        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("To (email):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(toField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(subjectField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Body:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(new JScrollPane(bodyArea), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(sendButton, gbc);

        gbc.gridy = 4;
        mainPanel.add(statusLabel, gbc);

        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                sendEmail();
            }
        });

        setContentPane(mainPanel);
    }

    private void sendEmail() {
        String to = toField.getText().trim().toLowerCase();
        String subject = subjectField.getText().trim();
        String body = bodyArea.getText().trim();

        
        if (to.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        User recipient = simulator.findUserByEmail(to);
        if (recipient == null) {
            showError("Recipient not found.");
            return;
        }

        if (recipient == currentUser) {
            showError("Cannot send email to yourself.");
            return;
        }

        try {
            currentUser.sendEmail(recipient, subject, body);
            simulator.userDatabase.updateUser(currentUser);
            simulator.userDatabase.updateUser(recipient);
            showSuccess("Email sent successfully!");
            clearFields();
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(new Color(0, 128, 0));
    }

    private void clearFields() {
        toField.setText("");
        subjectField.setText("");
        bodyArea.setText("");
    }
}