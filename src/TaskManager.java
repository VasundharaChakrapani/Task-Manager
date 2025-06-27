import java.util.*;
import java.io.*;
class TaskManger{
    private ArrayList<Task> tasks;
    public TaskManger(){
      tasks=  new ArrayList<>();
      loadTasksFromFile("tasks.dat");
    }

    //save tasks into file
    
    public void saveTasksToFile(String filename) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
        out.writeObject(tasks);
        System.out.println("Tasks saved to file.");
    } catch (IOException e) {
        System.out.println("Error saving tasks: " + e.getMessage());
    }
}

     //load tasks into arraylist tasks
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

//export tasks to file using bufferedwriter
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

    //view tasks
    public  void viewTasks(){
        if(tasks.isEmpty()){
            System.out.println("no tasks made");
            return;
        }
        for(int i=0;i<tasks.size();i++){
            System.out.print((i+1)+".");
            tasks.get(i).displayTask();
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

    // Delete a task
    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }
}


