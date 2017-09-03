/*
  This is a command-line based prototype of the Personal Task Management System. The prototype is still under development 
  but will incorporate the basic features of enabling a user to create a task (along with its respective date and time), complete 
  a task (and input a rating of completeness), and end the current day which will result in a grade based on the completeness of
  the day's tasks.
  
  Next tasK: debug grade calculator (issue: always resulting to F)
  Following task: threads for time checking and alerting user
*/


//Import scanner utility and time package
import java.util.Scanner;
import java.time.*;

public class PersonalTaskManagementSystem
{
    public static void main(String[] args)
    {
        //Define and initialize all global variables: tasks (limit of 12), scanner, command, isComplete, and the counter
        Task[] tasks = new Task[12];
        Scanner sc = new Scanner(System.in);
        String command = "";
        command = sc.nextLine();
        boolean isComplete = false;
        int count = 0;
        
        //Initiate command line input. User enters 'end' to exit program. 
        while(!(command.equals("end")))
        {
            //'add task' function
            if(command.equals("add task")){
                System.out.println("Input task name");
                String name = sc.nextLine();
                System.out.println("Input task time");
                String t = sc.nextLine();
                int id = count;
                LocalTime time = LocalTime.parse(t);    //User enters time in as a string (must be in hh:mm format); parsed into LocalTime
                tasks[count] = new Task(id, name, time);    //Task number (count + 1) created (See Task class for reference)
                System.out.println("task " + name + " created.");    //Confirmation to user
                count++;    //Increment task counter 
            }
            
            //'complete task' function
            else if(command.equals("complete task"))
            {
                Task t = new Task(-1,"null", LocalTime.MIDNIGHT);     //'null' task created; used to determine whether task t exists
                System.out.println("Which task would you like to complete? (Enter name)");
                String name = sc.nextLine();
                //Search for task 'name'; assumes no duplicates in name
                for(int i = 0; i<tasks.length; i++)
                {
                    System.out.println(i);
                    if((tasks[i].getName()).equals(name)){
                        t = tasks[i];  //If task found, set task t to equal that task
                        break;
                    }

                }
                
                //If task t's id still equals -1, task 'name' doesn't exist
                if(t.getId() == -1)
                {
                    System.out.println("Task not found");
                    break;  //We don't want to proceed further if the task isn't found
                }
                System.out.println("Please rate the completeness of this task out of 10");  //User rates (him/her)self based on completeness of task
                String r = sc.nextLine();
                t.addRating(Integer.parseInt(r)); //Rating is inputted as a string and then parsed to integer. 

            }
            
            //end task function
            else if(command.equals("end day"))
            {
                int ratingTotal = 0;  //User's total score is initialized 
                //Sum ratings of all tasks
                for(int i = 0; i<count; i++)
                {
                    ratingTotal += tasks[i].getRating(); 
                }
                int totalPoints = tasks.length * 10;  //Since all ratings are out of 10, total # of possible points is 10 * (number of tasks)
                int percent = (ratingTotal/totalPoints) * 100;  //Compute score and convert into percentage
                String grade = "N/A";  //Initialize grade.
                //Determine grade based on percentage. Standard grading scale used. 
                if((percent >= 0) && (percent <= 59)) grade = "F";
                else if((percent > 59) && (percent <= 69)) grade = "D";
                else if((percent > 69) && (percent <= 79)) grade = "C";
                else if((percent > 79) && (percent <= 89)) grade = "B";
                else if(percent > 89) && percent <= 100) grade = "A";
                else System.out.println("Invalid Grade");   //If grade is somehow beyond range, inform user. 
                System.out.println("All tasks completed. Your grade for today is: " + grade);
                isComplete = true;  //All tasks completed.
            }
            //If end of day, break out of loop. 
            if(isComplete == true){
                command = "end";
                break;
            }
            //If not end of day, proceed to next command. 
            else{
                command = sc.nextLine();
            }
        }

    }
}
