package expense_tracker;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    public Map<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public void registerUser(User user) {
        if (user != null) {
            users.put(user.getUsername(), user);
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Failed to register user. Please check your input.");
        }
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            System.out.println("Login successful.");
            return user;
        } else {
            System.out.println("Invalid username or password. Login failed.");
            return null;
        }
    }

    // Getter method for users
    public Map<String, User> getUsers() {
        return users;
    }
}
