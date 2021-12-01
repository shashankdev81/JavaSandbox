package concurrency.locks;

public class TestThread implements Runnable {

    private NonBlockingMutexFairLock lock;

    private int id;

    public TestThread(NonBlockingMutexFairLock lock, int num) {
        this.lock = lock;
        this.id = num;
    }

    @Override
    public void run() {
        try {
            lock.acquire();
            System.out.println("Thread has acquired lock:" + id);
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.release();
            System.out.println("Thread has released lock:" + id);
        }
    }


}
