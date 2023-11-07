package object_orinted_design.air_traffic_control;

public interface AirTrafficControl {

    public boolean requestLanding(Aircraft aircraft);

    public boolean requestTakeOff(Aircraft aircraft);

}
