package object_orinted_design.rate_limiter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Delay implements Delayed {
    private long time;

    public Delay(long time) {
        this.time = time;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = time - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed obj) {
        if (this.time < ((Delay) obj).time) {
            return -1;
        }
        if (this.time > ((Delay) obj).time) {
            return 1;
        }
        return 0;
    }

}
