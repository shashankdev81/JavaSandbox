package concurrency.problems.barbershop;

/*
Our barbershop has 3 chairs, 3 barbers, and a waiting
area that can accommodate 4 customers on a sofa and that has
standing room for additional customers. Fire codes limit the total
number of customers in the shop to 20.

A customer will not enter the shop if it is filled to capacity with
other customers. Once inside, the customer takes a seat on the sofa
or stands if the sofa is filled. When a barber is free, the customer
that has been on the sofa the longest is served and, if there are any
standing customers, the one that has been in the shop the longest
takes a seat on the sofa. When a customer’s haircut is finished,
any barber can accept payment, but because there is only one cash
register, payment is accepted for one customer at a time.

The barbers divide their time among cutting hair, accepting payment, and
sleeping in their chair waiting for a customer.
 */

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShopProblemManyBarbers {

    public static void main(String[] args) throws InterruptedException {
        BarberShopManyBarbers barberShop = new BarberShopManyBarbers();
        barberShop.open();
    }
}

class BarberShopManyBarbers {

    public static final int SITTING_CAPACITY = 4;

    public static final int STANDING_CAPACITY = 16;

    private Queue<String> standingQueue = new LinkedBlockingDeque<>(STANDING_CAPACITY);

    private Queue<String> sittingQueue = new LinkedBlockingDeque<>(SITTING_CAPACITY);

    private AtomicInteger sequence = new AtomicInteger(0);

    private Lock barber = new ReentrantLock();

    private Condition isNewCustomer = barber.newCondition();

    private CountDownLatch latch = new CountDownLatch(4);

    public void open() {
        new Thread(new QueueManager()).start();
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
            if (!standingQueue.offer(name)) {
                System.out.println("Customer walks out:" + name);
                return;
            } else {
                System.out.println("Customer walks in - stands in line :" + name + sequence.incrementAndGet());
            }
        }
    }

    private class Barber implements Runnable {


        @Override
        public void run() {
            while (true) {
                barber.lock();
                String nextCust = sittingQueue.poll();
                try {
                    if (nextCust == null) {
                        System.out.println("Barber will sleep");
                        isNewCustomer.await();
                        System.out.println("Barber is awake");
                    } else {
                        try {
                            Thread.sleep(10000);
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

    private class QueueManager implements Runnable {


        @Override
        public void run() {
            while (true) {
                String nextCust = standingQueue.poll();
                if (nextCust == null) {
                    continue;
                }
                sittingQueue.offer(nextCust);
                System.out.println("Customer is seated " + nextCust);
                System.out.println("Will awaken barber if asleep");
                barber.lock();
                isNewCustomer.signal();
                barber.unlock();
            }

        }
    }

}
