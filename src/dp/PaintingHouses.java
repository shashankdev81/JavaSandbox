package dp;

import java.util.HashMap;
import java.util.Map;

public class PaintingHouses {

    private static int count;

    public static void main(String[] args) {
        //RBG
        PaintingHouses paintingHouses = new PaintingHouses();
        //System.out.println(paintingHouses.minCost(new int[][]{{17, 2, 17}, {16, 16, 5}, {14, 3, 19}}));
        //System.out.println(paintingHouses.minCost(new int[][]{{17, 6, 2}}));
        System.out.println(paintingHouses.minCost(new int[][]{{17, 2, 17}, {8, 4, 10}, {6, 3, 19}, {4, 8, 12}}));
        //System.out.println(paintingHouses.minCost(new int[][]{{5, 8, 6}, {19, 14, 13}, {7, 5, 12}, {14, 15, 17}, {3, 20, 10},}));
    }

    public int minCost(int[][] costs) {
        Map<String, Integer> minCostMap = new HashMap<String, Integer>();
        int minCost = getMinCost(costs, 0, -1, minCostMap);
        return minCost;
    }

    private int getMinCost(int[][] cost, int house, int exclude, Map<String, Integer> minCostMap) {
        if (house == cost.length) {
            return 0;
        }
        String key = house + "," + exclude;
        if (minCostMap.containsKey(key)) {
            return minCostMap.get(key);
        }
        int col1, col2;
        if (exclude == 0) {
            col1 = 1;
            col2 = 2;
        } else if (exclude == 1) {
            col1 = 0;
            col2 = 2;
        } else {
            col1 = 0;
            col2 = 1;
        }

        int minCost = cost[house][col1] + getMinCost(cost, house + 1, col1, minCostMap);
        minCost = Math.min(minCost, cost[house][col2] + getMinCost(cost, house + 1, col2, minCostMap));
        if (exclude == -1) {
            minCost = Math.min(minCost, cost[house][2] + getMinCost(cost, house + 1, 2, minCostMap));
        }
        minCostMap.putIfAbsent(key, minCost);
        return minCost;
    }
}
