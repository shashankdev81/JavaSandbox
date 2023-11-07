package object_orinted_design.air_traffic_control;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/*
 * 2 Runway
 * 1 Control Room
 *  Aircraft
 * Flight
 *  Activity: Landing, Take Off
 * m Terminal
 *
 *
 *
 *
 * * * * */
public class SimpleAirTrafficControlRoom implements AirTrafficControl {

    private static final int PARKING_MAX = 20;

    private static final int TAKE_OFF_MAX = 2;

    private static final int LANDING_MAX = 2;

    private static final int RUNWAYS = 2;

    private List<Runway> runways;

    private Queue<Aircraft> landingQueue;

    private Queue<Aircraft> takeOffQueue;

    private Queue<ParkingSlot> parking;

    public SimpleAirTrafficControlRoom() {
        runways = new ArrayList<>();
        runways.add(new Runway("1"));
        landingQueue = new ArrayDeque<>();
        takeOffQueue = new ArrayDeque<>();
        parking = new LinkedBlockingDeque<>(PARKING_MAX);
        parking.add(new ParkingSlot("1"));
        new Thread(new Monitor()).start();
    }

    @Override
    public boolean requestLanding(Aircraft aircraft) {
        boolean canLand = PARKING_MAX - parking.size() > 0 && landingQueue.size() < LANDING_MAX;
        return canLand ? landingQueue.offer(aircraft) : false;
    }

    @Override
    public boolean requestTakeOff(Aircraft aircraft) {
        boolean canTakeOff = takeOffQueue.size() < TAKE_OFF_MAX;
        return canTakeOff ? takeOffQueue.offer(aircraft) : false;
    }

    private class Monitor implements Runnable {

        private ExecutorService service = Executors.newFixedThreadPool(RUNWAYS);

        @Override
        public void run() {
            boolean isTurnToLand = true;
            while (true) {
                if (canOccupyRunway(isTurnToLand, landingQueue)) {
                    service.submit(new LandOrTakeoff(landingQueue, Command.LAND));

                }
                if (canOccupyRunway(!isTurnToLand, takeOffQueue)) {
                    service.submit(new LandOrTakeoff(takeOffQueue, Command.TAKE_OFF));
                }
                isTurnToLand = !isTurnToLand;
            }
        }

        private boolean canOccupyRunway(boolean landingToggle, Queue<Aircraft> landingQueue) {
            return landingToggle && runways.stream().anyMatch(r -> r.isVaccant()) && !landingQueue.isEmpty() && parking.size() < PARKING_MAX;
        }

    }

    private class LandOrTakeoff implements Runnable {

        private Queue<Aircraft> queue;
        private Command command;

        public LandOrTakeoff(Queue<Aircraft> inQueue, Command command) {
            this.queue = inQueue;
            this.command = command;
        }

        @Override
        public void run() {
            Runway runway = runways.stream().filter(r -> r.isVaccant()).collect(Collectors.toList()).get(0);
            Aircraft aircraft = queue.poll();
            try {
                runway.occupy(aircraft);
                aircraft.receive(command, runway, command.equals(Command.LAND) ? parking.poll() : null);
                runway.vacate(aircraft);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
