package object_orinted_design.task_scheduler_generic;

import java.util.concurrent.CompletableFuture;

public interface ITaskSchedulerAsync<T, R> {
    CompletableFuture<Void> submit(TaskRequest<T> taskRequest, ISchedulable<T, R> schedulable);
}
