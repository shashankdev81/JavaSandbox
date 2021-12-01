package concurrency.locks;

import concurrency.locks.Lock;
import concurrency.locks.NonBlockingSemaphore;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentRWLock2 {

    public static final int READ_PATH_LOCKED = 1;
    public static final int WRITE_PATH_LOCKED = -1;
    public static final int UNLOCKED = 0;
    public static final int BLOCKED_ALL = -2;

    private Lock readLock = new NonBlockingSemaphore(Integer.MAX_VALUE);

    private Lock singleWriteLock = new NonBlockingSemaphore(1);

    private Object parking = new Object();

    private AtomicInteger readWriteLatch = new AtomicInteger(0);

    public boolean tryAcquireReadLock() {

        boolean writeBlocked = singleWriteLock.isAcquired() || singleWriteLock.tryAcquire();
        boolean readAcquired = false;

        if (writeBlocked) {
            readAcquired = readLock.tryAcquire();
        }
        return readAcquired;
    }

    public boolean tryAcquireWriteLock() {
        return singleWriteLock.tryAcquire();
    }


    public boolean isWriteLock(Lock lock) {
        return lock.permitsAllowed() == 1;
    }

    public boolean isReadLock(Lock lock) {
        return lock.permitsAllowed() > 1;
    }

    public void releaseReadLock() {
        while (!readWriteLatch.compareAndSet(READ_PATH_LOCKED, BLOCKED_ALL)) {
            synchronized (parking) {
                try {
                    parking.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        readLock.release();
        if (readLock.permitsAcquired() > 0) {
            readWriteLatch.set(READ_PATH_LOCKED);
        } else {
            readWriteLatch.set(UNLOCKED);
        }
        synchronized (parking) {
            parking.notifyAll();
        }
    }

    public void releaseWriteLock() {
        singleWriteLock.release();
        readWriteLatch.compareAndSet(WRITE_PATH_LOCKED, UNLOCKED);
        synchronized (parking) {
            parking.notifyAll();
        }

    }

    public void acquireReadLock() throws InterruptedException {
        while (!(readWriteLatch.compareAndSet(UNLOCKED, READ_PATH_LOCKED) || readWriteLatch.get() == READ_PATH_LOCKED)) {
            synchronized (parking) {
                parking.wait();
            }
        }
        readLock.acquire();
    }

    public void acquireWriteLock() throws InterruptedException {
        while (!(readWriteLatch.compareAndSet(UNLOCKED, WRITE_PATH_LOCKED))) {
            synchronized (parking) {
                parking.wait();
            }
        }
        singleWriteLock.acquire();
    }


}
