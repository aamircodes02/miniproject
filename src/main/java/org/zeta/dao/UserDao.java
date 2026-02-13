package org.zeta.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zeta.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String FILE_PATH = "users.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // Get all users from file
    private static List<User> getAllUsers() throws IOException {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            file.createNewFile();
            mapper.writeValue(file, new ArrayList<User>());
        }

        if (file.length() == 0) {
            return new ArrayList<>();
        }

        return mapper.readValue(file, new TypeReference<List<User>>() {});
    }

    // Save all users back to file
    private static void saveAllUsers(List<User> users) throws IOException {
        mapper.writeValue(new File(FILE_PATH), users);
    }

    // Add user
    public static void addUser(User user) throws IOException {

        List<User> users = getAllUsers();

        // check duplicate
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                throw new RuntimeException("User already exists");
            }
        }

        users.add(user);
        saveAllUsers(users);
    }

    // Get user by username
    public static User getUser(String username) throws IOException {

        List<User> users = getAllUsers();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }

        return null;
    }

    // Check if user exists
    public static boolean userExists(String username) throws IOException {

        List<User> users = getAllUsers();

        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }

        return false;
    }
}
