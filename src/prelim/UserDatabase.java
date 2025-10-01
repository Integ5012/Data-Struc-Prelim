package prelim;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDatabase class for managing user accounts in-memory (no file persistence)
 */
public class UserDatabase {
    private List<User> users;
    private int nextUserId;
    
    public UserDatabase() {
        this.users = new ArrayList<>();
        this.nextUserId = 1;
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
        if (!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email. Allowed domains: " + EmailValidator.getAllowedDomains());
        }
        if (findUserByEmail(email) != null) {
            throw new IllegalArgumentException("Email already registered. Please use a different email.");
        }
        User newUser = new User(nextUserId++, name, email, password);
        users.add(newUser);
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
     * Get all users (copy)
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    /**
     * Get next available user ID
     */
    public int getNextUserId() {
        return nextUserId;
    }
    
    /**
     * Update user information (no-op if user not present)
     */
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == user.getUserId()) {
                users.set(i, user);
                break;
            }
        }
    }
    
    /**
     * Delete user from database
     */
    public boolean deleteUser(int userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == userId) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get database statistics (in-memory)
     */
    public String getDatabaseInfo() {
        return "Database Info:\n" +
               "Total Users: " + users.size() + "\n" +
               "Next User ID: " + nextUserId + "\n" +
               "Persistence: In-Memory (no files)";
    }
}
