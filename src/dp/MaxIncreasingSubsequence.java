package dp;

import java.util.Arrays;
import java.util.Collections;

public class MaxIncreasingSubsequence {

    private int[] dp;

    public static void main(String[] args) {
        MaxIncreasingSubsequence maxIncreasingSubsequence = new MaxIncreasingSubsequence();
        int[] input = new int[]{1, 5, 4, 7, 12, 11};
        System.out.println(maxIncreasingSubsequence.getMaxIncrSubIterative(input));
        System.out.println(maxIncreasingSubsequence.geMaxIncSubRecursive(input));
        System.out.println(maxIncreasingSubsequence.MSIS(input, 0, input.length, Integer.MIN_VALUE, 0));
    }

    private int getMaxIncrSubIterative(int[] input) {
        Integer[] dp = new Integer[input.length];
        int length = dp.length;
        for (int i = 0; i < length; i++) {
            dp[i] = input[i];
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i; j++) {
                if (input[i] > input[j] && dp[i] < (dp[j] + input[i])) {
                    dp[i] = dp[j] + input[i];
                }
            }
        }

        return Collections.max(Arrays.asList(dp));

    }

    private int geMaxIncSubRecursive(int[] input) {
        dp = new int[input.length];
        Arrays.fill(dp, -1);
        int maxSub = Integer.MIN_VALUE;
        for (int i = dp.length - 1; i >= 0; i--) {
            if (dp[i] == -1) {
                dp[i] = input[i] + geMaxIncSubForIndex(input, prev(input, i));
            }
            maxSub = Math.max(maxSub, dp[i]);
        }
        return maxSub;
    }

    private int geMaxIncSubForIndex(int[] input, int i) {
        if (i == -1) {
            return 0;
        }
        if (i == 0) {
            return dp[i] = input[i];
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        for (int j = prev(input, i); j >= 0; j--) {
            dp[i] = Math.max(dp[i], input[i] + geMaxIncSubForIndex(input, j));
        }
        return dp[i];
    }

    private int prev(int[] input, int i) {
        int j = i - 1;
        while (j >= 0) {
            if (input[j] < input[i]) {
                return j;
            }
            j--;
        }
        return -1;
    }

    public int MSIS(int[] A, int i, int n, int prev, int sum) {
        // Base case: nothing is remaining
        if (i == n) {
            return sum;
        }

        // case 1: exclude the current element and process the
        // remaining elements
        int excl = MSIS(A, i + 1, n, prev, sum);

        // case 2: include the current element if it is greater
        // than the previous element
        int incl = sum;
        if (A[i] > prev) {
            incl = MSIS(A, i + 1, n, A[i], sum + A[i]);
        }

        // return the maximum of the above two choices
        return Integer.max(incl, excl);
    }

}

