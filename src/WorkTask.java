
public class WorkTask extends Task {
    private String project;
     private static final long serialVersionUID = 1L;

    public WorkTask(String name, String dueDate, String priority, String project) {
        super (name, dueDate, priority);
        this.project = project;
    }

    public String toFormattedString() {
    return super.toFormattedString() + "\nProject: " + project;
}
    public String getProject(){
        return project;
    }

    @Override
    public void displayTask() {
        super.displayTask();
        System.out.println("Project: " + project);
    }
}