package concurrency.locks;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentReadWriteLock implements ReadWriteLock {


    private AtomicInteger writeLock = new AtomicInteger(0);

    private Lock readLock = new NonBlockingSemaphore(Integer.MAX_VALUE);

    private Object readPathParking = new Object();

    private Object writePathParking = new Object();

    private Map<Type, Set<String>> owners = new ConcurrentHashMap<Type, Set<String>>();

    public ConcurrentReadWriteLock() {
        owners.putIfAbsent(Type.READ, new ConcurrentSkipListSet<String>());
        owners.putIfAbsent(Type.WRITE, new ConcurrentSkipListSet<String>());

    }

    private enum Type {
        READ, WRITE;
    }


    public void acquireReadLock() {
        synchronized (readPathParking) {

            while (!writeLock.compareAndSet(0, 1) && writeLock.get() != 1) {
                System.out.println("Waiting" + Thread.currentThread().getName());
                try {
                    readPathParking.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        readLock.acquire();
        owners.get(Type.READ).add(Thread.currentThread().getName());
    }

    public void acquireWriteLock() {
        synchronized (writePathParking) {
            while (!writeLock.compareAndSet(0, 2)) {
                try {
                    writePathParking.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        owners.get(Type.WRITE).add(Thread.currentThread().getName());

    }

    public void releaseReadLock() {
        if (!owners.get(Type.READ).contains(Thread.currentThread().getName())) {
            throw new IllegalMonitorStateException("Not lock owner");
        }
        owners.get(Type.READ).remove(Thread.currentThread().getName());
        readLock.release();
        if (readLock.permitsAcquired() == 0) {
            //two or more competing threads might be responsible for read lock counter to finally reach 0
            //notify waiting write threads
            synchronized (writePathParking) {
                writeLock.compareAndSet(1, 0);
                writePathParking.notifyAll();
            }
        }
    }

    public void releaseWriteLock() {
        if (!owners.get(Type.WRITE).contains(Thread.currentThread().getName())) {
            throw new IllegalMonitorStateException("Not lock owner");
        }
        owners.get(Type.WRITE).remove(Thread.currentThread().getName());
        //notify waiting read threads
        synchronized (readPathParking) {
            writeLock.set(0);
            readPathParking.notifyAll();
        }
        //notify waiting write threads
        synchronized (writePathParking) {
            writePathParking.notifyAll();
        }
    }
}
