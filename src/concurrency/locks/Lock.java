package concurrency.locks;

public interface Lock {

    public boolean tryAcquire();

    public void acquire();

    public void release();

    public boolean isAcquired();

    public int permitsAcquired();

    public int permitsAllowed();

}
