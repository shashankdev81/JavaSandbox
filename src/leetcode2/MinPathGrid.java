package leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinPathGrid {

    private int minCost = Integer.MAX_VALUE;

    public static void main(String[] args) {
        MinPathGrid minPathGrid = new MinPathGrid();
        System.out.println(minPathGrid.minPathCost(new int[][]{{5, 3}, {4, 0}, {2, 1}},
            new int[][]{{9, 8}, {1, 5}, {10, 12}, {18, 6}, {2, 4}, {14, 3}}));
    }

    public int minPathCost(int[][] grid, int[][] moveCost) {
        Map<Integer, Map<Integer, Integer>> costMap = new HashMap<>();
        for (int i = 0; i < moveCost.length; i++) {
            costMap.putIfAbsent(i, new HashMap<>());
            costMap.get(i).put(0, moveCost[i][0]);
            costMap.get(i).put(1, moveCost[i][1]);
        }
        minCost(grid, 0, 0, costMap, 0);
        return minCost;
    }

    private void minCost(int[][] grid, int row, int col,
        Map<Integer, Map<Integer, Integer>> costMap, int costSoFar) {
        System.out.println("row, col, costSoFar " + row + "," + col + "," + costSoFar);
        if (row == grid.length - 1) {
            minCost = Math.min(minCost, costSoFar + grid[row][col]);
            return;

        }

        List<Integer> nonDivisibles = new ArrayList<>();
        nonDivisibles.toArray(new Integer[nonDivisibles.size()]);

        minCost(grid, row + 1, 0, costMap,
            costSoFar + costMap.get(grid[row][col]).get(0) + grid[row][col]);
        minCost(grid, row + 1, 1, costMap,
            costSoFar + costMap.get(grid[row][col]).get(1) + grid[row][col]);

    }
}
