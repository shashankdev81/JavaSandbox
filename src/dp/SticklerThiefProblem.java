package dp;

import java.util.Arrays;
import java.util.Collections;

public class SticklerThiefProblem {

    public int getMaxBountyRecursive(int[] values) {

        Integer[] dp = new Integer[values.length];
        Arrays.fill(dp, -1);

        for (int i = dp.length - 1; i >= 0; i--) {
            if (dp[i] == -1) {
                dp[i] = values[i] + getMaxEarnings(dp, values, i - 2);
            }
        }

        return Collections.max(Arrays.asList(dp));
    }

    public int getMaxBountyIteraive(int[] values) {

        Integer[] dp = new Integer[values.length];
        Arrays.fill(dp, -1);

        for (int i = 0; i < dp.length; i++) {
            dp[i] = values[i];
            if (i == 0) {
                continue;
            }
            for (int j = 0; j < i; j++) {
                if (j == i - 1) {
                    dp[i] = Math.max(dp[i], dp[j]);
                } else {
                    dp[i] = Math.max(dp[i], dp[j] + values[i]);
                }
            }
        }

        return Collections.max(Arrays.asList(dp));
    }


    private int getMaxEarnings(Integer[] dp, int[] values, int i) {
        if (i < 0) {
            return 0;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        if (i == 0) {
            dp[i] = values[i];
            return dp[i];
        }
        dp[i] = values[i];
        for (int j = i - 1; j >= 0; j--) {
            dp[i] = Math.max(dp[i], (j == i - 1) ? getMaxEarnings(dp, values, j) : (getMaxEarnings(dp, values, j) + values[i]));
        }
        return dp[i];

    }

    public static void main(String[] args) {
        SticklerThiefProblem problem = new SticklerThiefProblem();
        System.out.println(problem.getMaxBountyIteraive(new int[]{4, 8, 3, 2, 10}));
    }
}
