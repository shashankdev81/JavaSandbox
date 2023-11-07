package concurrency.locks;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteLockUsingJavaLock implements ReadWriteLock {

    private int readPermits;

    private Lock lock = new ReentrantLock();

    private Condition readCondition = lock.newCondition();

    private Condition writeCondition = lock.newCondition();

    private AtomicInteger writeEnabled;

    private AtomicInteger readPermitsCounter;

    public ReadWriteLockUsingJavaLock(int readPermits) {
        this.readPermits = readPermits;
        this.readPermitsCounter = new AtomicInteger(readPermits);
        this.writeEnabled = new AtomicInteger(0);
    }

    @Override
    public void acquireReadLock() {
        lock.lock();
        while (!isWritePathBlocked()) {
            try {
                readCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        readPermitsCounter.decrementAndGet();
        lock.unlock();
    }

    private boolean isWritePathBlocked() {
        return writeEnabled.compareAndSet(0, 1) || writeEnabled.get() == 1;
    }

    private boolean isReadPathBlocked() {
        return writeEnabled.compareAndSet(0, 2);
    }

    @Override
    public void acquireWriteLock() {
        lock.lock();
        while (!isReadPathBlocked()) {
            try {
                writeCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    @Override
    public void releaseReadLock() {
        lock.lock();
        int left = readPermitsCounter.incrementAndGet();
        if (left >= readPermits) {
            writeEnabled.compareAndSet(1, 0);
            writeCondition.signalAll();
            readCondition.signalAll();
        }
        lock.unlock();
    }

    @Override
    public void releaseWriteLock() {
        lock.lock();
        writeEnabled.compareAndSet(2, 0);
        readCondition.signalAll();
        writeCondition.signalAll();
        lock.unlock();
    }

}
