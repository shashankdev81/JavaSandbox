package concurrency.java;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    private static ReadWriteLock lock = new ReentrantReadWriteLock();


    public static void main(String[] args) {

        new Thread(new Reader(1)).start();
        new Thread(new Reader(2)).start();
        new Thread(new Reader(3)).start();

        new Thread(new Writer(4)).start();
        new Thread(new Writer(5)).start();
        new Thread(new Writer(6)).start();

    }

    private static class Reader implements Runnable {

        private int n;

        public Reader(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            while (true) {
                long time = (long) (Math.random() * 1000);
                try {
                    Thread.sleep(time);
                    lock.readLock().lock();
                    System.out.println("Acquired read lock" + n);
                    lock.readLock().unlock();
                    System.out.println("Released read lock" + n);
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Writer implements Runnable {

        private int n;

        public Writer(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            while (true) {
                long time = (long) (Math.random() * 1000);
                try {
                    Thread.sleep(time);
                    lock.writeLock().lock();
                    System.out.println("Acquired write lock" + n);
                    lock.writeLock().unlock();
                    System.out.println("Released write lock" + n);
                    Thread.sleep(time);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
