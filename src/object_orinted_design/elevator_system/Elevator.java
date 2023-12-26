package object_orinted_design.elevator_system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Elevator {

    private int[] floorSwitch;

    private int capacity;

    private Direction direction;

    private int currFloor;

    private Floor currDestination;

    public int getCurrFloor() {
        return currFloor;
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public Elevator(int floors, int capacity) {
        this.floorSwitch = new int[floors + 1];
        this.capacity = capacity;
        this.direction = Direction.STATIONARY;
    }

    public void move(Direction direction, Floor destination) {
        int movement = direction.equals(Direction.UP) ? 1 : -1;
        this.direction = direction;
        this.currDestination = destination;
        while (currFloor != destination.getValue()) {
            currFloor += movement;
            try {
                System.out.println("Waiting at " + currFloor);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.direction = Direction.STATIONARY;
        System.out.println("Reached at " + currFloor);
    }

    public void halt() {

    }

    public void setDestination(Floor destination) {
        boolean isOnTheWayUp = direction == Direction.UP
            && currDestination.getValue() < destination.getValue();
        boolean isOnTheWayDown = direction == Direction.DOWN
            && currDestination.getValue() > destination.getValue();
        if (isOnTheWayUp || isOnTheWayDown
            || direction == Direction.STATIONARY) {
            currDestination = destination;
        }
    }
}
