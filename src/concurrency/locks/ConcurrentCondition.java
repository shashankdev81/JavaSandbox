package concurrency.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentCondition {



    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        lock.newCondition();


    }

}
