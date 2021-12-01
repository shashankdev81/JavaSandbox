package dp;

public class EditDistance {

    private int getMinEditDistance(String s1, String s2) {

        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int top = dp[i - 1][j];
                int left = dp[i][j - 1];
                int top_left = dp[i - 1][j - 1];
                dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1) ? top_left : Math.min(top_left, Math.min(top, left)) + 1;
            }
        }

        return dp[s1.length()][s2.length()];
    }


    public int minDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // if one of the strings is empty
        if (n * m == 0)
            return n + m;

        // array to store the convertion history
        int[][] d = new int[n + 1][m + 1];

        // init boundaries
        for (int i = 0; i < n + 1; i++) {
            d[i][0] = i;
        }
        for (int j = 0; j < m + 1; j++) {
            d[0][j] = j;
        }

        // DP compute
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                int top = d[i - 1][j] + 1;
                int left = d[i][j - 1] + 1;
                int left_top = d[i - 1][j - 1];
                if (word1.charAt(i - 1) != word2.charAt(j - 1))
                    left_top += 1;
                d[i][j] = Math.min(top, Math.min(left, left_top));

            }
        }
        return d[n][m];
    }


    public static void main(String[] args) {

        EditDistance editDistance = new EditDistance();
        System.out.println(editDistance.getMinEditDistance("horse", "ros"));
        System.out.println(editDistance.getMinEditDistance("sea", "eat"));
        System.out.println(editDistance.getMinEditDistance("plasma", "altruism"));
        System.out.println(editDistance.minDistance("plasma", "altruism"));

    }
}
