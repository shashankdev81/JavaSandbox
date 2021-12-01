package concurrency.nonblockingds;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentStackUsingArray<T> implements Stack<T> {

    private AtomicReferenceArray<T> array;
    private Lock lock;
    private AtomicInteger top;
    private int capacity;

    public ConcurrentStackUsingArray(int cap) {

        //test stack
        capacity = cap;
        lock = new ReentrantLock();
        array = new AtomicReferenceArray<T>(capacity);
        top = new AtomicInteger(0);
    }

    public boolean tryPush(T val) {
        push(val);
        return true;
    }


    private void doubleArraySize(int pos) {
        lock.lock();
        if (pos < capacity) {
            lock.unlock();
            return;
        }
        AtomicReferenceArray<T> newArray = new AtomicReferenceArray<T>(capacity * 2);
        for (int i = 0; i < array.length(); i++) {
            newArray.set(i, array.get(i));
        }
        capacity = capacity * 2;
        array = newArray;
        lock.unlock();
    }

    public void push(T val) {
        int index = -2;
        while (index < 0) {
            index = top.incrementAndGet();
            if (index < 0) {
                top.decrementAndGet();
            }
        }
        if (index >= capacity) {
            doubleArraySize(index);
        }
        array.set(index, val);
    }

    public T pop() {
        T value = null;
        boolean isEmpty = false;
        while (top.get() >= 0) {
            try {
                value = array.get(top.get());
            } catch (IndexOutOfBoundsException ie) {
                isEmpty = true;
            }
            int newTop = top.decrementAndGet();
            if (newTop >= -1 && !isEmpty) {
                break;
            } else {
                top.incrementAndGet();
            }
        }
        return value;

    }


    public T tryPop() {
        return pop();
    }


    private static ConcurrentStackUsingArray<Integer> stack = new ConcurrentStackUsingArray<Integer>(1000);

    public static void main(String[] args) {

        Pusher pusher1 = new Pusher(stack);
        Pusher pusher2 = new Pusher(stack);
        Popper popper1 = new Popper(stack);
        Popper popper2 = new Popper(stack);

        new Thread(pusher1).start();
        new Thread(pusher2).start();
        new Thread(popper1).start();
        new Thread(popper2).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pusher1.stop();
        pusher2.stop();
        popper1.stop();
        popper2.stop();

        System.out.println("Done");
    }


}