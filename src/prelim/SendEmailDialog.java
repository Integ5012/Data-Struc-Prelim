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

        // Create main panel with simple vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and configure fields
        toField = new JTextField(20);
        subjectField = new JTextField(20);
        bodyArea = new JTextArea(5, 20);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setFont(UIUtils.DEFAULT_FONT);

        JButton sendButton = UIUtils.createPrimaryButton("Send");
        statusLabel = new JLabel("");
        statusLabel.setFont(UIUtils.DEFAULT_FONT);

        // "To" field section
        JLabel toLabel = new JLabel("To (email):");
        toLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(toLabel);

        toField.setMaximumSize(new Dimension(350, 30));
        toField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(toField);

        // Add space between fields
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Subject field section
        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subjectLabel);

        subjectField.setMaximumSize(new Dimension(350, 30));
        subjectField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subjectField);

        // Add space between fields
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Body field section
        JLabel bodyLabel = new JLabel("Body:");
        bodyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(bodyLabel);

        JScrollPane bodyScrollPane = new JScrollPane(bodyArea);
        bodyScrollPane.setMaximumSize(new Dimension(350, 120));
        bodyScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(bodyScrollPane);

        // Add space before button
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Send button
        sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(sendButton);

        // Add space before status message
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Status label
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);

        // Send button action
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