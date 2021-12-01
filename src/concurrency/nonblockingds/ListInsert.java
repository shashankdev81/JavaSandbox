package concurrency.nonblockingds;

public class ListInsert implements Runnable {

    private ConcurrentListPractise<Integer> set;

    private int id;

    private boolean isAdd;

    public ListInsert(ConcurrentListPractise<Integer> set, int n, boolean isAdd) {
        this.set = set;
        this.id = n;
        this.isAdd = isAdd;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            try {
                if (isAdd) {
                    Thread.sleep((long) (Math.random() * 1000));
                    int num = (int) (Math.random() * 10);
                    set.add(num);
                    System.out.println("Added number to set:" + num + ",size=" + (++count) + ", by:" + id);
                } else {
                    Thread.sleep((long) (Math.random() * 1000 + 1000));
                    System.out.println("Removed number from set:" + set.remove(4));
                    System.out.println("Removed number from set:" + set.remove(5));
                    System.out.println("Removed number from set:" + set.remove(6));

                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
