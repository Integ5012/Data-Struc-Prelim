package prelim;

import javax.swing.*;
import java.awt.*;

public class SendEmailDialog extends JDialog {
    public interface StatusListener {
        void onStatus(String message);
    }

    private JTextField toField;
    private JTextField subjectField;
    private JTextArea bodyArea;
    private JLabel statusLabel;
    private User currentUser;
    private GmailSimulator simulator;
    private StatusListener statusListener;

    public SendEmailDialog(JFrame parent, User user, GmailSimulator simulator) {
        this(parent, user, simulator, null);
    }

    public SendEmailDialog(JFrame parent, User user, GmailSimulator simulator, StatusListener listener) {
        super(parent, "New message", true);
        this.currentUser = user;
        this.simulator = simulator;
        this.statusListener = listener;
        setupUI();
    }

    private void setupUI() {
        setSize(560, 480);
        setLocationRelativeTo(getParent());
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Header bar (Gmail-like)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(248, 249, 251));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 235, 245)));
        JLabel title = new JLabel("New message");
        title.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        title.setFont(UIUtils.DEFAULT_FONT.deriveFont(Font.BOLD, 14f));
        header.add(title, BorderLayout.WEST);

        JButton closeBtn = new JButton("✕");
        UIUtils.styleButton(closeBtn);
        closeBtn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        closeBtn.addActionListener(e -> dispose());
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 4));
        headerRight.setOpaque(false);
        headerRight.add(closeBtn);
        header.add(headerRight, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Fields panel
        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        fields.setBackground(Color.WHITE);
        fields.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        // To row
        JPanel toRow = new JPanel(new BorderLayout());
        toRow.setBackground(Color.WHITE);
        JLabel toLabel = new JLabel("To");
        toLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
        toField = new JTextField();
        toField.setPreferredSize(new Dimension(0, 28));
        toField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 229, 235)),
            BorderFactory.createEmptyBorder(0, 6, 0, 6)));
        toRow.add(toLabel, BorderLayout.WEST);
        toRow.add(toField, BorderLayout.CENTER);
        fields.add(toRow);
        fields.add(Box.createRigidArea(new Dimension(0, 8)));

        // Subject row
        JPanel subjectRow = new JPanel(new BorderLayout());
        subjectRow.setBackground(Color.WHITE);
        JLabel subjectLbl = new JLabel("Subject");
        subjectLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
        subjectField = new JTextField();
        subjectField.setPreferredSize(new Dimension(0, 28));
        subjectField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 229, 235)),
            BorderFactory.createEmptyBorder(0, 6, 0, 6)));
        subjectRow.add(subjectLbl, BorderLayout.WEST);
        subjectRow.add(subjectField, BorderLayout.CENTER);
        fields.add(subjectRow);
        fields.add(Box.createRigidArea(new Dimension(0, 10)));

        // Body area
        bodyArea = new JTextArea(10, 20);
        bodyArea.setLineWrap(true);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setFont(UIUtils.DEFAULT_FONT);
        JScrollPane bodyScroll = new JScrollPane(bodyArea);
        bodyScroll.setBorder(BorderFactory.createLineBorder(new Color(225, 229, 235)));
        fields.add(bodyScroll);

        add(fields, BorderLayout.CENTER);

        // Footer actions and status
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(248, 249, 251));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 235, 245)));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        actions.setOpaque(false);
        JButton sendBtn = UIUtils.createPrimaryButton("Send");
        JButton discardBtn = new JButton("Discard");
        UIUtils.styleButton(discardBtn);
        actions.add(sendBtn);
        actions.add(discardBtn);
        footer.add(actions, BorderLayout.WEST);

        statusLabel = new JLabel("");
        statusLabel.setFont(UIUtils.DEFAULT_FONT);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        footer.add(statusLabel, BorderLayout.CENTER);

        add(footer, BorderLayout.SOUTH);

        // Actions
        sendBtn.addActionListener(e -> sendEmail());
        discardBtn.addActionListener(e -> dispose());
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
            if (statusListener != null) {
                statusListener.onStatus("Email sent to " + recipient.getName());
            }
            clearFields();
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
            if (statusListener != null) {
                statusListener.onStatus("Failed to send email");
            }
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