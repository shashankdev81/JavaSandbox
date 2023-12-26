package object_orinted_design.task_scheduler;

import java.util.Date;

public class SimpleTask implements ISchedulable {

    @Override
    public long getScheduledTime() {
        return 2000;
    }

    @Override
    public long getFrequency() {
        return 2000;
    }

    @Override
    public TaskResponse execute(TaskRequest taskRequest) {
        return new TaskResponse("Hi this is a simple task", true);
    }

    @Override
    public void notifyOnFinish(TaskResponse response) {
        System.out.println(response.getResult());
    }
}
