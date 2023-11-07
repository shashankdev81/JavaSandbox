package concurrency.misc;

import java.util.concurrent.*;

public class RateLimiter {

    private int limit;

    private BlockingQueue<Window> queue;

    private class Window implements Delayed {

        private Semaphore permits;

        private Long delay;

        public Window(long delay, Semaphore permits) {
            this.permits = permits;
            this.delay = delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return delay;
        }

        @Override
        public int compareTo(Delayed o) {
            return this.delay.compareTo(o.getDelay(TimeUnit.SECONDS));
        }
    }

    public RateLimiter(int limit) {
        this.limit = limit;
        queue = new DelayQueue<Window>();
        queue.add(new Window(0, new Semaphore(limit)));
    }

    public void acquire(int permits) {
        while (!queue.isEmpty()) {
            int remainingPermits = permits;
            while (remainingPermits > 0) {
                try {
                    Window window = queue.take();
                    int available = window.permits.availablePermits();
                    int toBeAcquired = available > remainingPermits ? remainingPermits : available;
                    window.permits.acquire(toBeAcquired);
                    if (available > toBeAcquired) {
                        queue.put(window);
                    } else {
                        queue.put(new Window(1000, new Semaphore(limit)));
                    }
                    remainingPermits -= toBeAcquired;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}
