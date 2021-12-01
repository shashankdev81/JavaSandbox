package concurrency.locks;

import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockingMutexLock implements Lock {

    private AtomicInteger mutex = new AtomicInteger(1);

    private Object parking = new Object();


    public boolean tryAcquire() {
        return mutex.compareAndSet(1, 0);
    }

    public void acquire() {
        while (!mutex.compareAndSet(1, 0)) {
            synchronized (parking) {
                try {
                    parking.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void release() {
        if (mutex.compareAndSet(0, 1)) {
            synchronized (parking) {
                parking.notifyAll();
            }
        }
    }

    @Override
    public boolean isAcquired() {
        return mutex.get() == 1;
    }

    @Override
    public int permitsAcquired() {
        return isAcquired() ? 1 : 0;
    }

    @Override
    public int permitsAllowed() {
        return 1;
    }
}
