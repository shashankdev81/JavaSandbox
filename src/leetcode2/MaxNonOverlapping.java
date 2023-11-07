package leetcode2;

import java.util.ArrayDeque;
import java.util.HashSet;

public class MaxNonOverlapping {

    public static void main(String[] args) {
        MaxNonOverlapping maxNonOverlapping = new MaxNonOverlapping();
        System.out.println(maxNonOverlapping.maxNonOverlapping(new int[]{-1, 3, 5, 1, 4, 2, -9}, 6));
    }

    public int maxNonOverlapping(int[] nums, int target) {
        int res = 0;
        HashSet<Integer> preSum = new HashSet<>();
        preSum.add(0);
        int prev = 0;
        for (int i = 0; i < nums.length; i++) {
            int val = prev + nums[i];
            if (preSum.contains(val - target)) {
                res++;
                preSum = new HashSet<>();
            }
            preSum.add(val);
            prev = val;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        return res;
    }
}
