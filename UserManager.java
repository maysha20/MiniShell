import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserManager {
    private static final String USERS_FILE = "users.txt";
    private Map<String, String> users;

    public UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]); // username, password
                }
            }
        } catch (IOException e) {
            System.out.println("No existing user file found. A new one will be created.");
        }
    }

    public boolean isUsernameTaken(String username) {
        return users.containsKey(username);
    }

    private void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        saveUser(username, password);
        return true;
    }
}