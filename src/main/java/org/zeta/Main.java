package org.zeta;

import org.zeta.dao.UserDao;
import org.zeta.model.Role;
import org.zeta.model.User;
import org.zeta.service.implementation.AuthenticationService;
import org.zeta.validation.CommonValidator;
import org.zeta.validation.ValidationException;
import org.zeta.views.BuilderView;
import org.zeta.views.ClientView;
import org.zeta.views.ProjectManagerView;

import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Logger logger = Logger.getLogger("main");

        UserDao userDAO = new UserDao();
        AuthenticationService authService = new AuthenticationService(userDAO);

        while (true) {

            System.out.println("""
                    
                    ===== Builder Portfolio Management System =====
                    1. Register
                    2. Login
                    3. Exit application
                    Enter your choice:
                    """);

            try {

                String input = sc.nextLine();
                int choice = CommonValidator.validateInteger(input, "Menu choice");

                switch (choice) {

                    // ================= REGISTER =================
                    case 1:

                        System.out.println("Enter username:");
                        String regUsername = sc.nextLine();

                        System.out.println("Enter password:");
                        String regPassword = sc.nextLine();

                        System.out.println("Confirm password:");
                        String confirmPassword = sc.nextLine();

                        Role selectedRole = null;

                        while (selectedRole == null) {

                            System.out.println("""
                                    Select Role:
                                    1. Builder
                                    2. Project Manager
                                    3. Client
                                    Enter your choice:
                                    """);

                            String roleInput = sc.nextLine();
                            int roleChoice =
                                    CommonValidator.validateInteger(roleInput, "Role choice");

                            switch (roleChoice) {
                                case 1:
                                    selectedRole = Role.BUILDER;
                                    break;
                                case 2:
                                    selectedRole = Role.PROJECT_MANAGER;
                                    break;
                                case 3:
                                    selectedRole = Role.CLIENT;
                                    break;
                                default:
                                    System.out.println("Please select a valid option (1-3).");
                            }
                        }

                        authService.register(regUsername,
                                regPassword,
                                confirmPassword,
                                selectedRole);

                        break;

                    // ================= LOGIN =================
                    case 2:

                        try {
                            System.out.println("Enter username:");
                            String loginUsername = sc.nextLine();

                            System.out.println("Enter password:");
                            String loginPassword = sc.nextLine();

                            User loggedInUser =
                                    authService.login(loginUsername, loginPassword);

                            if (loggedInUser != null) {

                                System.out.println("Welcome "
                                        + loggedInUser.getUsername());

                                if (Objects.equals(loggedInUser.getRole(), Role.CLIENT)) {
                                    ClientView.clientDashboard(loggedInUser);
                                }

                                else if (Objects.equals(loggedInUser.getRole(), Role.BUILDER)) {
                                    BuilderView.builderDashboard(loggedInUser);
                                }

                                else if (Objects.equals(loggedInUser.getRole(),
                                        Role.PROJECT_MANAGER)) {
                                    ProjectManagerView
                                            .ProjectManagerDashboard(loggedInUser);
                                }
                            }

                        } catch (ValidationException e) {
                            logger.severe("Error: " + e.getMessage());
                        }

                        break;

                    // ================= EXIT =================
                    case 3:
                        System.out.println("Exiting application...");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
