package concurrency;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Fibonacci extends RecursiveTask<Long> implements Callable<Long> {

    ForkJoinPool customThreadPool = new ForkJoinPool(10); // Example with 4 parallel threads

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci(30);
        long now = System.currentTimeMillis();
        System.out.println(fibonacci.compute());
        long then = System.currentTimeMillis();
        System.out.println((then - now));
    }


    private final long n;

    Fibonacci(long n) {
        this.n = n;
    }

    public Long compute1() {
        if (n <= 1) {
            return n;
        }
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();
        Set<Integer> intersection = new HashSet<Integer>(s1); // use the copy constructor
        intersection.addAll(s2);
        long num = 0;

        Fibonacci f1 = new Fibonacci(n - 1);
        Fibonacci f2 = new Fibonacci(n - 2);
        List<Fibonacci> tasks = new ArrayList<>();
        tasks.add(f1);
        tasks.add(f2);
        customThreadPool.invokeAll(tasks);
        for (Fibonacci task : tasks) {
            num += task.join();
        }
        return num;
    }

    public Long compute2() {
        if (n <= 1) {
            return n;
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        Fibonacci f2 = new Fibonacci(n - 2);
        List<RecursiveTask<Long>> tasks = new ArrayList<>();
        tasks.add(f1);
        tasks.add(f2);
        long num = ForkJoinTask.invokeAll(tasks).stream().map(f -> f.join()).mapToInt(i -> i.intValue()).sum();
        return num;
    }

    public Long compute() {
        if (n <= 1) {
            return n;
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);
        return f2.compute() + f1.join();
    }

    @Override
    public Long call() throws Exception {
        return compute1();
    }
}

