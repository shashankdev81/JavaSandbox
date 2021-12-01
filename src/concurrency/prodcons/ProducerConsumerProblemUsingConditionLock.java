package concurrency.prodcons;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerProblemUsingConditionLock {


    private static Queue<String> queue = new ArrayDeque<String>();

    private static Lock LOCK = new ReentrantLock();

    private static Condition NOT_FULL = LOCK.newCondition();

    private static Condition NOT_EMPTY = LOCK.newCondition();

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
                try {
                    LOCK.lock();
                    if (COUNT == MAX) {
                        NOT_FULL.await();
                    } else {
                        COUNT++;
                        String value = "Thread:" + num + "=" + COUNT;
                        queue.add(value);
                        System.out.println("Produced new value:" + value);
                        NOT_EMPTY.signalAll();
                    }
                } catch (Exception e) {

                } finally {
                    LOCK.unlock();
                }

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
                LOCK.lock();
                try {
                    if (COUNT == 0) {
                        try {
                            NOT_EMPTY.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Consumed by Thread:" + num + ", value=" + queue.poll());
                        COUNT--;
                        NOT_FULL.signalAll();
                    }
                } catch (Exception e) {

                } finally {
                    LOCK.unlock();
                }
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
