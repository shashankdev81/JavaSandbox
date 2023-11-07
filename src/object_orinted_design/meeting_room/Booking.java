package object_orinted_design.meeting_room;

import object_orinted_design.User;

import java.util.Comparator;

public class Booking implements Comparator<Booking> {

    private User bookedBy;

    private final TimeWindow timeWindow = new TimeWindow();

    @Override
    public int compare(Booking o1, Booking o2) {
        if (o1.timeWindow.getMeetingStartTime() < o2.timeWindow.getMeetingStartTime() && o1.timeWindow.getMeetingEndTime() < o2.timeWindow.getMeetingStartTime()) {
            return 1;
        } else if (o1.timeWindow.getMeetingStartTime() > o2.timeWindow.getMeetingEndTime()) {
            return -1;
        }
        return 1;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Booking other = (Booking) obj;
        return inBetween(this, other) || inBetween(other, this);
    }

    private boolean inBetween(Booking booking, Booking other) {
        return booking.timeWindow.getMeetingStartTime() >= other.timeWindow.getMeetingStartTime() && booking.timeWindow.getMeetingEndTime() <= other.timeWindow.getMeetingEndTime();
    }
}