package leetcode2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MaxTastiness {

    public static void main(String[] args) {
        MaxTastiness maxTastiness = new MaxTastiness();
        System.out.println(maxTastiness.maxTastiness(
            new int[]{136, 331, 600, 275, 809, 101, 165, 276, 590, 200, 515, 615, 584, 337, 63},
            new int[]{463, 60, 223, 856, 167, 846, 575, 409, 733, 839, 852, 8, 250, 537, 797}, 899,
            5));
    }

    public int maxTastiness(int[] price, int[] tastiness, int maxAmount, int maxCoupons) {
        int[][] fruits = new int[price.length][2];
        for (int i = 0; i < fruits.length; i++) {
            fruits[i][0] = price[i];
            fruits[i][1] = tastiness[i];
        }
        Arrays.sort(fruits, (f1, f2) -> f2[1] - f1[1]);
        // Uncomment the next line for debugging if needed
        // System.out.println(Arrays.stream(fruits).map(a -> Arrays.toString(a)).collect(Collectors.toList()));
        return purchase(fruits, 0, maxAmount, maxCoupons, 0);
    }

    private int purchase(int[][] fruits, int idx, int maxAmount, int maxCoupons, int spent) {
        if (spent > maxAmount || idx >= fruits.length || maxCoupons < 0) {
            return 0;
        }

        // Try purchasing with a coupon
        boolean isPurchasableWithCoupon = spent + fruits[idx][0] / 2 <= maxAmount && maxCoupons > 0;
        int tastiness1 = isPurchasableWithCoupon
            ? fruits[idx][1] + purchase(fruits, idx + 1, maxAmount, maxCoupons - 1, spent + fruits[idx][0] / 2)
            : 0;

        // Try purchasing without a coupon
        boolean isPurchasableWithoutCoupon = spent + fruits[idx][0] <= maxAmount;
        int tastiness2 = isPurchasableWithoutCoupon
            ? fruits[idx][1] + purchase(fruits, idx + 1, maxAmount, maxCoupons, spent + fruits[idx][0])
            : 0;

        // Skip current fruit
        int tastiness3 = purchase(fruits, idx + 1, maxAmount, maxCoupons, spent);

        return Math.max(tastiness1, Math.max(tastiness2, tastiness3));
    }
}