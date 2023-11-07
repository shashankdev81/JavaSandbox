package concurrency.locks;

public class Reader implements Runnable {

    private int num = 0;

    private ReadWriteLock lock;

    static long time = (long) (Math.random() * 100);

    public Reader(ReadWriteLock lock, int n) {
        this.lock = lock;
        this.num = n;
    }

    @Override
    public void run() {
        while (true) {
            try {
                lock.acquireReadLock();
                System.out.println("Acquired read lock:" + num + ",for " + time);
                Thread.sleep(100);
                lock.releaseReadLock();
                System.out.println("Released read lock:" + num + ",for " + time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}