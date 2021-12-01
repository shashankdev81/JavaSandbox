package concurrency.locks;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockingMutexFairLock implements Lock {

    private AtomicInteger mutex = new AtomicInteger(1);

    private AtomicInteger tail = new AtomicInteger(-1);

    private AtomicInteger head = new AtomicInteger(-1);


    private Node[] parkingSlots = new Node[100];

    private class Node {

        private Thread owner;

        private AtomicBoolean isWait = new AtomicBoolean(false);

        public Node(Thread owner) {
            this.owner = owner;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "owner=" + owner.getName() +
                    '}';
        }
    }

    public boolean tryAcquire() {
        return mutex.compareAndSet(1, 0);
    }

    public void acquire() {
        System.out.println("Trying to acquire:" + Thread.currentThread().getName());
        while (!mutex.compareAndSet(1, 0)) {

            int ind = tail.incrementAndGet();
            parkingSlots[ind] = new Node(Thread.currentThread());

            synchronized (parkingSlots[ind]) {
                try {
                    System.out.println("Thread will be enqueued:" + parkingSlots[ind].owner.getName());
                    parkingSlots[ind].wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void release() {
        if (mutex.compareAndSet(0, 1)) {
            int ind = head.incrementAndGet();

            if (head.get() > tail.get()) {
                head.set(0);
                tail.set(0);
            } else {
                synchronized (parkingSlots[ind]) {
                    System.out.println("Thread will be notified:" + parkingSlots[ind].owner.getName());
                    parkingSlots[ind].notify();
                }
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

    public static void main(String[] args) {
        NonBlockingMutexFairLock lock = new NonBlockingMutexFairLock();
        new Thread(new TestThread(lock, 0)).start();
        new Thread(new TestThread(lock, 1)).start();
        new Thread(new TestThread(lock, 2)).start();
        new Thread(new TestThread(lock, 3)).start();

    }
}
