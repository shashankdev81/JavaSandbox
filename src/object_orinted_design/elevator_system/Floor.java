package object_orinted_design.elevator_system;

public class Floor {

    public int getValue() {
        return value;
    }

    private int value;

    public ElevatorControlSystem controlSystem;

    public Floor(int value, ElevatorControlSystem controlSystem) {
        this.value = value;
        this.controlSystem = controlSystem;
    }

    public void requestLift(Direction liftRequest) {
        controlSystem.requestLift(this, liftRequest);
    }


}
