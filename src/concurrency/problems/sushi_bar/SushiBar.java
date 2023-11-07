package concurrency.problems.sushi_bar;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class SushiBar {

    private int capacity;

    private CountDownLatch waitToBeSeated;

    private Semaphore seats;

    private Queue<Thread> queue;

    public SushiBar(int capacity) {
        this.capacity = capacity;
        waitToBeSeated = new CountDownLatch(capacity);
        queue = new ArrayDeque<>();
        seats = new Semaphore(capacity);
    }

    public void sitDown() {
        while (!seats.tryAcquire()) {
            try {
                queue.add(Thread.currentThread());
                while (new ArrayList<>(queue).contains(Thread.currentThread())) {
                    waitToBeSeated.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getUp() {
        seats.release();
        if (!queue.isEmpty()) {
            queue.poll();
        }
        waitToBeSeated.countDown();
    }


}
