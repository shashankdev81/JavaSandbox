package object_orinted_design.task_scheduler_generic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskSchedulerUsingCompletableFuture<T, R> implements ITaskSchedulerAsync<T, R> {

    public static TaskSchedulerUsingCompletableFuture INSTANCE = new TaskSchedulerUsingCompletableFuture();

    private int POOL_SIZE = 100;

    private ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

    private TaskSchedulerUsingCompletableFuture() {

    }

    public static TaskSchedulerUsingCompletableFuture getInstance() {
        return INSTANCE;
    }

    @Override
    public CompletableFuture<Void> submit(final TaskRequest<T> taskRequest, final ISchedulable<T, R> schedulable) {
        //ScheduleableTask scheduleableTask = new ScheduleableTask(taskRequest, schedulable);
        CompletableFuture<TaskResponse<R>> result = CompletableFuture.supplyAsync(() -> {
            return schedulable.execute(taskRequest);
        }, CompletableFuture.delayedExecutor(schedulable.getSchedule().getNextExecutionTime(), TimeUnit.MILLISECONDS));
        CompletableFuture<Void> outcome = result.thenAcceptAsync(response -> schedulable.notifyOnFinish(response));
        return outcome;
    }


}
