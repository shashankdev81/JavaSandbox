package object_orinted_design.rate_limiter_new;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class RateLimiterTestClass {

    public static void main(String[] args) {
        RateLimiter rateLimiter = new SlidingWindowRateLimiter(100, TimeUnit.MILLISECONDS, 5);
        rateLimiter.start();
        for (int i = 0; i < 10; i++) {
            System.out.println(rateLimiter.isPermitted());
        }
        rateLimiter.stop();
        rateLimiter = new TokenBucketRateLimiter(100, TimeUnit.MILLISECONDS, 5);
        rateLimiter.start();
        for (int i = 0; i < 100; i++) {
            System.out.println(rateLimiter.isPermitted());
        }
        rateLimiter.stop();
        rateLimiter = new LeakyBucketRateLimiter(100, TimeUnit.MILLISECONDS, 5);
        rateLimiter.start();
        for (int i = 0; i < 10; i++) {
            System.out.println(rateLimiter.isPermitted());
        }
        rateLimiter.stop();

    }

}
