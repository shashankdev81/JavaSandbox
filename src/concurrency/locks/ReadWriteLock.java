package concurrency.locks;

public interface ReadWriteLock {
    void acquireReadLock();

    void acquireWriteLock();

    void releaseReadLock();

    void releaseWriteLock();
}
