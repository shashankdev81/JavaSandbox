package concurrency.locks;

import javax.naming.OperationNotSupportedException;

public class ConcurrentReentrantLock implements Lock {

    private Lock lock;

    private String owner = "NO_OWNER";

    private int acquired;

    public ConcurrentReentrantLock() {
        this.lock = new NonBlockingMutexLock();
    }

    @Override
    public boolean tryAcquire() {
        return false;
    }

    @Override
    public void acquire() {
        if (!owner.equalsIgnoreCase(Thread.currentThread().getName())) {
            lock.acquire();
            owner = Thread.currentThread().getName();
        }
        acquired++;
    }

    @Override
    public void release() {
        if (!owner.equalsIgnoreCase(Thread.currentThread().getName())) {
            throw new RuntimeException("Not owner of the lock");
        }
        acquired--;
        if (acquired == 0) {
            owner = "";
            lock.release();
        }
    }

    @Override
    public boolean isAcquired() {
        return acquired > 0;
    }

    @Override
    public int permitsAcquired() {
        return acquired;
    }

    @Override
    public int permitsAllowed() {
        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ConcurrentReentrantLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.acquire();
                System.out.println("Acquired once in T1");
                lock.acquire();
                System.out.println("Acquired twice in T1");
                try {
                    Thread.sleep(3000);
                    lock.release();
                    lock.release();
                    System.out.println("Released twice by T1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.acquire();
                System.out.println("Acquired once in T2");
                lock.acquire();
                System.out.println("Acquired twice in T2");
                lock.release();
                lock.release();
                System.out.println("Released twice by T2");

            }
        }).start();

    }
}
