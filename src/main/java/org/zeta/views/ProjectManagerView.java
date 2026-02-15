package org.zeta.views;

import org.zeta.dao.BaseDao;
import org.zeta.dao.ProjectDao;
import org.zeta.dao.UserDao;
import org.zeta.model.Project;
import org.zeta.model.Role;
import org.zeta.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

public class ProjectManagerView {

    private static final Logger logger = Logger.getLogger(ProjectManagerView.class.getName());

    public static void ProjectManagerDashboard() {
        Scanner sc = new Scanner(System.in);
        BaseDao<Project> project=new ProjectDao();
        UserDao user = new UserDao();

        System.out.println("HI Project manager");

        boolean running=true;
        while(running) {

            System.out.println("""
                Enter 1 to see list of all submitted projects
                Enter 2 to assign projects\s
                Enter 3 to view the list of clients\s
                Enter 4 to view list of projects submitted by particular client
                Enter 5 to logout""");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    project = new ProjectDao();
                    List<Project> projects = project.getAll();
                    for (Project p : projects) {
                        System.out.println(p.getProjectName());
                    }
                    break;
                case 2:
                    List<Project> proj = project.getAll();
                    for (Project p : proj) {
                        if (p.getBuilderId() == null) {
                            System.out.println(p.getProjectName());
                        }
                    }


                    break;
                case 3:

                    List<User> allClients = user.findByRole(Role.CLIENT);
                    for (User client : allClients) {
                        System.out.println(client.getUsername());
                    }
                    break;
                case 4:
                    System.out.println("Enter client username:");
                    String username = sc.nextLine();

                    Optional<User> clientOpt = user.findIdbyName(username);

                    if (clientOpt.isEmpty()) {
                        logger.info("No client with name " + username);
                        break;
                    }

                    User client = clientOpt.get();
                    String clientId = client.getId();

                    List<Project> clientProjects = project.getAll();

                    for (Project p : clientProjects) {
                        if (clientId.equals(p.getClientId())) {
                            System.out.println(p.getProjectName());
                        }
                    }

                    break;

                case 5:
                    System.out.println("Logging out...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }

        }
    }
}
