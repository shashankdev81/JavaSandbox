package object_orinted_design.task_scheduler_generic;

public interface ISchedulable<T, R> {

    public Schedule getSchedule();

    public boolean isRecurring();

    public TaskResponse<R> execute(TaskRequest<T> taskRequest);

    public void notifyOnFinish(TaskResponse<R> response);

}
