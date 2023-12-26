package object_orinted_design.task_scheduler_generic;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class TaskSchedulerApplication {

    public static void main(String[] args) {
        TaskSchedulerUsingCompletableFuture<String, String> taskScheduler = TaskSchedulerUsingCompletableFuture.getInstance();
        ISchedulable<String, String> task = new SimpleTask(5000, Duration.ofMinutes(1));
        CompletableFuture<Void> outcome = taskScheduler.submit(
            new TaskRequest<String>("PrintName", "RequestBody"), task);
        System.out.println("An example of task scheduler");
        outcome.join();
        System.out.println("See the results");

    }
}
