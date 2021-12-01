package concurrency.locks;

import concurrency.nonblockingds.MeetingInvitee;

import javax.naming.OperationNotSupportedException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class ConcurrentBarrier {


    private AtomicInteger counter;

    private Lock lock = new ReentrantLock();

    private Condition parking = lock.newCondition();

    private int PARTIES;

    public ConcurrentBarrier(int PARTIES) {
        this.PARTIES = PARTIES;
        this.counter = new AtomicInteger(PARTIES);
    }

    private void reset() {
        counter = new AtomicInteger(PARTIES);
    }

    public void await() throws InterruptedException, OperationNotSupportedException {
        if (counter.get() <= 0) {
            throw new OperationNotSupportedException("Barrier broken");
        }
        if (counter.decrementAndGet() == 0) {
            lock.lock();
            parking.signalAll();
            lock.unlock();
        } else {
            lock.lock();
            parking.await();
            lock.unlock();
        }

    }


    public static void main(String[] args) throws OperationNotSupportedException, InterruptedException {

        ConcurrentBarrier barrier = new ConcurrentBarrier(4);
        MeetingInvitee invitee1 = new MeetingInvitee(barrier);
        MeetingInvitee invitee2 = new MeetingInvitee(barrier);
        MeetingInvitee invitee3 = new MeetingInvitee(barrier);
        workersPerformTasks(invitee1, invitee2, invitee3);
        barrier.await();
        System.out.println("Barrier tripped now");
        barrier.reset();
        workersPerformTasks(invitee1, invitee2, invitee3);
        barrier.await();
        System.out.println("Barrier tripped now");

    }

    private static void workersPerformTasks(MeetingInvitee invitee1, MeetingInvitee invitee2, MeetingInvitee invitee3) {
        new Thread(invitee1).start();
        new Thread(invitee2).start();
        new Thread(invitee3).start();
    }


}
