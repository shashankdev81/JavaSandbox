package object_orinted_design.rate_limiter_new;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LeakyBucketRateLimiter implements RateLimiter {

    private DelayQueue<Token> leakyBucket;

    private DelayQueue<Token> leakyBucketCopy;

    private int window;

    private long windowEnd;

    private int limits;

    private TimeUnit timeUnit;

    private ScheduledFuture<?> monitor1;

    private ScheduledFuture<?> monitor2;

    private ScheduledExecutorService service;

    public LeakyBucketRateLimiter(int duration, TimeUnit timeUnit, int limits) {
        this.limits = limits;
        this.window = duration;
        this.timeUnit = timeUnit;
        this.leakyBucket = new DelayQueue<>();
        int timeDiffBetweenRequests = window / limits;
        for (int i = 0; i < limits; i++) {
            leakyBucket.offer(new Token(timeDiffBetweenRequests, timeUnit));
        }

    }

    @Override
    public void register(String client) {
    }

    @Override
    public boolean isPermitted() {
        try {
            leakyBucket.poll(windowEnd - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    public int getAvailableLimits() {
        return leakyBucket.size();
    }

    @Override
    public boolean isPermitted(String client) {
        return isPermitted();
    }

    @Override
    public int getAvailableLimits(String client) {
        return leakyBucket.size();
    }

    @Override
    public boolean start() {
        service = Executors.newScheduledThreadPool(2);
        DelayQueue<Token> leakyBucketCopy = new DelayQueue<>();
        monitor1 = service.scheduleAtFixedRate(() -> {
            int timeDiffBetweenRequests = window / limits;
            for (int i = 0; i < limits; i++) {
                leakyBucketCopy.offer(new Token(timeDiffBetweenRequests, timeUnit));
            }
        }, 4 * window / 5, window, timeUnit);
        monitor2 = service.scheduleAtFixedRate(() -> {
            leakyBucket.clear();
            leakyBucket = leakyBucketCopy;
            windowEnd =
                System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(window, timeUnit);
        }, 0, window, timeUnit);
        return true;
    }

    @Override
    public boolean stop() {
        if (monitor1 != null) {
            monitor1.cancel(true);
        }
        if (monitor2 != null) {
            monitor2.cancel(true);
        }
        if (service != null) {
            service.shutdown();
        }
        return true;
    }
}
