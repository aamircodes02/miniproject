package org.zeta.views;

import org.zeta.model.User;
import org.zeta.service.implementation.BuilderService;
import org.zeta.validation.CommonValidator;
import org.zeta.validation.ValidationException;

import java.util.Scanner;
import java.util.logging.Logger;

public class BuilderView {

    static BuilderService builderService = new BuilderService();
<<<<<<< HEAD

    // Accept scanner from Main
    public static void builderDashboard(User builder, Scanner sc) {
=======
static Logger logger=Logger.getLogger("BuilderView");
    public static void builderDashboard(User builder) {
>>>>>>> 68e3ba8a767a6995a7fde6130a30d9a81712f387

        boolean running = true;

        System.out.println("HI Builder");

        while (running) {
            System.out.println("""
                    
                    ===== Builder Dashboard =====
                    1. View my tasks
                    2. Update tasks
                    3. Logout
                    Enter your choice:
                    """);

            try {
<<<<<<< HEAD

                // ALWAYS use nextLine (never nextInt)
                String input = sc.nextLine();
                int builderChoice = CommonValidator.validateInteger(input, "Builder menu choice");

=======
                int builderChoice = sc.nextInt();
                sc.nextLine();
>>>>>>> 68e3ba8a767a6995a7fde6130a30d9a81712f387
                switch (builderChoice) {

                    case 1 -> {
                        System.out.println("\n--- Your Tasks ---");
                        BuilderService.listOfTasks(builder);
<<<<<<< HEAD
                    }
=======
                        break;
                    case 2:
                        System.out.println("Enter Task");
                        String taskName=sc.nextLine();
                        BuilderService.updateStatus(taskName,builder);
                        logger.info("Status updated");
                        break;
>>>>>>> 68e3ba8a767a6995a7fde6130a30d9a81712f387

                    case 2 -> {
                        System.out.println("Enter Task Name:");
                        String taskName = sc.nextLine();

                        BuilderService.updateStatus(taskName, builder);
                    }

                    case 3 -> {
                        System.out.println("Logging out...");
                        running = false;   // return to Main menu
                    }

                    default -> System.out.println("Invalid choice. Please try again.");
                }

            } catch (ValidationException ve) {
                System.out.println("Error: " + ve.getMessage());
            }
        }
    }
}
