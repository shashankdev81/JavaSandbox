package concurrency.problems.unisex_bathoom;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnisexBathroom {

    private Bathroom bathroom = new Bathroom();

    public UnisexBathroom() {
        Person man1 = new Person("1", true);
        Person man2 = new Person("2", true);
        Person man3 = new Person("3", true);
        Person woman1 = new Person("4", false);
        Person woman2 = new Person("5", false);
        Person woman3 = new Person("6", false);
        man1.visitBathroom();
        man2.visitBathroom();
        man3.visitBathroom();
        woman1.visitBathroom();
        woman2.visitBathroom();
        woman3.visitBathroom();

    }

    public static void main(String[] args) {
        UnisexBathroom unisexBathroom = new UnisexBathroom();

    }

    public class Person {

        private String id;

        private boolean isMan;

        public Person(String id, boolean isMan) {
            this.id = id;
            this.isMan = isMan;
        }

        public void visitBathroom() {
            BathroomVisit bathroomVisit = new BathroomVisit(id, isMan, bathroom);
            new Thread(bathroomVisit).start();
        }

        private class BathroomVisit implements Runnable {

            private String id;

            private boolean isMan;

            private Bathroom bathroom;

            public BathroomVisit(String id, boolean isMan, Bathroom bathroom) {
                this.id = id;
                this.isMan = isMan;
                this.bathroom = bathroom;
            }


            @Override
            public void run() {
                bathroom.enter(isMan);
                try {
                    Thread.sleep((long) (1000 * Math.random()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bathroom.leave(isMan);
            }
        }


    }


    public class Bathroom {

        private Semaphore semaphore = new Semaphore(3);

        private Lock lock = new ReentrantLock();

        private AtomicInteger men = new AtomicInteger(0);

        private AtomicInteger women = new AtomicInteger(0);

        private Condition womenInside = lock.newCondition();

        private Condition menInside = lock.newCondition();

        public void enter(boolean isMan) {
            lock.lock();
            try {
                if (isMan) {
                    while (women.get() > 0) {
                        womenInside.await();
                    }
                    semaphore.acquire();
                    men.incrementAndGet();
                } else {
                    while (men.get() > 0) {
                        menInside.await();
                    }
                    semaphore.acquire();
                    women.incrementAndGet();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void leave(boolean isMan) {
            lock.lock();
            if (isMan) {
                semaphore.release();
                int remaining = men.decrementAndGet();
                if (remaining == 0) {
                    menInside.signalAll();
                }
            } else {
                int remaining = women.decrementAndGet();
                if (remaining == 0) {
                    womenInside.signalAll();
                }
            }
            lock.unlock();
        }

    }
}

