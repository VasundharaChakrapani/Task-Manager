 import org.junit.*;
 import static org.junit.Assert.*;
 public class TaskManagerTest {
    private TaskManger manager;
    @Before
    public void setup(){
         manager=new TaskManger();

    }
   
    @Test
    public void testAddTask() {
        
        manager.clearAllTasks();
        Task task=new Task("v", "2334-08-06", "high");
        manager.addTask(task);
        assertEquals(1,manager.getTaskCount());  
    }

    @Test
    public void testDeleteTask(){

         Task task=new Task("v", "2334-08-06", "high");
        manager.addTask(task);
        assertEquals(1,manager.getTaskCount()); 
        manager.deleteTask(0);
        assertEquals(0,manager.getTaskCount());
    }

    @Test
    public void testCompleteTest(){
        Task task=new Task("bleh","9593-07-07","low");
        manager.addTask(task);
        assertEquals(1,manager.getTaskCount());
        manager.completeTask(0);
        assertTrue(task.isCompleted);

    }

    
}
