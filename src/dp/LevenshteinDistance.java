package dp;

import java.util.HashMap;
import java.util.Map;

public class LevenshteinDistance {

    private Map<String, Integer> memorized = new HashMap<>();

    private int outer;

    private int cacheCounter;

    public int calculate(String first, String second) {
        outer++;
        if (memorized.containsKey(first + "," + second)) {
            cacheCounter++;
            return memorized.get(first + "," + second);
        }
        if (first.equalsIgnoreCase(second)) {
            return 0;
        }

        if (first.length() == 0) {
            return second.length();
        }

        if (second.length() == 0) {
            return first.length();
        }

        if (first.charAt(0) == second.charAt(0)) {
            return calculate(first.substring(1), second.substring(1));
        }
        int result = 1 + Math.min(calculate(first.substring(1), second.substring(1)),
                Math.min(calculate(first, second.substring(1)), calculate(first.substring(1), second)));
        memorized.putIfAbsent(first + "," + second, result);
        return result;

    }

    public int calculateDP(String first, String second) {
        int result = 0;

        int[][] dp = new int[first.length() + 1][second.length() + 1];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }

            }
        }
        result = dp[dp.length - 1][dp[0].length - 1];
        return result;

    }

    public static void main(String[] args) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        System.out.println(levenshteinDistance.calculate("cat", "sim"));
        //System.out.println(levenshteinDistance.calculateDP("kitten", "sitting"));
        System.out.println(levenshteinDistance.outer);
        System.out.println(levenshteinDistance.cacheCounter);
    }
}
