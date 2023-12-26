package object_orinted_design.rate_limiter_new;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskExample {


    public static void main(String[] args) {
        // Create a scheduled executor service with a single thread
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        // Define a task to be scheduled
        Runnable task = () -> {
            System.out.println("Task executed at: " + System.currentTimeMillis());
        };

        // Schedule the task to run every 2 seconds with an initial delay of 0 seconds
        // Note: The initial delay is the time before the first execution starts
        // The subsequent executions will be spaced at the fixed rate specified (2 seconds in this case)
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(task, 0,
            2, TimeUnit.SECONDS);

        // Sleep for a while to observe the scheduled task running
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cancel the scheduled task after observing it for a while
        scheduledFuture.cancel(true);

        // Shut down the executor service
        scheduledExecutorService.shutdown();
    }
}
