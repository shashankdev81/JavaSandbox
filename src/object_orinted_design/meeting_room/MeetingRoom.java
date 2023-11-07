package object_orinted_design.meeting_room;

public class MeetingRoom {
    private String id;
    private int capacity;

    public String getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public MeetingRoom(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }
}
