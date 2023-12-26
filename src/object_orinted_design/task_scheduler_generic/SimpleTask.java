package object_orinted_design.task_scheduler_generic;

import java.time.Duration;

public class SimpleTask implements ISchedulable<String, String> {

    private Schedule schedule;

    public SimpleTask(long executionTime, Duration period) {
        this.schedule = new Schedule(executionTime, period);
    }

    @Override
    public Schedule getSchedule() {
        return null;
    }

    @Override
    public boolean isRecurring() {
        return schedule.getFrequency() != null;
    }

    @Override
    public TaskResponse<String> execute(TaskRequest<String> taskRequest) {
        return new TaskResponse("200 OK", true, "Hi this is a simple task");
    }

    @Override
    public void notifyOnFinish(TaskResponse<String> response) {
        System.out.println(response.getStatus() + "|" + response.getResult());
    }
}
