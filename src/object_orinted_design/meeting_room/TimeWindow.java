package object_orinted_design.meeting_room;

public class TimeWindow {
    private Long meetingStartTime;
    private Long meetingEndTime;

    public TimeWindow() {
    }

    public TimeWindow(Long meetingStartTime, Long meetingEndTime) {
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
    }

    public Long getMeetingStartTime() {
        return meetingStartTime;
    }

    public Long getMeetingEndTime() {
        return meetingEndTime;
    }
}