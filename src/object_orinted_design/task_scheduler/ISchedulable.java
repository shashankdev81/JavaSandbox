package object_orinted_design.task_scheduler;

public interface ISchedulable {

    public long getScheduledTime();

    public long getFrequency();

    public TaskResponse execute(TaskRequest taskRequest);

    public void notifyOnFinish(TaskResponse response);

}
