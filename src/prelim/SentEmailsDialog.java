package prelim;

import javax.swing.*;
import java.awt.*;

public class SentEmailsDialog extends JDialog {
    private JTextArea sentArea;
    private User currentUser;

    public SentEmailsDialog(JFrame parent, User user) {
        super(parent, "Sent Emails", true);
        this.currentUser = user;
        setupUI();
        loadSentEmails();
    }

    private void setupUI() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());

        sentArea = new JTextArea();
        sentArea.setEditable(false);
        sentArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        sentArea.setMargin(new Insets(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(new JScrollPane(sentArea), BorderLayout.CENTER);
    }

    private void loadSentEmails() {
        try {
            LinkedList<Email> sent = currentUser.getSent();
            if (sent.isEmpty()) {
                sentArea.setText("No sent emails.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sent.size(); i++) {
                Email email = sent.get(i);
                sb.append(String.format("%d. To: %s (%s)%n", 
                    i + 1, 
                    email.getRecipient().getName(), 
                    email.getRecipient().getEmail()));
                sb.append(String.format("   Subject: %s%n", email.getSubject()));
                sb.append(String.format("   Date: %s%n", email.getDate()));
                String body = email.getBody();
                sb.append(String.format("   Body: %s%s%n%n", 
                    body.substring(0, Math.min(50, body.length())),
                    body.length() > 50 ? "..." : ""));
            }
            sentArea.setText(sb.toString());
        } catch (Exception ex) {
            sentArea.setText("Error viewing sent emails: " + ex.getMessage());
        }
    }
}