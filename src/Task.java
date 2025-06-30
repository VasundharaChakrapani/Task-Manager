import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String name;
    protected String dueDate;
    protected String priority;
    protected boolean isCompleted;


    //constructor
    public Task(String name,String dueDate,String priority,boolean isCompleted){
        this.name=name;
        this.dueDate=dueDate;
        this.priority=priority;
        this.isCompleted=false;
    }
    public Task(String name, String dueDate, String priority) {
    this(name, dueDate, priority, false); // Calls the other constructor
}
    //getters
     public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }


    public LocalDate getDueDateAsDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(dueDate, formatter);
}

    public String getPriority() {
        return priority;
    }

    // setters

    public void setName(String name) {
    this.name = name;
}

public void setDueDate(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    this.dueDate = date.format(formatter);
}


  public void setPriority(String priority) {
    this.priority = priority;
}

    public boolean isCompleted() {
        return isCompleted;
    }

      public void setisCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
}

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    //format to save tasks to file
    public String toFormattedString() {
    return "Task: " + name + "\nDue: " + dueDate +
           "\nPriority: " + priority + 
           "\nStatus: " + (isCompleted ? "Completed" : "Pending");
}


    // Display task
    public void displayTask() {
        String status = isCompleted ? "Done" : "Pending";
        System.out.println("Task: " + name);
        System.out.println("due : "+dueDate);
        System.out.println("priority : "+priority);
        System.out.println("status : "+status);

   
    }
}


    

