package concurrency.locks;

public class Writer implements Runnable {

    private int num = 0;

    private ConcurrentReadWriteLock lock;

    public Writer(ConcurrentReadWriteLock lock, int n) {
        this.num = n;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            long time = (long) (Math.random() * 1000);
            try {
                Thread.sleep(time);
                lock.acquireWriteLock();
                System.out.println("Acquired write lock:" + num + ",for " + time);
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.releaseWriteLock();
                System.out.println("Released write lock:" + num);
            }
        }
    }
}