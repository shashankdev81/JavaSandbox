package concurrency.locks;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class NonBlockingSemaphore implements Lock {

    private int MAX = 0;

    private AtomicInteger semaphore;

    private Object parking = new Object();

    public NonBlockingSemaphore(int mutex) {
        this.MAX = mutex;
        this.semaphore = new AtomicInteger(0);
    }

    public boolean isAcquired() {
        return semaphore.get() >= 1;
    }

    @Override
    public int permitsAcquired() {
        return semaphore.get();
    }

    @Override
    public int permitsAllowed() {
        return MAX;
    }

    public boolean tryAcquire() {
        if (semaphore.incrementAndGet() <= MAX) {
            return true;
        } else {
            semaphore.decrementAndGet();
            return false;
        }
    }

    public void acquire() {

        AtomicBoolean isAcquired = new AtomicBoolean(false);
        while (!isAcquired.get()) {
            if (semaphore.incrementAndGet() > MAX) {
                semaphore.decrementAndGet();
            } else {
                isAcquired.set(true);
            }
        }
    }


    public void release() {
        semaphore.decrementAndGet();
    }
}
