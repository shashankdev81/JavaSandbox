package concurrency.nonblockingds;

import concurrency.locks.ConcurrentBarrier;

import javax.naming.OperationNotSupportedException;

public class MeetingInvitee implements Runnable {


    private ConcurrentBarrier barrier;

    public MeetingInvitee(ConcurrentBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("Will wait for barrier trip");
            barrier.await();
        } catch (InterruptedException | OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }


}
