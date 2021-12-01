package concurrency.prodcons;

import java.util.ArrayDeque;
import java.util.Queue;

public class ProducerConsumerProblemUsingDiffLockDeadlock {


    private static Queue<String> queue = new ArrayDeque<String>();

    private static Object LOCK = new Object();

    private static Object EMPTY_LOCK = new Object();

    private static Object OVERFLOW_LOCK = new Object();

    private static int COUNT = 0;

    private static int MAX = Integer.MAX_VALUE;

    private static class Producer implements Runnable {

        int num;

        public Producer(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (LOCK) {
                    if (COUNT == MAX) {
                        try {
                            synchronized (OVERFLOW_LOCK) {
                                OVERFLOW_LOCK.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        COUNT++;
                        String value = "Thread:" + num + "=" + COUNT;
                        queue.add(value);
                        System.out.println("Produced new value:" + value);
                        synchronized (EMPTY_LOCK) {
                            EMPTY_LOCK.notifyAll();
                        }
                    }
                }
                System.out.println("Released lock by producer");
            }
        }
    }

    private static class Consumer implements Runnable {

        private int num;

        public Consumer(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (LOCK) {
                    if (COUNT == 0) {
                        try {
                            synchronized (EMPTY_LOCK) {
                                EMPTY_LOCK.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Consumed by Thread:" + num + ", value=" + queue.poll());
                        COUNT--;
                        synchronized (OVERFLOW_LOCK) {
                            OVERFLOW_LOCK.notifyAll();
                        }
                    }
                }
                System.out.println("Released lock by consumer");
            }
        }
    }

    public static void main(String[] args) {

        new Thread(new Consumer(1)).start();
        new Thread(new Consumer(2)).start();
        new Thread(new Producer(1)).start();
        new Thread(new Producer(2)).start();

    }

}
