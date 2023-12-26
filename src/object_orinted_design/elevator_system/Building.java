package object_orinted_design.elevator_system;

public class Building {

    private ElevatorControlSystem controlSystem;

    public Floor[] getFloors() {
        return floors;
    }

    private Floor[] floors;

    public Building(int floorCount, int elevators, int capacity) {
        this.controlSystem = new ElevatorControlSystem().withElevators(floorCount, elevators,
            capacity);
        floors = new Floor[floorCount];
        for (int i = 0; i < floorCount; i++) {
            floors[i] = new Floor(i, controlSystem);
        }
    }

    public static void main(String[] args) {
        Building building = new Building(4, 1, 5);
        building.getFloors()[3].requestLift(Direction.UP);

    }
}
