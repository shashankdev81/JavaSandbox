package elevator;

public class Building {

    private ElevatorControlSystem elevatorSystem;

    public Building() {
        this.elevatorSystem = new ElevatorControlSystem(10, 2);
        this.elevatorSystem.startElevators();
    }

    public static void main(String[] args) {
        Building building = new Building();
        building.elevatorSystem.placeRequest(new MoveRequest(6, 1, STATE.DOWN));
        building.elevatorSystem.placeRequest(new MoveRequest(2, 4, STATE.UP));

    }
}
