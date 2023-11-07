package concurrency.misc;

import java.util.*;
import java.util.concurrent.*;

public class WorkStealingExample {

    private static int total = 0;

    public static void main(String args[]) {
        CyclicBarrier barrier = new CyclicBarrier(4);
        barrier.reset();
        long start = System.currentTimeMillis();
        //ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinPool pool = (ForkJoinPool) Executors.newWorkStealingPool(10);
        WorkStealing work = new WorkStealing(1, 100);
        pool.invoke(work);
        pool.shutdown();
        long end = System.currentTimeMillis();
        System.out.println(total + " calculated,in " + (end - start) + " ms");

    }

    public static class WorkStealing extends RecursiveAction {

        int start;

        int end;

        public WorkStealing(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            System.out.println("start, end " + start + "," + end);
            List<WorkStealing> work = new ArrayList<>();
            if (end - start < 2) {
                total += sum(start, end);
                return;
            }
            int mid = (start + end) / 2;
            work.add(new WorkStealing(start, mid));
            work.add(new WorkStealing(mid + 1, end));
            ForkJoinTask.invokeAll(work);
        }

        private int sum(int start, int end) {
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
