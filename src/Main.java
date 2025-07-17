import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManger taskManager = new TaskManger();
        // CommandHandler handler=new CommandHandler(taskManager);
        String filename = "tasks.dat";
            taskManager.loadTasksFromFile(filename);


        while (true) {
            System.out.println("\n== Task Manager ==");
            
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.println("6. filter based on priority ,status or due date");
            System.out.println("7.clear all tasks");
            System.out.println("8. search task by name");
            System.out.println("9. save tasks to .txt file ");
            System.out.println("10. save tasks to .csv file ");
            System.out.println("11.import tasks from .csv files!");
            System.out.println("12.edit tasks");
            System.out.print("Choose an option: ");
            System.out.println();
            //String choice=scanner.nextLine();
           

            int choice = -1;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Choose Task Type:\n1. Work Task\n2. Personal Task:  \n 3.or jus general stuff ");
                    int type = scanner.nextInt();
                     String name = InputValidator.getValidName(scanner,false);
                     String dueDate = InputValidator.getValidDueDate(scanner,false);
                     String priority = InputValidator.getValidPriority(scanner,false);
                     Task task = null;

    if (type==1) {
        System.out.print("Enter project name: ");
        String project = scanner.nextLine();
        task = new WorkTask(name, dueDate, priority, project);

    } else if (type==2) {
        System.out.print("Enter occasion: ");
        String occasion = scanner.nextLine();
        task = new PersonalTask(name, dueDate, priority, occasion);

    } else {
        task = new Task(name, dueDate, priority, false); // default/general task
    }
                    taskManager.addTask(task);
                    break;

                case 2:
                    taskManager.viewTasks();
                    break;

                case 3:
                    System.out.print("Enter task number to complete: ");
                    int completeIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    taskManager.completeTask(completeIndex);
                    break;

                case 4:
                    System.out.print("Enter task number to delete: ");
                    int deleteIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    taskManager.deleteTask(deleteIndex);
                    break;

                case 5:
                taskManager.saveTasksToFile(filename);
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                    
                    case 6:
    System.out.println("Filter by:\n1. Priority\n2. Status\n 3.Due Date");
    String filterOption = scanner.nextLine();

    if (filterOption.equals("1")) {
        System.out.print("Enter priority (High/Medium/Low): ");
         priority = scanner.nextLine();
        taskManager.filterTasks("priority", priority);
    } else if (filterOption.equals("2")) {
        System.out.print("Show [Completed] or [Incomplete]: ");
        String status = scanner.nextLine();
        if (status.equalsIgnoreCase("Incomplete")) {
            taskManager.filterTasks("status", "incomplete");
        } else {
            taskManager.filterTasks("status", "completed");
        }
    }
        else if(filterOption.equals("3")){
           System.out.print("enter the due date: (yyyy-mm-dd)") ;
           dueDate=scanner.nextLine();
           taskManager.filterTasks("dueDate", dueDate);;

        }


     else {
        System.out.println("Invalid filter option.");
    }
    break;

    case 7:
    taskManager.clearAllTasks();
    break;

    case 8:
     System.out.print("Enter keyword to search: ");
    String keyword = scanner.nextLine();
    taskManager.searchTasksByName(keyword);
    break;

    case 9:
    taskManager.exportTasksToTextFile();
    break;

    case 10:
    taskManager.exportTasksToCSV();
    break;

    case 11:
      taskManager.importTasksFromCSV();
    break;

    case 12:
    
    if (!taskManager.hasTasks()) {
        System.out.println("No tasks to edit.");
        break;
    }

    // Display all tasks with indexes
    taskManager.viewTasks();

    System.out.print("Enter the task no  to edit: ");
    String input = scanner.nextLine().trim();

    try {
        int index = Integer.parseInt(input);
        index=index-1;
        taskManager.editTask(index, scanner);  // internally handles blank input using allowBlank = true
        taskManager.saveTasksToFile("tasks.dat");               // Save changes after editing
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
    } catch (IndexOutOfBoundsException e) {
        System.out.println("Invalid index. No such task.");
    }
    break;


                default:

                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
