package object_orinted_design.rate_limiter_new;

public interface RateLimiter {

    public void register(String client);

    public boolean isPermitted();

    public int getAvailableLimits();

    public boolean isPermitted(String client);

    public int getAvailableLimits(String client);

    public boolean start();

    public boolean stop();

}
