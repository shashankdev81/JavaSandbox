package object_orinted_design.air_traffic_control;

import java.util.concurrent.Semaphore;

public class Runway {

    String id;

    private static final int MAX = 1;

    private Aircraft currentOccupant;

    private Semaphore semaphore = new Semaphore(MAX);

    public Runway(String id) {
        this.id = id;
    }


    public void occupy(Aircraft occupant) throws InterruptedException {
        semaphore.acquire();
        this.currentOccupant = occupant;
    }

    public void vacate(Aircraft occupant) throws InterruptedException {
        semaphore.release();
        this.currentOccupant = null;

    }

    public boolean isVaccant() {
        return semaphore.availablePermits() < MAX;

    }
}
