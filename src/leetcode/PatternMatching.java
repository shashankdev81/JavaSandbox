package leetcode;

import java.util.LinkedHashMap;
import java.util.Map;

public class PatternMatching {

    private Map<Integer, Integer> buckets = new LinkedHashMap<>();

    private int maxChars;

    public String translate(int n, int k) {
        StringBuilder builder = new StringBuilder();
        init(k);
        int bucket = getBucket(n);
        if (bucket == -1) {
            return builder.toString();
        }
        builder.append(getLeadingNumbers(n, bucket));
        builder.append(getTrailingLetters(n, bucket));
        return builder.toString();
    }

    private String getTrailingLetters(int n, int bucket) {
        int remainder = buckets.get(bucket + 1) - n;
        int trailing = remainder % ((int) Math.pow(10, maxChars - bucket));
        return getTrailingChar(trailing, bucket);

    }

    private String getTrailingChar(int number, int charNos) {
        char start = 'A';
        if (number < 26) {
            return convert(number, (int) start);
        }
        return getTrailingChar(number % 26, charNos - 1) + convert(number / 26, (int) start);
    }

    private String convert(int leadingNumber, int start) {
        return ((char) (start + leadingNumber - 1)) + "";
    }

    private String getLeadingNumbers(int n, int bucket) {
        int remainder = buckets.get(bucket + 1) - n;
        int leadingDigits = remainder / ((int) Math.pow(10, maxChars - bucket));
        return String.valueOf(leadingDigits);
    }


    private void init(int k) {
        if (buckets.isEmpty()) {
            this.maxChars = k;
            buckets.putIfAbsent(0, 0);
            for (int i = 0; i <= k; i++) {
                int bucket = ((int) Math.pow(26, i)) * ((int) Math.pow(10, k - i));
                buckets.putIfAbsent(i + 1, bucket + buckets.get(i));
            }
        }
    }

    private int getBucket(int n) {
        for (int key = 1; key < buckets.size(); key++) {
            if (n < buckets.get(key) - 1) {
                return key - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        PatternMatching patternMatching = new PatternMatching();
        System.out.println(patternMatching.translate(172113, 5));
        System.out.println(patternMatching.translate(531112, 5));
    }
}
