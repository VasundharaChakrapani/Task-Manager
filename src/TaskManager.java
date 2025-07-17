import java.util.*;
import java.io.*;
import java.time.LocalDate;


class TaskManger{
    private ArrayList<Task> tasks;
    public TaskManger(){
      tasks=  new ArrayList<>();
      loadTasksFromFile("tasks.dat");
    }

    public int getTaskCount(){
        return tasks.size();
    }


    //check id tasks r thr
    public boolean hasTasks() {
    return !tasks.isEmpty();
}


    //save tasks into file (serialization- save data)
    
    public void saveTasksToFile(String filename) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
        out.writeObject(tasks);
        System.out.println("Tasks saved to file.");
    } catch (IOException e) {
        System.out.println("Error saving tasks: " + e.getMessage());
    }
}

     //load tasks into arraylist tasks (saved data)
     @SuppressWarnings("unchecked")// gives an error if uk the type of data(<list> tasks ) ur converting to..if they are the same data time u loaded as before)

     public void loadTasksFromFile(String filename) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
        tasks = (ArrayList<Task>) in.readObject();//throws the error
        System.out.println("Tasks loaded from file.");
    } catch (IOException | ClassNotFoundException e) {
         tasks = new ArrayList<>();
        System.out.println("Error loading tasks: " + e.getMessage());
    }
}

//clear all tasked saved in file
public void clearAllTasks() {
    tasks.clear(); // clear in-memory list

    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tasks.dat"))) {
        oos.writeObject(tasks); // saves empty list to file
        System.out.println("All tasks deleted.");
    } catch (IOException e) {
        System.out.println("Error clearing tasks: " + e.getMessage());
    }
}

//export tasks to .txt file using bufferedwriter
public void exportTasksToTextFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
        if (tasks.isEmpty()) {
            writer.write("No tasks to display.");
        } else {
            int count = 1;
            for (Task task : tasks) {
                writer.write(count++ + ". " + task.toFormattedString());
                writer.newLine();
                writer.write("------------------------");
                writer.newLine();
            }
        }
        System.out.println("Tasks exported to tasks.txt successfully.");
    } catch (IOException e) {
        System.out.println("Error writing to file: " + e.getMessage());
    }
}


//export tasks to .CSV file
public void exportTasksToCSV() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.csv"))) {
        // Write CSV header
        writer.write("Type,Name,DueDate,Priority,Status,Extra");
        writer.newLine();

        for (Task task : tasks) {
            String type = task instanceof WorkTask ? "Work" :
                          task instanceof PersonalTask ? "Personal" : "Generic";
            String extra = "";

            if (task instanceof WorkTask) {
                extra = ((WorkTask) task).getProject();
            } else if (task instanceof PersonalTask) {
                extra = ((PersonalTask) task).getOccasion();
            }

            String line = String.format("%s,%s,%s,%s,%s,%s",
                    type,
                    task.getName(),
                    task.getDueDate(),
                    task.getPriority(),
                    task.isCompleted() ? "Completed" : "Pending",
                    extra);

            writer.write(line);
            writer.newLine();
        }

        System.out.println("Tasks exported to tasks.csv successfully.");
    } catch (IOException e) {
        System.out.println("Error exporting to CSV: " + e.getMessage());
    }
}



//import tasks into csv files
public void importTasksFromCSV() {
    String fileName = "tasks.csv";
    int importedCount = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line = reader.readLine(); // Skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", -1); // -1 keeps empty strings
            if (parts.length >= 6) {
                String type = parts[0].trim();
                String name = parts[1].trim();
                String dueDate = parts[2].trim();
                String priority = parts[3].trim();
                String status = parts[4].trim();
                String extra = parts[5].trim();

                Task task;

                if (type.equalsIgnoreCase("Work")) {
                    task = new WorkTask(name, dueDate, priority, extra);
                } else if (type.equalsIgnoreCase("Personal")) {
                    task = new PersonalTask(name, dueDate, priority, extra);
                } else {
                    task = new Task(name, dueDate, priority);
                }

                if (status.equalsIgnoreCase("Completed")) {
                    task.markAsCompleted();
                }

                tasks.add(task);
                importedCount++;
            }
        }
        saveTasksToFile("tasks.dat"); // Save to tasks.dat
        System.out.println(importedCount + " tasks imported from CSV.");
    } catch (IOException e) {
        System.out.println("Error reading CSV file: " + e.getMessage());
    }
}




    //filter tasks  (filterType- priority,status....values-high/medium/low, completed/incomplete)
    public void filterTasks(String filterType,String value){
          if(filterType.equalsIgnoreCase("dueDate")){
            tasks.sort(Comparator.comparing(Task::getDueDateAsDate));
    System.out.println("Tasks sorted by due date:");
    for(Task task:tasks){
    task.displayTask();}
    return;}
         for (Task task : tasks) {
        if (filterType.equalsIgnoreCase("priority")) {
            if (task.getPriority().equalsIgnoreCase(value)) {
                task.displayTask();
            }
        } else if (filterType.equalsIgnoreCase("status")) {
            boolean isCompleted = value.equalsIgnoreCase("completed");
            if (task.isCompleted() == isCompleted) {
                task.displayTask();
            }
        }
       

        }
    }
    public void searchTasksByName(String keyword) {
    boolean found = false;
    for (Task task : tasks) {
        if (task.getName().toLowerCase().contains(keyword.toLowerCase())) {
            task.displayTask();
            found = true;
        }
    }
    if (!found) {
        System.out.println("No tasks found with that keyword.");
    }
}






  //add task

    public void addTask(Task task){
        
        tasks.add(task);
        System.out.println("task added successfully");
    }



 //edit task
    public void editTask(int index, Scanner scanner) {
    if (index < 0 || index >= tasks.size()) {
        System.out.println(tasks.size());
        System.out.println("Invalid task index.");
        return;
    }

    Task task = tasks.get(index);
    System.out.println("Editing Task:");
    task.displayTask();

    System.out.println("\n--- Leave input blank to keep the current value ---");

    // Name
    String newName = InputValidator.getValidName(scanner, true);
    if (!newName.isEmpty()) {
        task.setName(newName);
    }

    // Due Date
    String newDueDate = InputValidator.getValidDueDate(scanner, true);
    if (!newDueDate.isEmpty()) {
        try {
            task.setDueDate(LocalDate.parse(newDueDate));
        } catch (Exception e) {
            System.out.println("Invalid date format. Keeping original.");
        }
    }

    // Priority
    String newPriority = InputValidator.getValidPriority(scanner, true);
    if (!newPriority.isEmpty()) {
        task.setPriority(newPriority.toUpperCase());
    }

    // Status
 String newStatus = InputValidator.getValidStatus(scanner, true);
if (!newStatus.isEmpty()) {
    task.setisCompleted(newStatus.equalsIgnoreCase("COMPLETED"));
}


    // WorkTask: edit project name
    if (task instanceof WorkTask) {
        String newProject = InputValidator.getValidNote(scanner, true);
        if (!newProject.isEmpty()) {
            ((WorkTask) task).setProject(newProject);
        }
    }

    // PersonalTask: edit note
    if (task instanceof PersonalTask) {
        String newNote = InputValidator.getValidNote(scanner, true);
        if (!newNote.isEmpty()) {
            ((PersonalTask) task).setOccasion(newStatus);
        }
    }

    System.out.println("\n✅ Task updated successfully.");
}


    //view tasks
    public  void viewTasks(){
        if(tasks.isEmpty()){
            System.out.println("no tasks made");
            return;
        }
        for(int i=0;i<tasks.size();i++){
            System.out.print((i+1)+".");
            tasks.get(i).displayTask();
            System.out.println();
        }
    }

    //mark as completed
     public void completeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsCompleted();
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Invalid task number.");
        }
    }


    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    
}


