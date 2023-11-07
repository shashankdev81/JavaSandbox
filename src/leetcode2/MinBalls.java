package leetcode2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Collectors;

public class MinBalls {
    public static void main(String[] args) {
        MinBalls minBalls = new MinBalls();
        System.out.println(minBalls.minimumSize(new int[]{9}, 2));
        System.out.println(minBalls.minimumSize(new int[]{2, 4, 8, 2}, 4));

    }

    public int minimumSize(int[] nums, int maxOp) {
        int high = 0;
        for (int i : nums) {
            if (high < i) high = i;
        }
        int low = 1, ans = -1;
        while (low <= high) {
            int mid = (low + high) / 2, sum = 0;
            for (int i : nums) {
                sum += (i - 1) / mid;
            }
            if (sum <= maxOp) {
                high = mid - 1;
                ans = mid;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }
}

