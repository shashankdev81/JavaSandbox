package leetcode2;

public class MinimumBalls {

    public static void main(String[] args) {
        MinimumBalls minimumBalls = new MinimumBalls();
        System.out.println(minimumBalls.minimumSize(new int[]{2, 4, 8, 2}, 2));
    }

    public int minimumSize(int[] nums, int maxOperations) {

        int max = 0;
        for (int i : nums) {
            max = Math.max(i, max);
        }
        int low = 1, high = max;
        while (low < high) {
            int mid = (high - low) / 2 + low;
            if (helper(nums, mid, maxOperations)) {
                high = mid;
            } else
                low = mid + 1;
        }
        return low;
    }

    boolean helper(int arr[], int mid, int k) {

        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            counter += (arr[i] - 1) / mid;
            if (counter > k) {
                return false;
            }
        }
        return true;
    }
}

