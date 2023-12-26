package object_orinted_design.task_scheduler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;

public class TaskSchedulerApplication {

    public static void main(String[] args) {
        TaskSchedulerUsingScheduledExecuters taskScheduler = TaskSchedulerUsingScheduledExecuters.getInstance();
        ScheduledFuture future = taskScheduler.submit(new TaskRequest("PrintName"),
            new SimpleTask());
        sleep();
        future.cancel(true);
        sleep();
        taskScheduler.shutDown();
    }

    private static void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
