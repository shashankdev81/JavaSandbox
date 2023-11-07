package concurrency.problems.rowboat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RowBoat {


    private Lock lock = new ReentrantLock();

    private AtomicBoolean done = new AtomicBoolean(false);

    private Condition hackerWait = lock.newCondition();

    private Condition serfWait = lock.newCondition();

    private CountDownLatch latch = new CountDownLatch(4);

    private AtomicInteger hackers = new AtomicInteger(0);

    private AtomicInteger serfs = new AtomicInteger(0);

    public void tryBoard(boolean isHacker) {

        lock.lock();
        boolean isBoatFull = hackers.get() + serfs.get() == 4;
        if (isHacker) {
            while ((hackers.incrementAndGet() == 1 && serfs.get() == 3) || isBoatFull) {
                try {
                    hackerWait.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!isHacker) {
            while ((serfs.incrementAndGet() == 1 && hackers.get() == 3) || isBoatFull) {
                try {
                    serfWait.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        lock.unlock();
        latch.countDown();
        serfs.set(0);
        hackers.set(0);
        boat();
        AtomicInteger count = new AtomicInteger(4);
        if (done.compareAndSet(false, true)) {
            rowBoat();
        }
        if (count.incrementAndGet() == 4) {
            done.compareAndSet(true, false);
        }


    }

    private void boat() {
    }

    private void rowBoat() {
    }
}
