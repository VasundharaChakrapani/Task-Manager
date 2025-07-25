
public class PersonalTask extends Task {
    private static final long serialVersionUID = 1L;
    
    private String category;

    public PersonalTask(String name, String dueDate, String priority, String category) {
        super(name, dueDate, priority);
        this.category = category;
    }

    @Override
public String toFormattedString() {
    return (super.toFormattedString() + "\nOccasion: " + category);
}

    @Override
    public void displayTask() {
        super.displayTask();
        System.out.println("Category: " + category);
    }

    public String getOccasion(){
        return category;
    }
     public void setOccasion(String category){
        this.category=category;
     }
}

