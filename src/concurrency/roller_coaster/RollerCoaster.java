package concurrency.roller_coaster;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RollerCoaster {

    private Lock carDoorLock = new ReentrantLock();

    private Condition rideOver = carDoorLock.newCondition();

    private Semaphore permits;

    private CyclicBarrier boardingBarrier;

    private CyclicBarrier unBoardingBarrier;

    public RollerCoaster(int capacity) {
        this.permits = new Semaphore(capacity);
        this.boardingBarrier = new CyclicBarrier(capacity + 1);
        this.unBoardingBarrier = new CyclicBarrier(capacity + 1);
    }


    public void board() throws Exception {
        try {
            this.permits.acquire(1);
            this.boardingBarrier.await();
            System.out.println("Pax boarded");
            carDoorLock.lock();
            rideOver.await();
            carDoorLock.unlock();
        } catch (InterruptedException e) {
            throw new Exception("Couldn't board:" + e.getMessage());
        }
    }

    public void unboard() throws Exception {
        try {
            this.unBoardingBarrier.await();
            this.permits.release(1);
            System.out.println("Pax un boarded");
        } catch (InterruptedException e) {
            throw new Exception("Wasn't allowed to unboard:" + e.getMessage());
        } catch (BrokenBarrierException e) {
            throw new Exception("Couldn't load; something bad happened:" + e.getMessage());
        }

    }

    public void load() throws Exception {
        try {
            Thread.sleep(1000);
            this.boardingBarrier.await();
            System.out.println("Car loaded");
        } catch (InterruptedException e) {
            throw new Exception("Wasn't allowed to load:" + e.getMessage());
        } catch (BrokenBarrierException e) {
            throw new Exception("Couldn't load; something bad happened:" + e.getMessage());
        }

    }

    public void run() throws Exception {
        try {
            Thread.sleep(3000);
            System.out.println("joy ride over");
        } catch (InterruptedException e) {
            throw new Exception("Couldn't run; something bad happened:" + e.getMessage());
        }
    }

    public void unload() throws Exception {
        try {
            carDoorLock.lock();
            rideOver.signalAll();
            carDoorLock.unlock();
            this.unBoardingBarrier.await();
            System.out.println("Car un loaded");
        } catch (InterruptedException e) {
            throw new Exception("Wasn't allowed to load:" + e.getMessage());
        } catch (BrokenBarrierException e) {
            throw new Exception("Couldn't load; something bad happened:" + e.getMessage());
        }
    }

}
