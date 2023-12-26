package object_orinted_design.rate_limiter_new;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SlidingWindowRateLimiter implements RateLimiter {

    private AtomicInteger counter;

    private int window;

    private int limits;

    private TimeUnit timeUnit;

    private ScheduledFuture<?> monitor;

    private ScheduledExecutorService service;

    public SlidingWindowRateLimiter(int duration, TimeUnit timeUnit, int limits) {
        this.limits = limits;
        this.window = duration;
        this.timeUnit = timeUnit;
        counter = new AtomicInteger(limits);

    }

    @Override
    public void register(String client) {

    }

    @Override
    public boolean isPermitted() {
        return counter.decrementAndGet() > 0;
    }

    @Override
    public int getAvailableLimits() {
        return counter.get();
    }

    @Override
    public boolean isPermitted(String client) {
        return counter.decrementAndGet() >= 0;
    }

    @Override
    public int getAvailableLimits(String client) {
        return counter.get();
    }

    @Override
    public boolean start() {
        service = Executors.newScheduledThreadPool(1);
        monitor = service.scheduleAtFixedRate(() -> {
            counter.set(limits);
        }, 0, window, timeUnit);
        return true;
    }

    @Override
    public boolean stop() {
        if (monitor != null) {
            monitor.cancel(true);
        }
        if (service != null) {
            service.shutdown();
        }
        return true;
    }

}
