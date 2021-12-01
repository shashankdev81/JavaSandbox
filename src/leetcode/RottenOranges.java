package leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class RottenOranges {

    public int orangesRotting(int[][] grid) {
        int[] state = countFreshAndRottenOranges(grid);
        Queue<String> positions = new LinkedList<String>();

        if (state[1] == 0) {
            return 0;
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    positions.add(i + "," + j);
                }
            }
        }

        int minutes = traverse(grid, positions);
        state = countFreshAndRottenOranges(grid);
        if (state[1] != 0 && state[2] > 0) {
            return -1;
        }

        return minutes;
    }

    private int[] countFreshAndRottenOranges(int[][] grid) {
        int[] state = new int[3];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    state[0]++;
                } else if (grid[i][j] == 1) {
                    state[1]++;
                } else {
                    state[2]++;
                }
            }
        }
        return state;
    }

    private int traverse(int[][] grid, Queue<String> positions) {
        int count = 0;
        int mins = -1;
        while (!positions.isEmpty()) {
            count = positions.size();
            mins++;
            while (!positions.isEmpty() && count > 0) {
                String pos = positions.poll();
                int x = Integer.valueOf(pos.split(",")[0]);
                int y = Integer.valueOf(pos.split(",")[1]);
                grid[x][y] = 2;
                if (isVisitable(grid, x + 1, y, positions)) {
                    positions.add((x + 1) + "," + y);
                }
                if (isVisitable(grid, x - 1, y, positions)) {
                    positions.add((x - 1) + "," + y);
                }
                if (isVisitable(grid, x, y + 1, positions)) {
                    positions.add(x + "," + (y + 1));
                }
                if (isVisitable(grid, x, y - 1, positions)) {
                    positions.add(x + "," + (y - 1));
                }
                count--;
            }
        }
        return mins;
    }

    private boolean isVisitable(int[][] grid, int x, int y, Queue<String> positions) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y] == 1 && !positions.contains(x + "," + y);
    }

    public static void main(String[] args) {
        RottenOranges rottenOranges = new RottenOranges();
        System.out.println(rottenOranges.orangesRotting(new int[][]{
                {2, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        }));
        System.out.println(rottenOranges.orangesRotting(new int[][]{{2, 2}, {1, 1}, {0, 0}, {2, 0}}));
    }
}
