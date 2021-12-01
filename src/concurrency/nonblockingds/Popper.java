package concurrency.nonblockingds;

import java.util.concurrent.atomic.AtomicBoolean;

public class Popper implements Runnable {

    private Stack<Integer> stack;
    private volatile AtomicBoolean isStop = new AtomicBoolean(false);

    public Popper(Stack<Integer> stack) {
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
                System.out.println(Thread.currentThread().getName() + " Popped:" + stack.pop());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
