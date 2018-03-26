/*
  This is a command-line based prototype of the Personal Task Management System. The prototype is still under development
  but will incorporate the basic features of enabling a user to create a task (along with its respective date and time), complete
  a task (and input a rating of completeness), and end the current day which will result in a grade based on the completeness of
  the day's tasks.

  As of 9/16/2017:

  Next task: Test and clean up prototype
  Following task: Start implementing Database for efficient storage
  Looking into the future: Design GUI using JPanel and JFrame
*/


//Import scanner utility and time package

//import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.*;



public class PersonalTaskManagementSystem {
    //Main method
    public static void main(String[] args) {
        int counter = 0;
        File yourFile = new File("tasks.csv");
        try
        {
            yourFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream oFile = new FileOutputStream(yourFile, false);
        }

        catch (Exception e)
        {
            //do nothing
        }


        //Initialize array of tasks (maximum 12), Scanner, and TaskCounter
        Task[] tasks = new Task[12];

        try
        {

            BufferedReader br = new BufferedReader(new FileReader("tasks.csv"));
            String line = br.readLine();

            while(line != null && counter < 12){
                int id = 0;
                String name = "";
                LocalTime time = LocalTime.MIDNIGHT;
                int count = 0;
                try{


                    //goes up to 2 (changed from 3...may cause error)
                    for(int i = 0; i<line.length(); i++){
                        int j = i+1;
                        String input = line.substring(i,j);
                        if(input.equals(",")) count++;
                        else if(count == 0) id = Integer.parseInt(input);
                        else if(count == 1) name = input;
                        else if(count == 2) time = LocalTime.parse(input);
                    }
                    line = br.readLine();
                }
                catch(Exception e){
                    break;
                }
                tasks[counter] = new Task(id, name, time);
                counter++;
            }

            if(counter == 12) System.out.println("task limit reached.");

        }

        catch (Exception e)
        {
            System.out.println("Information cannot be retrieved.");
        }






        Scanner sc = new Scanner(System.in);
        TaskCounter count = new TaskCounter();

        //Define Runnable that InputThread will execute in background.
        Runnable InputHandler = new Runnable() {
            boolean isComplete = false; //Initialize end token

            //Define run function (What will the thread actually do?)
            public void run()
            {
                while (isComplete == false) //Make sure user is not done
                {
                    String command = "";  //Initialize command
                    if (sc.hasNext()) {
                        command = sc.nextLine(); //User input
                    }

                    //'add task' function
                    if (command.equals("add task")) {
                        System.out.println("Input task name");
                        String name = sc.nextLine();
                        System.out.println("Input task time");
                        String t = sc.nextLine();
                        int id = count.getCounter();
                        LocalTime time = LocalTime.parse(t);    //User enters time in as a string (must be in hh:mm format); parsed into LocalTime
                        if(count.getCounter() < 12) {
                            tasks[count.getCounter()] = new Task(id, name, time);    //Task number (count + 1) created (See Task class for reference)
                            try {
                                FileWriter fw = new FileWriter("tasks.csv");
                                fw.append(Integer.toString(id));
                                fw.append(',');
                                fw.append(name);
                                fw.append(',');
                                fw.append(time.toString());
                                fw.append('\n');

                            } catch (Exception e) {
                                System.out.println("Task Information cannot be stored. Exiting program will result in a loss of information.");
                            }
                            System.out.println("task " + name + " created.");    //Confirmation to user
                            count.increment();    //Increment task counter
                        }
                        else System.out.println("task limit reached");
                    }

                    //'complete task' function
                    else if (command.equals("complete task")) {
                        Task t = new Task(-1, "null", LocalTime.MIDNIGHT);     //'null' task created; used to determine whether task t exists
                        System.out.println("Which task would you like to complete? (Enter name)");
                        String name = sc.nextLine();
                        //Search for task 'name'; assumes no duplicates in name
                        for (int i = 0; i < tasks.length; i++) {
                            if ((tasks[i].getName()).equals(name)) {
                                t = tasks[i];  //If task found, set task t to equal that task
                                break;
                            }
                        }

                        //If task t's id still equals -1, task 'name' doesn't exist
                        if (t.getId() == -1) {
                            System.out.println("Task not found");

                        } else {
                            System.out.println("Please rate the completeness of this task out of 10");  //User rates (him/her)self based on completeness of task
                            String r = sc.nextLine();
                            t.addRating(Integer.parseInt(r)); //Rating is inputted as a string and then parsed to integer.
                        }

                    }

                    //end task function
                    else if (command.equals("end day")) {
                        double ratingTotal = 0;  //User's total score is initialized
                        //Sum ratings of all tasks
                        if(count.getCounter() == 0)
                        {
                            System.out.println("No tasks created.");
                            System.exit(0);
                        }
                        for (int i = 0; i < count.getCounter(); i++) {
                            ratingTotal += tasks[i].getRating();
                        }
                        double totalPoints = count.getCounter() * 10;  //Since all ratings are out of 10, total # of possible points is 10 * (number of tasks)
                        double percent = (ratingTotal / totalPoints) * 100;  //Compute score and convert into percentage
                        String grade = "N/A";  //Initialize grade.
                        //Determine grade based on percentage. Standard grading scale used.
                        if ((percent >= 0) && (percent <= 59)) grade = "F";
                        else if ((percent > 59) && (percent <= 69)) grade = "D";
                        else if ((percent > 69) && (percent <= 79)) grade = "C";
                        else if ((percent > 79) && (percent <= 89)) grade = "B";
                        else if ((percent > 89) && (percent <= 100)) grade = "A";
                        else System.out.println("Invalid Grade");   //If grade is somehow beyond range, inform user.
                        System.out.println("All tasks completed. Your grade for today is: " + grade);
                        isComplete = true;  //All tasks completed.
                    }



                }

                System.exit(0);
            }
        };

        Runnable Timer = new Runnable()
        {
            public void run()
            {
                while(true)
                {
                    int numTasks = count.getCounter();
                    if (numTasks == 0) {
                        System.out.println("Waiting for task......");
                        try {
                            Thread.sleep(60000);
                        } catch (Exception e) {
                            System.out.println("Interrupted");
                        }
                    }

                    for (int taskQueue = 0; taskQueue < numTasks; taskQueue++) {
                        if ((LocalTime.now().getHour() == tasks[taskQueue].getTime().getHour()) && (Math.abs(LocalTime.now().getMinute() - tasks[taskQueue].getTime().getMinute()) == 0)) {
                            System.out.println("It's time for task '" + tasks[taskQueue].getName() + "' !");
                            try {
                                Thread.sleep(60000);
                            } catch (Exception e) {
                                System.out.println("Interrupted");
                            }
                        }
                    }
                }
            }
        };

        Thread InputThread = new Thread(InputHandler);
        Thread TimerThread = new Thread(Timer);
        InputThread.start();
        TimerThread.start();
    }
}
