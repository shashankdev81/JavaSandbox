package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MinDifferenceThreeMoves {

    private int k = 3;

    public int minDifference(int[] nums) {
        int n = nums.length;
        if (n < 5) {
            return 0;
        }
        Arrays.sort(nums);
        int L = nums.length - 1;
        Integer min[] = new Integer[k + 1];
        for (int i = 0; i <= k; i++) {
            min[i] = nums[L - i] - nums[k - i];
        }

        return Collections.min(Arrays.asList(min));
    }

    public int minDifference1(int[] nums) {
        int n = nums.length;
        if (n < 5) {
            return 0;
        }
        Arrays.sort(nums);
        int ans = Integer.MAX_VALUE;

        int left = 0;
        int right = n - 4;
        int i = 0;
        while (i < 4) {
            ans = Math.min(ans, nums[right] - nums[left]);
            left++;
            right++;
            i++;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 5, 6, 7, 8, 9, 10, 14, 15};

        int[] nums1 = new int[]{5, 3, 2, 4};
        int[] nums2 = new int[]{1, 5, 6, 14, 15};
        int[] nums3 = new int[]{6, 6, 0, 1, 1, 4, 6};
        int[] nums4 = new int[]{1, 5, 6, 14, 15};

        MinDifferenceThreeMoves moves = new MinDifferenceThreeMoves();
        System.out.println(moves.minDifference(nums));
        System.out.println(moves.minDifference(nums1));
        System.out.println(moves.minDifference(nums2));
        System.out.println(moves.minDifference(nums3));
        System.out.println(moves.minDifference(nums4));
    }

}
