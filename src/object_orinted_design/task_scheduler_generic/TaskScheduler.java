package object_orinted_design.task_scheduler_generic;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.*;

public class TaskScheduler implements ITaskSchedulerNonBlocking {

    public static TaskScheduler INSTANCE = new TaskScheduler();

    private volatile boolean isTerminated = false;

    private int POOL_SIZE = 100;

    private int MINI_POOL_SIZE = 5;

    private ExecutorService executorService = Executors.newFixedThreadPool(MINI_POOL_SIZE);

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(
        POOL_SIZE);

    private DelayQueue<Delayed> taskQueue = new DelayQueue<>();

    private TaskScheduler() {
        new Thread(new Scheduler()).start();
    }

    public static TaskScheduler getInstance() {
        return INSTANCE;
    }

    private Map<String, Future<TaskResponse>> futureMap = new HashMap<>();

    @Override
    public void terminate() {
        isTerminated = true;
    }

    @Override
    public String schedule(TaskRequest taskRequest, ISchedulable schedulable) {

        if (isTerminated) {
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        ScheduleableTask scheduleableTask = new ScheduleableTask(taskRequest, schedulable, uuid);
        taskQueue.offer(scheduleableTask);
        futureMap.put(uuid, null);
        return uuid;
    }

    @Override
    public TaskResponse get(String taskId) {
        try {
            return futureMap.get(taskId).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new TaskResponse("500 Failure", false, null);
        }
    }

    @Override
    public TaskResponse getIfDone(String taskId) {
        try {
            if (futureMap.get(taskId).isDone()) {
                return futureMap.get(taskId).get();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new TaskResponse("500 Failure", false, null);
        }
    }

    @Override
    public Future<TaskResponse> getPromise(String taskId) {
        return null;
    }

    private class Scheduler implements Runnable {

        @Override
        public void run() {
            while (!isTerminated || !taskQueue.isEmpty()) {
                ScheduleableTask task = (ScheduleableTask) taskQueue.poll();
                if (task.getScheduleable().getSchedule() != null) {
                    ScheduledFuture<TaskResponse> future = scheduledExecutorService.schedule(task,
                        task.getScheduleable().getSchedule().getFrequency().toMillis(),
                        TimeUnit.MILLISECONDS);
                    taskQueue.offer(task);
                } else {
                    futureMap.put(task.getTaskId(), executorService.submit(task));
                }
            }
        }
    }

}
