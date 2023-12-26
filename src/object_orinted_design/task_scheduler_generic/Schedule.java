package object_orinted_design.task_scheduler_generic;

import java.time.Duration;

public class Schedule {


    public long getStartTime() {
        return startTime;
    }

    public long getNextExecutionTime() {
        return startTime;
    }

    public Duration getFrequency() {
        return frequency;
    }

    private long startTime;

    private Duration frequency;


    public Schedule(long startTime, Duration frequency) {
        this.startTime = startTime;
        this.frequency = frequency;
    }
}
