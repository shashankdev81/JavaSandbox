package dp;

import java.util.HashMap;
import java.util.Map;

public class RodLength {

    private Map<String, Integer> profitMap = new HashMap<>();

    private int count;

    public static void main(String[] args) {
        RodLength rodLength = new RodLength();
        System.out.println(rodLength.getMaxProfit(new int[]{1, 5, 8, 9, 10}, 5));
        System.out.println(rodLength.getMaxProfit(new int[]{1, 5, 8, 9, 10, 17, 17, 20}, 8));

    }

    private int getMaxProfit(int[] values, int n) {
        int maxProfit = Integer.MIN_VALUE;
        for (int i = n; i > 0; i--) {
            maxProfit = Math.max(maxProfit, maximise(i, n - i, values));
        }
        System.out.println("Iterations=" + count);
        return maxProfit;
    }

    private int maximise(int currSize, int remaining, int[] values) {
        count++;
        String key = currSize + "," + remaining;
        if (profitMap.containsKey(key)) {
            return profitMap.get(key);
        }
        int sum = Integer.MIN_VALUE;
        if (remaining == 0) {
            profitMap.putIfAbsent(currSize + "," + remaining, values[0]);
            return values[currSize - 1];
        }
        for (int i = 1; i <= remaining; i++) {
            sum = Math.max(sum, values[currSize - 1] + maximise(i, remaining - i, values));
        }
        profitMap.putIfAbsent(key, sum);
        return sum;

    }
}
