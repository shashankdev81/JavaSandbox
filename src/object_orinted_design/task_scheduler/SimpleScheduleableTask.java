package object_orinted_design.task_scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class SimpleScheduleableTask implements Runnable {

    private TaskRequest taskRequest;

    public TaskRequest getTaskRequest() {
        return taskRequest;
    }

    public ISchedulable getScheduleable() {
        return scheduleable;
    }

    private ISchedulable scheduleable;

    public SimpleScheduleableTask(TaskRequest taskRequest, ISchedulable scheduleable) {
        this.taskRequest = taskRequest;
        this.scheduleable = scheduleable;
    }

    @Override
    public void run() {
        TaskResponse response = scheduleable.execute(taskRequest);
        scheduleable.notifyOnFinish(response);

    }

}
