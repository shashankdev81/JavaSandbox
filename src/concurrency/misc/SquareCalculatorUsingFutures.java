package concurrency.misc;

import java.util.concurrent.*;

public class SquareCalculatorUsingFutures {

    private static ExecutorService service = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Integer> addFuture1 = add(2, 3);
        Future<Integer> addFuture2 = add(8, 9);
        boolean isMax = false;
        if (addFuture1.isDone() && addFuture2.isDone()) {
            isMax = addFuture1.get() > addFuture2.get();
        }
        System.out.println("isMax=" + isMax);

        FutureTask<Integer> task = new FutureTask<Integer>(() -> {
            System.out.println("Pretend that something complicated is computed");
            Thread.sleep(1000);
            return 42;
        });

        FutureTask<Integer> task2 = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Pretend that something complicated is computed");
                Thread.sleep(1000);
                return 42;
            }
        });

        Thread t1 = new Thread(() -> {
            try {
                int r = task.get();
                System.out.println("Result is " + r);
            } catch (InterruptedException | ExecutionException e) {
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                int r = task.get();
                System.out.println("Result is " + r);
            } catch (InterruptedException | ExecutionException e) {
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                int r = task.get();
                System.out.println("Result is " + r);
            } catch (InterruptedException | ExecutionException e) {
            }
        });

        System.out.println("Several threads are going to wait until computations is ready");
        t1.start();
        t2.start();
        t3.start();
        task.run(); // let the main thread to compute the value

    }

    private static Future<Integer> add(int x, int y) {
        Future<Integer> calculation = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(2000);
                return x + y;
            }
        });

        return calculation;
    }
}
