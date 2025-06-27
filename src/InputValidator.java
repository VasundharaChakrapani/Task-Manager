import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getValidName(Scanner scanner) {
        String name;
        do {
            System.out.print("Enter task name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Task name cannot be empty.");
            }
        } while (name.isEmpty());
        return name;
    }

    public static String getValidDueDate(Scanner scanner) {
        String dueDate;
        while (true) {
            System.out.print("Enter due date (yyyy-MM-dd): ");
            dueDate = scanner.nextLine().trim();
            try {
                LocalDate.parse(dueDate, DATE_FORMAT);
                return dueDate;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use yyyy-MM-dd.");
            }
        }
    }

    public static String getValidPriority(Scanner scanner) {
        String priority;
        do {
            System.out.print("Enter priority (High/Medium/Low): ");
            priority = scanner.nextLine().trim().toLowerCase();
            if (!priority.equals("high") && !priority.equals("medium") && !priority.equals("low")) {
                System.out.println("Invalid priority.");
            }
        } while (!priority.equals("high") && !priority.equals("medium") && !priority.equals("low"));
        return priority;
    }

    public static String getNonEmptyInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("This field cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }
}
