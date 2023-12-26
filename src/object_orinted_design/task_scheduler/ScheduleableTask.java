package object_orinted_design.task_scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Delayed;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ScheduleableTask implements Callable<TaskResponse>, Delayed {

    private TaskRequest taskRequest;

    private ISchedulable scheduleable;

    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    private Future future;

    public ScheduleableTask(TaskRequest taskRequest, ISchedulable scheduleable) {
        this.taskRequest = taskRequest;
        this.scheduleable = scheduleable;
        this.future = new CompletableFuture();
    }

    @Override
    public TaskResponse call() throws Exception {
        return scheduleable.execute(taskRequest);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return scheduleable.getScheduledTime();
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.valueOf(scheduleable.getScheduledTime() - o.getDelay(TimeUnit.MILLISECONDS))
            .intValue();
    }
}
