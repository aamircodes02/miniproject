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

import java.io.Console;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); // ONE scanner for entire app
        Logger logger = Logger.getLogger("main");

        UserDao userDAO = new UserDao();
        AuthenticationService authService = new AuthenticationService(userDAO);

        while (true) {

            System.out.println("\nPlease choose:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit application");

            try {

                String input = sc.nextLine();
                int choice = CommonValidator.validateInteger(input, "Menu choice");

                switch (choice) {

                    case 1 -> {
                        System.out.println("Enter username:");
                        String regUsername = sc.nextLine();

                        Console console = System.console();
                        String regPassword;
                        String confirmPassword;

<<<<<<< HEAD
                        if (console != null) {
                            regPassword = new String(console.readPassword("Enter password: "));
                            confirmPassword = new String(console.readPassword("Confirm password: "));
                        } else {
                            // IntelliJ fallback
                            System.out.println("Enter password:");
                            regPassword = sc.nextLine();

                            System.out.println("Confirm password:");
                            confirmPassword = sc.nextLine();
                        }

=======
                        System.out.println("Confirm password:");
                        String confirmPassword = sc.nextLine();
>>>>>>> 68e3ba8a767a6995a7fde6130a30d9a81712f387
                        Role selectedRole = null;
                        while (selectedRole == null) {
                            System.out.println("""
                                    Select Role:
                                    1. Builder
                                    2. Project Manager
                                    3. Client
                                    Enter your choice:
                                    """);

<<<<<<< HEAD
                            String roleInput = sc.nextLine();
                            int roleChoice =
                                    CommonValidator.validateInteger(roleInput, "Role choice");
=======
                            if (!sc.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a number.");
                                sc.nextLine();
                                continue;
                            }
                            int roleChoice = sc.nextInt();
                            sc.nextLine();
>>>>>>> 68e3ba8a767a6995a7fde6130a30d9a81712f387

                            switch (roleChoice) {
                                case 1 -> selectedRole = Role.BUILDER;
                                case 2 -> selectedRole = Role.PROJECT_MANAGER;
                                case 3 -> selectedRole = Role.CLIENT;
                                default -> System.out.println("Please select a valid option (1-3).");
                            }
                        }

                        boolean registered = authService.register(
                                regUsername, regPassword, confirmPassword, selectedRole
                        );

                        if (registered) {
                            System.out.println("Registration successful!");
                        } else {
                            System.out.println("Registration failed.");
                        }
                    }

                    case 2 -> {
                        System.out.println("Enter username:");
                        String loginUsername = sc.nextLine();

                        Console console = System.console();
                        String loginPassword;

                        if (console != null) {
                            loginPassword = new String(console.readPassword("Enter password: "));
                        } else {
                            // IntelliJ fallback
                            System.out.println("Enter password:");
                            loginPassword = sc.nextLine();
                        }

                        try {
                            User loggedInUser = authService.login(loginUsername, loginPassword);
                            System.out.println("Welcome " + loggedInUser.getUsername());

                            Role role = loggedInUser.getRole();

                            if (role == Role.CLIENT) {
                                ClientView.clientDashboard(loggedInUser, sc);
                            } else if (role == Role.BUILDER) {
                                BuilderView.builderDashboard(loggedInUser, sc);
                            } else if (role == Role.PROJECT_MANAGER) {
                                ProjectManagerView.ProjectManagerDashboard(loggedInUser, sc);
                            }

                        } catch (ValidationException e) {
                            logger.severe("Login failed: " + e.getMessage());
                        }
                    }

                    case 3 -> {
                        System.out.println("Exiting application...");
                        sc.close();
                        System.exit(0);
                    }

                    default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }

            } catch (ValidationException ve) {
                System.out.println("Error: " + ve.getMessage());
            }
        }
    }
}
