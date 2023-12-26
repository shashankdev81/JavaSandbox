package object_orinted_design.task_scheduler_generic;

public class TaskRequest<T> {

    private String command;

    private T request;


    public TaskRequest(String command, T request) {
        this.command = command;
        this.request = request;
    }
}
