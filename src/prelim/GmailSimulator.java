package prelim;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.List;

public class GmailSimulator {
    public UserDatabase userDatabase;
    private User currentUser = null;
    private Scanner scanner;

    public GmailSimulator() {
        this.scanner = new Scanner(System.in);
        this.userDatabase = new UserDatabase();
    }

    public static void main(String[] var0) {
        GmailSimulator var1 = new GmailSimulator();
        var1.run();
    }

    public void run() {
        System.out.println("=== Welcome to Gmail Simulator ===");

        while(true) {
            while(this.currentUser != null) {
                this.showUserMenu();
            }

            this.showMainMenu();
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int var1 = this.getIntInput();
        switch (var1) {
            case 1:
                this.login();
                break;
            case 2:
                this.register();
                break;
            case 3:
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
        }

    }

    private void showUserMenu() {
        System.out.println("\n=== User Menu ===");
        System.out.println("Welcome, " + this.currentUser.getName() + "!");
        System.out.println("1. Send Email");
        System.out.println("2. Read Inbox");
        System.out.println("3. View Sent Emails");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        int var1 = this.getIntInput();
        switch (var1) {
            case 1 -> this.sendEmail();
            case 2 -> this.readInbox();
            case 3 -> this.viewSentEmails();
            case 4 -> this.logout();
            default -> System.out.println("Invalid option. Please try again.");
        }

    }

    private void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Enter email: ");
        String var1 = this.scanner.nextLine();
        System.out.print("Enter password: ");
        String var2 = this.scanner.nextLine();

        try {
            User var3 = this.findUserByEmail(var1);
            if (var3 != null && var3.login(var2)) {
                this.currentUser = var3;
                System.out.println("Login successful! Welcome back, " + var3.getName() + "!");
                new Menu().showGUI(this, var3);
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        } catch (Exception var4) {
            System.out.println("Error during login: " + var4.getMessage());
            System.out.println("Please try again.");
        }
    }

    private void register() {
        System.out.println("\n=== Registration ===");
        System.out.println("Note: Please enter a valid email address.");
        System.out.print("Enter name: ");
        String var1 = this.scanner.nextLine();

        String var2;
        while(true) {
            System.out.print("Enter email: ");
            var2 = this.scanner.nextLine();

            try {
                if (EmailValidator.isValidEmail(var2)) {
                    break;
                }

                System.out.println("Invalid email format! Please use a valid email address.");
                System.out.println("Example: username@gmail.com, username@hotmail.com, or username@slu.edu.ph");
            } catch (Exception var6) {
                System.out.println("Error validating email: " + var6.getMessage());
            }
        }

        if (this.findUserByEmail(var2) != null) {
            System.out.println("Email already registered. Please use a different email.");
        } else {
            System.out.print("Enter password: ");
            String var3 = this.scanner.nextLine();

            try {
                User var4 = this.userDatabase.addUser(var1, var2, var3);
                System.out.println("Registration successful! You can now login.");
            } catch (Exception var5) {
                System.out.println("Error during registration: " + var5.getMessage());
                System.out.println("Please try again.");
            }

        }
    }

    private void sendEmail() {
        System.out.println("\n=== Send Email ===");

        try {
            System.out.print("Enter recipient's email address: ");
            String recipientEmail = this.scanner.nextLine().trim().toLowerCase();
            
            if (recipientEmail.isEmpty()) {
                System.out.println("Recipient email cannot be empty.");
                return;
            }
            
            User recipient = this.findUserByEmail(recipientEmail);
            if (recipient == null) {
                System.out.println("Recipient not found. Please check the email address.");
                return;
            }
            
            if (recipient == this.currentUser) {
                System.out.println("You cannot send an email to yourself.");
                return;
            }

            System.out.print("Enter subject: ");
            String var3 = this.scanner.nextLine();
            if (var3.trim().isEmpty()) {
                System.out.println("Subject cannot be empty.");
                return;
            }

            System.out.print("Enter email body: ");
            String var4 = this.scanner.nextLine();
            if (var4.trim().isEmpty()) {
                System.out.println("Email body cannot be empty.");
                return;
            }

            Email var5 = this.currentUser.sendEmail(recipient, var3, var4);
            // Update both users in the database
            this.userDatabase.updateUser(this.currentUser);
            this.userDatabase.updateUser(recipient);
            this.userDatabase.updateUser(this.currentUser);
            this.userDatabase.updateUser(recipient);
            System.out.println("Email sent successfully!");
            System.out.println("Email ID: " + var5.getEmailId());
        } catch (Exception var6) {
            System.out.println("Error sending email: " + var6.getMessage());
            System.out.println("Please try again.");
        }

    }

    private void readInbox() {
        System.out.println("\n=== Inbox ===");

        try {
            System.out.println(this.currentUser.readInbox());
        } catch (Exception var2) {
            System.out.println("Error reading inbox: " + var2.getMessage());
        }

    }

    private void viewSentEmails() {
        System.out.println("\n=== Sent Emails ===");

        try {
            LinkedList var1 = this.currentUser.getSent();
            if (var1.isEmpty()) {
                System.out.println("No sent emails.");
                return;
            }

            System.out.println("Sent emails (" + var1.size() + "):");

            for(int var2 = 0; var2 < var1.size(); ++var2) {
                Email var3 = (Email)var1.get(var2);
                System.out.println(var2 + 1 + ". To: " + var3.getRecipient().getName() + " (" + var3.getRecipient().getEmail() + ")");
                PrintStream var10000 = System.out;
                String var10001 = var3.getSubject();
                var10000.println("   Subject: " + var10001);
                var10000 = System.out;
                var10001 = String.valueOf(var3.getDate());
                var10000.println("   Date: " + var10001);
                var10000 = System.out;
                var10001 = var3.getBody().substring(0, Math.min(50, var3.getBody().length()));
                var10000.println("   Body: " + var10001 + (var3.getBody().length() > 50 ? "..." : ""));
                System.out.println();
            }
        } catch (Exception var4) {
            System.out.println("Error viewing sent emails: " + var4.getMessage());
        }

    }

    public void logout() {
        System.out.println("Logging out... Goodbye, " + this.currentUser.getName() + "!");
        this.currentUser = null;
    }
    


    public User findUserByEmail(String var1) {
        try {
            return this.userDatabase.findUserByEmail(var1);
        } catch (Exception var4) {
            System.out.println("Error finding user: " + var4.getMessage());
            return null;
        }
    }

    private int getIntInput() {
        while(true) {
            try {
                return Integer.parseInt(this.scanner.nextLine());
            } catch (NumberFormatException var2) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}

