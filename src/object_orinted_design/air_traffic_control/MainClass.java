package object_orinted_design.air_traffic_control;

public class MainClass {

    public static void main(String[] args) {
        AirTrafficControl controlRoom = new SimpleAirTrafficControlRoom();
        Aircraft boeing737_1 = new PassengerAircraft("1", "boeing737_1", 300);
        Aircraft boeing737_2 = new PassengerAircraft("2", "boeing737_2", 300);
        Aircraft boeing737_3 = new PassengerAircraft("3", "boeing737_3", 300);
        Aircraft airbus_1 = new PassengerAircraft("4", "airbus_1", 300);
        controlRoom.requestLanding(boeing737_1);
        controlRoom.requestLanding(boeing737_2);
        controlRoom.requestTakeOff(boeing737_3);
        controlRoom.requestTakeOff(airbus_1);

    }
}