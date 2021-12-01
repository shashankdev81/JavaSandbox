package concurrency.nonblockingds;

import concurrency.locks.ConcurrentLatch;

public class Task implements Runnable {

    private ConcurrentLatch latch;

    public Task(ConcurrentLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("Task done. Will countdown");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}
