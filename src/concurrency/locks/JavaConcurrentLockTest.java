package concurrency.locks;

import concurrency.locks.ConcurrentRWLock2;
import concurrency.locks.Reader;
import concurrency.locks.Writer;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JavaConcurrentLockTest {


    public static void main(String[] args) {

        ReadWriteLock lock1 = new ReentrantReadWriteLock();
        lock1.readLock().lock();
        //ConcurrentRWLock2 lock1 = new ConcurrentRWLock2();
        ConcurrentReadWriteLock lock = new ConcurrentReadWriteLock();
        Thread reader1 = new Thread(new Reader(lock, 1));
        Thread reader2 = new Thread(new Reader(lock, 2));
        Thread reader3 = new Thread(new Reader(lock, 3));

        Thread writer1 = new Thread(new Writer(lock, 4));
        Thread writer2 = new Thread(new Writer(lock, 5));
        Thread writer3 = new Thread(new Writer(lock, 6));

        reader1.start();
        reader2.start();
        reader3.start();
        writer1.start();
        writer2.start();
        writer3.start();

    }
}
