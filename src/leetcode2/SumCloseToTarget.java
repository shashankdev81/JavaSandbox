package leetcode2;


import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

class SumCloseToTarget {

    // 3,4,9  9,12,27
    // 2,3,5   6,6,5

    public static void main(String[] args) {
        SumCloseToTarget sumCloseToTarget = new SumCloseToTarget();
        sumCloseToTarget.findBestValue(new int[]{9,4,3}, 10);

    }

    public int findBestValue(int[] arr, int target) {
        int high = Arrays.stream(arr).max().orElseGet(() -> -1);
        final AtomicInteger val = new AtomicInteger(0);
        int low = 0;
        while (low < high) {
            val.set(low + (high - low) / 2);
            int sum1 = Math.abs(Arrays.stream(arr).map(i -> (i > val.get() ? val.get() : i)).sum() - target);
            int sum2 = Math.abs(Arrays.stream(arr).map(i -> (i > val.get() + 1 ? val.get() + 1 : i)).sum() - target);
            System.out.println(low + "," + high + "," + val.get() + "," + sum1 + "," + sum2);
            if (sum1 < sum2) {
                high = val.get();
                System.out.println("high=" + high);
            } else if (sum1 > sum2) {
                low = val.incrementAndGet();
                System.out.println("low=" + low);
            } else {
                return val.get();
            }
        }
        return low;

    }
}