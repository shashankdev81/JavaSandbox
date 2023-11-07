package leetcode2;

import java.util.*;

public class MaxToppings {

    public static void main(String[] args) {
        MaxToppings maxToppings = new MaxToppings();
        System.out.println(maxToppings.closestCost(new int[]{1, 7}, new int[]{3, 4}, 10));
        System.out.println(maxToppings.closestCost(new int[]{3, 10}, new int[]{2, 5}, 9));
        System.out.println(maxToppings.closestCost(new int[]{2, 3}, new int[]{4, 5, 100}, 18));

    }

    private static int MAX_TOPPINGS = 2;

    private PriorityQueue<CostAndDiff> queue = new PriorityQueue<>();

    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        Arrays.sort(baseCosts);
        Map<Integer, Integer> toppingsMap = new HashMap<>();
        for (int i = 0; i < baseCosts.length; i++) {
            addTopping(baseCosts[i], toppingCosts, 0, target);
        }
        CostAndDiff costAndDiff = queue.poll();
        return costAndDiff.cost;
    }

    private void addTopping(int costSoFar, int[] toppingCosts, int toppingIndex, int target) {
        if (toppingIndex >= toppingCosts.length || costSoFar >= target) {
            int costDiff = Math.abs(target - costSoFar);
            queue.offer(new CostAndDiff(costSoFar, costDiff));
            return;
        }

        for (int i = 0; i <= MAX_TOPPINGS; i++) {
            addTopping(costSoFar + i * toppingCosts[toppingIndex], toppingCosts, toppingIndex + 1, target);
        }
    }

    public class CostAndDiff implements Comparable<CostAndDiff> {
        int cost;
        int diff;

        public CostAndDiff(int c, int d) {
            cost = c;
            diff = d;

        }

        public int compareTo(CostAndDiff ext) {
            if (diff == ext.diff) {
                return Integer.compare(cost, ext.cost);
            } else {
                return Integer.compare(diff, ext.diff);
            }
        }
    }

}