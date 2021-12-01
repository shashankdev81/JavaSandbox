package elevator;

import java.util.Objects;

public class MoveRequest {


    private STATE STATE;

    private int fromFloor;

    public STATE getDirection() {
        return STATE;
    }

    public int getFromFloor() {
        return fromFloor;
    }

    public int getToFloor() {
        return toFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveRequest request = (MoveRequest) o;
        return fromFloor == request.fromFloor && toFloor == request.toFloor && STATE == request.STATE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(STATE, fromFloor, toFloor);
    }

    private int toFloor;


    public MoveRequest(int fromFloor, int toFloor, STATE STATE) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.STATE = STATE;
    }
}
