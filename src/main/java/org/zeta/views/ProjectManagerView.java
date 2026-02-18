package org.zeta.views;

import org.zeta.dao.ProjectDao;
import org.zeta.dao.TaskDao;
import org.zeta.dao.UserDao;
import org.zeta.model.User;
import org.zeta.service.implementation.ProjectManagerService;
import org.zeta.validation.CommonValidator;
import org.zeta.validation.ValidationException;

import java.util.Scanner;
import java.util.logging.Logger;

public class ProjectManagerView {

    private static final Logger logger =
            Logger.getLogger(ProjectManagerView.class.getName());

    static ProjectManagerService managerService =
            new ProjectManagerService();

    // Scanner comes from Main
    public static void ProjectManagerDashboard(User projectManager, Scanner sc) {

        ProjectDao projectDao = new ProjectDao();
        UserDao userDao = new UserDao();
        TaskDao taskDao = new TaskDao();

        System.out.println("Hi Project Manager: " + projectManager.getUsername());

        boolean running = true;

        while (running) {

            System.out.println("""
                    
                    ===== Project Manager Dashboard =====
                    1. View your upcoming projects
                    2. Add project details (Start project)
                    3. Create tasks for a project
                    4. Assign tasks to builders
                    5. List clients
                    6. View projects by client
                    7. Logout
                    Enter your choice:
                    """);

            try {

                String input = sc.nextLine();
                int choice = CommonValidator.validateInteger(input, "Menu choice");

                switch (choice) {

                    case 1 -> {
                        managerService.listProjects(projectDao, projectManager);
                    }

                    case 2 -> {
                        System.out.println("Enter Project ID to update:");
                        String projectId = sc.nextLine().trim();

                        System.out.println("Enter Project Description:");
                        String description = sc.nextLine().trim();

                        System.out.println("Enter the duration for this project:");
                        String durationStr = sc.nextLine();
                        int duration = CommonValidator.validateInteger(durationStr, "Duration");

                        managerService.addProjectDetails(
                                projectId, description, duration, projectDao, projectManager
                        );
                    }

                    case 3 -> {
                        System.out.println("Enter Project ID to create task in:");
                        String projectIdForTask = sc.nextLine().trim();

                        System.out.println("Enter Task Name:");
                        String taskName = sc.nextLine().trim();

                        managerService.createTask(
                                projectIdForTask, taskName, taskDao, projectDao, projectManager
                        );
                    }

                    case 4 -> {
                        System.out.println("Enter Project ID to assign tasks:");
                        String projectId = sc.nextLine().trim();

                        System.out.println("Enter Task ID to assign:");
                        String taskId = sc.nextLine().trim();

                        System.out.println("Enter Builder ID to assign:");
                        String builderId = sc.nextLine().trim();

                        managerService.assignTask(projectId, taskId, builderId, taskDao, userDao);
                    }

                    case 5 -> managerService.listClients(userDao);

                    case 6 -> {
                        System.out.println("Enter Client Username:");
                        String username = sc.nextLine().trim();

                        managerService.viewProjectsByClient(username, userDao, projectDao);
                    }

                    case 7 -> {
                        System.out.println("Logging out...");
                        running = false; // back to Main menu
                    }

                    default -> System.out.println("Invalid choice. Please try again.");
                }

            } catch (ValidationException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
