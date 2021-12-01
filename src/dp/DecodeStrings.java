package dp;

import java.util.HashMap;
import java.util.Map;

public class DecodeStrings {

    Map<String, Integer> strToNumWays = new HashMap<String, Integer>();


    public int numDecodings(String s) {
        if (s.isEmpty()) {
            return 0;

        }
        if (s.length() == 1) {
            return isValidThisChar(s) ? 1 : 0;
        }

        int[] dp = new int[s.length()];

        dp[0] = isValidThisChar(s) ? 1 : 0;
        for (int i = 1; i < s.length(); i++) {
            boolean isValidFirstTwo = isValidFirstTwoChars(s.substring(i - 1, i + 1));
            boolean isValidThisChar = isValidThisChar(s.substring(i, i + 1));

            if (!isValidFirstTwo && !isValidThisChar) {
                return 0;
            }

            if (isValidFirstTwo && isValidThisChar) {
                dp[i] = dp[i - 1] + (i >= 2 ? dp[i - 2] : 1);
            } else if (isValidFirstTwo && s.substring(i - 1, i + 1).endsWith("0")) {
                dp[i] = (i >= 2 ? dp[i - 2] : 1);
            } else {
                dp[i] = dp[i - 1];
            }
        }

        return dp[dp.length - 1];
    }

    public int numDecodings1(String s) {
        if (s.isEmpty()) {
            return 1;
        }
        if (strToNumWays.containsKey(s)) {
            return strToNumWays.get(s);
        }
        boolean isValidFirstTwo = isValidFirstTwoChars(s);
        boolean isValidFirstChar = isValidThisChar(s);
        int numWays = 0;
        if (isValidFirstTwo && isValidFirstChar) {
            numWays = numDecodings1(s.substring(1, s.length())) + numDecodings1(s.substring(2, s.length()));
        } else if (isValidFirstChar) {
            numWays = numDecodings1(s.substring(1, s.length()));
        } else {
            numWays = 0;
        }
        strToNumWays.putIfAbsent(s, numWays);
        return numWays;
    }

    private boolean isValidThisChar(String s) {
        return s.charAt(0) != '0';
    }

    private boolean isValidFirstTwoChars(String s) {
        return s.length() >= 2 && s.charAt(0) != '0' && Integer.valueOf(s.substring(0, 2)) > 9 && Integer.valueOf(s.substring(0, 2)) <= 26;
    }

    public static void main(String[] args) {
        DecodeStrings decodeStrings = new DecodeStrings();
        //System.out.println(decodeStrings.numDecodings1("06"));
        System.out.println(decodeStrings.numDecodings("230"));
    }


}
