package concurrency.nonblockingds;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NonBlockingAtomicArrayList<T> {

    private int CAPACITY = 10;
    private AtomicReferenceArray<T> array = new AtomicReferenceArray<T>(10);

    private AtomicInteger index = new AtomicInteger(-1);

    private Lock lock = new ReentrantLock();

    public NonBlockingAtomicArrayList() {
    }

    public void add(T obj) {
        int pos = index.incrementAndGet();
        if (pos > CAPACITY) {
            doubleArraySize(pos);
        }
        array.set(pos, obj);
    }

    private void doubleArraySize(int pos) {
        lock.lock();
        if (pos < CAPACITY) {
            return;
        }
        AtomicReferenceArray<T> newArray = new AtomicReferenceArray<T>(CAPACITY * 2);
        for (int i = 0; i < array.length(); i++) {
            newArray.set(i, array.get(i));
        }
        CAPACITY = CAPACITY * 2;
        array = newArray;
        lock.unlock();
    }

    public void remove(T obj) {
        lock.lock();
        int ind = 0;
        while (!array.get(ind).equals(obj)) {
            ind++;
        }
        while (ind < array.length() - 1) {
            array.set(ind, array.get(ind + 1));
            ind++;
        }
        lock.unlock();
    }
}
