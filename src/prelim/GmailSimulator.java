package prelim;

import javax.swing.*;

public class GmailSimulator {
    public UserDatabase userDatabase;

    public GmailSimulator() {
        this.userDatabase = new UserDatabase();
    }

    public void logout() {
        System.out.println("Logging out...");
        SwingUtilities.invokeLater(() -> new LogIn().createAndShowGUI(this));
    }

    public User findUserByEmail(String email) {
        try {
            return this.userDatabase.findUserByEmail(email);
        } catch (Exception ex) {
            System.out.println("Error finding user: " + ex.getMessage());
            return null;
        }
    }
}

