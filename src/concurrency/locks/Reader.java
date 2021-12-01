package concurrency.locks;

public class Reader implements Runnable {

    private int num = 0;

    private ConcurrentReadWriteLock lock;

    public Reader(ConcurrentReadWriteLock lock, int n) {
        this.lock = lock;
        this.num = n;
    }

    @Override
    public void run() {
        while (true) {
            long time = (long) (Math.random() * 1000);
            try {
                Thread.sleep(time);
                lock.acquireReadLock();
                System.out.println("Acquired read lock:" + num + ",for " + time);
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.releaseReadLock();
                System.out.println("Released read lock:" + num);
            }
        }
    }
}