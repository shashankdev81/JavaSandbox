package object_orinted_design.task_scheduler;

public class TaskResponse {

    public TaskResponse(String result, boolean isSuccess) {
        this.result = result;
        this.isSuccess = isSuccess;
    }

    public String getResult() {
        return result;
    }

    private String result;

    private boolean isSuccess;


    public boolean isSuccess() {
        return isSuccess;
    }

}
