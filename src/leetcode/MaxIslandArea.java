package leetcode;

import java.util.HashSet;
import java.util.Set;

public class MaxIslandArea {

    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = Integer.MIN_VALUE;
        Set<String> visited = new HashSet<String>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !visited.contains(i + "," + j)) {
                    maxArea = Math.max(maxArea, area(i, j, grid, visited));
                }
            }
        }
        return maxArea == Integer.MIN_VALUE ? 0 : maxArea;
    }

    private int area(int i, int j, int[][] grid, Set<String> visited) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 0 || visited.contains(i + "," + j)) {
            return 0;
        }
        visited.add(i + "," + j);
        return 1 + area(i - 1, j, grid, visited) + area(i + 1, j, grid, visited) + area(i, j - 1, grid, visited) + area(i, j + 1, grid, visited);
    }

    public static void main(String[] args) {
        MaxIslandArea area = new MaxIslandArea();
        int[][] grid = new int[][]{{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0}, {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        System.out.println(area.maxAreaOfIsland(grid));
    }
}
