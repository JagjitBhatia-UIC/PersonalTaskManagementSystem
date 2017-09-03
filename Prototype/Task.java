/* Task class that allows for efficient storage of task information. */

//import time package
import java.time.*;

public class Task
{
    //Initialize global variables id, name, time, and rating; these are all components of the Task object
    int id;
    String name;
    LocalTime time;
    int rating = 0;
    
    //Constructor; arguments are the tasks's id, name, and time (must be in LocalTime format)
    public Task(int id, String name, LocalTime time)
    {
        this.id = id;
        this.name = name;
        this.time = time;
    }
    
    //Method 'getId'; returns id of Task
    public int getId()
    {
        return id;
    }
    
    //Method 'getName'; returns name of task
    public String getName()
    {
        return name;
    }
    
    //Method 'getTime'; returns time when task begins
    public LocalTime getTime()
    {
        return time;
    }
    
    //Method 'addRating'; inputs and stores rating that user gives (him/her)self based on completeness of task; argument rating (Integer)
    public void addRating(int rating)
    {
        this.rating = rating;
    }
    
    //Method 'getRating'; returns completeness rating of task
    public int getRating()
    {
        return rating;
    }
}
