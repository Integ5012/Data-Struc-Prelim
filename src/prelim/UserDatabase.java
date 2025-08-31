package prelim;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDatabase class for managing user accounts with text file persistence
 */
public class UserDatabase {
    private static final String DATABASE_FILE = "users.txt";
    private static final String SENT_MESSAGES_FILE = "sent_messages.txt";
    private List<User> users;
    private int nextUserId;
    
    public UserDatabase() {
        this.users = new ArrayList<>();
        this.nextUserId = 1;
        loadUsers();
    }
    
    /**
     * Add a new user to the database
     * @param name user's name
     * @param email user's email
     * @param password user's password
     * @return the created User object
     * @throws IllegalArgumentException if validation fails
     */
    public User addUser(String name, String email, String password) {
        // Validate email format
        if (!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format. Please use a valid email address.");
        }
        
        // Check if email already exists
        if (findUserByEmail(email) != null) {
            throw new IllegalArgumentException("Email already registered. Please use a different email.");
        }
        
        // Create new user
        User newUser = new User(nextUserId++, name, email, password);
        users.add(newUser);
        
        // Save to database
        saveUsers();
        
        return newUser;
    }
    
    /**
     * Find user by email address
     * @param email email to search for
     * @return User object if found, null otherwise
     */
    public User findUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        
        String searchEmail = email.trim().toLowerCase();
        for (User user : users) {
            if (user.getEmail().equals(searchEmail)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Find user by ID
     * @param userId user ID to search for
     * @return User object if found, null otherwise
     */
    public User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Get all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    /**
     * Get next available user ID
     * @return next user ID
     */
    public int getNextUserId() {
        return nextUserId;
    }
    
    /**
     * Update user information
     * @param user User object to update
     */
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == user.getUserId()) {
                users.set(i, user);
                saveUsers();
                break;
            }
        }
    }
    
    /**
     * Delete user from database
     * @param userId ID of user to delete
     * @return true if user was deleted, false otherwise
     */
    public boolean deleteUser(int userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == userId) {
                users.remove(i);
                saveUsers();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Save users to database text file
     */
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE))) {
            // Write next user ID
            writer.write("NEXT_USER_ID:" + nextUserId);
            writer.newLine();
            
            // Write user data
            for (User user : users) {
                writer.write("USER_START");
                writer.newLine();
                writer.write("ID:" + user.getUserId());
                writer.newLine();
                writer.write("NAME:" + user.getName());
                writer.newLine();
                writer.write("EMAIL:" + user.getEmail());
                writer.newLine();
                writer.write("PASSWORD:" + user.getPassword());
                writer.newLine();
                
                // Write inbox emails
                writer.write("INBOX_START");
                writer.newLine();
                for (int i = 0; i < user.getInbox().size(); i++) {
                    Email email = user.getInbox().get(i);
                    writer.write("EMAIL_START");
                    writer.newLine();
                    writer.write("EMAIL_ID:" + email.getEmailId());
                    writer.newLine();
                    writer.write("SENDER_ID:" + email.getSender().getUserId());
                    writer.newLine();
                    writer.write("RECIPIENT_ID:" + email.getRecipient().getUserId());
                    writer.newLine();
                    writer.write("SUBJECT:" + email.getSubject());
                    writer.newLine();
                    writer.write("BODY:" + email.getBody());
                    writer.newLine();
                    writer.write("DATE:" + email.getDate().getTime());
                    writer.newLine();
                    writer.write("EMAIL_END");
                    writer.newLine();
                }
                writer.write("INBOX_END");
                writer.newLine();
                
                // Write sent emails
                writer.write("SENT_START");
                writer.newLine();
                for (int i = 0; i < user.getSent().size(); i++) {
                    Email email = user.getSent().get(i);
                    writer.write("EMAIL_START");
                    writer.newLine();
                    writer.write("EMAIL_ID:" + email.getEmailId());
                    writer.newLine();
                    writer.write("SENDER_ID:" + email.getSender().getUserId());
                    writer.newLine();
                    writer.write("RECIPIENT_ID:" + email.getRecipient().getUserId());
                    writer.newLine();
                    writer.write("SUBJECT:" + email.getSubject());
                    writer.newLine();
                    writer.write("BODY:" + email.getBody());
                    writer.newLine();
                    writer.write("DATE:" + email.getDate().getTime());
                    writer.newLine();
                    writer.write("EMAIL_END");
                    writer.newLine();
                }
                writer.write("SENT_END");
                writer.newLine();
                
                // Save sent messages to separate file
                saveSentMessagesToFile(user);
                
                writer.write("USER_END");
                writer.newLine();
            }
            
        } catch (IOException e) {
            System.err.println("Error saving users to database: " + e.getMessage());
        }
    }
    
    /**
     * Load users from database text file
     */
    private void loadUsers() {
        File file = new File(DATABASE_FILE);
        if (!file.exists()) {
            return; // No database file yet, start with empty list
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int currentUserId = 0;
            String currentUserName = "";
            String currentUserEmail = "";
            String currentUserPassword = "";
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                if (line.startsWith("NEXT_USER_ID:")) {
                    nextUserId = Integer.parseInt(line.substring(13));
                } else if (line.equals("USER_START")) {
                    // Reset user data for new user
                    currentUserId = 0;
                    currentUserName = "";
                    currentUserEmail = "";
                    currentUserPassword = "";
                } else if (line.startsWith("ID:")) {
                    currentUserId = Integer.parseInt(line.substring(3));
                } else if (line.startsWith("NAME:")) {
                    currentUserName = line.substring(5);
                } else if (line.startsWith("EMAIL:")) {
                    currentUserEmail = line.substring(6);
                } else if (line.startsWith("PASSWORD:")) {
                    currentUserPassword = line.substring(9);
                } else if (line.equals("USER_END")) {
                    // Create user object when we have all the data
                    if (currentUserId > 0 && !currentUserName.isEmpty() && !currentUserEmail.isEmpty() && !currentUserPassword.isEmpty()) {
                        User newUser = new User(currentUserId, currentUserName, currentUserEmail, currentUserPassword);
                        users.add(newUser);
                    }
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error loading users from database: " + e.getMessage());
            // If loading fails, start with empty list
            users = new ArrayList<>();
            nextUserId = 1;
        }
    }
    
    /**
     * Read email data from file
     */
    private Email readEmailFromFile(BufferedReader reader) throws IOException {
        try {
            int emailId = 0;
            int senderId = 0;
            int recipientId = 0;
            String subject = "";
            String body = "";
            long date = 0;
            
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                if (line.equals("EMAIL_END")) {
                    break;
                } else if (line.startsWith("EMAIL_ID:")) {
                    emailId = Integer.parseInt(line.substring(9));
                } else if (line.startsWith("SENDER_ID:")) {
                    senderId = Integer.parseInt(line.substring(10));
                } else if (line.startsWith("RECIPIENT_ID:")) {
                    recipientId = Integer.parseInt(line.substring(13));
                } else if (line.startsWith("SUBJECT:")) {
                    subject = line.substring(8);
                } else if (line.startsWith("BODY:")) {
                    body = line.substring(5);
                } else if (line.startsWith("DATE:")) {
                    date = Long.parseLong(line.substring(5));
                }
            }
            
            // Find sender and recipient users
            User sender = findUserById(senderId);
            User recipient = findUserById(recipientId);
            
            if (sender != null && recipient != null) {
                Email email = new Email(emailId, sender, recipient, subject, body);
                email.setDate(new java.util.Date(date));
                return email;
            }
            
        } catch (Exception e) {
            System.err.println("Error reading email from file: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Save sent messages to a separate text file
     * @param user User whose sent messages to save
     */
    private void saveSentMessagesToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SENT_MESSAGES_FILE, true))) {
            // Append sent messages to the file
            for (int i = 0; i < user.getSent().size(); i++) {
                Email email = user.getSent().get(i);
                writer.write("SENT_MESSAGE");
                writer.newLine();
                writer.write("SENDER:" + email.getSender().getName() + " (" + email.getSender().getEmail() + ")");
                writer.newLine();
                writer.write("RECIPIENT:" + email.getRecipient().getName() + " (" + email.getRecipient().getEmail() + ")");
                writer.newLine();
                writer.write("SUBJECT:" + email.getSubject());
                writer.newLine();
                writer.write("BODY:" + email.getBody());
                writer.newLine();
                writer.write("DATE:" + email.getDate());
                writer.newLine();
                writer.write("MESSAGE_END");
                writer.newLine();
                writer.newLine(); // Add blank line for readability
            }
        } catch (IOException e) {
            System.err.println("Error saving sent messages to file: " + e.getMessage());
        }
    }
    
    /**
     * Get database statistics
     * @return String with database information
     */
    public String getDatabaseInfo() {
        return "Database Info:\n" +
               "Total Users: " + users.size() + "\n" +
               "Next User ID: " + nextUserId + "\n" +
               "Database File: " + DATABASE_FILE + "\n" +
               "Sent Messages File: " + SENT_MESSAGES_FILE + "\n" +
               "File Format: Human-readable text";
    }
}
