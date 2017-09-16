/*The purpose of this class is to create an "counter" object that can communicate between the two running threads. 
  It is a small class but is vital to the execution of the program. */
  
  public class TaskCounter {
    int count = 0; //initialize the counter 
        //Constructor
        public TaskCounter(){
            count = 0;
        }
        //Increments the counter 
        public void increment(){
            count++;
        }
        //Returns the counter
        public int getCounter(){
            return count;
        }
}
