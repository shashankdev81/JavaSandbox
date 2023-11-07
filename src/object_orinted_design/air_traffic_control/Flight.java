package object_orinted_design.air_traffic_control;

import java.util.Arrays;

public class Flight {

    private String flightNo;

    private Aircraft aircraft;

    private long arrivalTime;

    private String origin;

    private long departureTime;

    private String destination;


    public Flight(String flightNo) {
        this.flightNo = flightNo;
    }

    public String status() {
        return aircraft.getStatus();
    }
}
