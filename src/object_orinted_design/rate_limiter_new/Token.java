package object_orinted_design.rate_limiter_new;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Token implements Delayed {

    private long delay;

    private TimeUnit timeUnit;

    public Token(int value, TimeUnit timeUnit) {
        this.delay = value;
        this.timeUnit = timeUnit;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return delay;
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.delay, o.getDelay(timeUnit));
    }
}
