class ThreadDemo extends Thread {
   private Thread t;
   private String threadName;
   
   ThreadDemo( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      System.out.println("Running " +  threadName );
      try {
         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // 让线程睡醒一会
            Thread.sleep(1000);
         }
      }catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}
 
public class Draft {
 
   public static void main(String args[]) {
      int a = 232;
      int b = 5 ;
      for (int i=0; i<b; i++){
    	  if (i==(b-1)){
    		  System.out.println(a/b*i+1 + "-" + a);
    	  }
    	  else{
    		  System.out.println(a/b*i+1 + "-" + a/b*(i+1));
    	  }
      }
   }   
}