package concurrency.misc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class ZeroEvenOdd {
    private int n;

    private volatile int count = 1;

    private volatile int even = 0;

    private volatile int odd = -1;

    private volatile boolean isDone = false;

    private Lock lock = new ReentrantLock();

    private Condition zeroCondition = lock.newCondition();

    private Condition oddEvenCondition = lock.newCondition();

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        while (count % 2 == 0) {
            isDone = even == n || odd == n;
            interruptIfDone();
            zeroCondition.await();
        }
        if (!isDone) {
            printNumber.accept(0);
            count++;
        }
        oddEvenCondition.signalAll();
        interruptIfDone();
        lock.unlock();
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        while (count % 4 != 0) {
            isDone = even == n || odd == n;
            interruptIfDone();
            oddEvenCondition.await();
        }
        if (!isDone) {
            even += 2;
            printNumber.accept(even);
            count++;
        }
        zeroCondition.signalAll();
        interruptIfDone();
        lock.unlock();
    }


    public void odd(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        while ((count - 2) % 4 != 0) {
            isDone = even == n || odd == n;
            interruptIfDone();
            oddEvenCondition.await();
        }
        if (!isDone) {
            odd += 2;
            printNumber.accept(odd);
            count++;
        }
        zeroCondition.signalAll();
        interruptIfDone();
        lock.unlock();

    }

    private void interruptIfDone() throws InterruptedException {
        if (isDone) {
            zeroCondition.signalAll();
            oddEvenCondition.signalAll();
            lock.unlock();
            throw new InterruptedException("");
        }
    }


    public void startTest() {
        Printer printer = new Printer();
        Odd o1 = new Odd(this, printer);
        Odd o2 = new Odd(this, printer);
        Zero z1 = new Zero(this, printer);
        Zero z2 = new Zero(this, printer);
        Even e1 = new Even(this, printer);
        Even e2 = new Even(this, printer);
        new Thread(z1).start();
        new Thread(z2).start();
        new Thread(e1).start();
        new Thread(e2).start();
        new Thread(o1).start();
        new Thread(o2).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        System.out.println(printer.buffer.toString());

    }


    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(10);
        zeroEvenOdd.startTest();

    }


    private class Even implements Runnable {

        ZeroEvenOdd zeroEvenOdd;

        Printer printer;

        volatile boolean isStop = false;

        public Even(ZeroEvenOdd zeroEvenOdd, Printer printer) {
            this.zeroEvenOdd = zeroEvenOdd;
            this.printer = printer;
        }

        @Override
        public void run() {
            while (!isStop) {
                try {
                    zeroEvenOdd.even(printer);
                } catch (InterruptedException e) {
                    isStop = true;
                }
            }
        }
    }

    private class Odd implements Runnable {

        ZeroEvenOdd zeroEvenOdd;

        Printer printer;

        volatile boolean isStop = false;

        public Odd(ZeroEvenOdd zeroEvenOdd, Printer printer) {
            this.zeroEvenOdd = zeroEvenOdd;
            this.printer = printer;
        }

        @Override
        public void run() {
            while (!isStop) {
                try {
                    zeroEvenOdd.odd(printer);
                } catch (InterruptedException e) {
                    isStop = true;
                }
            }
        }
    }

    private class Zero implements Runnable {

        ZeroEvenOdd zeroEvenOdd;

        Printer printer;

        volatile boolean isStop = false;

        public Zero(ZeroEvenOdd zeroEvenOdd, Printer printer) {
            this.zeroEvenOdd = zeroEvenOdd;
            this.printer = printer;
        }

        @Override
        public void run() {
            while (!isStop) {
                try {
                    zeroEvenOdd.zero(printer);
                } catch (InterruptedException e) {
                    isStop = true;
                }
            }
        }
    }


    private class Printer implements IntConsumer {
        StringBuffer buffer = new StringBuffer();

        @Override
        public void accept(int value) {
            buffer.append(value).append(",");

        }

        @Override
        public IntConsumer andThen(IntConsumer after) {
            return IntConsumer.super.andThen(after);
        }
    }
}
