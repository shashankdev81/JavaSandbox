package concurrency.problems.barbershop;

/*
* A barbershop consists of a waiting room with n chairs, and the
barber room containing the barber chair. If there are no customers
to be served, the barber goes to sleep. If a customer enters the
barbershop and all chairs are occupied, then the customer leaves
the shop. If the barber is busy, but chairs are available, then the
customer sits in one of the free chairs. If the barber is asleep, the
customer wakes up the barber. Write a program to coordinate the
barber and the customers.
* * */

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopProblem {

    public static void main(String[] args) throws InterruptedException {
        BarberShop barberShop = new BarberShop();
        barberShop.open();
    }
}

class BarberShop {

    private Queue<String> waitingChairs = new LinkedBlockingDeque<>(3);

    private AtomicInteger sequence = new AtomicInteger(0);

    private Lock barber = new ReentrantLock();

    private Condition isNewCustomer = barber.newCondition();

    private CountDownLatch latch = new CountDownLatch(3);

    public void open() {
        new Thread(new Customer("shashank")).start();
        new Thread(new Customer("gaurav")).start();
        new Thread(new Customer("shreesha")).start();
        new Thread(new Customer("amitabh")).start();
        new Thread(new Barber()).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class Customer implements Runnable {

        private String name;

        public Customer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            if (!waitingChairs.offer(name)) {
                System.out.println("Customer walks out:" + name);
                return;
            } else {
                System.out.println("Customer walks in:" + name + sequence.incrementAndGet());
                barber.lock();
                System.out.println("Will awaken barber if asleep");
                isNewCustomer.signal();
                barber.unlock();
            }
        }
    }

    private class Barber implements Runnable {


        @Override
        public void run() {
            while (true) {
                barber.lock();
                String nextCust = waitingChairs.poll();
                try {
                    if (nextCust == null) {
                        System.out.println("Barber will sleep");
                        isNewCustomer.await();
                        System.out.println("Barber is awake");
                    } else {
                        try {
                            Thread.sleep(2000);
                            System.out.println("Customer hair cut:" + nextCust);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        latch.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                barber.unlock();
            }

        }

    }
}
