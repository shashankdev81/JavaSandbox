package object_orinted_design.task_scheduler_generic;

import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ScheduleableTask implements Callable<TaskResponse>, Delayed {

    private String taskId;
    private TaskRequest taskRequest;
    private ISchedulable scheduleable;

    public ScheduleableTask(TaskRequest taskRequest, ISchedulable scheduleable, String id) {

        this.taskRequest = taskRequest;
        this.scheduleable = scheduleable;
        this.taskId = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskRequest getTaskRequest() {
        return taskRequest;
    }

    public ISchedulable getScheduleable() {
        return scheduleable;
    }

    @Override
    public TaskResponse call() throws Exception {
        return scheduleable.execute(taskRequest);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return scheduleable.getSchedule().getNextExecutionTime();
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.valueOf(
                scheduleable.getSchedule().getNextExecutionTime() - o.getDelay(TimeUnit.MILLISECONDS))
            .intValue();
    }
}
