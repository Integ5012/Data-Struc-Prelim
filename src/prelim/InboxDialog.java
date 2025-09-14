package prelim;

import javax.swing.*;
import java.awt.*;

public class InboxDialog extends JDialog {
    private JTextArea inboxArea;
    private User currentUser;

    public InboxDialog(JFrame parent, User user) {
        super(parent, "Inbox", true);
        this.currentUser = user;
        setupUI();
        loadInbox();
    }

    private void setupUI() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());

        inboxArea = new JTextArea();
        inboxArea.setEditable(false);
        inboxArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inboxArea.setMargin(new Insets(10, 10, 10, 10));
        
    setLayout(new BorderLayout());
    add(new JScrollPane(inboxArea), BorderLayout.CENTER);
    }

    private void loadInbox() {
        try {
            String inboxContent = currentUser.readInbox();
            if (inboxContent == null || inboxContent.trim().isEmpty()) {
                inboxArea.setText("No emails in inbox.");
            } else {
                inboxArea.setText(inboxContent);
            }
        } catch (Exception ex) {
            inboxArea.setText("Error reading inbox: " + ex.getMessage());
        }
    }
}