package concurrency.java;

import concurrency.locks.NonBlockingMutexLock;

import java.util.concurrent.Semaphore;

public class MutexTest {

    private static NonBlockingMutexLock mutex = new NonBlockingMutexLock();

    public static void main(String[] args) {

        Semaphore s = new Semaphore(5);
        new Thread(new Process()).start();
        new Thread(new Process()).start();
        new Thread(new Process()).start();
        new Thread(new Process()).start();

    }

    private static class Process implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    mutex.acquire();
                    Thread.sleep(1000);
                    System.out.println("Acquired:" + this.toString());
                    mutex.release();
                    System.out.println("Released:" + this.toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
