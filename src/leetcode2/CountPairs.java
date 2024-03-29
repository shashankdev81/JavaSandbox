package leetcode2;

public class CountPairs {

    public static void main(String[] args) {
        countPairs(new int[]{1, 10, 6, 2}, new int[]{1, 4, 1, 5});
    }

    public static long countPairs(int[] nums1, int[] nums2) {
        long ans = 0;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        // nums1[i] - nums2[i] > nums2[j] - nums1[j]
        for (int i = 0; i < nums1.length; i++) {
            max = Math.max(max, Math.abs(nums2[i] - nums1[i]));
            min = Math.min(min, -Math.abs(nums2[i] - nums1[i]));
        }

        int[] bit = new int[max - min + 2];
        for (int i = 0; i < nums1.length; i++) {
            int d = nums1[i] - nums2[i];
            int k = (sum(bit, d - 1 - min) - sum(bit, -1));
            ans += k;
            update(bit, 1, -d - min);
        }

        return ans;
    }

    private static void update(int[] bit, int inc, int idx) {
        ++idx;
        while (idx < bit.length) {
            bit[idx] += inc;
            idx += idx & -idx;
        }
    }

    private static int sum(int[] bit, int idx) {
        ++idx;
        int sum = 0;
        while (idx > 0) {
            sum += bit[idx];
            idx -= idx & -idx;
        }
        return sum;
    }
}
