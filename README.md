# PersonalTaskManagementSystem
The goal behind the PTMS project is to create a simple software written in Java that can help a user manage his or her day. The user will be able to input a task name, a time upon which the task should begin, and a rating (out of 10) for how well the task was completed (if it was). Once the day is over, the user clicks the "End Day" button which will clear all tasks from the system memory and display a "grade" representing the user's performance on that specific day. If a task is left incomplete, the rating is automatically set to 0. This grade is calculated through the following process:
  1. Total_Points = (# of tasks) * 10;
  2. Score = sum(rating for each task);
  3. Percentage = (Score/Total_Points) * 100; 
  4. If(Percentage <= 59), Grade = 'F';
     Else If(Percentage > 59 && Percentage <= 69), Grade = 'D';
     Else If(Percentage > 69 && Percentage <= 79), Grade = 'C';
     Else If(Percentage > 79 && Percentage <= 89), Grade = 'B';
     Else Grade = 'A'; 
 
 The PTMS can later be modified to be used by companies to manage inventories or by teachers to keep track of their students' performance in their class.
 
 Please feel free to contribute to the project! 
