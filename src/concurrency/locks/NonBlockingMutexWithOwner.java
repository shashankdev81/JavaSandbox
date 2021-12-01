package concurrency.locks;

import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockingMutexWithOwner {

    private AtomicInteger mutex = new AtomicInteger(1);

    private String mutexId = null;


    public String acquire() {
        if (mutex.compareAndSet(1, 0)) {
            mutexId = Thread.currentThread().getName();
        }
        return mutexId;
    }


    public boolean release() {
        if (mutexId.equalsIgnoreCase(Thread.currentThread().getName()) && mutex.compareAndSet(0, 1)) {
            mutexId = null;
            return true;
        }
        return false;
    }
}
