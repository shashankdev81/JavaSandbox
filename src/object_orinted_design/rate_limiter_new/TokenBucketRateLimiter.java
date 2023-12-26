package object_orinted_design.rate_limiter_new;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucketRateLimiter implements RateLimiter {

    private AtomicInteger tokenBucket;

    private int bucket;

    private static int BUCKET_CAPACITY;

    private int window;

    private int limits;

    private TimeUnit timeUnit;

    private ScheduledFuture<?> scheduledFuture;

    private ScheduledExecutorService service;

    public TokenBucketRateLimiter(int duration, TimeUnit timeUnit, int limits) {
        this.window = duration;
        this.limits = limits;
        this.timeUnit = timeUnit;
        BUCKET_CAPACITY = 2 * this.limits;
        this.tokenBucket = new AtomicInteger(BUCKET_CAPACITY);
    }


    @Override
    public void register(String client) {

    }

    @Override
    public boolean isPermitted() {
        return tokenBucket.decrementAndGet() > 0;
    }

    @Override
    public int getAvailableLimits() {
        return tokenBucket.get();
    }

    @Override
    public boolean isPermitted(String client) {
        return tokenBucket.decrementAndGet() > 0;
    }


    @Override
    public int getAvailableLimits(String client) {
        return tokenBucket.get();
    }

    @Override
    public boolean start() {
        service = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            int diff = BUCKET_CAPACITY - tokenBucket.get();
            tokenBucket.set(Math.min(diff, limits));
            System.out.println("Task executed at: " + System.currentTimeMillis());
        };
        scheduledFuture = service.scheduleAtFixedRate(task, 0, window, timeUnit);
        return true;
    }

    @Override
    public boolean stop() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        if (service != null) {
            service.shutdown();
        }
        return true;
    }

}
