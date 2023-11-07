package object_orinted_design.air_traffic_control;

import java.util.Objects;

public abstract class Aircraft {

    private enum Status {
        PARKED, ON_RUNWAY, LANDED, TAKEN_OFF

    }

    public String getStatus() {
        return status.name();
    }

    private Status status;

    private String id;

    private String name;

    private Integer capacity;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return id.equals(aircraft.id) && name.equals(aircraft.name) && capacity.equals(aircraft.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity);
    }

    public Aircraft(String id, String name, Integer capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public void receive(Command command, Runway runway, ParkingSlot parking) {
        try {
            if (command.equals(Command.LAND)) {
                land(runway);
                taxi(runway);
                park(parking);
            } else if (command.equals(Command.TAKE_OFF)) {
                unpark(parking);
                taxi(runway);
                takeOff(runway);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void land(Runway runway) throws InterruptedException {
        System.out.println("Aircraft " + this.id + " will land");
        Thread.sleep(1000);
    }

    public void takeOff(Runway runway) throws InterruptedException {
        System.out.println("Aircraft " + this.id + " will take off");
        Thread.sleep(1000);
    }

    public void taxi(Runway runway) throws InterruptedException {
        System.out.println("Aircraft " + this.id + " will hit runway " + runway.id);
        Thread.sleep(1000);
    }

    public void park(ParkingSlot parking) throws InterruptedException {
        System.out.println("Aircraft " + this.id + " will hit park in terminal " + parking.id);
        Thread.sleep(1000);
    }

    public void unpark(ParkingSlot parking) throws InterruptedException {
        System.out.println("Aircraft " + this.id + " will hit unpark from terminal " + parking.id);
        Thread.sleep(1000);
    }
}
