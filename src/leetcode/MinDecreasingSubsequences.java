package leetcode;

public class MinDecreasingSubsequences {

    public static int leastSubsequences(int... nums) {
        int[] piles = new int[nums.length];
        int size = 0;
        for (int val : nums) {
            int pile = binarySearch(piles, 0, size, val);
            piles[pile] = val;
            if (pile == size) size++;
        }
        return size;
    }

    // find first element greater than target
    public static int binarySearch(int[] nums, int lo, int hi, int target) {
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (nums[mid] <= target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    public static void main(String[] args) {
        test(leastSubsequences(2, 9, 12, 13, 4, 7, 6, 5, 10), 4);
        test(leastSubsequences(5, 2, 4, 3, 1, 6), 3);
        test(leastSubsequences(1, 1, 1), 3);
    }

    private static void test(int actual, int expected) {
        if (actual == expected) {
            System.out.println("PASSED!");
        } else {
            System.out.println(String.format("FAILED! Expected %d, but got %d", expected, actual));
        }
    }
}
