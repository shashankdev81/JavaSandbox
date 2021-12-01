package concurrency.prodcons;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerProblemConcurrentQueue {

    private static int MAX = 100;

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(MAX);

    private static class Producer implements Runnable {

        int num;

        public Producer(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String value = "Thread:" + num + "=" + UUID.randomUUID().toString();
                    queue.put(value);
                    System.out.println("Produced value=" + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                try {
                    System.out.println("Consumed by Thread:" + num + ", value=" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        new Thread(new Producer(1)).start();
        new Thread(new Producer(2)).start();
        new Thread(new Consumer(1)).start();
        new Thread(new Consumer(2)).start();

    }

}
