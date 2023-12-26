package object_orinted_design.task_scheduler;

import java.util.PriorityQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;

public class TaskScheduler implements ITaskScheduler {

    public static TaskScheduler INSTANCE = new TaskScheduler();

    private int POOL_SIZE = 100;

    private ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

    private PriorityQueue<Delayed> taskQueue = new PriorityQueue<>();

    private TaskScheduler() {

    }

    public static TaskScheduler getInstance() {
        return INSTANCE;
    }

    public Future submit(TaskRequest taskRequest, ISchedulable schedulable) {
        ScheduleableTask scheduleableTask = new ScheduleableTask(taskRequest, schedulable);
        taskQueue.offer(scheduleableTask);
        return scheduleableTask.getFuture();
    }

    @Override
    public void shutDown() {

    }

    private class Scheduler implements Runnable {

        @Override
        public void run() {
            while (true) {
                ScheduleableTask task = (ScheduleableTask) taskQueue.poll();
                Future<TaskResponse> future = executorService.submit(task);
                task.setFuture(future);
            }
        }
    }

}
