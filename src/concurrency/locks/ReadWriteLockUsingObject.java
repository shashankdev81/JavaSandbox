package concurrency.locks;

import java.util.concurrent.atomic.AtomicInteger;

public class ReadWriteLockUsingObject implements ReadWriteLock {

    private int readPermits;

    private Object lock = new Object();

    private AtomicInteger writeEnabled;

    private AtomicInteger readPermitsCounter;

    public ReadWriteLockUsingObject(int readPermits) {
        this.readPermits = readPermits;
        this.readPermitsCounter = new AtomicInteger(readPermits);
        this.writeEnabled = new AtomicInteger(0);
    }

    @Override
    public void acquireReadLock() {
        synchronized (lock) {
            while (true) {
                if (isWritePathBlocked()) {
                    int left = readPermitsCounter.decrementAndGet();
                    if (left >= 0) {
                        break;
                    }
                    readPermitsCounter.incrementAndGet();
                }
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private boolean isWritePathBlocked() {
        return writeEnabled.compareAndSet(0, 1) || writeEnabled.get() == 1;
    }

    private boolean isReadPathBlocked() {
        return writeEnabled.compareAndSet(0, 2);
    }

    @Override
    public void acquireWriteLock() {
        synchronized (lock) {
            while (!isReadPathBlocked()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void releaseReadLock() {
        synchronized (lock) {
            int left = readPermitsCounter.incrementAndGet();
            if (left >= readPermits) {
                writeEnabled.compareAndSet(1, 0);
                lock.notifyAll();
            }
        }
    }

    @Override
    public void releaseWriteLock() {
        synchronized (lock) {
            writeEnabled.compareAndSet(2, 0);
            lock.notifyAll();
        }
    }

}
