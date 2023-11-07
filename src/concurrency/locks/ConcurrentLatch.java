package concurrency.locks;

import concurrency.nonblockingds.Task;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class ConcurrentLatch {

    private AtomicInteger counter;

    private int SIZE;

    private Lock lock = new ReentrantLock();

    private AtomicBoolean isStarted = new AtomicBoolean(false);

    private Condition workflowTermination = lock.newCondition();

    public ConcurrentLatch(int capacity) {
        this.SIZE = capacity;
        this.counter = new AtomicInteger(SIZE);
    }


    public void await() throws InterruptedException {
        System.out.println("Will wait for countdown");
        lock.lock();
        workflowTermination.await();
        lock.unlock();
        System.out.println("Done waiting");
    }

    public void await(long timeOut) throws InterruptedException {
        Thread.sleep(timeOut);
    }

    public void countDown() {
        int left = counter.decrementAndGet();
        if (left == 0) {
            lock.lock();
            workflowTermination.signal();
            lock.unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentLatch latch = new ConcurrentLatch(3);
        new Thread(new Task(latch)).start();
        new Thread(new Task(latch)).start();
        new Thread(new Task(latch)).start();
        latch.await();
    }


}
