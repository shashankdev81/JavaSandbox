package object_orinted_design.meeting_room;

import java.text.SimpleDateFormat;
import java.util.*;

//TODO: make this singleton
public class Calender {

    private Map<String, Map<MeetingRoom, Set<Booking>>> roomBookingsByDateAndTime;

    //TODO: replace with criteria
    public List<MeetingRoom> getAvailableRooms(Date date, Booking booking, int capacity) {
        List<MeetingRoom> roomOptions = new ArrayList<>();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        if (roomBookingsByDateAndTime.get(modifiedDate) == null) {
            return roomOptions;
        }
        for (Map.Entry<MeetingRoom, Set<Booking>> roomOption : roomBookingsByDateAndTime.get(modifiedDate).entrySet()) {
            if (roomOption.getKey().getCapacity() < capacity) {
                continue;
            }
            if (!roomOption.getValue().contains(booking)) {
                roomOptions.add(roomOption.getKey());
            }

        }
        return roomOptions;
    }

    public List<TimeWindow> getAvailableTimings(Date date, Booking booking, MeetingRoom meetingRoom) {
        List<TimeWindow> timeOptions = new ArrayList<>();
        return timeOptions;
    }

    public boolean book(Date date, Booking booking, MeetingRoom meetingRoom) {
        return false;
    }

    public boolean cancel(Date date, Booking booking, MeetingRoom meetingRoom) {
        return false;
    }
}

