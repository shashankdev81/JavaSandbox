package object_orinted_design.task_scheduler_generic;

public class TaskResponse<T> {


    public T getResult() {
        return result;
    }

    public TaskResponse(String result, boolean isSuccess, T response) {
        this.status = result;
        this.isSuccess = isSuccess;
        this.result = response;
    }

    public String getStatus() {
        return status;
    }

    private String status;

    private boolean isSuccess;


    private T result;


    public boolean isSuccess() {
        return isSuccess;
    }

}
