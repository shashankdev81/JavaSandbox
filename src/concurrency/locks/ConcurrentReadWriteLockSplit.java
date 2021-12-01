package concurrency.locks;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentReadWriteLockSplit {


    private AtomicInteger writePathLock = new AtomicInteger(0);

    private Lock readPathLock = new NonBlockingSemaphore(Integer.MAX_VALUE);

    private Object readPathParking = new Object();

    private Object writePathParking = new Object();

    private Map<Type, Set<String>> owners = new ConcurrentHashMap<Type, Set<String>>();


    private Lock readLock = new ReadLock();

    private Lock writeLock = new WriteLock();

    public ConcurrentReadWriteLockSplit() {
        owners.putIfAbsent(Type.READ, new ConcurrentSkipListSet<String>());
        owners.putIfAbsent(Type.WRITE, new ConcurrentSkipListSet<String>());

    }

    private enum Type {
        READ, WRITE;
    }

    private class WriteLock implements Lock {

        @Override
        public boolean tryAcquire() {
            return false;
        }

        @Override
        public void acquire() {
            while (!writePathLock.compareAndSet(0, 2)) {
                synchronized (writePathParking) {
                    try {
                        writePathParking.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            owners.get(Type.WRITE).add(Thread.currentThread().getName());
        }

        @Override
        public void release() {
            if (!owners.get(Type.WRITE).contains(Thread.currentThread().getName())) {
                throw new IllegalMonitorStateException("Not lock owner");
            }
            owners.get(Type.WRITE).remove(Thread.currentThread().getName());
            writePathLock.set(0);
            //notify waiting read threads
            synchronized (readPathParking) {
                readPathParking.notifyAll();
            }
            //notify waiting write threads
            synchronized (writePathParking) {
                writePathParking.notifyAll();
            }
        }

        @Override
        public boolean isAcquired() {
            return writePathLock.get() == 1;
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

    private class ReadLock implements Lock {

        @Override
        public boolean tryAcquire() {
            return false;
        }

        @Override
        public void acquire() {
            while (!writePathLock.compareAndSet(0, 1) || writePathLock.get() != 1) {
                synchronized (readPathParking) {
                    try {
                        readPathParking.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            readPathLock.acquire();
            owners.get(Type.READ).add(Thread.currentThread().getName());
        }

        @Override
        public void release() {
            if (!owners.get(Type.READ).contains(Thread.currentThread().getName())) {
                throw new IllegalMonitorStateException("Not lock owner");
            }
            owners.get(Type.READ).remove(Thread.currentThread().getName());
            readPathLock.release();
            if (readPathLock.permitsAcquired() == 0) {
                //two or more competing threads might be responsible for read lock counter to finally reach 0
                writePathLock.compareAndSet(1, 0);

                //notify waiting write threads
                synchronized (writePathParking) {
                    writePathParking.notifyAll();
                }
            }
        }

        @Override
        public boolean isAcquired() {
            return false;
        }

        @Override
        public int permitsAcquired() {
            return 0;
        }

        @Override
        public int permitsAllowed() {
            return 0;
        }
    }

    public Lock getWriteLock() {
        return writeLock;
    }

    public Lock getReadLock() {
        return readLock;
    }

    public static void main(String[] args) {
        ConcurrentReadWriteLockSplit readWriteLockSplit = new ConcurrentReadWriteLockSplit();
        Lock readLock = readWriteLockSplit.getReadLock();
        Lock writeLock = readWriteLockSplit.getWriteLock();


    }

}
