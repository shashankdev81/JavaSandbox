package concurrency.locks;

public class NonBlockingSemaphoreTest {

    private static NonBlockingSemaphore semaphore = new NonBlockingSemaphore(3);

    public static void main(String[] args) {
        new Thread(new Process(1)).start();
        new Thread(new Process(2)).start();
        new Thread(new Process(3)).start();

    }

    private static class Process implements Runnable {

        private int n;

        public Process(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            while (true) {
                semaphore.acquire();
                System.out.println("Acquired" + n);
                semaphore.release();
                System.out.println("Released" + n);
            }
        }
    }
}
