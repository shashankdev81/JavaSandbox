package concurrency.problems.baboon_crossing;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BaboonCrossingProblem {

    public BaboonCrossingProblem() {
        Rope rope = new Rope();
        new Thread(new Baboon("1", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("2", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("11", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("3", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("12", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("4", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("13", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("5", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("14", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("6", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("15", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("7", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("8", rope, Direction.A_TO_B)).start();

        new Thread(new Baboon("21", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("22", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("31", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("23", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("32", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("24", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("33", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("25", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("34", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("26", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("35", rope, Direction.B_TO_A)).start();
        new Thread(new Baboon("27", rope, Direction.A_TO_B)).start();
        new Thread(new Baboon("28", rope, Direction.A_TO_B)).start();
    }

    public static void main(String[] args) {
        BaboonCrossingProblem baboonCrossingProblem = new BaboonCrossingProblem();

    }

    private class Rope {

        int MAX_OCCUPANTS = 5;

        Lock lock = new ReentrantLock();

        AtomicInteger a_to_b_waitList = new AtomicInteger();

        AtomicInteger b_to_a_waitList = new AtomicInteger();

        Condition waitForAtoB = lock.newCondition();

        Condition waitForBtoA = lock.newCondition();

        Semaphore permits = new Semaphore(MAX_OCCUPANTS);

        Direction currentTurn;

        private void acquire(Direction direction, String name) {
            boolean isAcquired = false;
            while (!isAcquired) {
                try {
                    lock.lock();
                    if (currentTurn == null) {
                        currentTurn = direction;
                    }

                    //if the rope is already occupied by baboons coming in opposite direction
                    if (direction != currentTurn && isSomeoneWaiting()) {
                        waitForTurn(direction);
                    }

                    //if the rope is already occupied by 5 baboons in same direction
                    if (!permits.tryAcquire()) {
                        System.out.println("Will wait:" + name);
                        waitForTurn(direction);
                    } else {
                        isAcquired = true;
                        System.out.println("permit acquired:" + name + "," + (MAX_OCCUPANTS - permits.availablePermits()));
                    }
                    lock.unlock();
                } catch (Exception e) {

                }
            }
        }

        private boolean isSomeoneWaiting() {
            return a_to_b_waitList.get() > 0 || b_to_a_waitList.get() > 0;
        }

        private void waitForTurn(Direction direction) throws InterruptedException {
            if (direction.equals(Direction.A_TO_B)) {
                a_to_b_waitList.incrementAndGet();
                waitForAtoB.await();
            } else {
                b_to_a_waitList.incrementAndGet();
                waitForBtoA.await();
            }
        }

        private void release(Direction direction) {
            lock.lock();
            permits.release(1);
            if (permits.availablePermits() == MAX_OCCUPANTS) {
                System.out.println("b_to_a_waitList=" + b_to_a_waitList.get());
                System.out.println("a_to_b_waitList=" + a_to_b_waitList.get());
                if (currentTurn.equals(Direction.A_TO_B)) {
                    if (b_to_a_waitList.get() > 0) {
                        awakenBabons(b_to_a_waitList, waitForBtoA);
                    } else {
                        awakenBabons(a_to_b_waitList, waitForAtoB);
                    }
                    currentTurn = Direction.B_TO_A;
                } else {
                    if (a_to_b_waitList.get() > 0) {
                        awakenBabons(a_to_b_waitList, waitForAtoB);
                    } else {
                        awakenBabons(b_to_a_waitList, waitForBtoA);
                    }
                    currentTurn = Direction.A_TO_B;
                }
            }
            lock.unlock();
        }

        private void awakenBabons(AtomicInteger waitList, Condition condition) {
            condition.signalAll();
            waitList.set(0);
        }
    }

    private enum Direction {
        A_TO_B, B_TO_A
    }

    private class Baboon implements Runnable {

        private String name;

        private Rope rope;

        private Direction direction;

        public Baboon(String name, Rope rope, Direction direction) {
            this.name = name;
            this.rope = rope;
            this.direction = direction;
        }

        @Override
        public void run() {
            System.out.println(name + " will try to acquire rope");
            rope.acquire(direction, name);
            try {
                System.out.println(name + " will cross rope from " + direction.name());
                Thread.sleep((long) (200 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rope.release(direction);
        }
    }
}
