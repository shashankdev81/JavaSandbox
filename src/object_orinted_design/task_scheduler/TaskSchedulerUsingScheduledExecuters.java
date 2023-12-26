package object_orinted_design.task_scheduler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TaskSchedulerUsingScheduledExecuters implements ITaskScheduler {

    public static TaskSchedulerUsingScheduledExecuters INSTANCE = new TaskSchedulerUsingScheduledExecuters();

    private int POOL_SIZE = 100;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(POOL_SIZE);

    private PriorityQueue<Delayed> taskQueue = new PriorityQueue<>();

    private Set<ScheduledFuture> futureSet = new HashSet<>();

    private TaskSchedulerUsingScheduledExecuters() {

    }

    public static TaskSchedulerUsingScheduledExecuters getInstance() {
        return INSTANCE;
    }

    @Override
    public ScheduledFuture submit(TaskRequest taskRequest, ISchedulable schedulable) {
        SimpleScheduleableTask scheduleableTask = new SimpleScheduleableTask(taskRequest,
            schedulable);
        ScheduledFuture future = executorService.scheduleAtFixedRate(scheduleableTask,
            scheduleableTask.getScheduleable().getScheduledTime(),
            scheduleableTask.getScheduleable().getFrequency(), TimeUnit.MILLISECONDS);
        futureSet.add(future);
        return future;
    }


    @Override
    public void shutDown() {
        futureSet.parallelStream().forEach(f -> f.cancel(true));
        executorService.shutdown();
    }
}
