package concurrency.locks;

import concurrency.locks.ConcurrentRWLock2;
import concurrency.locks.Reader;
import concurrency.locks.Writer;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JavaConcurrentLockTest {


    public static void main(String[] args) {

        //ReadWriteLock lock = new ConcurrentReadWriteLock();
        //ReadWriteLock lock = new ReadWriteLockUsingObject(5);
        ReadWriteLock lock = new ReadWriteLockUsingJavaLock(5);
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
