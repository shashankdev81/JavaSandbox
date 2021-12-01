package concurrency.nonblockingds;

import java.util.concurrent.atomic.AtomicBoolean;

public class Pusher implements Runnable {

    private Stack<Integer> stack;

    private volatile AtomicBoolean isStop = new AtomicBoolean(false);

    public Pusher(Stack<Integer> stack) {
        this.stack = stack;
    }

    public void stop() {
        this.isStop.compareAndSet(false, true);
    }

    @Override
    public void run() {
        while (!isStop.get()) {
            try {
                Thread.sleep(1);
                int num = (int) (Math.random() * 10000);
                stack.push(num);
                System.out.println(Thread.currentThread().getName() + " Pushed:" + num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
