package concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBlockingQueue {

    private int[] buffer;

    private int counter = -1;


    private Lock lock = new ReentrantLock(true);

    private Condition queueFull = lock.newCondition();

    private Condition queueEmpty = lock.newCondition();


    public BoundedBlockingQueue(int capacity) {
        buffer = new int[capacity];

    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        if (counter == buffer.length - 1) {
            queueFull.await();
        } else {
            buffer[++counter] = element;
            queueEmpty.signalAll();

        }
        lock.unlock();

    }

    public int dequeue() throws InterruptedException {
        int result = -1;
        lock.lock();
        if (counter < 0) {
            queueEmpty.await();
        } else {
            result = buffer[counter--];
            queueFull.signalAll();

        }
        lock.unlock();
        return result;

    }

    public int size() {
        return counter + 1;

    }

    public static void main(String[] args) throws InterruptedException {
        BoundedBlockingQueue queue = new BoundedBlockingQueue(2);
        queue.enqueue(3);
        queue.enqueue(2);
        System.out.println(queue.dequeue());
        queue.enqueue(1);
        System.out.println(queue.dequeue());

    }
}
