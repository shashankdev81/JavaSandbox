package concurrency.nonblockingds;

public class ConcurrentListTestClass {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentListPractise<Integer> set = new ConcurrentListPractise<Integer>();
        Thread t1 = new Thread(new ListInsert(set, 1, true));
        Thread t2 = new Thread(new ListInsert(set, 2, false));
        t1.start();
        t2.start();
        Thread.sleep(10000);
        t1.interrupt();
        t2.interrupt();
        System.out.println(set);
    }
}
