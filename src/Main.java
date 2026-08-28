import java.util.logging.Logger;
import java.util.logging.Level;

public class Main{

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static int sharedCounter = 0;

    public static void main(String[] args) throws InterruptedException{
        LOGGER.info("Starting JFR demo...");

        // Create 10 threads to access a synchronized method
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(
                    () -> {
                        for (int j = 0; j < 100000; j++) {
                            processLock();
                        }
                    }, "WorkerThread-" + i
            );
        }
        for (Thread t : threads){
            t.start();
        }
        for (Thread t: threads){
            t.join();
        }
        LOGGER.info("Demo FINISHED.");
    }


    private static synchronized void processLock(){
        sharedCounter++;
        double math = 0;
        for (int i = 0; i < 500; i++) {
            math += Math.sqrt(i);
        }
        if (sharedCounter %2500 == 0){
            LOGGER.log(Level.INFO, "Counter reached: ", new Object[]{sharedCounter, math});
        }
    }
}