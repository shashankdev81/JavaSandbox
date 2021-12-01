package concurrency.prodcons;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * This class is thread UNSAFE because the producing of item and increment of counter isn't an atomic operation.
 * Only increment and decrement of counter is! What this means is when a consumer has consumed an event and decremeted
 * a counter there exists a scenari wherein producer increments a count and is yet to add a new item. In the meanwhile
 * same or different consumer consumes the same item once again thinking its a new one!
 * */
public class ProducerConsumerProblemThreadUnsafeNonBlocking {


    private static Set<String> values = new ConcurrentSkipListSet<String>();

    private static String[] queue = new String[1000];

    private static AtomicInteger COUNT = new AtomicInteger(0);

    private static int MAX = 100;

    private static class Producer implements Runnable {

        private int num;

        public Producer(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (true) {
                int curr = COUNT.intValue();
                if (curr < MAX && COUNT.compareAndSet(curr, curr + 1)) {
                    queue[curr] = "Thread:" + num + "=" + UUID.randomUUID().toString();
                    System.out.println("Produced new item:" + curr + ", value=" + queue[curr]);
                    if (curr > 10) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
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
                int curr = COUNT.intValue();
                if (curr > 0 && COUNT.compareAndSet(curr, curr - 1)) {
                    String value = queue[curr - 1];
                    if (values.contains(value)) {
                        System.out.println("Duplicate Consumed by Thread:" + num + ", index=" + (curr - 1) + ", value=" + value);
                    } else {
                        values.add(value);
                        System.out.println("Consumed by Thread:" + num + ", index=" + (curr - 1) + ", value=" + value);
                    }
                }
            }
        }

        public static void main(String[] args) {
            new Thread(new Producer(1)).start();
            new Thread(new Consumer(1)).start();
            new Thread(new Consumer(2)).start();
            new Thread(new Consumer(3)).start();
            new Thread(new Consumer(4)).start();

        }

    }
}
