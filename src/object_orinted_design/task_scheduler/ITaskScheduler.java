package object_orinted_design.task_scheduler;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

public interface ITaskScheduler {

    public Future submit(TaskRequest taskRequest, ISchedulable schedulable);

    public void shutDown();
}
