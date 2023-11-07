package concurrency.locks;

public class Writer implements Runnable {

    private int num = 0;

    private ReadWriteLock lock;

    static long time = (long) (Math.random() * 100);

    public Writer(ReadWriteLock lock, int n) {
        this.num = n;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                lock.acquireWriteLock();
                System.out.println("Acquired write lock:" + num + ",for " + time);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.releaseWriteLock();
                System.out.println("Released write lock:" + num);
            }
        }
    }
}