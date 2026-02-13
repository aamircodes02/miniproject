package org.zeta.service;
import org.zeta.dao.UserDao;
import org.zeta.model.Role;
import org.zeta.model.User;

import java.io.IOException;

public class AuthenticationService {

    private UserDao userDAO;

    public AuthenticationService(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    public boolean register(String username, String password, String confirmPassword, String role) throws IOException {

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return false;
        }

        if (UserDao.userExists(username)) {
            System.out.println("User already exists!");
            return false;
        }

        User user = new User(username, password, role, Role.BUILDER);
        UserDao.addUser(user);

        System.out.println("Registration successful!");
        return true;
    }

    public User login(String username, String password) throws IOException {

        User user = UserDao.getUser(username);

        if (user == null) {
            System.out.println("User not found!");
            return null;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("Invalid password!");
            return null;
        }

        System.out.println("Login successful!");
        return user;
    }
}
