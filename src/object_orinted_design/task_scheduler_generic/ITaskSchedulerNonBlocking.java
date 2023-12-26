package object_orinted_design.task_scheduler_generic;

import java.util.concurrent.Future;

public interface ITaskSchedulerNonBlocking {

    public void terminate();

    public String schedule(TaskRequest taskRequest, ISchedulable schedulable);

    public TaskResponse get(String taskId);

    public TaskResponse getIfDone(String taskId);

    public Future<TaskResponse> getPromise(String taskId);

}
